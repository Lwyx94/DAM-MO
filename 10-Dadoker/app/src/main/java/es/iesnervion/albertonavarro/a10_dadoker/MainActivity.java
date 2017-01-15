package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnIA;
    private Button btnHumano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIA = (Button) findViewById(R.id.btnIA);
        btnIA.setOnClickListener(this);
        btnHumano = (Button) findViewById(R.id.btnHumano);
        btnHumano.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHumano:
                startActivity(new Intent(this, BuscaJugadores.class));
                break;
            case R.id.btnIA:
                startActivity(new Intent(this, vsIA.class));
                break;
        }
    }
}

