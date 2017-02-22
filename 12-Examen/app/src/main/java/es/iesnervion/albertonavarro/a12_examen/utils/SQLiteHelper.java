package es.iesnervion.albertonavarro.a12_examen.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    private String sql = "CREATE TABLE Contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INT, phone TEXT, gender TEXT)";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Contacts");
        db.execSQL(sql);
    }
}
