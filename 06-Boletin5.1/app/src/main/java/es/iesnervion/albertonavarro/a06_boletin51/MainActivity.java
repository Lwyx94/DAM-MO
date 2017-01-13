package es.iesnervion.albertonavarro.a06_boletin51;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    //Variables
    private ArrayList<Equipo> equipos = new ArrayList<Equipo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anadeEquipos(equipos);

        setListAdapter(new MiArrayAdapter(this, R.layout.fila_layout, R.id.txtNombre, equipos));

    }


    public void onListItemClick(ListView parent, View v, int pos, long id) {
        Toast.makeText(this, "HOLA AMIJO", Toast.LENGTH_SHORT).show();

    }


    /**
     * AñadeEquipos
     * <p>
     * Añade los 30 equipos de la NBA a un Array     *
     *
     * @param equipos El ArrayList al que se añadirán los equipos.
     */

    public static void anadeEquipos(ArrayList<Equipo> equipos) {
        equipos.add(new Equipo("Golden State Warriors", R.drawable.warriors));
        equipos.add(new Equipo("Los Angeles Lakers", R.drawable.lakers));
        equipos.add(new Equipo("Cleveland Cavaliers", R.drawable.cavaliers));
        equipos.add(new Equipo("New York Knicks", R.drawable.knicks));
        equipos.add(new Equipo("Chicago Bulls", R.drawable.bulls));
        equipos.add(new Equipo("San Antonio Spurs", R.drawable.spurs));
        equipos.add(new Equipo("Boston Celtics", R.drawable.celtics));
        equipos.add(new Equipo("Miami Heat", R.drawable.heat));
        equipos.add(new Equipo("Toronto Raptors", R.drawable.raptors));
        equipos.add(new Equipo("Oklahoma City Thunder", R.drawable.thunder));
        equipos.add(new Equipo("Houston Rockets", R.drawable.rockets));
        equipos.add(new Equipo("Philadelphia 76ers", R.drawable.seventysixers));
        equipos.add(new Equipo("Los Angeles Clippers", R.drawable.clippers));
        equipos.add(new Equipo("Brooklyn Nets", R.drawable.nets));
        equipos.add(new Equipo("Dallas Maverick", R.drawable.mavericks));
        equipos.add(new Equipo("Minnesota Timberwolves", R.drawable.timberwolves));
        equipos.add(new Equipo("New Orleans Pelicans", R.drawable.pelicans));
        equipos.add(new Equipo("Sacramento Kings", R.drawable.kings));
        equipos.add(new Equipo("Milwaukee Bucks", R.drawable.bucks));
        equipos.add(new Equipo("Indiana Pacers", R.drawable.pacers));
        equipos.add(new Equipo("Charlotte Hornets", R.drawable.hornets));
        equipos.add(new Equipo("Portland Trail Blazers", R.drawable.blazers));
        equipos.add(new Equipo("Detroit Pistons", R.drawable.pistons));
        equipos.add(new Equipo("Phoenix Suns", R.drawable.suns));
        equipos.add(new Equipo("Atlanta Hawks", R.drawable.hawks));
        equipos.add(new Equipo("Utah Jazz", R.drawable.jazz));
        equipos.add(new Equipo("Washington Wizards", R.drawable.wizards));
        equipos.add(new Equipo("Memphis Grizzlies", R.drawable.grizzlies));
        equipos.add(new Equipo("Denver Nuggets", R.drawable.nuggets));
        equipos.add(new Equipo("Orlando Magic", R.drawable.magic));
    }
}