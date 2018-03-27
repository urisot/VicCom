package mx.com.viccom.viccom.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;


public class ResConCambioConFragment extends Fragment {
    //    onPageChangedLisener Listener;

    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private Button btnHelpContrasena_reg,btnHelpConfirmCon_res,btnCambiarCon;
    private LinearLayout llCamContrasena;
    private EditText txtContrasena_reg,txtConfirmContrasena_res;
    private ProgressBar pbProgresoCambioCon;
    private RelativeLayout rlCambioCon;
    private Animation animEntrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      /*  Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("USUARIOAPP");
        }*/

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_res_con_cambio_con, container, false);
        txtContrasena_reg = (EditText) view.findViewById(R.id.txtContrasena_reg);
        txtConfirmContrasena_res = (EditText) view.findViewById(R.id.txtConfirmContrasena_res);
        btnHelpContrasena_reg = (Button) view.findViewById(R.id.btnHelpContrasena_reg);
        btnHelpConfirmCon_res = (Button) view.findViewById(R.id.btnHelpConfirmCon_res);
        btnCambiarCon = (Button) view.findViewById(R.id.btnCambiarCon);
        pbProgresoCambioCon = (ProgressBar) view.findViewById(R.id.pbProgresoCambioCon);
        llCamContrasena = (LinearLayout) view.findViewById(R.id.llCamContrasena);
        rlCambioCon = (RelativeLayout) view.findViewById(R.id.rlCambioCon);
        animEntrar = AnimationUtils.loadAnimation(getContext(),R.anim.entrar_fragment);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      /*  try {
            Listener = (onPageChangedLisener) getActivity();
        } catch (ClassCastException e) {
            //
        }*/

        usuarioApp = clsUsuarioApp.getUsrApp();

        rlCambioCon.setAnimation(animEntrar);


        btnCambiarCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DatosValidos()){
                    RestablecerContrasena();
                }
            }
        });

    }

    private void RestablecerContrasena(){

        if ((usuarioApp.getKey() != null && usuarioApp.getKey() != "") && (usuarioApp.getId_usuarioapp() != null && usuarioApp.getId_usuarioapp() != "") ){

            String email = usuarioApp.getMail();
            String password = StringMD.getStringMessageDigest(email + txtContrasena_reg.getText().toString(), StringMD.SHA1).toUpperCase();

            ArrayList<clsParameter> Parametros = new ArrayList<clsParameter>();
            Parametros.add(new clsParameter("pass", usuarioApp.getKey()));
            Parametros.add(new clsParameter("cIdUsuarioApp", usuarioApp.getId_usuarioapp()));
            Parametros.add(new clsParameter("cContrasena", password));

            new RestablecerContrasena().execute(Parametros);
        }


    }

    private void MostrarEspera(Boolean boolMostrar) {
        if (boolMostrar){
            pbProgresoCambioCon.setVisibility(View.VISIBLE);
            llCamContrasena.setVisibility(View.INVISIBLE);

        }else{
            pbProgresoCambioCon.setVisibility(View.INVISIBLE);
            llCamContrasena.setVisibility(View.VISIBLE);

        }
    }

    public class RestablecerContrasena extends AsyncTask<ArrayList<clsParameter>,Integer,clsResultadoWCF> {


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
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/ActConUsrApp";

                String Resultado = SendToWCF.Send_Post(Url, Parametros);

                //Pudo establecer conexion
                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErrorJSON")) {

                    Gson gson = new GsonBuilder().create();
                    Resultado = Resultado.replace("\\/","/").replace("\n","");
                    Resultado = Resultado.substring(22,Resultado.length()-1);
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
               /* Fragment fragment = new ResConPideCodigoFragment();
                Listener.onChangedPasFragment(fragment);*/
                Toast.makeText(getContext(),"Contrseña restablecida con exito.",Toast.LENGTH_LONG).show();
                getActivity().finish();
            }else{
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnCambiarCon,getContext());
            }
            MostrarEspera(false);
        }
    }


    private Boolean DatosValidos(){
        boolean boolRetornar = false;
        String strContrasena =  txtContrasena_reg.getText().toString().trim();
        String strConfirmContrasena =  txtConfirmContrasena_res.getText().toString().trim();

        if (!TextUtils.isEmpty(strContrasena))
        {
            if(txtContrasena_reg.getText().toString().length()>=6){

                if (!TextUtils.isEmpty(strConfirmContrasena))
                {
                    if(strContrasena.equals(strConfirmContrasena)){
                        boolRetornar = true;

                    }else{
                        btnHelpConfirmCon_res.setVisibility(View.GONE);
                        txtConfirmContrasena_res.setError("¡ERROR!: La contraseña no es igual.");
                        // Util.customSnackBar("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.",txtContrasena,RegistroActivity.this);
                    }
                }else {
                    btnHelpConfirmCon_res.setVisibility(View.GONE);
                    txtConfirmContrasena_res.setError("¡ERROR!: vuelve a escribir la contraseña.");
                    //Util.customSnackBar("¡ERROR!: Espesifique un email valido.",txtEmail,RegistroActivity.this);
                }

            }else{
                btnHelpContrasena_reg.setVisibility(View.GONE);
                txtContrasena_reg.setError("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.");
                // Util.customSnackBar("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.",txtContrasena,RegistroActivity.this);
            }
        } else {
            btnHelpContrasena_reg.setVisibility(View.GONE);
            txtContrasena_reg.setError("¡ERROR!: Espesifique una contraseña nueva.");
            // Util.customSnackBar("¡ERROR!: Espesifique un nombre valido.",txtNombre,RegistroActivity.this);
        }

        return boolRetornar;
    }
}
