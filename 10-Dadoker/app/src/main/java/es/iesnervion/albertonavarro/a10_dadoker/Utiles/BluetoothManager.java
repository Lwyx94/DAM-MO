package es.iesnervion.albertonavarro.a10_dadoker.Utiles;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.Set;

/**
 * Created by dgarcia on 17/01/17.
 */

public class BluetoothManager {


    BluetoothAdapter mBluetoothAdapter = null;
    Boolean bluetoothSupported = true;


    public BluetoothManager() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Verifica si el dispositivo soporta Bluetooth,
     * devuelve false si el dispositivo no lo soporta; true si el dispositivo lo soporta.
     *
     * @return boolean
     */
    public boolean isBluetoothSupported() {
        if (mBluetoothAdapter == null) {
            bluetoothSupported = false;
        }
        return bluetoothSupported;
    }

    /**
     * Pide permiso para habilitar el bluetooth en caso de que esté desactivado.
     *
     * @return Intent
     */

    public Intent enableBluetooth() {
        Intent enableBtIntent = null;
        if (!mBluetoothAdapter.isEnabled()) {
            enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        }

        return enableBtIntent;
    }

    /**
     * Devuelve los dispositivos emparejados del dispositivo actual.
     *
     * @return Set<BluetoothDevice> de todos los dispositivos emparejados
     */
    public Set<BluetoothDevice> getPairedDevices() {
        Set<BluetoothDevice> listPaired = mBluetoothAdapter.getBondedDevices();
        return listPaired;
    }


    /**
     * Comienza a buscar dispositivos por Bluetooth
     */
    public void searchDevices() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter.startDiscovery();
        } else {
            mBluetoothAdapter.startDiscovery();
        }


    }

    /**
     * Cancela la búsqueda de dispositivos si está buscando.
     */
    public void cancelSearchDevices() {
        if (mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

    }


}