package es.iesnervion.albertonavarro.a10_dadoker.Utiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends AsyncTask {

    private Context context;
    private TextView statusText;

    public Servidor(Context context, View statusText) {
        this.context = context;
        this.statusText = (TextView) statusText;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {

            /**
             * Create a server socket and wait for client connections. This
             * call blocks until a connection is accepted from a client
             */
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();
            serverSocket.close();

        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    /**
     * Start activity that can handle the JPEG image
     */
    protected void onPostExecute(Long result) {
        if (result != null) {
            statusText.setText("File copied - " + result);
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + result), "image/*");
            context.startActivity(intent);
        }
    }
}

