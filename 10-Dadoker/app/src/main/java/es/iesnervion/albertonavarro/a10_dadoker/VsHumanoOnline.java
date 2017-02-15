package es.iesnervion.albertonavarro.a10_dadoker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private String dadosAdversario;
    private boolean esServidor = true;
    boolean losDosHanTirado = false;

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


        //Maneja los mensajes recibidos
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                byte[] write = (byte[]) msg.obj;
                String weapon = new String(write, 0, msg.arg1);
                dadosAdversario = weapon.trim();
                losDosHanTirado = true;
            }
        };
        //endregion


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            esServidor = false;
            connectDevice(extras.getString("bluetooth"));
        } else {
            esServidor = true;
            AcceptThread acceptThread = new AcceptThread();
            acceptThread.start();
        }


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnRoll:
                tirarDados();
                break;
        }
    }


    public void tirarDados() {

        btnRoll.setEnabled(false);
        btnRoll.setText("TIRANDO");

        String dadosSeleccionados = obtenerDadosSeleccionados();
        byte[] send = dadosSeleccionados.getBytes();
        connectedThread.write(send);
        btnRoll.setText("Esperando adversario...");

        while (!losDosHanTirado) {
        }

        char[] arrayString = dadosAdversario.toCharArray();
        for (int i=0; i<arrayString.length; i++) {
            if (arrayString[i] == '1')
                dados[i].setColorFilter(colorSel, PorterDuff.Mode.MULTIPLY);
        }

        boolean noMovimientos = true;

        tirando = true;

        btnRoll.setTextColor(Color.GRAY);

        if (primeraTirada) {
            tableroH.setBackgroundColor(Color.LTGRAY);
            tableroIA.setBackgroundColor(Color.LTGRAY);
        }

        if(esServidor) {
            //Usuario
            for (Dado dado : dados) {
                if (primeraTirada || dado.getColorFilter() != null) {
                    noMovimientos = false;
                    dado.startAnimation(animDado);
                    dado.setValor(ale.nextInt(6) + 2);
                    dado.clearColorFilter();
                }
            }

            //Adversario
            for (Dado dado : dadosIA) {
                if (primeraTirada || dado.getColorFilter() != null) {
                    noMovimientos = false;
                    dado.startAnimation(animDado);
                    dado.setValor(ale.nextInt(6) + 2);
                    dado.clearColorFilter();
                }
            }
            if (noMovimientos)
                enAnimacionFinal();

            String tempDadosUsuario = obtenerDadosSeleccionados();
            connectedThread.write(tempDadosUsuario.getBytes());
            String tempDadosAdversario = obtenerDadosSeleccionados();
            connectedThread.write(tempDadosAdversario.getBytes());
            losDosHanTirado=false;
        }
    }

    /**0 - No seleccionado
     * 1 - Seleccionado **/
    private String obtenerDadosSeleccionados() {
        String res = "";
        for (Dado dado : dados) {
            if (dado.getColorFilter() != null)
                res+="1";
            else
                res+="0";
        }
        return  res;
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
        btnRoll.setText(msg);
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
            setStateMessage("Conectado");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;


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
}
