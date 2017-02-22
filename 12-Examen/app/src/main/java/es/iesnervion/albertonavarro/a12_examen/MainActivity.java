package es.iesnervion.albertonavarro.a12_examen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a12_examen.fragments.AddFragment;
import es.iesnervion.albertonavarro.a12_examen.fragments.ContactListFragment;
import es.iesnervion.albertonavarro.a12_examen.fragments.EditFragment;
import es.iesnervion.albertonavarro.a12_examen.fragments.OptionsFragment;
import es.iesnervion.albertonavarro.a12_examen.models.Contact;
import es.iesnervion.albertonavarro.a12_examen.utils.SQLiteHandler;
import es.iesnervion.albertonavarro.a12_examen.utils.SQLiteHelper;

public class MainActivity extends AppCompatActivity {

    private SQLiteHandler sqlHandler;
    private SQLiteHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQLite
        sqlHelper =  new SQLiteHelper(this, "DBContacts", null, 2);
        sqlHandler = new SQLiteHandler(sqlHelper.getWritableDatabase());


        //Create fragment
        //Mobile
        if (findViewById(R.id.container) != null) {
            OptionsFragment optFragment = new OptionsFragment();
            optFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, optFragment).commit();
        }
        else {
            OptionsFragment optFragment = new OptionsFragment();
            optFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.containerLeft, optFragment).commit();

        }

    }


    public void clickOption(String option){
        switch (option){
            case "Añadir":
                AddFragment addFragment=  new AddFragment();
                if (findViewById(R.id.container) != null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, addFragment).addToBackStack(null).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerRight, addFragment).addToBackStack(null).commit();
                break;
            case "Consultar":
                ContactListFragment contactListFragment=  new ContactListFragment();
                if (findViewById(R.id.container) != null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, contactListFragment).addToBackStack(null).commit();
                else
                    getSupportFragmentManager().beginTransaction().replace(R.id.containerRight, contactListFragment).addToBackStack(null).commit();
                break;
        }
    }

    public void clickContact(Contact c){
        EditFragment editFragment=  new EditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", c.getId());
        editFragment.setArguments(bundle);
        if (findViewById(R.id.container) != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, editFragment).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRight, editFragment).addToBackStack(null).commit();
    }

    public void addContact(Contact contact) {
        sqlHandler.insertContact(contact);
    }

    public ArrayList<Contact> getContacts(){
        ArrayList<Contact> res = sqlHandler.getContacts();
        return res;
    }


    public void editContact(Contact contact) {
        sqlHandler.updateContact(contact);
    }

    public Contact getContact(int id) {
        return sqlHandler.getContact(id);
    }
}
