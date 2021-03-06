package es.iesnervion.albertonavarro.a10_dadoker;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a10_dadoker.Models.DispositivoBluetooth;
import es.iesnervion.albertonavarro.a10_dadoker.Utiles.BluetoothManager;


public class BuscaJugadores extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView txtInfo;
    private TextView txtLista;
    private ListView lista;
    private ImageView imagenReloj;
    private ArrayAdapter<DispositivoBluetooth> adapter;
    private ArrayList<DispositivoBluetooth> dispositivos;
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
        dispositivos = new ArrayList<>();
        btManager = new BluetoothManager();
        //endregion
        //region Animación del reloj
        animReloj = AnimationUtils.loadAnimation(this, R.anim.anim_dado);
        animReloj.setDuration(9000);

        adapter = new ArrayAdapter<DispositivoBluetooth>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        //endregion
        //region Lista
        lista.setAdapter(adapter);

        limpiarLista();
        lista.setOnItemClickListener(this);
        //endregion



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


        setTexto("Tú eres "+android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_name"));

    }

    public void setTexto(String s) {
        txtInfo.setText(s);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBuscar:
                int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                btManager.searchDevices();

                if(!BluetoothAdapter.getDefaultAdapter().isEnabled())
                    Toast.makeText(this, "Necesitas activar el Bluetooth", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void limpiarLista() {
        
        dispositivos.clear();
        if(btManager!=null)
            dispositivos.addAll(btManager.obtenerDireccionesDeDispositivosEmparejados());
        else
            Toast.makeText(this, "btManager es null", Toast.LENGTH_SHORT).show();
        //adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        lista.setAdapter(adapter);
    }

    public void agregarDispositivo(DispositivoBluetooth s) {
        dispositivos.add(s);
        //adapter.notifyDataSetChanged();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dispositivos);
        lista.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        btManager.cancelSearchDevices();
        if(!(lista.getItemAtPosition(position)).equals("")) {
            // Create the result Intent and include the MAC address
            DispositivoBluetooth dp =(DispositivoBluetooth) lista.getItemAtPosition(position);
            Intent intent = new Intent(this, VsHumanoOnline.class);
            intent.putExtra("bluetooth", dp.getDispositivo().getAddress());
            startActivity(intent);
        }
    }

    //Recibidor
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        //Se mete cuando ocurre un evento de búsqueda
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //Buscando dispositivos
                Log.d("HEY","Buscando.");
                btnBuscar.setEnabled(false);
                imagenReloj.setVisibility(View.VISIBLE);
                imagenReloj.startAnimation(animReloj);
            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //Dispositivo encontrado
                Log.d("HEY","Dispositivo encontrado.");
                DispositivoBluetooth device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                agregarDispositivo(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //Búsqueda finalizada
                Log.d("HEY","Búsqueda terminada.");
                btnBuscar.setEnabled(true);
                imagenReloj.clearAnimation();
                imagenReloj.setVisibility(View.INVISIBLE);
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mReceiver);
        }catch (RuntimeException e){}
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mReceiver);
        }catch (RuntimeException e){}
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mReceiver);
        }catch (RuntimeException e){}
    }

}
