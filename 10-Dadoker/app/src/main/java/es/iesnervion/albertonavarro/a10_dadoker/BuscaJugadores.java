package es.iesnervion.albertonavarro.a10_dadoker;

import android.bluetooth.BluetoothClass;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class BuscaJugadores extends AppCompatActivity implements View.OnClickListener {

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    Recibidor mReceiver;
    IntentFilter mIntentFilter;
    WifiP2pManager.PeerListListener peerListListener;

    private TextView txtInfo;
    private TextView txtLista;
    private ListView lista;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> direcciones = new ArrayList<>();
    private Button btnBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_jugadores);

        //TEST
        direcciones.add("Dispositivo Uno");
        direcciones.add("Dispositivo TWo");
        direcciones.add("Dispositivo Trois");
        direcciones.add("Dispositivo Dummy");

        txtInfo= (TextView) findViewById(R.id.txtInformacion);
        txtLista= (TextView) findViewById(R.id.txtLista);
        btnBuscar= (Button) findViewById(R.id.btnBuscar);
        lista= (ListView) findViewById(R.id.list);
        btnBuscar.setOnClickListener(this);


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

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                String  itemValue    = (String) lista.getItemAtPosition(position);
                mReceiver.conectarse(itemValue);

            }

        });


    }

    public void setTexto(String s){
        txtInfo.setText(s);
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
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


}
