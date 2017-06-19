package com.jaenx.muestraapp.Colecciones;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaenx.muestraapp.R;

public class ColeccionActivity extends AppCompatActivity {

    public static final String EXTRA_COLECCION_ID = "extra_coleccion_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coleccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ColeccionFragment fragment = (ColeccionFragment) getSupportFragmentManager().findFragmentById(R.id.content_coleccion);

        if(fragment == null) {
            fragment = ColeccionFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_coleccion, fragment)
                    .commit();
        }

    }

}
