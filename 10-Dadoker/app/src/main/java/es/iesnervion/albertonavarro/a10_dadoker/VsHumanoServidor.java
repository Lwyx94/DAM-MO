package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;

import es.iesnervion.albertonavarro.a10_dadoker.Models.Dado;

@Deprecated
public class VsHumanoServidor extends AppCompatActivity implements View.OnClickListener{
    private Button btnRoll;
    private Dado dado1, dado2, dado3, dado4, dado5;
    private TextView txtResHum, txtResIA;
    private Dado dadoIA1, dadoIA2, dadoIA3, dadoIA4, dadoIA5;
    private Dado[] dados = new Dado[5];
    private Dado[] dadosIA = new Dado[5];
    private ImageView cor1,cor2,cor3,cor4,cor5;
    private ImageView corIA1,corIA2,corIA3,corIA4,corIA5;
    private ImageView[] corasonesH = new ImageView[5];
    private ImageView[] corasonesIA = new ImageView[5];
    private LinearLayout tableroIA, tableroH;
    private Random ale = new Random();
    private int vidaH = 5;
    private int vidaIA = 5;
    private boolean primeraTirada = true;

    //Servidor
    private TextView serverStatus;
    public static String SERVERIP = "10.0.2.15";
    public static final int SERVERPORT = 8080;
    private Handler handler = new Handler();
    private ServerSocket serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_humano);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(this);

        txtResHum = (TextView) findViewById(R.id.txtResHum);
        txtResIA = (TextView) findViewById(R.id.txtResIA);
        tableroIA = (LinearLayout) findViewById(R.id.tableroIA);
        tableroH = (LinearLayout) findViewById(R.id.tableroH);

        //Dados del usaurio
        dado1 = (Dado) findViewById(R.id.dado1);
        dado1.setOnClickListener(this);
        dados[0] = dado1;
        dado2 = (Dado) findViewById(R.id.dado2);
        dado2.setOnClickListener(this);
        dados[1] = dado2;
        dado3 = (Dado) findViewById(R.id.dado3);
        dado3.setOnClickListener(this);
        dados[2] = dado3;
        dado4 = (Dado) findViewById(R.id.dado4);
        dado4.setOnClickListener(this);
        dados[3] = dado4;
        dado5 = (Dado) findViewById(R.id.dado5);
        dado5.setOnClickListener(this);
        dados[4] = dado5;

        //Dados del adversario
        dadoIA1 = (Dado) findViewById(R.id.dadoIA1);
        dadosIA[0] = dadoIA1;
        dadoIA2 = (Dado) findViewById(R.id.dadoIA2);
        dadosIA[1] = dadoIA2;
        dadoIA3 = (Dado) findViewById(R.id.dadoIA3);
        dadosIA[2] = dadoIA3;
        dadoIA4 = (Dado) findViewById(R.id.dadoIA4);
        dadosIA[3] = dadoIA4;
        dadoIA5 = (Dado) findViewById(R.id.dadoIA5);
        dadosIA[4] = dadoIA5;

        //Corazones del usuarioo
        cor1 = (ImageView) findViewById(R.id.cor1);
        corasonesH[0] = cor1;
        cor2 = (ImageView) findViewById(R.id.cor2);
        corasonesH[1] = cor2;
        cor3 = (ImageView) findViewById(R.id.cor3);
        corasonesH[2] = cor3;
        cor4 = (ImageView) findViewById(R.id.cor4);
        corasonesH[3] = cor4;
        cor5 = (ImageView) findViewById(R.id.cor5);
        corasonesH[4] = cor5;

        //Corazones del adversario
        corIA1 = (ImageView) findViewById(R.id.corIA1);
        corasonesIA[0] = corIA1;
        corIA2 = (ImageView) findViewById(R.id.corIA2);
        corasonesIA[1] = corIA2;
        corIA3 = (ImageView) findViewById(R.id.corIA3);
        corasonesIA[2] = corIA3;
        corIA4 = (ImageView) findViewById(R.id.corIA4);
        corasonesIA[3] = corIA4;
        corIA5 = (ImageView) findViewById(R.id.corIA5);
        corasonesIA[4] = corIA5;

        //Servidor
        serverStatus = (TextView) findViewById(R.id.server_status);
        SERVERIP = getLocalIpAddress();
        Thread fst = new Thread(new VsHumanoServidor.ServerThread());
        fst.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRoll:
                tirarDados();
                break;
            case R.id.dado1:
                if(!primeraTirada) {
                    if (dado1.getColorFilter()==null)
                        dado1.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                    else
                        dado1.clearColorFilter();
                }
                break;
            case R.id.dado2:
                if(!primeraTirada) {
                    if (dado2.getColorFilter()==null)
                        dado2.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                    else
                        dado2.clearColorFilter();
                }
                break;
            case R.id.dado3:
                if(!primeraTirada) {
                    if (dado3.getColorFilter()==null)
                        dado3.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                    else
                        dado3.clearColorFilter();
                }
                break;
            case R.id.dado4:
                if(!primeraTirada) {
                    if (dado4.getColorFilter()==null)
                        dado4.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                    else
                        dado4.clearColorFilter();
                }
                break;
            case R.id.dado5:
                if(!primeraTirada) {
                    if (dado5.getColorFilter()==null)
                        dado5.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                    else
                        dado5.clearColorFilter();
                }
                break;
        }
    }

    private void tirarDados() {
        btnRoll.setTextColor(Color.YELLOW);

        if(primeraTirada) {
            tableroH.setBackgroundColor(Color.LTGRAY);
            tableroIA.setBackgroundColor(Color.LTGRAY);
        }

        //Usuario
       for (Dado dado : dados) {
           if (primeraTirada || dado.getColorFilter() == null)
               dado.setValor(ale.nextInt(6) + 1);
           if (dado.getColorFilter() != null)
               dado.clearColorFilter();
       }

        //IA
        for(Dado dado: dadosIA)
            dado.setValor(ale.nextInt(6)+1);


        for(Dado dado : dados){
            switch (dado.getValor()){
                case 1:
                    dado.setImageResource(R.drawable.dado1);
                    break;
                case 2:
                    dado.setImageResource(R.drawable.dado2);
                    break;
                case 3:
                    dado.setImageResource(R.drawable.dado3);
                    break;
                case 4:
                    dado.setImageResource(R.drawable.dado4);
                    break;
                case 5:
                    dado.setImageResource(R.drawable.dado5);
                    break;
                case 6:
                    dado.setImageResource(R.drawable.dado6);
                    break;
            }
        }

        for(Dado dado : dadosIA){
            switch (dado.getValor()){
                case 1:
                    dado.setImageResource(R.drawable.dado1);
                    break;
                case 2:
                    dado.setImageResource(R.drawable.dado2);
                    break;
                case 3:
                    dado.setImageResource(R.drawable.dado3);
                    break;
                case 4:
                    dado.setImageResource(R.drawable.dado4);
                    break;
                case 5:
                    dado.setImageResource(R.drawable.dado5);
                    break;
                case 6:
                    dado.setImageResource(R.drawable.dado6);
                    break;
            }
        }

        mostrarResultado();
        actualizarVida();

        if(vidaH==0 || vidaIA==0){
            String titulo = (vidaH==0)?"¡DERROTA!": "¡VICTORIA!";
            String mensaje = (vidaH==0)?"¡Has perdido!": "¡Has ganado!";
            new AlertDialog.Builder(this)
                    .setTitle(titulo)
                    .setMessage(mensaje)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }


        primeraTirada=!primeraTirada;
        btnRoll.setTextColor(Color.GREEN);
    }

    private void actualizarVida() {
        int cont = 0;
        for(ImageView cor: corasonesH){
            if(cont < vidaH)
                cor.setImageResource(R.drawable.corasonrojo);
            else
                cor.setImageResource(R.drawable.corasonnegro);
            cont++;
        }

        cont = 0;

        for(ImageView cor: corasonesIA){
            if(cont < vidaIA)
                cor.setImageResource(R.drawable.corasonrojo);
            else
                cor.setImageResource(R.drawable.corasonnegro);
            cont++;
        }

    }

    private void mostrarResultado() {
        int[] manoH = obtenerMano(dados);
        int[] manoIA = obtenerMano(dadosIA);

        txtResHum.setText(obtenerResultado(manoH));
        txtResIA.setText(obtenerResultado(manoIA));

        String res = "resultado";
        switch (humanoGanador(manoH, manoIA)) {
            case 1:
                res="¡HAS GANADO!";
                if(!primeraTirada) {
                    tableroH.setBackgroundColor(Color.GREEN);
                    tableroIA.setBackgroundColor(Color.RED);
                    vidaIA--;
                }
                break;
            case 2:
                res="¡HAS PERDIDO! ¡JUAS!";
                if(!primeraTirada) {
                    tableroH.setBackgroundColor(Color.RED);
                    tableroIA.setBackgroundColor(Color.GREEN);
                    vidaH--;
                }
                break;
            case 3:
                res="¡EMPATE! ¡AW YEAH!";
                break;
        }
       /* if(!primeraTirada)
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();*/

    }



    private String obtenerResultado(int[] mano) {
        String res = "resultado";
        switch (mano[0]){
            case 0:
               res = "Vaya mierda...";
                break;
            case 1:
                res = "Pareja de "+mano[1];
                break;
            case 2:
                res = "Doble Pareja";
                break;
            case 3:
                //res = "Trío ( ͡° ͜ʖ ͡°)";
                res = "Trío de "+mano[1];
                break;
            case 4:
                res = "Escalera (1-5)";
                break;
            case 5:
                res = "Escalera (2-6)";
                break;
            case 6:
                res = "Full HD";
                break;
            case 7:
                res = "¡Póker de "+mano[1]+"!";
                break;
            case 8:
                res = "¡¡¡REPÓKER DE "+mano[1]+"!!!";
                break;
        }
        return res;

    }

    private int humanoGanador(int[] manoH, int[] manoIA) {
        int res = 3;
        if(manoH[0]>manoIA[0])
            res=1;
        else if(manoH[0]<manoIA[0])
            res=2;
        else {
            if(manoH[1]>manoIA[1]||(manoH[1]==1&&manoH[1]!=manoIA[1]))
                res=1;
            else if(manoH[1]<manoIA[1]||(manoIA[1]==1&&manoIA[1]!=manoH[1]))
                res=2;
            else {
                if(manoH[2]>manoIA[2])
                    res=1;
                else if(manoH[2]<manoIA[2])
                    res=2;
            }
        }

        return res;
    }

    /**
     *
     * @return 0-Nada
     *         1-Pareja
     *         2-DoblePareja
     *         3-Trío
     *         4-Escalera(1-5)
     *         5-Escalera(2-6)
     *         6-Full
     *         7-Poker
     *         8-RePoker
     */
    private int[] obtenerMano(Dado[] array){
        int[] res = new int[3];
        int numRep1=0, numRep2=0;
        int cuent1=1, cuent2=1;
        int[] numeros = volcarNumeros(array);
        Arrays.sort(numeros);

        if(esEscalera(numeros)){
            if(numeros[0]==1)
                res[0]=4;
            else
                res[0]=5;
        }else{
            for(int i = 1; i < numeros.length; i++) {
                if(numeros[i] == numeros[i - 1]) {
                    if(numRep1==0) {
                        numRep1=numeros[i];
                        cuent1++;
                    }
                    else if(numRep1==numeros[i]){
                        cuent1++;
                    }else {
                        numRep2 = numeros[i];
                        cuent2++;
                    }
                }
            }
            if(numRep1!=0&&numRep2!=0) {//Dos combinaciones
                if (cuent1==3&&cuent2==2){//Full
                    res[0]=6;res[1]=numRep1;res[2]=numRep2;
                }else if(cuent1==2&&cuent2==3){//Full
                    res[0]=6;res[1]=numRep2;res[2]=numRep1;
                }else if(numRep1>numRep2){//Doble Pareja
                    res[0]=2;res[1]=numRep1;res[2]=numRep2;}
                else{//Pareja
                    res[0]=2;res[1]=numRep2;res[2]=numRep1;}

            }else if(numRep1!=0){
                switch (cuent1){
                    case 2:
                        res[0]=1;res[1]=numRep1;
                        break;
                    case 3:
                        res[0]=3;res[1]=numRep1;
                        break;
                    case 4:
                        res[0]=7;res[1]=numRep1;
                        break;
                    case 5:
                        res[0]=8;res[1]=numRep1;
                        break;
                }
            }
            else
                res[0]=0;
        }
        return res;
    }



    private int[] volcarNumeros(Dado[] array){
        int[] res = new int[5];
        for (int i=0; i<array.length; i++){
            res[i] = array[i].getValor();
        }
        return res;
    }

    private boolean esEscalera(int[] numeros) {
        boolean res = true;
        for(int i = 0; i< numeros.length-1 && res; i++){
            if(numeros[i]+1 != numeros[i+1])
                res = false;
        }
        return res;
    }


    /**COSAS DEL SERVIDOR**/
    public class ServerThread implements Runnable {

        public void run() {
            try {
                if (SERVERIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("ESPERANDO CONEXION");
                        }
                    });
                    serverSocket = new ServerSocket(SERVERPORT);
                    while (true) {
                        // listen for incoming clients
                        Socket client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("Connected.");
                            }
                        });

                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                Log.d("ServerActivity", line);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // do whatever you want to the front end
                                        // this is where you can be creative
                                    }
                                });
                            }
                            break;
                        } catch (Exception e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Couldn't detect internet connection.");
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
