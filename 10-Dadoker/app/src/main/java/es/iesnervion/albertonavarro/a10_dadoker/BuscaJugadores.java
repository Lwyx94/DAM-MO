package es.iesnervion.albertonavarro.a10_dadoker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import es.iesnervion.albertonavarro.a10_dadoker.Utiles.BluetoothManager;
import es.iesnervion.albertonavarro.a10_dadoker.Utiles.Recibidor;

public class BuscaJugadores extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView txtInfo;
    private TextView txtLista;
    private ListView lista;
    private ImageView imagenReloj;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dispositivos = new ArrayList<>();
    private Animation animReloj;
    private Button btnBuscar;

    private BluetoothManager btManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_jugadores);
        //region Inicialización de cosas
        txtInfo = (TextView) findViewById(R.id.txtInformacion);
        txtLista = (TextView) findViewById(R.id.txtLista);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        lista = (ListView) findViewById(R.id.list);
        imagenReloj = (ImageView) findViewById(R.id.imagenReloj);
        btnBuscar.setOnClickListener(this);
        //endregion
        //region Animación del reloj
        animReloj = AnimationUtils.loadAnimation(this, R.anim.anim_dado);
        animReloj.setDuration(9000);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        //endregion
        //region Lista
        lista.setAdapter(adapter);
        new Intent(this, ServerActivity.class);
        lista.setOnItemClickListener(this);
        //endregion

        btManager = new BluetoothManager();

        //IntentFilters
        // Register for broadcasts cuando se descubre un nuevo dispositivo
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        // Register for broadcasts cuando comienza la búsqueda de bluetooth
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mReceiver, filter);

        // Register for broadcasts cuando finaliza la búsqueda de bluetooth
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);

    }

    public void setTexto(String s) {
        txtInfo.setText(s);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBuscar:
                btManager.searchDevices();

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
        dispositivos.clear();
        dispositivos.addAll(alLista);
        //adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        lista.setAdapter(adapter);
    }

    public void agregarDispositivo(String s) {
        dispositivos.add(s);
        //adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        lista.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String itemValue = (String) lista.getItemAtPosition(position);
        //startActivity(new Intent(this, VsHumano.class));
    }

    //Recibidor
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        //Se mete cuando ocurre un evento de búsqueda
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //Buscando dispositivos

            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //Dispositivo encontrado
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                agregarDispositivo(device.getName());
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Búsqueda finalizada

            }

        }
    };

}
