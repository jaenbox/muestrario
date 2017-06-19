package com.jaenx.muestraapp.ColeccionDetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaenx.muestraapp.Colecciones.ColeccionActivity;
import com.jaenx.muestraapp.Colecciones.ColeccionFragment;
import com.jaenx.muestraapp.R;
import com.jaenx.muestraapp.addeditcoleccion.AddEditColeccionActivity;
import com.jaenx.muestraapp.data.Coleccion;
import com.jaenx.muestraapp.data.ColeccionDbHelper;

public class ColeccionDetailFragment extends Fragment {

    private static final String ARG_COLECCION_ID = "coleccionId";

    private String mColeccionId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mDescription;

    private ColeccionDbHelper mColeccionDbHelper;


    public ColeccionDetailFragment() {
        // Required empty public constructor
    }

    public static ColeccionDetailFragment newInstance(String coleccionId) {
        ColeccionDetailFragment fragment = new ColeccionDetailFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coleccion_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mDescription = (TextView) root.findViewById(R.id.tv_description);

        mColeccionDbHelper = new ColeccionDbHelper(getActivity());

        loadColeccion();
        return root;
    }

    private void loadColeccion() {
        new GetColeccionByIdTask().execute();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteColeccionTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ColeccionFragment.REQUEST_UPDATE_DELETE_COLECCION) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showColeccion(Coleccion coleccion) {
        mCollapsingView.setTitle(coleccion.getName());
        /*
            Falta implementar la imagen
        */

        mDescription.setText(coleccion.getDescription());

    }

    private void showEditScreen() {

        Intent intent = new Intent(getActivity(), AddEditColeccionActivity.class);
        intent.putExtra(ColeccionActivity.EXTRA_COLECCION_ID, mColeccionId);
        startActivityForResult(intent, ColeccionFragment.REQUEST_UPDATE_DELETE_COLECCION);

    }

    private void showColeccionScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar colección", Toast.LENGTH_SHORT).show();
    }

    private class GetColeccionByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mColeccionDbHelper.getColeccionById(mColeccionId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showColeccion(new Coleccion(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteColeccionTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mColeccionDbHelper.deleteColeccion(mColeccionId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showColeccionScreen(integer > 0);
        }

    }
}
