package es.iesnervion.albertonavarro.a10_dadoker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import es.iesnervion.albertonavarro.a10_dadoker.Models.Dado;
import es.iesnervion.albertonavarro.a10_dadoker.Utiles.BluetoothManager;


public class VsHumanoOnline extends VsHumanoLocal {

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothManager btManager = new BluetoothManager();

    String string_uuid = "b86743e3-4905-4f36-9cc4-dce7a77c8a1f";
    UUID uuid = UUID.fromString(string_uuid);
    //ServerSocket
    private BluetoothServerSocket mmServerSocket;

    ConnectedThread connectedThread;
    Handler handler;
    private boolean conectado = false;
    boolean adversarioTirado = false;
    boolean usuarioTirado = false;
    String dadosPAraEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region ¡Cambio de interfaz!
        btnTirarUsuario.setVisibility(View.GONE);
        btnTirarAdversario.setVisibility(View.GONE);
        btnRoll.setVisibility(View.VISIBLE);
        btnRoll.setEnabled(false);
        dadoIA1.setOnClickListener(null);
        dadoIA2.setOnClickListener(null);
        dadoIA3.setOnClickListener(null);
        dadoIA4.setOnClickListener(null);
        dadoIA5.setOnClickListener(null);
        corIA1.setScaleY(1);
        corIA2.setScaleY(1);
        corIA3.setScaleY(1);
        corIA4.setScaleY(1);
        corIA5.setScaleY(1);
        txtResIA.setRotation(0);
        //endregion

        adversarioTirado = false;
        usuarioTirado =  false;


