package es.iesnervion.albertonavarro.a04_boletin32;

import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaración variables
    private EditText texto, num1, num2;
    private CheckBox cbNegrita, cbGrande, cbPequenyo, cbRojo;
    private Button btSumar, btRestar, btMulti, btDiv, btReset;
    private TextView simbolo, resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciar variables
        texto = (EditText) findViewById(R.id.texto);
        simbolo = (TextView) findViewById(R.id.simbolo);
        resultado = (TextView) findViewById(R.id.resultado);
        num1 = (EditText) findViewById(R.id.calc1);
        num2 = (EditText) findViewById(R.id.calc2);

        //Poner OnClickListeners
        cbNegrita = (CheckBox) findViewById(R.id.cbNegrita);
        cbNegrita.setOnClickListener(this);
        cbGrande = (CheckBox) findViewById(R.id.cbGrande);
        cbGrande.setOnClickListener(this);
        cbPequenyo = (CheckBox) findViewById(R.id.cbPequenyo);
        cbPequenyo.setOnClickListener(this);
        cbRojo = (CheckBox) findViewById(R.id.cbRojo);
        cbRojo.setOnClickListener(this);
        btSumar = (Button) findViewById(R.id.btnSumar);
        btSumar.setOnClickListener(this);
        btRestar = (Button) findViewById(R.id.btnRestar);
        btRestar.setOnClickListener(this);
        btMulti = (Button) findViewById(R.id.btnMult);
        btMulti.setOnClickListener(this);
        btDiv = (Button) findViewById(R.id.btnDiv);
        btDiv.setOnClickListener(this);
        btReset = (Button) findViewById(R.id.btnReset);
        btReset.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**CheckBoxes**/
            //Negrita
            case R.id.cbNegrita:
                if(cbNegrita.isChecked())
                    texto.setTypeface(Typeface.DEFAULT_BOLD);
                else
                    texto.setTypeface(Typeface.DEFAULT);
                break;
            //Agrandar
            case R.id.cbGrande:
                if(cbGrande.isChecked()) {
                    cbPequenyo.setChecked(false);
                    texto.setTextSize(40);
                }
                else
                    texto.setTextSize(25);
                break;

            //Empequeñecer
            case R.id.cbPequenyo:
                if(cbPequenyo.isChecked()) {
                    cbGrande.setChecked(false);
                    texto.setTextSize(12);
                }
                else
                    texto.setTextSize(25);
                break;

            //Rojear
            case R.id.cbRojo:
                if(cbRojo.isChecked())
                    texto.setTextColor(Color.RED);
                else
                    texto.setTextColor(Color.BLACK);
                break;

            /**Calculadora**/
            //Sumar
            case R.id.btnSumar:
                simbolo.setText("+");
                if(!(num1.getText().toString().trim().length() <= 0) && !(num2.getText().toString().trim().length() <= 0))
                    resultado.setText(String.valueOf(Double.parseDouble(num1.getText().toString()) + Double.parseDouble(num2.getText().toString())));
                break;
            //Restar
            case R.id.btnRestar:
                simbolo.setText("-");
                if(!(num1.getText().toString().trim().length() <= 0) && !(num2.getText().toString().trim().length() <= 0))
                    resultado.setText(String.valueOf(Double.parseDouble(num1.getText().toString()) - Double.parseDouble(num2.getText().toString())));
                break;
            //Multiplicar
            case R.id.btnMult:
                simbolo.setText("*");
                if(!(num1.getText().toString().trim().length() <= 0) && !(num2.getText().toString().trim().length() <= 0))
                    resultado.setText(String.valueOf(Double.parseDouble(num1.getText().toString()) * Double.parseDouble(num2.getText().toString())));
                break;
            //Dividir
            case R.id.btnDiv:
                simbolo.setText("/");
                if(!(num1.getText().toString().trim().length() <= 0) && !(num2.getText().toString().trim().length() <= 0))
                    resultado.setText(String.valueOf(Double.parseDouble(num1.getText().toString()) / Double.parseDouble(num2.getText().toString())));
                break;
            /**Resetear**/
            case R.id.btnReset:
                num1.setText("");
                num2.setText("");
                resultado.setText("");
                texto.setText("");
                texto.setTextSize(25);
                texto.setTextColor(Color.BLACK);
                texto.setTypeface(Typeface.DEFAULT);
                cbPequenyo.setChecked(false);cbNegrita.setChecked(false);cbRojo.setChecked(false);
                cbGrande.setChecked(false);
                break;
        }

    }
}
