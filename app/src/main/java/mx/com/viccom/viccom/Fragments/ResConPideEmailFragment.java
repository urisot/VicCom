package mx.com.viccom.viccom.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import mx.com.viccom.viccom.Activities.RegistroActivity;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.Util;


public class ResConPideEmailFragment extends Fragment {

    onPageChangedLisener Listener;

    //private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txt_email_res;
    private Button btnHelpEmail_res,btnEnviar_res;
    private ProgressBar restablece_progress;
    private LinearLayout llRestablece;

    public ResConPideEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      /*  Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("USUARIOAPP");
        }*/
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_con_pide_email, container, false);

        txt_email_res = (EditText) view.findViewById(R.id.txt_email_res);
        btnHelpEmail_res = (Button) view.findViewById(R.id.btnHelpEmail_res);
        btnEnviar_res = (Button) view.findViewById(R.id.btnEnviar_res);
        restablece_progress = (ProgressBar) view.findViewById(R.id.restablece_progress);
        llRestablece = (LinearLayout) view.findViewById(R.id.llRestablece);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Listener = (onPageChangedLisener) getActivity();
        } catch (ClassCastException e) {
            //
        }

        btnEnviar_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DatosValidos()){
                    EnviarCorreoResablecer();
                }
            }
        });
    }

    private void EnviarCorreoResablecer(){

        String strCodigo = Util.getCodigoAleatorio();
        Util.setCodigoRegistro(strCodigo);

        ArrayList<clsParameter> Parametros = new ArrayList<clsParameter>();
        Parametros.add(new clsParameter("cEmailAEnviar", txt_email_res.getText().toString()));
        Parametros.add(new clsParameter("cCodigo", strCodigo));

        new EnviarCodigoResContEmil().execute(Parametros);
    }

    private void MostrarEspera(Boolean boolMostrar) {
        if (boolMostrar){
            restablece_progress.setVisibility(View.VISIBLE);
            llRestablece.setVisibility(View.INVISIBLE);

        }else{
            restablece_progress.setVisibility(View.INVISIBLE);
            llRestablece.setVisibility(View.VISIBLE);

        }
    }

    public class EnviarCodigoResContEmil extends AsyncTask<ArrayList<clsParameter>,Integer,clsResultadoWCF> {


        @Override
        protected void onPreExecute() {
            MostrarEspera(true);
            super.onPreExecute();
        }

        @Override
        protected clsResultadoWCF doInBackground(ArrayList<clsParameter>[] alParametros) {
            clsResultadoWCF oResultadoWCF = new clsResultadoWCF();
            final ArrayList<clsParameter> Parametros = alParametros[0];
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/CamConEmail";

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
        }


        @Override
        protected void onPostExecute(clsResultadoWCF oResultadoWCF) {
            super.onPostExecute(oResultadoWCF);
            if (oResultadoWCF.getError_number() == 0){

                //Listener.onChangedPasPage(1);
                Fragment fragment = new ResConPideCodigoFragment();
                Listener.onChangedPasFragment(fragment);
            }else{
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnEnviar_res,getContext());
            }
            MostrarEspera(false);
        }
    }

    private Boolean DatosValidos(){
        boolean boolRetornar = false;
        String strMail =  txt_email_res.getText().toString();

            if (Util.isValidEmail(strMail))
            {
                boolRetornar = true;
            }else {
                btnHelpEmail_res.setVisibility(View.GONE);
                txt_email_res.setError("¡ERROR!: Espesifique un email valido.");
                //Util.customSnackBar("¡ERROR!: Espesifique un email valido.",txtEmail,RegistroActivity.this);
            }

        return boolRetornar;
    }
}
