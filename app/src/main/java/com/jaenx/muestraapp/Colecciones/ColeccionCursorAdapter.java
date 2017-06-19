package com.jaenx.muestraapp.Colecciones;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaenx.muestraapp.R;

/**
 * Created by jaenx on 16/06/2017.
 */

public class ColeccionCursorAdapter extends CursorAdapter {

    public ColeccionCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_coleccion, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView icono = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView nombre = (TextView) view.findViewById(R.id.tv_name);


        icono.setImageResource(R.drawable.ic_work_black_24dp);
        nombre.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
}
