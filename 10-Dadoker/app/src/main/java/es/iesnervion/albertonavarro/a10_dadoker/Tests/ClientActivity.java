package es.iesnervion.albertonavarro.a10_dadoker.Tests;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import es.iesnervion.albertonavarro.a10_dadoker.R;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText serverIp;
    private EditText txtMensaje;
    private Button btnEnviar;

    private Button connectPhones;
    private String textoAEnviar;

    private String serverIpAddress = "";

    private boolean connected = false;
    private boolean enviarMensaje = false;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        serverIp = (EditText) findViewById(R.id.server_ip);
        connectPhones = (Button) findViewById(R.id.btnConectar);
        connectPhones.setOnClickListener(this);

        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEnviar:
                if(txtMensaje.getText().toString().equals(""))
                    Toast.makeText(this,"Escribe algo ¿no?", Toast.LENGTH_SHORT).show();
                else {
                    enviarMensaje = true;
                    textoAEnviar = txtMensaje.getText().toString();
                    txtMensaje.setText("");
                    Toast.makeText(this,"Enviando", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnConectar:
                if (!connected) {
                    serverIpAddress = serverIp.getText().toString();
                    if (!serverIpAddress.equals("")) {
                        Thread cThread = new Thread(new ClientThread());
                        cThread.start();
                    }
                }
                break;
        }


    }

    public class ClientThread implements Runnable {

        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
                Log.d("ClientActivity", "C: Connecting...");
                Socket socket = new Socket(serverAddr, ServerActivity.SERVERPORT);
                connected = true;
                while (connected) {
                    try {
                        if(enviarMensaje) {
                            Log.d("ClientActivity", "C: Enviando.");
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            // where you issue the commands
                            out.println(textoAEnviar);
                            Log.d("ClientActivity", "C: Enviado.");
                            enviarMensaje= false;


                            //WHADAFAC
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String response = in.readLine();
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);


                        }
                    } catch (Exception e) {
                        Log.e("ClientActivity", "S: Error", e);
                    }
                }
                socket.close();
                Log.d("ClientActivity", "C: Closed.");
            } catch (Exception e) {
                Log.e("ClientActivity", "C: Error", e);
                connected = false;
            }
        }
    }
}
