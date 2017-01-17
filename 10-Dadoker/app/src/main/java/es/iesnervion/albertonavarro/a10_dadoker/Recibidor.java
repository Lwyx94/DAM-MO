package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;

public class Recibidor extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BuscaJugadores mActivity;

    public Recibidor(WifiP2pManager manager, WifiP2pManager.Channel channel,
                     BuscaJugadores activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mActivity.setTexto("HAY WIFI");
            } else {
                mActivity.setTexto("NO HAY WIFI");
            }
        }

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                mManager.requestPeers(mChannel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                        ArrayList<String> s = new ArrayList<>();

                        for(WifiP2pDevice device:wifiP2pDeviceList.getDeviceList()){
                            s.add(device.deviceAddress);
                        }
                        mActivity.actualizarLista(s);
                    }
                });
            }
        }
    }

    public void desubrirParejas()
    {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.e("WOWOW", "WEWEWE");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.e("WIWIWI", "BOOOO");
            }
        });
    }

    public void conectarse(String direccion){
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = direccion;
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                //success logic
                Log.v("Jejeu", "Conexión exitosa!");
            }

            @Override
            public void onFailure(int reason) {
                //failure logic
                Log.v("Vaia", "Conexión fracasada");
            }
        });
    }

}
