package es.iesnervion.albertonavarro.a10_dadoker.Tests;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import es.iesnervion.albertonavarro.a10_dadoker.R;

public class ServerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView serverStatus;
    private EditText etTesto;
    private Button btnEnviarServer;
    private boolean enviar = false;

    // default ip
    public static String SERVERIP = "10.0.2.15";

    // designate a port
    public static final int SERVERPORT = 8080;

    private Handler handler = new Handler();

    private ServerSocket serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        serverStatus = (TextView) findViewById(R.id.server_status);
        btnEnviarServer = (Button) findViewById(R.id.btnEnviarServer);
        btnEnviarServer.setOnClickListener(this);
        etTesto = (EditText) findViewById(R.id.txtMensajeServer);

        SERVERIP = getLocalIpAddress();

        Thread fst = new Thread(new ServerThread());
        fst.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEnviarServer:
                if(!enviar)
                    enviar=true;
                break;
        }
    }

    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (SERVERIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Escuchando en IP: " + SERVERIP);
                        }
                    });
                    serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                        // listen for incoming clients
                        final Socket client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("Conectado.");
                            }
                        });

                        try {
                            if(enviar && client.isConnected()) {
                                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                                // where you issue the commands
                                out.println(etTesto.getText().toString());
                                Log.d("Servidor", "C: Enviado.");
                                enviar = false;
                            }


                            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                Log.d("ServerActivity", line);
                                //serverStatus.setText(line);
                                final String m = line;

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // do whatever you want to the front end
                                        // this is where you can be creative
                                        ponerTesto(m);

                                    }
                                });
                            }
                            break;
                        } catch (Exception e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    serverStatus.setText("Oh Vaya. Se ha interrumpido la conexión.");
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("No se pudo detectar ninguna conexion a Internet.");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        serverStatus.setText("Error");
                    }
                });
                e.printStackTrace();
            }
        }
    }

    private void ponerTesto(String s){
        serverStatus.setText(s);
    }

    // gets the ip address of your phone's network
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            // make sure you close the socket upon exiting
            if(serverSocket!=null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
