package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEmail;
    // private EditText txtCelular;
    private EditText txtContrasena;
    // private EditText txtConfirmContrasena;
    private EditText txtCodigoReg;
    private Button btnRegistrar;
    private Button btnReenviarCodigo;
    private ProgressBar pbValidando;
    private TextView txtMensje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Activar flecha ir atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrate");

        txtNombre = (EditText) findViewById(R.id.txt_Nombre_reg);
        txtEmail = (EditText) findViewById(R.id.txt_email_reg);
        //txtCelular = (EditText) findViewById(R.id.txtCelular_reg);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena_reg);
        //txtConfirmContrasena = (EditText) findViewById(R.id.txtConfirmContrasena_reg);
        txtCodigoReg = (EditText) findViewById(R.id.txtCodigo_reg);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnReenviarCodigo = (Button) findViewById(R.id.btnEnviarNvo_reg);
        pbValidando = (ProgressBar) findViewById(R.id.pbProgresoRegistro);
        txtMensje = (TextView) findViewById(R.id.txtMesajeEnvio_reg);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( DatosValidos()&& ContrasenaValida()){

                    EnviarCorreoRegistro("");

                }
            }
        });

        txtCodigoReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(),"start "+start+" before "+before+" count "+count+ " secuence "+s,Toast.LENGTH_SHORT).show();
                if (s.length() == 6){
                    String strCodigo = Util.getCodigoRegistro();
                    String strTxtCod = txtCodigoReg.getText().toString();
                    if (strTxtCod.equals(strCodigo)){


                        String strMacAddress = Util.getMacAddress(RegistroActivity.this);
                        String strContrasena = StringMD.getStringMessageDigest(txtEmail.getText().toString() + txtContrasena.getText().toString(), StringMD.SHA1).toUpperCase();

                        clsUsuarioApp oUsuarioApp = new clsUsuarioApp(""
                                ,txtNombre.getText().toString()
                                ,txtEmail.getText().toString()
                                ,""
                                ,strContrasena
                                ,strMacAddress
                                ,""
                                ,"" );
                        new AgregarUsrAppHttp().execute(oUsuarioApp);

                    }else {
                        Util.customSnackBar("Verifique el codigo",txtCodigoReg,RegistroActivity.this);
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

                EnviarCorreoRegistro(strCodigo);
            }
        });
    }
    private void EnviarCorreoRegistro(String strCodigo){

        if(strCodigo.equals("") && strCodigo.length() == 0){
            strCodigo = Util.getCodigoAleatorio();
            Util.setCodigoRegistro(strCodigo);
        }

        //txtMensje.setText(strCodigo);

        clsUsuarioApp usuarioApp = new clsUsuarioApp(""
                ,txtNombre.getText().toString()
                ,txtEmail.getText().toString()
                ,strCodigo
                ,""
                ,""
                ,""
                ,"" );
        new EnviarCodigoRegistroEmailHttp().execute(usuarioApp);
    }

    public class EnviarCodigoRegistroEmailHttp extends AsyncTask<clsUsuarioApp,Integer,clsResultadoWCF> {

        @Override
        protected void onPreExecute() {
            pbValidando.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
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
                if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErroJSON")) {

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
                btnRegistrar.setVisibility(View.GONE);
                txtNombre.setEnabled(false);
                txtEmail.setEnabled(false);
                txtContrasena.setEnabled(false);
                txtCodigoReg.setVisibility(View.VISIBLE);
                txtMensje.setVisibility(View.VISIBLE);
                btnReenviarCodigo.setVisibility(View.VISIBLE);
                txtCodigoReg.requestFocus();


            }else{
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnRegistrar,RegistroActivity.this);

            }
            pbValidando.setVisibility(View.INVISIBLE);
        }
    }

    public class AgregarUsrAppHttp extends AsyncTask<clsUsuarioApp,Integer,clsResultadoWCF> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbValidando.setVisibility(View.VISIBLE);
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

                final clsUsuarioApp  oUsuarioApp = new clsUsuarioApp(
                        oResultadoWCF.getFolio_registro().toString()
                        ,txtNombre.getText().toString()
                        ,txtEmail.getText().toString()
                        ,""
                        ,""
                        ,""
                        ,oResultadoWCF.getFecha()
                        ,"" );

                clsUsuarioApp.upgradeUsuarioApp(oUsuarioApp);

                Util.customSnackBar("Registro exitoso",btnRegistrar,RegistroActivity.this);
                try {
                    HandlerThread.sleep(3000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                IrALogIn();
            }else {
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnRegistrar,RegistroActivity.this);

            }
            pbValidando.setVisibility(View.INVISIBLE);
        }
    }

    private Boolean DatosValidos(){
        boolean boolRetornar = false;
        String strNombre =  txtNombre.getText().toString();
        String strMail =  txtEmail.getText().toString();
        // String strCelular =  txtCelular.getText().toString();

        if (!TextUtils.isEmpty(strNombre))
        {
            if (Util.isValidEmail(strMail))
            {
               /* if (!TextUtils.isEmpty(strCelular) && strCelular.length() == 10)
                {*/
                boolRetornar = true;

              /*  }else {
                    txtCelular.setError("¡ERROR!: Espesifique un numero valido.");
                    Util.customSnackBar("¡ERROR!: Espesifique un numero valido.",txtCelular,RegistroActivity.this);
                }*/
            }else {
                txtEmail.setError("¡ERROR!: Espesifique un email valido.");
                //Util.customSnackBar("¡ERROR!: Espesifique un email valido.",txtEmail,RegistroActivity.this);
            }
        } else {
            txtNombre.setError("¡ERROR!: Espesifique un nombre valido.");
            // Util.customSnackBar("¡ERROR!: Espesifique un nombre valido.",txtNombre,RegistroActivity.this);
        }

        return boolRetornar;
    }
    private Boolean ContrasenaValida(){
        boolean boolRetornar = false;
        if(txtContrasena.getText().toString().length()>=6){

            boolRetornar = true;


        }else{
            txtContrasena.setError("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.");
            // Util.customSnackBar("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.",txtContrasena,RegistroActivity.this);
        }
        return boolRetornar;
    }
    private void IrAMenuPrincipal(){
        Intent intent = new Intent(this,MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void IrALogIn(){
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
