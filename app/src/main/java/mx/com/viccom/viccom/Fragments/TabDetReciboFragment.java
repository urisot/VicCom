package mx.com.viccom.viccom.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mx.com.viccom.viccom.Adapters.ConceptosRecyclerViewAdapter;
import mx.com.viccom.viccom.Clases.clsDetRecibos;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDetReciboFragment extends Fragment {

    private clsRecibos o_recibo = new clsRecibos();
    private ActualizaDetalleHttp actualizaDetalleHttp;

    //Contenedor de datos
    private ArrayList<clsDetRecibos> ArListConceptos = new ArrayList<clsDetRecibos>();

    private RecyclerView mRecyclerView;
    private ProgressBar pbCargandoConceptos;
    private ConceptosRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TabDetReciboFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_det_recibo, container, false);
        mRecyclerView = (RecyclerView)  view.findViewById(R.id.recConceptos);
        pbCargandoConceptos = (ProgressBar) view.findViewById(R.id.pbCargarConceptos);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ConceptosRecyclerViewAdapter(ArListConceptos, R.layout.conceptos_recycler_vew_item, getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ActualizarDatos();
    }

    private void ActualizarDatos(){
        //refreshLayout.setRefreshing(true);
        pbCargandoConceptos.setVisibility(View.VISIBLE);
        ArrayList<clsParameter> Parametros = new ArrayList<>();
        Parametros.add(new clsParameter("p1", o_recibo.getId_recibo()));

        actualizaDetalleHttp =  new ActualizaDetalleHttp();
        actualizaDetalleHttp.execute(Parametros);

    }

    public class ActualizaDetalleHttp extends AsyncTask<ArrayList<clsParameter>,Integer,ArrayList<clsDetRecibos>> {
        @Override
        protected ArrayList<clsDetRecibos> doInBackground(ArrayList<clsParameter>[] Parametros) {
            ArrayList<clsDetRecibos> listConceptosReturn = new ArrayList<clsDetRecibos>();
            clsParameter HttpResult = new clsParameter();

            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getDetalleRecibo";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {


                    Gson gson = new GsonBuilder().serializeNulls().create();
                    HttpResult.setParameter_name(HttpResult.getParameter_name().replace("\\/","/"));
                    String jSonResult =HttpResult.getParameter_name().substring(26,HttpResult.getParameter_name().length()-1);
                    listConceptosReturn = gson.fromJson(jSonResult, new TypeToken<ArrayList<clsDetRecibos>>(){}.getType());


                }
                else
                    HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");
                return listConceptosReturn;

            }catch (Exception e) {//4
                e.printStackTrace();
                Log.e("Error", e.toString());
                HttpResult.setParameter_name("Error");
                HttpResult.setParameter_value("Error!: "+e.toString());

                return listConceptosReturn;
            }//4

        }

        @Override
        protected void onPostExecute(ArrayList<clsDetRecibos> ListConceptos) {
            super.onPostExecute(ListConceptos);
            mAdapter.updateData(ListConceptos);
            pbCargandoConceptos.setVisibility(View.GONE);
            //refreshLayout.setRefreshing(false);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actualizaDetalleHttp.cancel(true);
    }

}
