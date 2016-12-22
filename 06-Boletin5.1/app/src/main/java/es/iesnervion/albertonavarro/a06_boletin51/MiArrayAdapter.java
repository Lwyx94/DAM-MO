package es.iesnervion.albertonavarro.a06_boletin51;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MiArrayAdapter extends ArrayAdapter<Equipo> {


    //Constructor
    public MiArrayAdapter(Context context, int layout, int nombre, ArrayList<Equipo> equipos) {
        super(context, layout, nombre, equipos);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (getItemViewType(position) == 0)
                row = inflater.inflate(R.layout.fila_layout, parent, false);
            else
                row = inflater.inflate(R.layout.filarara_layout, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(row);

        ImageView logo = viewHolder.getLogo();
        TextView nombre = viewHolder.getNombre();

        Equipo e = (Equipo) getItem(position);


        nombre.setText(e.getNombre());
        logo.setImageResource(e.getLogo());

        if (getItemViewType(position) == 1)
            logo.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);

        return row;
    }

    /**
     * Número de vistas que hay
     **/
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * Vista que le toca al Item
     **/
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }


    class ViewHolder {

        ImageView logo;
        TextView nombre;

        public ViewHolder(View view) {
            logo = (ImageView) view.findViewById(R.id.logo);
            nombre = (TextView) view.findViewById(R.id.txtNombre);

        }

        public ImageView getLogo() {
            return this.logo;
        }

        public TextView getNombre() {
            return this.nombre;
        }

    }

}
