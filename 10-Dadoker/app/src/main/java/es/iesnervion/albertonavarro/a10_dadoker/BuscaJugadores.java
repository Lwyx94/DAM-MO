package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a10_dadoker.Tests.ServerActivity;
import es.iesnervion.albertonavarro.a10_dadoker.Utiles.Recibidor;

public class BuscaJugadores extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    Recibidor mReceiver;
    IntentFilter mIntentFilter;

    private TextView txtInfo;
    private TextView txtLista;
    private ListView lista;
    private ImageView imagenReloj;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> direcciones = new ArrayList<>();
    private Animation animReloj;
    private Button btnBuscar;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_jugadores);

        txtInfo= (TextView) findViewById(R.id.txtInformacion);
        txtLista= (TextView) findViewById(R.id.txtLista);
        btnBuscar= (Button) findViewById(R.id.btnBuscar);
        lista= (ListView) findViewById(R.id.list);
        imagenReloj = (ImageView) findViewById(R.id.imagenReloj);
        btnBuscar.setOnClickListener(this);


        animReloj = AnimationUtils.loadAnimation(this, R.anim.anim_dado);
        animReloj.setDuration(9000);

        mManager = (WifiP2pManager) getSystemService(this.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new Recibidor(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        //Lista
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, direcciones);

        lista.setAdapter(adapter);
        new Intent(this, ServerActivity.class);

        lista.setOnItemClickListener(this);

        //region **Handler
        //Maneja los mensajes recibidos
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //Tratamiento de datos recibidos
            }
        };
        //endregion
    }

    public void setTexto(String s){
        txtInfo.setText(s);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBuscar:
                mReceiver.desubrirParejas();
                btnBuscar.setEnabled(false);
                //region Animación del reloj
                imagenReloj.setVisibility(View.VISIBLE);
                imagenReloj.startAnimation(animReloj);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        btnBuscar.setEnabled(true);
                        imagenReloj.clearAnimation();
                        imagenReloj.setVisibility(View.INVISIBLE);
                    }
                }, 10000);
                //endregion
                break;
        }
    }

    public void actualizarLista(ArrayList<String> alLista) {
        direcciones.clear();
        direcciones.addAll(alLista);
        //adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, android.R.id.text1, direcciones);
        lista.setAdapter(adapter);

        /*String s = "Lista de dispositivos conectados:\n";
        for(String nombre: lista)
            s += nombre+"\n";
        txtLista.setText(s);*/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

        String itemValue = (String) lista.getItemAtPosition(position);
        if(mReceiver.conectarse(itemValue)){
            //Ejecutar codigo



            //startActivity(new Intent(this, VsHumano.class));
        }
    }
}
