package com.jaenx.muestraapp.Colecciones;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jaenx.muestraapp.ColeccionDetail.ColeccionDetailActivity;
import com.jaenx.muestraapp.R;
import com.jaenx.muestraapp.addeditcoleccion.AddEditColeccionActivity;
import com.jaenx.muestraapp.data.ColeccionContract;
import com.jaenx.muestraapp.data.ColeccionDbHelper;

/**
 *
 */
public class ColeccionFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_COLECCION = 2;

    private ColeccionDbHelper mColeccionDbHelper;

    private ListView mColeccionList;
    private ColeccionCursorAdapter mColeccionAdapter;
    private FloatingActionButton mAddButton;

    public ColeccionFragment() {
        // Required empty public constructor
    }

    public static ColeccionFragment newInstance() {
        return new ColeccionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_coleccion, container, false);

        // Referencias UI
        mColeccionList = (ListView) root.findViewById(R.id.coleccion_list);
        mColeccionAdapter = new ColeccionCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mColeccionList.setAdapter(mColeccionAdapter);

        // Eventos
        mColeccionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mColeccionAdapter.getItem(i);
                String currentColeccionId = currentItem.getString(
                        currentItem.getColumnIndex(ColeccionContract.Colecciones.ID));

                showDetailScreen(currentColeccionId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        //getActivity().deleteDatabase(ColeccionDbHelper.DATABASE_NAME);

        // Instancia de helper
        mColeccionDbHelper = new ColeccionDbHelper(getActivity());

        // Carga de datos
        loadColeccion();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {

                case AddEditColeccionActivity.REQUEST_ADD_COLECCION:
                    showSuccessfullSavedMessage();
                    loadColeccion();
                    break;

                case REQUEST_UPDATE_DELETE_COLECCION:
                    loadColeccion();
                    break;
            }
        }

    }

    private void loadColeccion() {
        new ColeccionLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Colección guardada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {

        Intent intent = new Intent(getActivity(), AddEditColeccionActivity.class);
        startActivityForResult(intent, AddEditColeccionActivity.REQUEST_ADD_COLECCION);

    }

    private void showDetailScreen(String coleccionId) {

        Intent intent = new Intent(getActivity(), ColeccionDetailActivity.class);
        intent.putExtra(ColeccionActivity.EXTRA_COLECCION_ID, coleccionId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_COLECCION);

    }

    private class ColeccionLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mColeccionDbHelper.getAllColeccion();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mColeccionAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }


}
