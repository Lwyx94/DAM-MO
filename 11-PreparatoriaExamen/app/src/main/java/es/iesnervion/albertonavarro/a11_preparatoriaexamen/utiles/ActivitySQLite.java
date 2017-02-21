package es.iesnervion.albertonavarro.a11_preparatoriaexamen.utiles;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a11_preparatoriaexamen.R;

public class ActivitySQLite extends AppCompatActivity {

    private SQLiteDatabase db;
    private ManejadoraSQLite msqlite;
    private ArrayList<String> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        msqlite = new ManejadoraSQLite(this, "DBUsuarios", null, 1);
    }


    public void cargarUsuarios() {
        db = msqlite.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery(" SELECT nombre FROM Usuarios", null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String nombre = c.getString(0);
                } while (c.moveToNext());
            }
        }
        db.close();
    }

    public void insertarUsuario(String user, String pass) {
        db = msqlite.getWritableDatabase();
        if (db != null && !user.equals("") && !pass.equals("")) {
            db.execSQL("INSERT INTO Usuarios (codigo, nombre, ) " + "VALUES (" + user + ", '" + pass + "')");
        }else
            Toast.makeText(this, "No puedes dejar campos vacíos", Toast.LENGTH_SHORT).show();
        db.close();
    }


    public boolean comprobarUsuario(String user, String pass) {
        boolean res = false;
        db = msqlite.getReadableDatabase();
        if (db != null && !user.equals("") && !pass.equals("")) {
            String[] args = {user, pass};
            Cursor c = db.rawQuery(" SELECT user, pass FROM Usuarios WHERE user=? and pass=?", args);
            res = c.moveToFirst();
        }
        else
            Toast.makeText(this, "No puedes dejar campos vacíos", Toast.LENGTH_SHORT).show();
        db.close();
        return res;
    }
}
