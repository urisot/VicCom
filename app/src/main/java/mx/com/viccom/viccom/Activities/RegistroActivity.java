package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.Arrays;

import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Fragments.RegistroPideDatosFragment;
import mx.com.viccom.viccom.Fragments.ResConPideEmailFragment;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;

public class RegistroActivity extends AppCompatActivity implements onPageChangedLisener {

    private EditText txtNombre,txtEmail,txtContrasena;
    private Button btnRegistrar,btnAyuaNom,btnAyudaEmail,btnAyudaCont;
    private ProgressBar pbValidando;
    private Animation animCaida,animSubir;
    private LinearLayout llRegistro;
    private ImageView imgUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Activar flecha ir atrás
/*        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrate");*/

 /*       txtNombre = (EditText) findViewById(R.id.txt_Nombre_reg);
        txtEmail = (EditText) findViewById(R.id.txt_email_reg);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena_reg);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        pbValidando = (ProgressBar) findViewById(R.id.pbProgresoRegistro);
        btnAyuaNom = (Button) findViewById(R.id.btnHelpNombre_reg);
        btnAyudaEmail = (Button) findViewById(R.id.btnHelpEmail_reg);
        btnAyudaCont = (Button) findViewById(R.id.btnHelpContrasena_reg);
        llRegistro = (LinearLayout) findViewById(R.id.llRegistro);
        imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

        animCaida = AnimationUtils.loadAnimation(this,R.anim.bajar);
        animSubir = AnimationUtils.loadAnimation(this,R.anim.subir);

        llRegistro.setAnimation(animCaida);
        imgUsuario.setAnimation(animSubir);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( DatosValidos()){
                    EnviarCorreoRegistro("");
                }
            }
        });

        btnAyuaNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuestraTooltip(view , Gravity.START,"Ingresa tu nombre completo.");
            }
        });

        btnAyudaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuestraTooltip(view ,Gravity.START,"Ingresa un correo electronico valido.");
            }
        });

        btnAyudaCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuestraTooltip(view ,Gravity.START,"Ingresa una contraseña mayor a 6 dijitos.");
            }
        });

        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(btnAyuaNom.getVisibility() == View.GONE){
                    btnAyuaNom.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(btnAyudaEmail.getVisibility() == View.GONE){
                    btnAyudaEmail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });

        txtContrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(btnAyudaCont.getVisibility() == View.GONE){
                    btnAyudaCont.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });*/

        setFragmentByDefault();
    }
    private void setFragmentByDefault() {
        Fragment fragment = new RegistroPideDatosFragment();
        changeFragment(fragment);
    }
    private void changeFragment(Fragment fragment) {

   /*     if (fragment != null){
            Bundle argumentos = new Bundle();
            argumentos.putParcelable("USUARIOAPP",usuarioApp);
            fragment.setArguments(argumentos);
        }*/

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        //getSupportActionBar().setTitle(item.getTitle());
        //getSupportActionBar().setTitle("");
    }

    @Override
    public void onChangedPasObject(Object Datos) {

    }

    @Override
    public void onChangedPasPage(int page) {

    }

    @Override
    public void onChangedPasFragment(Fragment fragment) {
        changeFragment(fragment);
    }
    
/*    private void MostrarEspera(Boolean boolMostrar) {
        if (boolMostrar){
            pbValidando.setVisibility(View.VISIBLE);
            llRegistro.setVisibility(View.INVISIBLE);

        }else{
            pbValidando.setVisibility(View.INVISIBLE);
            llRegistro.setVisibility(View.VISIBLE);

        }
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
            MostrarEspera(true);
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
                btnRegistrar.setVisibility(View.GONE);

                String strMacAddress = Util.getMacAddress(RegistroActivity.this);

                clsUsuarioApp oUsuarioApp = new clsUsuarioApp(""
                        ,txtNombre.getText().toString()
                        ,txtEmail.getText().toString()
                        ,""
                        ,txtContrasena.getText().toString()
                        ,strMacAddress
                        ,""
                        ,"" );

                PedirCodigoActivacion(oUsuarioApp);
*//*                txtNombre.setEnabled(false);
                txtEmail.setEnabled(false);
                txtContrasena.setEnabled(false);
                txtCodigoReg.setVisibility(View.VISIBLE);
                txtMensje.setVisibility(View.VISIBLE);
                btnReenviarCodigo.setVisibility(View.VISIBLE);
                txtCodigoReg.requestFocus();*//*


            }else{
                Util.customSnackBar(oResultadoWCF.getError_menssage(),btnRegistrar,RegistroActivity.this);

            }
            MostrarEspera(false);
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
                if(txtContrasena.getText().toString().length()>=6){
                    boolRetornar = true;

                }else{
                    btnAyudaCont.setVisibility(View.GONE);
                    txtContrasena.setError("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.");
                    // Util.customSnackBar("¡ERROR!: La contraseña debe ser mayor o igual a 6 digitos.",txtContrasena,RegistroActivity.this);
                }
            }else {
                btnAyudaEmail.setVisibility(View.GONE);
                txtEmail.setError("¡ERROR!: Espesifique un email valido.");
                //Util.customSnackBar("¡ERROR!: Espesifique un email valido.",txtEmail,RegistroActivity.this);
            }
        } else {
            btnAyuaNom.setVisibility(View.GONE);
            txtNombre.setError("¡ERROR!: Espesifique un nombre valido.");
            // Util.customSnackBar("¡ERROR!: Espesifique un nombre valido.",txtNombre,RegistroActivity.this);
        }

        return boolRetornar;
    }

    private void PedirCodigoActivacion(clsUsuarioApp o_usuario){
        Intent intent = new Intent(RegistroActivity.this,CodigoRegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("REGUSUARIOAPP",o_usuario);
        startActivity(intent);
    }*/
}
