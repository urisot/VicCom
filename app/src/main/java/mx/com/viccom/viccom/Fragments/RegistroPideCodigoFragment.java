package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tooltip.Tooltip;

import java.util.ArrayList;

import mx.com.viccom.viccom.Activities.CodigoRegistroActivity;
import mx.com.viccom.viccom.Activities.MenuActivity;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroPideCodigoFragment extends Fragment {

    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txtCodigoReg;
    private Button btnReenviarCodigo,btnAyudaCod;
    private ProgressBar pbValidando;
    private ImageView imgCodigo;
    private LinearLayout llCodigo;
    private Animation animCaida,animSubir;
    private RelativeLayout rlPrincipal;

    public RegistroPideCodigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("REGUSUARIOAPP");
        }

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registro_pide_codigo, container, false);
        txtCodigoReg = (EditText) view.findViewById(R.id.txtCodigo_reg);
        btnReenviarCodigo = (Button) view.findViewById(R.id.btnEnviarNvo_reg);
        btnAyudaCod = (Button) view.findViewById(R.id.btnHelpCodigo);
        pbValidando = (ProgressBar) view.findViewById(R.id.pbProgresoRegistro);
        imgCodigo = (ImageView) view.findViewById(R.id.imgCodigo);
        llCodigo = (LinearLayout) view.findViewById(R.id.llCodigo);
        rlPrincipal = (RelativeLayout) view.findViewById(R.id.rlPrincipal);

        animCaida = AnimationUtils.loadAnimation(getContext(),R.anim.bajar);
        animSubir = AnimationUtils.loadAnimation(getContext(),R.anim.subir);
