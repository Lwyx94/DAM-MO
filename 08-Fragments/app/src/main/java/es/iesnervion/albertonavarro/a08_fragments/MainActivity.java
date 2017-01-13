package es.iesnervion.albertonavarro.a08_fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class MainActivity extends FragmentActivity implements FragmentCabecera.OnHeadlineSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Comprueba si la actividad está usando el container (para poder añadir el fragment)
        if (findViewById(R.id.contenedor) != null) {

            //OJO! Si estamos restaurando la actividad, no hace falta hacer nada.
            if (savedInstanceState != null) {
                return;
            }

            //Crear fragment
            FragmentCabecera primerFragment =  new FragmentCabecera();
            primerFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor, primerFragment).commit();

        }
    }

    public void onArticleSelected(int position) {
        FragmentArticulo articuloFrag = (FragmentArticulo) getSupportFragmentManager().findFragmentById(R.id.nombre);

        //Si el fragment ya existe, significa que estamos en la vista maestro-detalle
        if (articuloFrag != null)
            articuloFrag.actualizarCosa(position);

            //Si no existe hay que intercambiar los fragments
        else {
            FragmentArticulo nuevoFragment = new FragmentArticulo();
            Bundle args = new Bundle();
            args.putInt(FragmentArticulo.ARG_POSITION, position);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            //Se sustituye lo que hay en el contenedor con el nuevo fragment
            transaction.replace(R.id.contenedor, nuevoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
