package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

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
public class PagoTarjetaFragment extends Fragment {

    private clsRecibos o_recibo = new clsRecibos();
    private ImageView ivTipoTarjeta;
    private TextView PikerCambiarTarjeta,txtNoTarjeta,txtImporteRequerido,txtComiciones,txtTotal;
    private Button btnPagarTarjeta;
    private EjecutaCargoHttp ejecutaCargoHttp;
    public PagoTarjetaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_pago_tarjeta, container, false);
        ivTipoTarjeta = (ImageView) view.findViewById(R.id.ivTipoTarjeta);
        PikerCambiarTarjeta = (TextView) view.findViewById(R.id.PikerCambiarTarjeta);
        txtNoTarjeta = (TextView) view.findViewById(R.id.txtNoTarjeta);
        txtImporteRequerido = (TextView) view.findViewById(R.id.txtImporteRequerido);
        txtComiciones = (TextView) view.findViewById(R.id.txtComiciones);
        txtTotal = (TextView) view.findViewById(R.id.txtTotal);
        btnPagarTarjeta = (Button) view.findViewById(R.id.btnPagarTarjeta);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (o_recibo.getTotal() != 0 ){
            txtImporteRequerido.setText(o_recibo.getPago_requerido()+"");
        }
        txtComiciones.setText(CalculaComicion(o_recibo.getTotal()));
        txtTotal.setText(calculaTotal());

        btnPagarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<clsParameter> Parametros = new ArrayList<clsParameter>();
                Parametros.add(new clsParameter("CARD_NUM", "5134422031476272"));
                Parametros.add(new clsParameter("CARD_OWN", "JAIME URIEL SOTO MENDEZ"));
                Parametros.add(new clsParameter("CARD_EXP_DT", "1219"));
                Parametros.add(new clsParameter("CARD_CVV", "162"));
                Parametros.add(new clsParameter("CARD_AVS_ADDR", "conocida 123"));
                Parametros.add(new clsParameter("CARD_AVS_ZIPCODE", "25700"));
                Parametros.add(new clsParameter("CARD_TYPE", "mastercard"));
                Parametros.add(new clsParameter("ORD_CURR_CD", "MXN"));
                Parametros.add(new clsParameter("ORD_ID", "654321"));
                Parametros.add(new clsParameter("ORD_AMT", txtTotal.getText().toString()));
                Parametros.add(new clsParameter("ORD_CONCEPT", "Recibo de agua "+o_recibo.getCiclo_facturado().toString()));
                Parametros.add(new clsParameter("user", "Pruebasbw"));
                Parametros.add(new clsParameter("CUST_IP", "201.144.165.83"));
                Parametros.add(new clsParameter("CUST_USER_ID", "231456"));
                Parametros.add(new clsParameter("CUST_PHONE", "8661207705"));
                Parametros.add(new clsParameter("CUST_EMAIL", "leiru87@gmail.com"));

                 ejecutaCargoHttp = new EjecutaCargoHttp();
                 ejecutaCargoHttp.execute(Parametros);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ejecutaCargoHttp.cancel(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String calculaTotal() {
        double douImporte = Double.parseDouble(txtImporteRequerido.getText().toString()) + Double.parseDouble(txtComiciones.getText().toString());
        return  douImporte+"";
    }

    private String CalculaComicion(float importe){
        // String strReturn ="";

        float floPorComicion = 0;
        double douImpComFija = 2.5;
        double douImComicion = 0;


        floPorComicion = Math.round(importe * 0.264);
        douImComicion = Math.round((floPorComicion + douImpComFija) * 0.16);

        return  douImComicion+"";
    }

    public class EjecutaCargoHttp extends AsyncTask<ArrayList<clsParameter>,Integer,clsResultadoWCF> {
       private clsResultadoWCF oResultadoWCF = new clsResultadoWCF();
        @Override
        protected clsResultadoWCF doInBackground(ArrayList<clsParameter>... arParametros) {

            try {

                String Resultado = SendToWCF.Send_Post_Banwire(arParametros[0]);

                //Pudo establecer conexion
                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErrorJSON")) {

                    JSONObject jsonObject = new JSONObject(Resultado);

                    Gson gson = new GsonBuilder().create();
                    Resultado = Resultado.replace("\\/","/").replace("\n","");
                    Resultado = Resultado.substring(21,Resultado.length()-1);
                    oResultadoWCF = gson.fromJson(Resultado, clsResultadoWCF.class);


                } else {

                    oResultadoWCF.setOperacion("Establecioendo conexion");
                    oResultadoWCF.setComando("Conexion");
                    oResultadoWCF.setError_number(1);
                    oResultadoWCF.setFecha(Util.getFechaActual());
                    oResultadoWCF.setFolio_registro("");
                    oResultadoWCF.setError_menssage("Error de Conexion.");

                }



            }catch (Exception e) {
                e.printStackTrace();
                oResultadoWCF.setOperacion("Establecioendo conexion");
                oResultadoWCF.setComando("Error");
                oResultadoWCF.setError_number(1);
                oResultadoWCF.setFecha(Util.getFechaActual());
                oResultadoWCF.setFolio_registro("");
                oResultadoWCF.setError_menssage("Error en el Servicio. "+e.toString());

                return oResultadoWCF;
            }
            return oResultadoWCF;
        }

        @Override
        protected void onPreExecute() {
            //MostrarEspera(true);
            super.onPreExecute();
        }

       /* @Override
        protected clsResultadoWCF doInBackground(clsUsuarioApp... oUsuarioApps) {
            clsResultadoWCF oResultadoWCF = new clsResultadoWCF();
            final clsUsuarioApp usuarioApp = oUsuarioApps[0];
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/EnvCodEmail";

                ArrayList<clsParameter> Parametros = new ArrayList<clsParameter>();
                Parametros.add(new clsParameter("cEmailAEnviar", usuarioApp.getMail()));
                Parametros.add(new clsParameter("cTitular", usuarioApp.getNombre()));
                Parametros.add(new clsParameter("cCodigo", usuarioApp.getCelular()));



                String Resultado = SendToWCF.Send_Post(Url, Parametros);

                //Pudo establecer conexion
                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErrorJSON")) {

                    Gson gson = new GsonBuilder().create();
                    Resultado = Resultado.replace("\\/","/").replace("\n","");
                    Resultado = Resultado.substring(21,Resultado.length()-1);
                    oResultadoWCF = gson.fromJson(Resultado, clsResultadoWCF.class);


                } else {

                    oResultadoWCF.setOperacion("Establecioendo conexion");
                    oResultadoWCF.setComando("Conexion");
                    oResultadoWCF.setError_number(1);
                    oResultadoWCF.setFecha(Util.getFechaActual());
                    oResultadoWCF.setFolio_registro("");
                    oResultadoWCF.setError_menssage("Error de Conexion.");

                }



            }catch (Exception e) {
                e.printStackTrace();
                oResultadoWCF.setOperacion("Establecioendo conexion");
                oResultadoWCF.setComando("Error");
                oResultadoWCF.setError_number(1);
                oResultadoWCF.setFecha(Util.getFechaActual());
                oResultadoWCF.setFolio_registro("");
                oResultadoWCF.setError_menssage("Error en el Servicio. "+e.toString());

                return oResultadoWCF;
            }
            return oResultadoWCF;

        }*/


        @Override
        protected void onPostExecute(clsResultadoWCF oResultadoWCF) {
            super.onPostExecute(oResultadoWCF);

         /*   if (oResultadoWCF.getError_number() == 0){
                btnRegistrar.setVisibility(View.GONE);

                String strMacAddress = Util.getMacAddress(getContext());

                clsUsuarioApp oUsuarioApp = new clsUsuarioApp(""
                        ,txtNombre.getText().toString()
                        ,txtEmail.getText().toString()
                        ,""
                        ,txtContrasena.getText().toString()
                        ,strMacAddress
                        ,""
                        ,"" );

                Fragment fragment = new RegistroPideCodigoFragment();

                Bundle argumentos = new Bundle();
                argumentos.putParcelable("REGUSUARIOAPP",oUsuarioApp);
                fragment.setArguments(argumentos);

                Listener.onChangedPasFragment(fragment);

                // PedirCodigoActivacion(oUsuarioApp);



            }else{
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnRegistrar,getContext());

            }
            MostrarEspera(false);*/
        }
    }
}
