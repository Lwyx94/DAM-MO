package es.iesnervion.albertonavarro.a09_examen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Galeria extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayList<Drawable> fotos;
    private GridView gvGaleria;
    private DatosGlobales dg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        gvGaleria = (GridView) findViewById(R.id.gvGaleria);
        dg = DatosGlobales.getInstance();



        gvGaleria.setAdapter(new FotosArrayAdapter(this, R.layout.foto_layout, fotos));
        gvGaleria.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BitmapDrawable bmd = (BitmapDrawable) gvGaleria.getItemAtPosition(position);
        Bitmap bitmap=bmd.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        Intent intent = new Intent(this, AgregarActivity.class);
        intent.putExtra("foto", b);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
