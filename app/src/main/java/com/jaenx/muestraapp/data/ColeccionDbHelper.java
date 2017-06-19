package com.jaenx.muestraapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaenx on 16/06/2017.
 */

public class ColeccionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "muestraApp.db";

    public ColeccionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ColeccionContract.Colecciones.TABLE_NAME + " ("
                + ColeccionContract.Colecciones._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ColeccionContract.Colecciones.ID + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.NAME + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.AVATAR + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.DESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + ColeccionContract.Colecciones.ID + "))");



        // Insertar datos ficticios para prueba inicial
        initialData(db);

    }

    private void initialData(SQLiteDatabase sqLiteDatabase) {
        /*
        addColeccion(sqLiteDatabase, new Coleccion("coleccion 1", "Colección primera-verano 2017", "icono_coleccion.png"));
        addColeccion(sqLiteDatabase, new Coleccion("coleccion 2", "Colección otoño-invierno 2017", "icono_coleccion.png"));
        addColeccion(sqLiteDatabase, new Coleccion("coleccion 3", "Colección A 2017", "icono_coleccion.png"));
        addColeccion(sqLiteDatabase, new Coleccion("coleccion 4", "Colección B 2017", "icono_coleccion.png"));
        */
    }

    public long addColeccion(SQLiteDatabase db, Coleccion coleccion) {
        return db.insert(
                ColeccionContract.Colecciones.TABLE_NAME,
                null,
                coleccion.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE " + ColeccionContract.Colecciones.TABLE_NAME + " ("
                + ColeccionContract.Colecciones._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ColeccionContract.Colecciones.ID + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.NAME + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.AVATAR + " TEXT NOT NULL,"
                + ColeccionContract.Colecciones.DESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + ColeccionContract.Colecciones.ID + "))");
    }

    /**
     * Método tabla coleccion
     */

    public long saveColeccion(Coleccion coleccion) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ColeccionContract.Colecciones.TABLE_NAME,
                null,
                coleccion.toContentValues());

    }

    public Cursor getAllColeccion() {
        return getReadableDatabase()
                .query(
                        ColeccionContract.Colecciones.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getColeccionById(String coleccionId) {
        Cursor c = getReadableDatabase().query(
                ColeccionContract.Colecciones.TABLE_NAME,
                null,
                ColeccionContract.Colecciones.ID + " LIKE ?",
                new String[]{coleccionId},
                null,
                null,
                null);
        return c;
    }

    public int deleteColeccion(String coleccionId) {
        return getWritableDatabase().delete(
                ColeccionContract.Colecciones.TABLE_NAME,
                ColeccionContract.Colecciones.ID + " LIKE ?",
                new String[]{coleccionId});
    }

    public int updateColeccion(Coleccion coleccion, String coleccionId) {
        return getWritableDatabase().update(
                ColeccionContract.Colecciones.TABLE_NAME,
                coleccion.toContentValues(),
                ColeccionContract.Colecciones.ID + " LIKE ?",
                new String[]{coleccionId}
        );
    }
}
