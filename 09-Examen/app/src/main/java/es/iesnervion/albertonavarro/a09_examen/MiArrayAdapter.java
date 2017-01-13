package es.iesnervion.albertonavarro.a09_examen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a09_examen.models.Jugador;


public class MiArrayAdapter extends ArrayAdapter<Jugador>{
    //Constructor
    public MiArrayAdapter(Context context, int layout, int nombre, ArrayList<Jugador> jugadores) {
        super(context, layout, nombre, jugadores);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fila_layout, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(row);

        ImageView foto = viewHolder.getFoto();
        TextView nombre = viewHolder.getNombre();

        Jugador j = (Jugador) getItem(position);


        nombre.setText(j.getNombre());
        foto.setImageBitmap(j.getIdImagen());


        return row;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    class ViewHolder {

        ImageView foto;
        TextView nombre;

        public ViewHolder(View view) {
            foto = (ImageView) view.findViewById(R.id.ivCara);
            nombre = (TextView) view.findViewById(R.id.txtNombre);

        }

        public ImageView getFoto() {
            return this.foto;
        }

        public TextView getNombre() {
            return this.nombre;
        }

    }
}
