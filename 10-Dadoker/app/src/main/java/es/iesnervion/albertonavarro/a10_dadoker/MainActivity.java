package es.iesnervion.albertonavarro.a10_dadoker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**http://stackoverflow.com/questions/6931118/for-multiplayer-online-game-how-to-initiate-data-transfer-by-server-and-send-da/ **/
/**https://thinkandroid.wordpress.com/2010/03/27/incorporating-socket-programming-into-your-applications/ **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnIA;
    private Button btnHumano;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIA = (Button) findViewById(R.id.btnIA);
        btnIA.setOnClickListener(this);
        btnHumano = (Button) findViewById(R.id.btnHumano);
        btnHumano.setOnClickListener(this);
        btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHumano:
                startActivity(new Intent(this, BuscaJugadores.class));
                break;
            case R.id.btnIA:
                startActivity(new Intent(this, VsIA.class));
                break;
            case R.id.btnTest:
                startActivity(new Intent(this, ServerActivity.class));
                break;
        }
    }
}

