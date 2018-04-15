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
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mx.com.viccom.viccom.Activities.AgregarReciboActivity;
import mx.com.viccom.viccom.Activities.DetCuentaActivity;
import mx.com.viccom.viccom.Activities.RealizarPagoActivity;
import mx.com.viccom.viccom.Activities.TabPagosActivity;
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
    private EliminaCuenta eliminaCuenta;
    private ImageView imgLogo,imgCiudadViva;
    private Animation animEntrar,animSalir;
    private CardView cvNuevoRecibo;
    private ImageButton btnAddReciboNuevo;


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
        imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
        imgCiudadViva = (ImageView) view.findViewById(R.id.imgCiudad);
        cvNuevoRecibo = (CardView) view.findViewById(R.id.cvNuevoRecibo);
        btnAddReciboNuevo = (ImageButton) view.findViewById(R.id.btnAddReciboNuevo);

        animEntrar = AnimationUtils.loadAnimation(getContext(),R.anim.entrar);
        animSalir = AnimationUtils.loadAnimation(getContext(),R.anim.salir);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imgLogo.setAnimation(animEntrar);
        imgCiudadViva.setAnimation(animSalir);

        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new RecibosRecyclerViewAdapter(ArListRecibos, R.layout.recibos_recycler_view_item, new RecibosRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(clsRecibos recibo, int position) {

                Intent intento = new Intent(getActivity(),DetCuentaActivity.class);
                intento.putExtra("RECIBO",  recibo);
                startActivityForResult(intento, Util.SOLICITUD_VER_DETALLE_CUENTA);

                //Toast.makeText(act_MenuPrincipal.this, recibo.getNombre() + " - " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongItemClick(clsRecibos recibo, int position) {




            }

            @Override
            public void onButton1ItemClick(clsRecibos recibo, int position) {
                if (recibo.getTotal() >0){
                    PagarRecibo(recibo);
                }

                //Toast.makeText(getContext(), "Pagar recibo de: "+recibo.getRazon_social() + " - " + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onButton2ItemClick(clsRecibos recibo, int position) {
                final clsRecibos o_recibo = recibo;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(),R.style.AppCompatAlertDialogStyle);
                alertDialog.setMessage("¿Deseas eliminar el recibo con la cuenta ["+recibo.getId_cuenta()+"] "+recibo.getRazon_social()+"?");
                alertDialog.setTitle("Eliminar recibo");
                alertDialog.setIcon(android.R.drawable.ic_input_delete);
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        ArrayList<clsParameter> listParametros = new ArrayList<clsParameter>();
                        //liasParametros.add(new clsParameter("pass", "BA587B93B5E6A993F5A3D"));
                        listParametros.add(new clsParameter("pass", usuarioApp.getKey().toString()));
                        listParametros.add(new clsParameter("cIdUsuarioApp", usuarioApp.getId_usuarioapp().toString()));
                        listParametros.add(new clsParameter("nIdCueta", o_recibo.getId_cuenta()+""));

                        eliminaCuenta = new EliminaCuenta();
                        eliminaCuenta.execute(listParametros);

                    }
                });
                alertDialog.show();

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

        btnAddReciboNuevo.setOnClickListener(new View.OnClickListener() {

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

    private void PagarRecibo( clsRecibos o_recibo) {
        //Intent intent = new Intent(getContext(), RealizarPagoActivity.class);
        Intent intent = new Intent(getContext(), TabPagosActivity.class);
        intent.putExtra("RECIBO", o_recibo);
        startActivity(intent);
    }

    private void ActualizarDatos(){
        refreshLayout.setRefreshing(true);
        ArrayList<clsParameter> Parametros = new ArrayList<>();
        Parametros.add(new clsParameter("p1", usuarioApp.getId_usuarioapp()));

        actualizaHttp = new ActualizaHttp();
        actualizaHttp.execute(Parametros);

    }

    public class EliminaCuenta extends AsyncTask<ArrayList<clsParameter>,Integer,clsResultadoWCF> {

        @Override
        protected clsResultadoWCF doInBackground(ArrayList<clsParameter>[] Parametros) {
            clsResultadoWCF resultadoWCF = new clsResultadoWCF();
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/DelCuentasUsrApp";

                String Resultado = SendToWCF.Send_Post(Url, Parametros[0]);

                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErrorJSON")) {

                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Resultado = Resultado.replace("\\/","/").replace("\n","");
                    Resultado = Resultado.substring(26,Resultado.length()-1);
                    resultadoWCF = gson.fromJson(Resultado, clsResultadoWCF.class);

                } else {

                    resultadoWCF.setOperacion("Establecioendo conexion WCF");
                    resultadoWCF.setComando("Conexion");
                    resultadoWCF.setError_number(1);
                    resultadoWCF.setFecha(Util.getFechaActual());
                    resultadoWCF.setFolio_registro("");
                    resultadoWCF.setError_menssage("Error de Conexion.");

                }

            }catch (Exception e) {//4
                e.printStackTrace();

                resultadoWCF.setOperacion("Establecioendo conexion");
                resultadoWCF.setComando("Conexion");
                resultadoWCF.setError_number(1);
                resultadoWCF.setFecha(Util.getFechaActual());
                resultadoWCF.setFolio_registro("");
                resultadoWCF.setError_menssage(e.getMessage());

            }//4

            return resultadoWCF;
        }

        @Override
        protected void onPostExecute(clsResultadoWCF clsResultadoWCF) {
            super.onPostExecute(clsResultadoWCF);
            if(clsResultadoWCF.getError_number()>0){
               Util.customSnackBar(clsResultadoWCF.getError_menssage(),fabAgregarCuentas,getContext());
            }else {
                ActualizarDatos();
            }

        }
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

         /*           o_resultadoWCF.setId(0);
                    o_resultadoWCF.setComando("getReciboUsrApp");
                    o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con exito");
                    o_resultadoWCF.setError_number(0);
                    o_resultadoWCF.setError_menssage("");
                    o_resultadoWCF.setFolio_registro("");
                    o_resultadoWCF.setFecha(Util.getFechaActual());*/

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
                refreshLayout.setRefreshing(false);

                if (ListRecibos.size() > 0){
                    cvNuevoRecibo.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                   // fabAgregarCuentas.setVisibility(View.VISIBLE);

                    mAdapter.updateData(ListRecibos);
                }else{
                    cvNuevoRecibo.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                   // fabAgregarCuentas.setVisibility(View.GONE);
                }


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
    
/*
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
*/


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
