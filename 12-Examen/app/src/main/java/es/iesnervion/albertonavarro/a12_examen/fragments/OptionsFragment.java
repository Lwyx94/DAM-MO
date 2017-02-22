package es.iesnervion.albertonavarro.a12_examen.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a12_examen.MainActivity;
import es.iesnervion.albertonavarro.a12_examen.R;

public class OptionsFragment extends ListFragment {

    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.options_fragment, container, false);

        ArrayList<String> optionsList = new ArrayList<>();
        optionsList.add("Añadir");optionsList.add("Consultar");

        setListAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, optionsList));

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((MainActivity)getActivity()).clickOption((String) l.getItemAtPosition(position));
    }

}