        //Maneja los mensajes recibidos
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Toast.makeText(VsHumanoOnline.this, "Ha llegado un mensaje", Toast.LENGTH_SHORT).show();
                //Log.d("HEYEY","Ha llegado un mensaje");
                byte[] write = (byte[]) msg.obj;
                String res = new String(write, 0, msg.arg1);
                String mensaje = res.trim();
                //setStateMessage(dadosAdversario);
                recibirMensaje(mensaje);
            }
        };
        //endregion


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            connectDevice(extras.getString("bluetooth"));
        } else {
            AcceptThread acceptThread = new AcceptThread();
            acceptThread.start();
        }


    }

    private void recibirMensaje(String mensaje) {
        adversarioTirado = true;
        tirarDados();
        if(tirando&&!mensaje.contains("A"))
            acabarTirada(mensaje);
    }

    private void acabarTirada(String mensaje) {
        //Log.d("JEUAJ", mensaje);
        char[] array = mensaje.toCharArray();
        for(int i = 0; i<5; i++){
            dadosIA[i].setValor(Integer.parseInt(""+array[i]));
        }
        super.enAnimacionFinal();
        tirando = false;
        adversarioTirado = false;
        usuarioTirado = false;
        btnRoll.setText("Tirar");
        //btnRoll.setTextColor(Color.GREEN);
        btnRoll.setEnabled(true);
    }

    @Override
    public void enAnimacionFinal(){
        connectedThread.write(dadosPAraEnviar.getBytes());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnRoll:
                btnRoll.setEnabled(false);
                usuarioTirado=true;
                connectedThread.write("A".getBytes());
                tirarDados();
                break;
        }
    }


    public void tirarDados() {
        if(usuarioTirado&&adversarioTirado) {
            usuarioTirado = false;
            adversarioTirado = false;
            boolean noMovimientos = true;
            tirando = true;
            btnRoll.setText("TIRANDO");

            //btnRoll.setTextColor(Color.GRAY);

            if (primeraTirada) {
                tableroH.setBackgroundColor(Color.LTGRAY);
                tableroIA.setBackgroundColor(Color.LTGRAY);
            }

            dadosPAraEnviar = "";

            //Usuario
            for (Dado dado : dados) {
                if (primeraTirada || dado.getColorFilter() != null) {
                    noMovimientos = false;
                    dado.startAnimation(animDado);
                    dado.setValor(ale.nextInt(6) + 2);
                    dado.clearColorFilter();
                }
                dadosPAraEnviar += "" + dado.getValor();
            }

            /*for (Dado dado : dadosIA)
                dado.startAnimation(animDado);*/



            if(noMovimientos)
                enAnimacionFinal();


        }

    }

    @Override
    public void finalizarRonda() {
        if(vidaH==0 || vidaIA==0){
            mediaPlayer.stop();
            String titulo = (vidaH==0)?"¡DERROTA!": "¡VICTORIA!";
            String mensaje = (vidaH==0)?"¡Has perdido!": "¡Has ganado!";
            if(vidaH==0)
                soundPool.play(idMusicLose, 1, 1, 1, 0, 1);
            else
                soundPool.play(idMusicVictory,0.5f, 0.5f, 1, 0, 1);


            new AlertDialog.Builder(this)
                    .setTitle(titulo)
                    .setMessage(mensaje)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }else {
            btnRoll.setText("TIRAR");
            //btnRoll.setTextColor(Color.GREEN);
            btnRoll.setEnabled(true);
            primeraTirada = !primeraTirada;
            tirando = false;
        }
    }



    /**Movidas de servidores y tal..**/


    /**
     * Conecta con el dispositivo remoto
     *
     * @param mac String Dirección MAC del dispositivo remoto
     */
    public void connectDevice(String mac) {

        BluetoothDevice deviceRemote = mBluetoothAdapter.getRemoteDevice(mac);
        ConnectThread connectThread = new ConnectThread(deviceRemote);
        connectThread.start();
    }

    /**
     * Realiza la conexión entre los dos dispositivos emparejados
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {

        setStateMessage(device.getAddress());
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

    }

    /**
     * Escribe el estado de la conexión
     *
     * @param msg - String; Mensaje que se quiere mostrar
     */
    public void setStateMessage(String msg) {
        final String tmpMsg = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRoll.setText(tmpMsg);

            }
        });
        //Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
    }

    public void setEnableButton(boolean bol) {
        final boolean tmpMsg = bol;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRoll.setEnabled(tmpMsg);

            }
        });
        //Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
    }



    //region Clase_AcceptThread

    //TODO: Pasar esta clase a BuscarJugadores

    /**
     * Clase Conexión como Servidor
     * Se mantiene escuchando hasta que otro dispositivo con el mismo UUID le pida conexión
     */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            setStateMessage("Esperando adversario");
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(string_uuid, uuid);
            } catch (IOException e) {
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            boolean listening = true;
            // Keep listening until exception occurs or a socket is returned
            while (listening) {
                try {
                    socket = mmServerSocket.accept();
                    // If a connection was accepted
                    if (socket != null) {
                        // Do work to manage the connection (in a separate thread)
                        listening = false;
                        mmServerSocket.close();

                    }
                } catch (IOException e) {
                    return;
                }
            }

            if (!listening) {
                connected(socket, socket.getRemoteDevice());


            }
        }

        /**
         * Will cancel the listening socket, and cause the thread to finish
         */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
            }
        }
    }

    //endregion

    //region Clase_ConnectThread

    /**
     * Clase conexión como Cliente
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            setStateMessage("Conectando con servidor");
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            connected(mmSocket, mmDevice);


        }

        /**
         * Will cancel an in-progress connection, and close the socket
         */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }
    //endregion

    //region Clase_ConnectedThread

    /**
     * Clase que maneja dos dispositivos conectados
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            //setStateMessage("Conectado");
            setStateMessage("Tirar");
            setEnableButton(true);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            conectado = true;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[10];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    //Obligatorio mandar buffer ( sino me petaba )
                    handler.obtainMessage(1, bytes, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    setStateMessage("Desconectado");
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                //Log.d("OIOIOI","Escribiendo el mensaje");
                //Toast.makeText(VsHumanoOnline.this, "Escribiendo el mensaje", Toast.LENGTH_SHORT).show();
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    //endregion


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(conectado)
            connectedThread.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(conectado)
            connectedThread.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(conectado)
            connectedThread.cancel();
    }
}