//        animEntrar = AnimationUtils.loadAnimation(getContext(),R.anim.entrar_fragment);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        rlPrincipal.setAnimation(animEntrar);

        llCodigo.setAnimation(animCaida);
        imgCodigo.setAnimation(animSubir);

        txtCodigoReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(),"start "+start+" before "+before+" count "+count+ " secuence "+s,Toast.LENGTH_SHORT).show();
                if (s.length() == 6){
                    String strCodigo = Util.getCodigoRegistro();
                    String strTxtCod = txtCodigoReg.getText().toString();
                    if (strTxtCod.equals(strCodigo)){

                        String strContrasena = StringMD.getStringMessageDigest(usuarioApp.getMail() + usuarioApp.getContrasena(), StringMD.SHA1).toUpperCase();

                        clsUsuarioApp oUsuarioApp = new clsUsuarioApp(""
                                ,usuarioApp.getNombre()
                                ,usuarioApp.getMail()
                                ,""
                                ,strContrasena
                                ,usuarioApp.getMac_address()
                                ,""
                                ,"" );
                        new AgregarUsrAppHttp().execute(oUsuarioApp);

                    }else {
                        Util.customSnackBar("Verifique el codigo",txtCodigoReg,getContext());
                    }

                }else {
                    //Campos_Requeridos();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });

        btnReenviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCodigo = Util.getCodigoRegistro();

                // EnviarCorreoRegistro(strCodigo);
            }
        });
        btnAyudaCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuestraTooltip(view , Gravity.START,"Código de 6 digitos.");
            }
        });
    }

    private void MuestraTooltip(View view, int gravity, String mensaje) {
        Button btn = (Button) view;

        Tooltip tooltip = new Tooltip.Builder(btn)
                .setText(mensaje)
                .setTextColor(Color.WHITE)
                .setGravity(gravity)
                .setCornerRadius(8f)
                .setDismissOnClick(true)
                .show();
    }

    private void MostrarEspera(Boolean boolMostrar) {
        if (boolMostrar){
            pbValidando.setVisibility(View.VISIBLE);
            llCodigo.setVisibility(View.INVISIBLE);
        }else{
            pbValidando.setVisibility(View.INVISIBLE);
            llCodigo.setVisibility(View.VISIBLE);
        }
    }

    public class AgregarUsrAppHttp extends AsyncTask<clsUsuarioApp,Integer,clsResultadoWCF> {
        clsUsuarioApp o_UsuarioRegistrado = new clsUsuarioApp();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MostrarEspera(true);
        }

        @Override
        protected clsResultadoWCF doInBackground(clsUsuarioApp... oUsuarioApps) {
            clsResultadoWCF oResultadoWCF = new clsResultadoWCF();
            final clsUsuarioApp usuarioApp = oUsuarioApps[0];
            //clsParameter HttpResult = new clsParameter();
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/InsUsrApp";

                ArrayList<clsParameter> Parametros = new ArrayList<clsParameter>();
                Parametros.add(new clsParameter("cNombre", usuarioApp.getNombre()));
                Parametros.add(new clsParameter("cMail", usuarioApp.getMail()));
                Parametros.add(new clsParameter("cCelular", usuarioApp.getCelular()));
                Parametros.add(new clsParameter("cContrasena", usuarioApp.getContrasena()));
                Parametros.add(new clsParameter("cMacAdres", usuarioApp.getMac_address()));


                String Resultado = SendToWCF.Send_Post(Url, Parametros);

                //Pudo establecer conexion
                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErroJSON")) {

                    Gson gson = new GsonBuilder().create();
                    Resultado = Resultado.replace("\\/","/").replace("\n","");
                    Resultado = Resultado.substring(19,Resultado.length()-1);
                    oResultadoWCF = gson.fromJson(Resultado, clsResultadoWCF.class);

                    //Inserto correctamente el usuario y obtobo el folio consecutivo de registro
                    if (oResultadoWCF.getFolio_registro().toString().length()>0){

                        Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/ValidaUsrApp";

                        String strMailUsr = usuarioApp.getMail();
                        String strContrasenaUsr = usuarioApp.getContrasena();

                        ArrayList<clsParameter> ParametrosUsr = new ArrayList<clsParameter>();
                        ParametrosUsr.add(new clsParameter("cMail", strMailUsr));
                        ParametrosUsr.add(new clsParameter("cContrasena", strContrasenaUsr));

                        String ResultadoUsr = SendToWCF.Send_Post(Url, ParametrosUsr);

                        if (!ResultadoUsr.equals("ErrorConexion") && !ResultadoUsr.equals("ErrorURL") && !ResultadoUsr.equals("ErrorJSON")) {

                            Gson gsonUsr = new GsonBuilder().create();
                            ResultadoUsr = ResultadoUsr.replace("\\/","/").replace("\n","");
                            ResultadoUsr = ResultadoUsr.substring(22,ResultadoUsr.length()-1);
                            o_UsuarioRegistrado = gsonUsr.fromJson(ResultadoUsr, clsUsuarioApp.class);

                            if (o_UsuarioRegistrado.getId_usuarioapp().toString().length()>0){

                            }else{
                                oResultadoWCF.setOperacion("Insertando Usuario");
                                oResultadoWCF.setComando("Conexion");
                                oResultadoWCF.setError_number(1);
                                oResultadoWCF.setFecha(Util.getFechaActual());
                                oResultadoWCF.setFolio_registro("");
                                oResultadoWCF.setError_menssage(o_UsuarioRegistrado.getNombre());
                            }

                        } else {

                            oResultadoWCF.setOperacion("Establecioendo conexion al validar");
                            oResultadoWCF.setComando("Conexion");
                            oResultadoWCF.setError_number(1);
                            oResultadoWCF.setFecha(Util.getFechaActual());
                            oResultadoWCF.setFolio_registro("");
                            oResultadoWCF.setError_menssage("Error de Conexion.");

                        }


                    }else{
                        oResultadoWCF.setOperacion("Registrando Usuario");
                        oResultadoWCF.setComando("Registro");
                        oResultadoWCF.setError_number(1);
                        oResultadoWCF.setFecha(Util.getFechaActual());
                        oResultadoWCF.setFolio_registro("");
                        oResultadoWCF.setError_menssage("No pudo obtener elfolio consecutivo.");
                    }


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
                Log.e("Error", e.toString());
                //HttpResult.setParameter_name("Error");
                //HttpResult.setParameter_value("Error!: "+e.toString());
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

            if (oResultadoWCF.getFolio_registro().toString().length()>0){

                clsUsuarioApp.upgradeUsuarioApp(o_UsuarioRegistrado);

                Util.customSnackBar("Registro exitoso",txtCodigoReg,getContext());

                goToMain();
            }else {
                Util.customSnackBar(oResultadoWCF.getError_menssage(),txtCodigoReg,getContext());

            }
            MostrarEspera(false);
        }
    }

    private void goToMain() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
