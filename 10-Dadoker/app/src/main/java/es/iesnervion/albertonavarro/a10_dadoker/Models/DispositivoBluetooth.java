package es.iesnervion.albertonavarro.a10_dadoker.Models;


import android.bluetooth.BluetoothDevice;

public class DispositivoBluetooth {

    private BluetoothDevice dispositivo;

    public BluetoothDevice getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(BluetoothDevice dispositivo) {
        this.dispositivo = dispositivo;
    }

    @Override
    public String toString(){
        return dispositivo.getName();
    }
}
