package es.iesnervion.albertonavarro.a08_fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentArticulo extends Fragment{
    final static String ARG_POSITION = "position";
    int posicion = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Si se restaura la actividad, cargar los argumentos
        if (savedInstanceState != null) {
            posicion = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflar los layouts de este fragment
        return inflater.inflate(R.layout.layout_texto, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Comprobar si se le han pasado argumentos al fragment
        Bundle args = getArguments();
        if (args != null)
            actualizarCosa(args.getInt(ARG_POSITION));
        else if (posicion != -1)
            actualizarCosa(posicion);
    }

    public void actualizarCosa(int pos) {
        TextView nombre = (TextView) getActivity().findViewById(R.id.nombre);
        TextView desc = (TextView) getActivity().findViewById(R.id.descripcion);
        ImageView foto = (ImageView) getActivity().findViewById(R.id.foto);

        nombre.setText(Cosas.Cosas[pos].getNombre());
        desc.setText(Cosas.Cosas[pos].getDescripcion());
        foto.setImageResource(Cosas.Cosas[pos].getFoto());
        posicion = pos;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardar los argumentos
        outState.putInt(ARG_POSITION, posicion);
    }
}
