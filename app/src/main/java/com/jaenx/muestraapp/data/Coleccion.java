package com.jaenx.muestraapp.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/**
 * Created by jaenx on 16/06/2017.
 */

public class Coleccion {

    private String id;
    private String name;
    private String avatar;
    private String description;

    public Coleccion(String name, String description, String avatar) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.avatar = avatar;
    }

    public Coleccion(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(ColeccionContract.Colecciones.ID));
        name = cursor.getString(cursor.getColumnIndex(ColeccionContract.Colecciones.NAME));
        avatar = cursor.getString(cursor.getColumnIndex(ColeccionContract.Colecciones.AVATAR));
        description = cursor.getString(cursor.getColumnIndex(ColeccionContract.Colecciones.DESCRIPTION));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ColeccionContract.Colecciones.ID, id);
        values.put(ColeccionContract.Colecciones.NAME, name);
        values.put(ColeccionContract.Colecciones.AVATAR, avatar);
        values.put(ColeccionContract.Colecciones.DESCRIPTION, description);
        return values;
    }
    /**
     * Getters
     */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

}