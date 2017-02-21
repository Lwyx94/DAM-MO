package es.iesnervion.albertonavarro.a11_preparatoriaexamen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSQLite;
    private Button btnSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSQLite = (Button) findViewById(R.id.btnSQLite);
        btnSQLite.setOnClickListener(this);
        btnSharedPreferences = (Button) findViewById(R.id.btnSharedPreferences);
        btnSharedPreferences.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSQLite:
                startActivity(new Intent(this, ActivitySQLite.class));
                break;
            case R.id.btnSharedPreferences:
                Toast.makeText(this, "No implementado todavía", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
