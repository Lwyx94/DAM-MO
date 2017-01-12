package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class vsIA extends AppCompatActivity implements View.OnClickListener{
    private Button btnRoll;
    private TextView dado1, dado2, dado3, dado4, dado5;
    private TextView dadoIA1, dadoIA2, dadoIA3, dadoIA4, dadoIA5;
    private TextView[] dados = new TextView[5];
    private TextView[] dadosIA = new TextView[5];
    private Random ale = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_ia);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(this);

        //Dados del usaurio
        dado1 = (TextView) findViewById(R.id.dado1);
        dado1.setOnClickListener(this);
        dados[0] = dado1;
        dado2 = (TextView) findViewById(R.id.dado2);
        dado2.setOnClickListener(this);
        dados[1] = dado2;
        dado3 = (TextView) findViewById(R.id.dado3);
        dado3.setOnClickListener(this);
        dados[2] = dado3;
        dado4 = (TextView) findViewById(R.id.dado4);
        dado4.setOnClickListener(this);
        dados[3] = dado4;
        dado5 = (TextView) findViewById(R.id.dado5);
        dado5.setOnClickListener(this);
        dados[4] = dado5;

        //Dados del adversario
        dadoIA1 = (TextView) findViewById(R.id.dadoIA1);
        dadoIA1.setOnClickListener(this);
        dadosIA[0] = dadoIA1;
        dadoIA2 = (TextView) findViewById(R.id.dadoIA2);
        dadoIA2.setOnClickListener(this);
        dadosIA[1] = dadoIA2;
        dadoIA3 = (TextView) findViewById(R.id.dadoIA3);
        dadoIA3.setOnClickListener(this);
        dadosIA[2] = dadoIA3;
        dadoIA4 = (TextView) findViewById(R.id.dadoIA4);
        dadoIA4.setOnClickListener(this);
        dadosIA[3] = dadoIA4;
        dadoIA5 = (TextView) findViewById(R.id.dadoIA5);
        dadoIA5.setOnClickListener(this);
        dadosIA[4] = dadoIA5;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRoll:
                tirarDados();
                break;

        }
    }

    private void tirarDados() {
        //Usuario
        dado1.setText(String.valueOf(ale.nextInt(6)+1));
        dado2.setText(String.valueOf(ale.nextInt(6)+1));
        dado3.setText(String.valueOf(ale.nextInt(6)+1));
        dado4.setText(String.valueOf(ale.nextInt(6)+1));
        dado5.setText(String.valueOf(ale.nextInt(6)+1));

        //Usuario
        dadoIA1.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA2.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA3.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA4.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA5.setText(String.valueOf(ale.nextInt(6)+1));
    }

    /**
     *
     * @return 0-Nada
     *         1-Pareja
     *         2-DoblePareja
     *         3-Trío
     *         4-Escalera(1-5)
     *         5-Escalera(2-6)
     *         6-Full
     *         7-Poker
     *         8-RePoker
     */
    private int obtenerMano(TextView[] array){
        int res=0, numIguales1, numIguales2;
        Arrays.sort(volcarNumeros(array));


        return res;

    }

    private int[] volcarNumeros(TextView[] array){
        int[] res = new int[5];
        for (int i=0; i<array.length; i++){
            res[i] = Integer.parseInt(array[i].getText().toString());
        }
        return res;
    }


}
