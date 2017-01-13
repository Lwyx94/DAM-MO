package es.iesnervion.albertonavarro.a09_examen;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a09_examen.models.Jugador;

//TODO: Implementar Singleton
public class DatosGlobales extends Application implements Parcelable {

    private static final DatosGlobales INSTANCE = new DatosGlobales();
    private ArrayList<Jugador> jugadores;


    public static DatosGlobales getInstance() {
        return INSTANCE;
    }



    private DatosGlobales(){
        //jugadores=getJugadoresTest();//Borrar
        jugadores = new ArrayList<>();

    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }


    //Añade un jugador a la lista
    public void anadirJugador(Jugador j){
        jugadores.add(j);
    }

    //Devuelve un ArrayList con las fotos usadas
    public ArrayList<Bitmap> getFotosUsadas(){
        ArrayList<Bitmap> res = new ArrayList<Bitmap>();
        for(Jugador j: jugadores)
            res.add(j.getIdImagen());
        return res;
    }

    //Devuelve true si la posición esta repetida
    public boolean getPosicionRepetida(Posiciones p){
        boolean res = false;
        int contador = 0;
        for(Jugador j : jugadores)
            if(j.getPosicion()==p)
                contador++;
        if(contador>=2)
            res=true;
        return res;

    }

    //Crea varios Jugadores de prueba
    /*public static ArrayList<Jugador> getJugadoresTest(){
        ArrayList<Jugador> res = new ArrayList<>();
        res.add(new Jugador("Pepe", R.drawable.jugador00, 170, 150, Posiciones.Alero));
        res.add(new Jugador("Antonio", R.drawable.jugador01, 190, 160, Posiciones.Base));
        res.add(new Jugador("Fernando", R.drawable.jugador02, 190, 160, Posiciones.Base));
        res.add(new Jugador("Juan", R.drawable.jugador03, 180, 170, Posiciones.Escolta));
        res.add(new Jugador("Pepito", R.drawable.jugador04, 180, 170, Posiciones.Pívot));
        res.add(new Jugador("Manolo", R.drawable.jugador05, 180, 170, Posiciones.Alapívot));
        return res;
    }*/



    //Añadir fotos
    ArrayList<Bitmap> fotosDisponibles = new ArrayList<>();

    //Añadir del 00 al 09






    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.jugadores);
    }

    protected DatosGlobales(Parcel in) {
        this.jugadores = new ArrayList<Jugador>();
        in.readList(this.jugadores, Jugador.class.getClassLoader());
    }

    public static final Creator<DatosGlobales> CREATOR = new Creator<DatosGlobales>() {
        @Override
        public DatosGlobales createFromParcel(Parcel source) {
            return new DatosGlobales(source);
        }

        @Override
        public DatosGlobales[] newArray(int size) {
            return new DatosGlobales[size];
        }
    };

    public void setFotosDisponibles(ArrayList<Bitmap> fotos) {
        fotosDisponibles=fotos;
    }
}
