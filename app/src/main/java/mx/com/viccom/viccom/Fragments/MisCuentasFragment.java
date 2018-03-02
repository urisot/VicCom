package mx.com.viccom.viccom.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mx.com.viccom.viccom.Activities.AgregarReciboActivity;
import mx.com.viccom.viccom.Activities.DetCuentaActivity;
import mx.com.viccom.viccom.Adapters.RecibosRecyclerViewAdapter;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisCuentasFragment extends Fragment {

    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private FloatingActionButton fabAgregarCuentas;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private ActualizaHttp actualizaHttp;


    //Contenedor de datos
    private ArrayList<clsRecibos> ArListRecibos = new ArrayList<clsRecibos>();

    //Adaptador y manejador de datos
    private RecibosRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public MisCuentasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("USUARIOAPP");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_cuentas, container, false);

        fabAgregarCuentas = (FloatingActionButton) view.findViewById(R.id.fabAgregarCuentas);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recMisRecibos);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new RecibosRecyclerViewAdapter(ArListRecibos, R.layout.cuentas_recycler_view_item, new RecibosRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(clsRecibos recibo, int position) {



                //Toast.makeText(act_MenuPrincipal.this, recibo.getNombre() + " - " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(clsRecibos recibo, int position) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(),R.style.AppCompatAlertDialogStyle);
                alertDialog.setMessage("¿Deseas eliminar el recibo con la cuenta ["+recibo.getId_cuenta()+"] "+recibo.getRazon_social()+"?");
                alertDialog.setTitle("Eliminar recibo");
                alertDialog.setIcon(android.R.drawable.ic_delete);
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                alertDialog.show();

            }

            @Override
            public void onButton1ItemClick(clsRecibos recibo, int position) {
                Toast.makeText(getContext(), "Pagar recibo de: "+recibo.getRazon_social() + " - " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onButton2ItemClick(clsRecibos recibo, int position) {
                Intent intento = new Intent(getActivity(),DetCuentaActivity.class);
                intento.putExtra("RECIBO",  recibo);
                startActivityForResult(intento, Util.SOLICITUD_VER_DETALLE_CUENTA);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // Seteamos los colores que se usarán a lo largo de la animación
        refreshLayout.setColorSchemeResources(
                R.color.back1,
                R.color.back2,
                R.color.back3,
                R.color.back4
        );

        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh()
                    {
                        ActualizarDatos();
                    }
                }
        );


        fabAgregarCuentas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getActivity(),AgregarReciboActivity.class);
                intento.putExtra("USUARIOAPP",usuarioApp);
                startActivityForResult(intento, Util.SOLICITUD_AGREGAR_RECIBOS);
            }
        });

        ActualizarDatos();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Util.SOLICITUD_AGREGAR_RECIBOS){
            if(resultCode == Util.RESULTADO_OK ){
                ActualizarDatos();
            }
        }
    }

    private void ActualizarDatos(){
        refreshLayout.setRefreshing(true);
        ArrayList<clsParameter> Parametros = new ArrayList<>();
        Parametros.add(new clsParameter("p1", usuarioApp.getId_usuarioapp()));

        actualizaHttp = new ActualizaHttp();
        actualizaHttp.execute(Parametros);

    }

    public class ActualizaHttp extends AsyncTask<ArrayList<clsParameter>,Integer,ArrayList<clsRecibos>> {
        private clsResultadoWCF o_resultadoWCF= new clsResultadoWCF();
        @Override
        protected ArrayList<clsRecibos> doInBackground(ArrayList<clsParameter>[] Parametros) {
            ArrayList<clsRecibos> listRecibosReturn = new ArrayList<clsRecibos>();
            clsParameter HttpResult = new clsParameter();

            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getReciboUsrApp";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {


                    Gson gson = new GsonBuilder().serializeNulls().create();
                    HttpResult.setParameter_name(HttpResult.getParameter_name().replace("\\/","/"));
                    //String jSonResult =HttpResult.getParameter_name().substring(24,HttpResult.getParameter_name().length()-1);
                    listRecibosReturn = gson.fromJson(HttpResult.getParameter_name(), new TypeToken<ArrayList<clsRecibos>>(){}.getType());

                    o_resultadoWCF.setId(0);
                    o_resultadoWCF.setComando("getReciboUsrApp");
                    o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con exito");
                    o_resultadoWCF.setError_number(0);
                    o_resultadoWCF.setError_menssage("");
                    o_resultadoWCF.setFolio_registro("");
                    o_resultadoWCF.setFecha(Util.getFechaActual());

                }
                else {
                    o_resultadoWCF.setId(0);
                    o_resultadoWCF.setComando("getReciboUsrApp");
                    o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con error");
                    o_resultadoWCF.setError_number(1);
                    o_resultadoWCF.setError_menssage("Ha ocurrido un error de conexion.");
                    o_resultadoWCF.setFolio_registro("");
                    o_resultadoWCF.setFecha(Util.getFechaActual());
                    // HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");
                }

           /*     return listRecibosReturn;*/

            }catch (Exception e) {//4
                e.printStackTrace();
                o_resultadoWCF.setId(0);
                o_resultadoWCF.setComando("getReciboUsrApp");
                o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con error");
                o_resultadoWCF.setError_number(1);
                o_resultadoWCF.setError_menssage("Error!: "+e.toString());
                o_resultadoWCF.setFolio_registro("");
                o_resultadoWCF.setFecha(Util.getFechaActual());
                Log.e("Error", e.toString());
            /*    HttpResult.setParameter_name("Error");
                HttpResult.setParameter_value("Error!: "+e.toString());*/


            }//4

            return listRecibosReturn;


        }

        @Override
        protected void onPostExecute(ArrayList<clsRecibos> ListRecibos) {
            super.onPostExecute(ListRecibos);
            if (!(o_resultadoWCF.getError_number()>0)){
                mAdapter.updateData(ListRecibos);
                refreshLayout.setRefreshing(false);
            }else{
                refreshLayout.setRefreshing(false);
                Util.customSnackBar(o_resultadoWCF.getError_menssage(),fabAgregarCuentas,getContext());
            }


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
            refreshLayout.setRefreshing(false);
        }
    }

    public class ActualizaListRecibos extends AsyncTask<Void,Integer,ArrayList<clsRecibos>>{
        static final int DURACION = 3 * 1000; // 3 segundos de carga
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList<clsRecibos> doInBackground(Void... voids) {
            ArrayList<clsRecibos> ArListResibosReturn = new ArrayList<clsRecibos>();
            ArListResibosReturn =clsRecibos. CargarListaRecibosTodos();
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return ArListResibosReturn;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<clsRecibos> clsRecibos) {
            mAdapter.updateData(clsRecibos);
            refreshLayout.setRefreshing(false);
            super.onPostExecute(clsRecibos);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actualizaHttp.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // actualizaHttp.cancel(true);
    }

}
