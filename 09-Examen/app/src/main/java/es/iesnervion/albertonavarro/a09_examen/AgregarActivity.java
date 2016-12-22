package es.iesnervion.albertonavarro.a09_examen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.ArrayList;

import es.iesnervion.albertonavarro.a09_examen.models.Jugador;

public class AgregarActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etNombre;
    private ImageView ivFoto;
    private Spinner spAltura;
    private Spinner spPeso;
    private DatosGlobales dg;
    private RadioGroup rgPosicion;
    private RadioButton rbBase;
    private RadioButton rbEscolta;
    private RadioButton rbAlero;
    private RadioButton rbAlapivot;
    private RadioButton rbPivot;
    public static final String FOTO = "foto";
    public static final String DATOS = "datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dg=DatosGlobales.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(this);

        etNombre = (EditText) findViewById(R.id.etNombre);
        spAltura = (Spinner) findViewById(R.id.spAltura);
        spPeso = (Spinner) findViewById(R.id.spPeso);

        //Radio Buttons
        rgPosicion = (RadioGroup) findViewById(R.id.rgPosicion);
        rbBase = (RadioButton) findViewById(R.id.rbBase);
        rbBase.setEnabled(!dg.getPosicionRepetida(Posiciones.Base));
        rbAlapivot = (RadioButton) findViewById(R.id.rbAlapivot);
        rbAlapivot.setEnabled(!dg.getPosicionRepetida(Posiciones.Alapívot));
        rbAlero = (RadioButton) findViewById(R.id.rbAlero);
        rbAlero.setEnabled(!dg.getPosicionRepetida(Posiciones.Alero));
        rbEscolta = (RadioButton) findViewById(R.id.rbEscolta);
        rbEscolta.setEnabled(!dg.getPosicionRepetida(Posiciones.Escolta));
        rbPivot = (RadioButton) findViewById(R.id.rbPivot);
        rbPivot.setEnabled(!dg.getPosicionRepetida(Posiciones.Pívot));

        //Spinner altura
        ArrayList<Integer> altura = new ArrayList<Integer>();
        for (int i = 150; i<=250; i++)
            altura.add(i);

        spAltura.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, altura));

        //Spinner peso
        ArrayList<Integer> pesos = new ArrayList<Integer>();
        for (int i = 45; i<=250; i++)
            pesos.add(i);

        spPeso.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, pesos));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivFoto:
                startActivityForResult(new Intent(this, Galeria.class), 7);
                break;

            case R.id.fab:
                guardarJugador();
                break;

        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        Drawable draw = (Drawable) extras.get("foto");
        ivFoto.setImageDrawable(draw);
    }*/


    /*Valida los datos y guarda el jugador en caso de que sean correctos*/
    private void guardarJugador() {
        String nombre = etNombre.getText().toString();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivFoto.getDrawable();
        Bitmap foto = null;
        if(bitmapDrawable.getBitmap() != null)
            foto =  bitmapDrawable.getBitmap();

        int altura = (Integer) spAltura.getSelectedItem();
        int peso = (Integer) spPeso.getSelectedItem();
        Posiciones posicion = Posiciones.Alero;
        switch (rgPosicion.getCheckedRadioButtonId()){
            case R.id.rbAlapivot:
                posicion = Posiciones.Alapívot;
                break;
            case R.id.rbAlero:
                posicion = Posiciones.Alero;
                break;
            case R.id.rbBase:
                posicion = Posiciones.Base;
                break;
            case R.id.rbEscolta:
                posicion = Posiciones.Escolta;
                break;
            case R.id.rbPivot:
                posicion = Posiciones.Pívot;
                break;
        }

        //TODO:Validar datos jugador

        //Jugador j = new Jugador("sdasd",R.drawable.jugador21,altura,peso,Posiciones.Alero);
        Jugador j = new Jugador(nombre, foto, altura, peso, posicion);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("jugador", j);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("foto");

        if(b!=null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            ivFoto.setImageBitmap(bmp);
        }
    }
}
