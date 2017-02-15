package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.iesnervion.albertonavarro.a10_dadoker.Tests.ClientActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnIA;
    private Button btnHumano;
    private Button btnBuscarPartida;
    private Button btnCrearPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIA = (Button) findViewById(R.id.btnIA);
        btnIA.setOnClickListener(this);
        btnHumano = (Button) findViewById(R.id.btnHumano);
        btnHumano.setOnClickListener(this);
        btnBuscarPartida = (Button) findViewById(R.id.btnBuscarPartida);
        btnBuscarPartida.setOnClickListener(this);
        btnCrearPartida = (Button) findViewById(R.id.btnCrearPartida);
        btnCrearPartida.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHumano:
                startActivity(new Intent(this, VsHumanoLocal.class));
                break;
            case R.id.btnIA:
                startActivity(new Intent(this, VsIA.class));
                break;
            case R.id.btnBuscarPartida:
                startActivity(new Intent(this, BuscaJugadores.class));
                break;
            case R.id.btnCrearPartida:
                startActivity(new Intent(this, VsHumanoOnline.class));
                break;
        }
    }
}

