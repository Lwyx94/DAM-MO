package es.iesnervion.albertonavarro.a11_preparatoriaexamen.utiles;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class ManejadoraSQLite extends SQLiteOpenHelper {

    private String sentenciaSQL = "CREATE TABLE Usuarios (user TEXT, pass TEXT)";
    private SQLiteDatabase db;

    public ManejadoraSQLite(Context contexto, String nombre,SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
        insertarUsuario("Pepito", "Perez");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(sentenciaSQL);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sentenciaSQL);
    }

    public ArrayList<String> getUsuarios() {
        db = getReadableDatabase();
        ArrayList<String> res = new ArrayList<>();
        if (db != null) {
            Cursor c = db.rawQuery(" SELECT user FROM Usuarios", null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    res.add(c.getString(0));
                } while (c.moveToNext());
            }
            db.close();
        }
        return res;

    }

    public void insertarUsuario(String user, String pass) {
        db = getWritableDatabase();
        if (db != null && !user.equals("") && !pass.equals("")) {
            db.execSQL("INSERT INTO Usuarios (user, pass) " + "VALUES ('" + user + "', '" + pass + "')");
            db.close();
        }
    }


    public boolean comprobarUsuario(String user, String pass) {
        boolean res = false;
        db = getReadableDatabase();
        if (db != null && !user.equals("") && !pass.equals("")) {
            String[] args = {user, pass};
            Cursor c = db.rawQuery(" SELECT user, pass FROM Usuarios WHERE user=? and pass=?", args);
            res = c.moveToFirst();
            c.close();
            db.close();
        }
        return res;
    }

    public void borrarTodo(){
        db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL(sentenciaSQL);
        db.close();

    }
}
