package es.iesnervion.albertonavarro.a12_examen.utils;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import es.iesnervion.albertonavarro.a12_examen.models.Contact;

public class SQLiteHandler {

    private SQLiteDatabase db;

    public SQLiteHandler(SQLiteDatabase db){
        this.db=db;
    }


    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> res = new ArrayList<>();
        if (db != null) {
            Cursor c = db.rawQuery(" SELECT id, name, phone, age, gender FROM Contacts", null);
            if (c.moveToFirst()) {
                do {
                    res.add(new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4)));
                } while (c.moveToNext());
            }
        }
        return res;

    }

    public Contact getContact(int id) {
        Contact res = null;
        if (db != null) {
            String[] args = {""+id};
            Cursor c = db.rawQuery(" SELECT id, name, phone, age, gender FROM Contacts WHERE id=?", args);
            if (c.moveToFirst()) {
                res = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4));
            }
        }
        return res;

    }

    public void insertContact(Contact c) {
        if (db != null) {
            db.execSQL("INSERT INTO Contacts (name, phone, age, gender) " + "VALUES ('" + c.getName() + "', '"+c.getPhone()+"',"+c.getAge()+", '"+c.getGender()+"')");
        }
    }

    public void updateContact(Contact c) {
        if (db != null) {
            String[] args = {""+c.getId()};
            db.execSQL("UPDATE Contacts SET name='"+c.getName()+ "', phone='"+c.getPhone()+"', age="+c.getAge()+", gender='"+c.getGender()+"' WHERE id=?", args);
        }
    }


    public void deleteContact(int id){
    }


}
