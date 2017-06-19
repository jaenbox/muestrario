package com.jaenx.muestraapp.addeditcoleccion;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaenx.muestraapp.R;
import com.jaenx.muestraapp.data.Coleccion;
import com.jaenx.muestraapp.data.ColeccionDbHelper;

public class AddEditColeccionFragment extends Fragment {
    private static final String ARG_COLECCION_ID = "arg_coleccion_id";

    private String mColeccionId;

    private ColeccionDbHelper mColeccionDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mDescriptionField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mDescriptionLabel;

    public AddEditColeccionFragment() {
        // Required empty public constructor
    }

    public static AddEditColeccionFragment newInstance(String coleccionId) {
        AddEditColeccionFragment fragment = new AddEditColeccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLECCION_ID, coleccionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColeccionId = getArguments().getString(ARG_COLECCION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_edit_coleccion, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditColeccion();
            }
        });

        mColeccionDbHelper = new ColeccionDbHelper(getActivity());

        // Carga de datos
        if (mColeccionId != null) {
            loadColeccion();
        }

        return root;

    }

    private void loadColeccion() {
        new GetColeccionByIdTask().execute();
    }

    private void addEditColeccion() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String description = mDescriptionField.getText().toString();
        String avatar= "";

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Coleccion coleccion = new Coleccion(name, description, avatar);

        new AddEditColeccionTask().execute(coleccion);

    }

    private void showColeccionScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }
        // Se finliza la actividad
        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(), "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showLawyer(Coleccion coleccion) {
        mNameField.setText(coleccion.getName());
        mDescriptionField.setText(coleccion.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(), "Error al editar colección", Toast.LENGTH_SHORT).show();
    }

    private class GetColeccionByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mColeccionDbHelper.getColeccionById(mColeccionId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Coleccion(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditColeccionTask extends AsyncTask<Coleccion, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Coleccion... coleccion) {
            if (mColeccionId != null) {
                return mColeccionDbHelper.updateColeccion(coleccion[0], mColeccionId) > 0;

            } else {
                return mColeccionDbHelper.saveColeccion(coleccion[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showColeccionScreen(result);
        }

    }
}
