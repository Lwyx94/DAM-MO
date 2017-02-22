package es.iesnervion.albertonavarro.a12_examen.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a12_examen.MainActivity;
import es.iesnervion.albertonavarro.a12_examen.R;
import es.iesnervion.albertonavarro.a12_examen.models.Contact;

public class ContactListFragment extends ListFragment {

    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_list_fragment, container, false);

        ArrayList<Contact> contactList = ((MainActivity)getActivity()).getContacts();
        if(contactList.size()==0)
            Toast.makeText(getContext(), "La lista de contactos está vacía", Toast.LENGTH_LONG).show();
        setListAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contactList));

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((MainActivity)getActivity()).clickContact((Contact) l.getItemAtPosition(position));
    }

}
