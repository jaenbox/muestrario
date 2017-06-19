package com.jaenx.muestraapp.data;

import android.provider.BaseColumns;

/**
 * Created by jaenx on 16/06/2017.
 */

public class ColeccionContract {
    public static abstract class Colecciones implements BaseColumns {
        public static final String TABLE_NAME ="coleccion";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String AVATAR = "avatar";
    }
}
