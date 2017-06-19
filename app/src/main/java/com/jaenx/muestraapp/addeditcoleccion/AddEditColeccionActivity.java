package com.jaenx.muestraapp.addeditcoleccion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jaenx.muestraapp.Colecciones.ColeccionActivity;
import com.jaenx.muestraapp.R;

public class AddEditColeccionActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_COLECCION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_coleccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String coleccionId = getIntent().getStringExtra(ColeccionActivity.EXTRA_COLECCION_ID);

        setTitle(coleccionId == null ? "Añadir coleccion" : "Editar coleccion");

        AddEditColeccionFragment addEditColeccionFragment = (AddEditColeccionFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_coleccion_container);
        if (addEditColeccionFragment == null) {
            addEditColeccionFragment = AddEditColeccionFragment.newInstance(coleccionId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_coleccion_container, addEditColeccionFragment)
                    .commit();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
