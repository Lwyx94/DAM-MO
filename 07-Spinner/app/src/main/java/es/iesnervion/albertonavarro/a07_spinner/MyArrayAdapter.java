package es.iesnervion.albertonavarro.a07_spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Band> {

    public MyArrayAdapter(Context context, int layout, ArrayList<Band> bands) {
        super(context, layout, bands);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout, parent, false);

        }

        ViewHolder viewHolder = new ViewHolder(row);

        ImageView logo = viewHolder.getLogo();
        TextView nombre = viewHolder.getNombre();

        Band b = getItem(position);


        nombre.setText(b.getName());

        logo.setImageResource(b.getLogo());

        return row;
    }

    class ViewHolder {

        ImageView logo;
        TextView nombre;

        public ViewHolder(View view) {
            logo = (ImageView) view.findViewById(R.id.logo);
            nombre = (TextView) view.findViewById(R.id.txtName);

        }

        public ImageView getLogo() {
            return this.logo;
        }

        public TextView getNombre() {

            return this.nombre;
        }
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
