package es.iesnervion.albertonavarro.a11_preparatoriaexamen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a11_preparatoriaexamen.R;
import es.iesnervion.albertonavarro.a11_preparatoriaexamen.utiles.ManejadoraSQLite;

public class ActivitySQLite extends AppCompatActivity {

    public ManejadoraSQLite msqlite;
    private ArrayList<String> listaUsuarios;
    private  SQLFragment sqlFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);




        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        msqlite = new ManejadoraSQLite(this, "DBUsuarios", null, 1);

        //Comprueba si la actividad está usando el container (para poder añadir el fragment)
        if (findViewById(R.id.contenedor) != null) {

            //OJO! Si estamos restaurando la actividad, no hace falta hacer nada.
            if (savedInstanceState != null) {
                return;
            }

            //Crear fragment
            sqlFragment =  new SQLFragment();
            sqlFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor, sqlFragment).commit();

            sqlFragment.actualizarLista(this, msqlite.getUsuarios());


            SQLFragmentControles controles = new SQLFragmentControles();
            controles.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.controles, controles).commit();


        }
    }

    public void actualizarLista(){
        sqlFragment.actualizarLista(this, msqlite.getUsuarios());
    }


}
