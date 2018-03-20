package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tooltip.Tooltip;

import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;

public class CodigoRegistroActivity extends AppCompatActivity {
    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txtCodigoReg;
    private Button btnReenviarCodigo,btnAyudaCod;
    private ProgressBar pbValidando;
    private ImageView imgCodigo;
    private LinearLayout llCodigo;
    private Animation animCaida,animSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_registro);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("REGUSUARIOAPP");

        }


        txtCodigoReg = (EditText) findViewById(R.id.txtCodigo_reg);
        btnReenviarCodigo = (Button) findViewById(R.id.btnEnviarNvo_reg);
        btnAyudaCod = (Button) findViewById(R.id.btnHelpCodigo);
        pbValidando = (ProgressBar) findViewById(R.id.pbProgresoRegistro);
        imgCodigo = (ImageView) findViewById(R.id.imgCodigo);
        llCodigo = (LinearLayout) findViewById(R.id.llCodigo);

        animCaida = AnimationUtils.loadAnimation(this,R.anim.bajar);
        animSubir = AnimationUtils.loadAnimation(this,R.anim.subir);

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
                        Util.customSnackBar("Verifique el codigo",txtCodigoReg,CodigoRegistroActivity.this);
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

                        String strMail = usuarioApp.getMail();
                        String strContrasena = usuarioApp.getContrasena();

                        ArrayList<clsParameter> ParametrosValidar = new ArrayList<clsParameter>();
                        Parametros.add(new clsParameter("cMail", strMail));
                        Parametros.add(new clsParameter("cContrasena", strContrasena));

                        String ResultadoValidar = SendToWCF.Send_Post(Url, ParametrosValidar);

                        if (!ResultadoValidar.equals("ErrorConexion") && !ResultadoValidar.equals("ErrorURL") && !ResultadoValidar.equals("ErrorJSON")) {

                            Gson gson2 = new GsonBuilder().create();
                            ResultadoValidar = ResultadoValidar.replace("\\/","/").replace("\n","");
                            ResultadoValidar = ResultadoValidar.substring(22,ResultadoValidar.length()-1);
                            o_UsuarioRegistrado = gson2.fromJson(ResultadoValidar, clsUsuarioApp.class);


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
                //si se pudo registrar en la nube lo registro en la bd local
              /*  clsUsuarioApp  oUsuarioApp = new clsUsuarioApp(
                        oResultadoWCF.getFolio_registro().toString()
                        ,usuarioApp.getNombre()
                        ,usuarioApp.getMail()
                        ,""
                        ,""
                        ,""
                        ,oResultadoWCF.getFecha()
                        ,"" );*/

                clsUsuarioApp.upgradeUsuarioApp(o_UsuarioRegistrado);

                Util.customSnackBar("Registro exitoso",txtCodigoReg,CodigoRegistroActivity.this);
           /*     try {
                    HandlerThread.sleep(3000);
                } catch (Exception e){
                    e.printStackTrace();
                }*/
                goToMain();
            }else {
                Util.customSnackBar(oResultadoWCF.getError_menssage(),txtCodigoReg,CodigoRegistroActivity.this);

            }
            MostrarEspera(false);
        }
    }
    private void goToMain() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("EMAIL", usuarioApp.getMail().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
