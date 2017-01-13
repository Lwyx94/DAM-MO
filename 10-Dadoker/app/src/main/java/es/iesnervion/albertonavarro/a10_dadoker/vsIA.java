package es.iesnervion.albertonavarro.a10_dadoker;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Random;

//TODO: Implementar for extendido -_-
public class vsIA extends AppCompatActivity implements View.OnClickListener{
    private Button btnRoll;
    private TextView dado1, dado2, dado3, dado4, dado5, txtResHum;
    private TextView dadoIA1, dadoIA2, dadoIA3, dadoIA4, dadoIA5, txtResIA;
    private TextView[] dados = new TextView[5];
    private TextView[] dadosIA = new TextView[5];
    private LinearLayout tableroIA, tableroH;
    private Random ale = new Random();
    private boolean primeraTirada = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_ia);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        btnRoll.setOnClickListener(this);

        txtResHum = (TextView) findViewById(R.id.txtResHum);
        txtResIA = (TextView) findViewById(R.id.txtResIA);
        tableroIA = (LinearLayout) findViewById(R.id.tableroIA);
        tableroH = (LinearLayout) findViewById(R.id.tableroH);

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
        dadosIA[0] = dadoIA1;
        dadoIA2 = (TextView) findViewById(R.id.dadoIA2);
        dadosIA[1] = dadoIA2;
        dadoIA3 = (TextView) findViewById(R.id.dadoIA3);
        dadosIA[2] = dadoIA3;
        dadoIA4 = (TextView) findViewById(R.id.dadoIA4);
        dadosIA[3] = dadoIA4;
        dadoIA5 = (TextView) findViewById(R.id.dadoIA5);
        dadosIA[4] = dadoIA5;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRoll:
                tirarDados();
                break;
            case R.id.dado1:
                if(!primeraTirada) {
                    if (dado1.getCurrentTextColor() == Color.BLACK)
                        dado1.setTextColor(Color.RED);
                    else
                        dado1.setTextColor(Color.BLACK);
                }
                break;
            case R.id.dado2:
                if(!primeraTirada) {
                    if (dado2.getCurrentTextColor() == Color.BLACK)
                        dado2.setTextColor(Color.RED);
                    else
                        dado2.setTextColor(Color.BLACK);
                }
                break;
            case R.id.dado3:
                if(!primeraTirada) {
                    if (dado3.getCurrentTextColor() == Color.BLACK)
                        dado3.setTextColor(Color.RED);
                    else
                        dado3.setTextColor(Color.BLACK);
                }
                break;
            case R.id.dado4:
                if(!primeraTirada) {
                    if (dado4.getCurrentTextColor() == Color.BLACK)
                        dado4.setTextColor(Color.RED);
                    else
                        dado4.setTextColor(Color.BLACK);
                }
                break;
            case R.id.dado5:
                if(!primeraTirada) {
                    if (dado5.getCurrentTextColor() == Color.BLACK)
                        dado5.setTextColor(Color.RED);
                    else
                        dado5.setTextColor(Color.BLACK);
                }
                break;
        }
    }

    private void tirarDados() {
        btnRoll.setTextColor(Color.YELLOW);

        if(primeraTirada) {
            tableroH.setBackgroundColor(Color.LTGRAY);
            tableroIA.setBackgroundColor(Color.LTGRAY);
        }

        //Usuario
        if(primeraTirada){
            dado1.setText(String.valueOf(ale.nextInt(6) + 1));
            dado2.setText(String.valueOf(ale.nextInt(6) + 1));
            dado3.setText(String.valueOf(ale.nextInt(6) + 1));
            dado4.setText(String.valueOf(ale.nextInt(6) + 1));
            dado5.setText(String.valueOf(ale.nextInt(6) + 1));
        }else {
            if (dado1.getCurrentTextColor() == Color.BLACK)
                dado1.setText(String.valueOf(ale.nextInt(6) + 1));
            if (dado2.getCurrentTextColor() == Color.BLACK)
                dado2.setText(String.valueOf(ale.nextInt(6) + 1));
            if (dado3.getCurrentTextColor() == Color.BLACK)
                dado3.setText(String.valueOf(ale.nextInt(6) + 1));
            if (dado4.getCurrentTextColor() == Color.BLACK)
                dado4.setText(String.valueOf(ale.nextInt(6) + 1));
            if (dado5.getCurrentTextColor() == Color.BLACK)
                dado5.setText(String.valueOf(ale.nextInt(6) + 1));

            for(TextView tv : dados)
                tv.setTextColor(Color.BLACK);

        }

        //IA
        dadoIA1.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA2.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA3.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA4.setText(String.valueOf(ale.nextInt(6)+1));
        dadoIA5.setText(String.valueOf(ale.nextInt(6)+1));

        mostrarResultado();
        primeraTirada=!primeraTirada;
        btnRoll.setTextColor(Color.GREEN);
    }

    private void mostrarResultado() {
        int[] manoH = obtenerMano(dados);
        int[] manoIA = obtenerMano(dadosIA);

        txtResHum.setText(obtenerResultado(manoH));
        txtResIA.setText(obtenerResultado(manoIA));

        String res = "resultado";
        switch (humanoGanador(manoH, manoIA)) {
            case 1:
                res="¡HAS GANADO!";
                if(!primeraTirada) {
                    tableroH.setBackgroundColor(Color.GREEN);
                    tableroIA.setBackgroundColor(Color.RED);
                }
                break;
            case 2:
                res="¡HAS PERDIDO! ¡JUAS!";
                if(!primeraTirada) {
                    tableroH.setBackgroundColor(Color.RED);
                    tableroIA.setBackgroundColor(Color.GREEN);
                }
                break;
            case 3:
                res="¡EMPATE! ¡AW YEAH!";
                break;
        }
       /* if(!primeraTirada)
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();*/

    }



    private String obtenerResultado(int[] mano) {
        String res = "resultado";
        switch (mano[0]){
            case 0:
               res = "Vaya mierda...";
                break;
            case 1:
                res = "Pareja de "+mano[1];
                break;
            case 2:
                res = "DoblePareja";
                break;
            case 3:
                res = "Trío ( ͡° ͜ʖ ͡°)";
                break;
            case 4:
                res = "Escalera (1-5)";
                break;
            case 5:
                res = "Escalera (2-6)";
                break;
            case 6:
                res = "Full HD";
                break;
            case 7:
                res = "¡Póker de "+mano[1]+"!";
                break;
            case 8:
                res = "¡¡¡REPÓKER DE "+mano[1]+"!!!";
                break;
        }
        return res;

    }

    private int humanoGanador(int[] manoH, int[] manoIA) {
        int res = 3;
        if(manoH[0]>manoIA[0])
            res=1;
        else if(manoH[0]<manoIA[0])
            res=2;
        else {
            if(manoH[1]>manoIA[1]||(manoH[1]==1&&manoH[1]!=manoIA[1]))
                res=1;
            else if(manoH[1]<manoIA[1]||(manoIA[1]==1&&manoIA[1]!=manoH[1]))
                res=2;
            else {
                if(manoH[2]>manoIA[2])
                    res=1;
                else if(manoH[2]<manoIA[2])
                    res=2;
            }
        }

        return res;
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
    private int[] obtenerMano(TextView[] array){
        int[] res = new int[3];
        int numRep1=0, numRep2=0;
        int cuent1=1, cuent2=1;
        int[] numeros = volcarNumeros(array);
        Arrays.sort(numeros);

        if(esEscalera(numeros)){
            if(numeros[0]==1)
                res[0]=4;
            else
                res[0]=5;
        }else{
            for(int i = 1; i < numeros.length; i++) {
                if(numeros[i] == numeros[i - 1]) {
                    if(numRep1==0) {
                        numRep1=numeros[i];
                        cuent1++;
                    }
                    else if(numRep1==numeros[i]){
                        cuent1++;
                    }else {
                        numRep2 = numeros[i];
                        cuent2++;
                    }
                }
            }
            if(numRep1!=0&&numRep2!=0) {//Dos combinaciones
                if (cuent1==3&&cuent2==2){//Full
                    res[0]=6;res[1]=numRep1;res[2]=numRep2;
                }else if(cuent1==2&&cuent2==3){//Full
                    res[0]=6;res[1]=numRep2;res[2]=numRep1;
                }else if(numRep1>numRep2){//Doble Pareja
                    res[0]=2;res[1]=numRep1;res[2]=numRep2;}
                else{//Pareja
                    res[0]=2;res[1]=numRep2;res[2]=numRep1;}

            }else if(numRep1!=0){
                switch (cuent1){
                    case 2:
                        res[0]=1;res[1]=numRep1;
                        break;
                    case 3:
                        res[0]=3;res[1]=numRep1;
                        break;
                    case 4:
                        res[0]=7;res[1]=numRep1;
                        break;
                    case 5:
                        res[0]=8;res[1]=numRep1;
                        break;
                }
            }
            else
                res[0]=0;
        }
        return res;
    }



    private int[] volcarNumeros(TextView[] array){
        int[] res = new int[5];
        for (int i=0; i<array.length; i++){
            res[i] = Integer.parseInt(array[i].getText().toString());
        }
        return res;
    }

    private boolean esEscalera(int[] numeros) {
        boolean res = true;
        for(int i = 0; i< numeros.length-1 && res; i++){
            if(numeros[i]+1 != numeros[i+1])
                res = false;
        }
        return res;
    }
}
