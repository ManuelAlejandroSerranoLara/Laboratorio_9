package com.example.laboratorio_9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase"; // Database Name
    private static final String TABLE_NAME = "myTable"; // Table Name
    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String UID = "ID"; // Column I (Primary Key)
    private static final String NAME = "Name"; //Column II
    private static final String MyPASSWORD= "Password"; // Column III
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

    public myDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Db) {
        try {
            Db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase Db, int i, int i1) {
        try {
            Db.execSQL(DROP_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean insertarDatos(String username, String password) {
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(myDBHelper.NAME, username);
        contentValues.put(myDBHelper.MyPASSWORD, password);

        long result = Db.insert(myDBHelper.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor obtenerDatos() {
        SQLiteDatabase Db = this.getWritableDatabase();
        return Db.rawQuery("select * from "+myDBHelper.TABLE_NAME, null);
    }

    public Boolean borrarDatos(String username) {
        SQLiteDatabase Db = this.getWritableDatabase();
        String[] whereArgs = {username};

        Cursor cursor = Db.rawQuery("select * from "+myDBHelper.TABLE_NAME+" where "+myDBHelper.NAME+" = ?", whereArgs);
        if(cursor.getCount()>0) {
            long result = Db.delete(myDBHelper.TABLE_NAME, myDBHelper.NAME+" = ?", whereArgs);
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean actializarNombreDeUsuario(String pastUsername, String newUsername) {
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBHelper.NAME, newUsername);
        String[] whereArgs = {pastUsername};

        Cursor cursor = Db.rawQuery("select * from "+myDBHelper.TABLE_NAME+" where "+myDBHelper.NAME+" = ?", whereArgs);
        if(cursor.getCount()>0) {
            contentValues.put(myDBHelper.NAME, newUsername);
            long result = Db.update(myDBHelper.TABLE_NAME, contentValues, myDBHelper.NAME+" = ?", whereArgs);
            return result != -1;
        } else {
            return  false;
        }
    }
}
