package es.iesnervion.albertonavarro.a03_boletin31;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaración de variables
    private TextView resultadoSuma;
    private EditText ed;
    private EditText sum1;
    private EditText sum2;
    private float size;
    private ImageView foto;
    private int[] imagenes;
    private int imgActual=0;
    private boolean img = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización variables
        ed = (EditText) findViewById(R.id.textoGrande);
        resultadoSuma = (TextView) findViewById(R.id.sumRes);
        sum1 = (EditText) findViewById(R.id.sum1);
        sum2 = (EditText) findViewById(R.id.sum2);
        ed.setTextSize(27f);
        size = ed.getTextSize();
        foto = (ImageView) findViewById(R.id.foto);
        foto.setImageResource(R.drawable.tierra);
        imagenes = new int[]{R.drawable.tierra, R.drawable.blue, R.drawable.green, R.drawable.mars};



        //Colocar OnClickListeners
        Button botonSumar = (Button) findViewById(R.id.sumBoton);
        botonSumar.setOnClickListener(this);
        RadioButton rbAzul = (RadioButton) findViewById(R.id.rbBlue);
        rbAzul.setOnClickListener(this);
        RadioButton rbRojo = (RadioButton) findViewById(R.id.rbRed);
        rbRojo.setOnClickListener(this);
        RadioButton rbVerde = (RadioButton) findViewById(R.id.rbGreen);
        rbVerde.setOnClickListener(this);
        SwitchCompat scAlin =(SwitchCompat) findViewById(R.id.switchAlin);
        scAlin.setOnClickListener(this);
        Button botonMas = (Button) findViewById(R.id.botonMas);
        botonMas.setOnClickListener(this);
        Button botonMenos = (Button) findViewById(R.id.botonMenos);
        botonMenos.setOnClickListener(this);
        ImageButton galDer = (ImageButton) findViewById(R.id.imagenDer);
        galDer.setOnClickListener(this);
        ImageButton galIzq = (ImageButton) findViewById(R.id.imagenIzq);
        galIzq.setOnClickListener(this);
        foto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            //Boton de suma (Ej1)
            case R.id.sumBoton:
                int res = 0;
                if((sum1.getText().toString().trim().length() <= 0) || (sum2.getText().toString().trim().length() <= 0))
                    Toast.makeText(getApplicationContext(), "No puedo hacer eso, cabesa.", Toast.LENGTH_SHORT).show();
                else
                    res = Integer.parseInt(sum1.getText().toString()) + Integer.parseInt(sum2.getText().toString());
                resultadoSuma.setText(String.valueOf(res));
                break;
            //Botones de colores(Ej2)
            case R.id.rbBlue:
                ed.setTextColor(Color.BLUE);
                break;
            case R.id.rbRed:
                ed.setTextColor(Color.RED);
                break;
            case R.id.rbGreen:
                ed.setTextColor(Color.GREEN);
                break;
            //Boton de Alinear (Ej3)
            case R.id.switchAlin:
                if(ed.getTextAlignment() == View.TEXT_ALIGNMENT_TEXT_END)
                    ed.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                else
                    ed.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                break;
            //Botones de cambiar tamaño del texto (Ej5)
            case R.id.botonMas:
                size++;
                ed.setTextSize(size);
                break;
            case R.id.botonMenos:
                size--;
                ed.setTextSize(size);
                break;
            //Botones de galería (Ej4)
            case R.id.imagenDer:
                if(imgActual<imagenes.length-1) {
                    imgActual++;
                    foto.setImageResource(imagenes[imgActual]);
                }
                break;
            case R.id.imagenIzq:
                if(imgActual>0) {
                    imgActual--;
                    foto.setImageResource(imagenes[imgActual]);
                }
                break;
            //Cambiar imagenes (Ej6)
            case R.id.foto:
                if(img) {
                    foto.setImageResource(R.drawable.pelota);
                    img= false;
                }
                else {
                    foto.setImageResource(imagenes[imgActual]);
                    img=true;
                }
                break;

            default:
                break;
        }
    }
}
