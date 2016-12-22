package es.iesnervion.albertonavarro.a09_examen;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a09_examen.models.Jugador;

public class MainActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DatosGlobales dg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botón flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        anadirFotos();
        dg = DatosGlobales.getInstance();
        setListAdapter(new MiArrayAdapter(this, R.layout.fila_layout, R.id.txtNombre, dg.getJugadores()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent i = new Intent(this, AgregarActivity.class);
                i.putExtra(AgregarActivity.DATOS, dg);

                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            Jugador j = extras.getParcelable("jugador");

            if (j != null)
                dg.anadirJugador(j);
        }
        setListAdapter(new MiArrayAdapter(this, R.layout.fila_layout, R.id.txtNombre, dg.getJugadores()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
    }


    public void anadirFotos(){
        ArrayList<Bitmap> fotosDisponibles = new ArrayList<>();


        //Añadir del 00 al 09
        for(int i=0; i<=9; i++){
            int id = getResources().getIdentifier("jugador0" + i,"drawable", this.getPackageName());
            Drawable dImage = this.getResources().getDrawable(id);
            BitmapDrawable bmdImage = (BitmapDrawable) dImage;
            Bitmap bmImage = bmdImage.getBitmap();
            fotosDisponibles.add(bmImage);
        }

        //Añadir del 10 al 24
        for(int i=10; i<=24; i++){
            int id = getResources().getIdentifier("jugador" + i,"drawable", this.getPackageName());
            Bitmap image = BitmapFactory.decodeResource(getResources(), id);
            fotosDisponibles.add(image);
        }

        dg.setFotosDisponibles(fotosDisponibles);
    }

}
