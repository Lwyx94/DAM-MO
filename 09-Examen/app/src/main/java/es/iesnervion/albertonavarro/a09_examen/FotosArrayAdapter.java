package es.iesnervion.albertonavarro.a09_examen;

import android.graphics.drawable.Drawable;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by anavarro on 7/12/16.
 */

public class FotosArrayAdapter extends ArrayAdapter<Drawable>

    {

        public FotosArrayAdapter(Context context, int layout, ArrayList<Drawable> bands){
        super(context, layout, bands);
    }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){
            View row = convertView;

            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.foto_layout, parent, false);

            }

            ViewHolder viewHolder = new ViewHolder(row);
            ImageView foto = viewHolder.getFoto();
            Drawable drawable = getItem(position);
            foto.setImageDrawable(drawable);



        return row;
    }

        class ViewHolder {

            ImageView foto;

            public ViewHolder(View view) {
                foto = (ImageView) view.findViewById(R.id.fotoJugador);
            }

            public ImageView getFoto() {
                return this.foto;
            }
        }


        @Override
        public View getDropDownView ( int position, View convertView, ViewGroup parent){
        return getView(position, convertView, parent);
    }}