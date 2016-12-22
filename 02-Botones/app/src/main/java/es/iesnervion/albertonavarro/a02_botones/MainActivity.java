package es.iesnervion.albertonavarro.a02_botones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btMe = (Button) findViewById(R.id.button_me);
        btMe.setOnClickListener(this);
        Button btCago = (Button) findViewById(R.id.button_cago);
        btCago.setOnClickListener(this);
        Button btEn = (Button) findViewById(R.id.button_en);
        btEn.setOnClickListener(this);
        Button btTodo = (Button) findViewById(R.id.button_todo);
        btTodo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_me:
                anadirPalabra("ROJO");
                break;
            case R.id.button_cago:
                anadirPalabra("AMARILLO");
                break;
        }
    }

    public void anadirPalabra(String palabra){
        TextView tv = (TextView) findViewById(R.id.texto);
        tv.setText(tv.getText()+palabra+" ");
     }
}

