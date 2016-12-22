package es.iesnervion.albertonavarro.a09_examen.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import es.iesnervion.albertonavarro.a09_examen.Posiciones;

public class Jugador implements Parcelable {

    //Atributos
    private String nombre;
    private Bitmap idImagen;
    private int altura;
    private int peso;
    private Posiciones posicion;

    public Jugador(String nombre, Bitmap idImagen, int altura, int peso, Posiciones posicion) {
        this.nombre = nombre;
        this.idImagen = idImagen;
        this.altura = altura;
        this.peso = peso;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Bitmap idImagen) {
        this.idImagen = idImagen;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Posiciones getPosicion() {
        return posicion;
    }

    public void setPosicion(Posiciones posicion) {
        this.posicion = posicion;
    }


    //Parcelable


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeParcelable(this.idImagen, flags);
        dest.writeInt(this.altura);
        dest.writeInt(this.peso);
        dest.writeInt(this.posicion == null ? -1 : this.posicion.ordinal());
    }

    protected Jugador(Parcel in) {
        this.nombre = in.readString();
        this.idImagen = in.readParcelable(Bitmap.class.getClassLoader());
        this.altura = in.readInt();
        this.peso = in.readInt();
        int tmpPosicion = in.readInt();
        this.posicion = tmpPosicion == -1 ? null : Posiciones.values()[tmpPosicion];
    }

    public static final Creator<Jugador> CREATOR = new Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel source) {
            return new Jugador(source);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };
}
