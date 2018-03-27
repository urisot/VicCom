package mx.com.viccom.viccom.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tooltip.Tooltip;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private SharedPreferences prefs;

    private EditText txtEmail;
    private EditText txtPassword;
    private Switch switchRemember;
    private Button btnLogin,btnHelpMail,btnHelpContrasena;
    private CheckedTextView linkRegiste;
    private CheckedTextView linkResetContrasena;
    private ProgressBar pbValidando;
    private ImageView imgCandado;
    private Animation animCaida,animSubir;
    private LinearLayout llLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtEmail.getText().toString();
                String password = StringMD.getStringMessageDigest(txtEmail.getText().toString() + txtPassword.getText().toString(), StringMD.SHA1).toUpperCase();
//                String password = editTextPassword.getText().toString();

                ArrayList<String> listToSend = new ArrayList<String>();
                listToSend.add(email);
                listToSend.add(password);
                new ValidarUsrAppHttp().execute(listToSend);

            }
        });

        linkRegiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irARegistro();
            }
        });

        linkResetContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irARestablecerContrasena();
            }
        });

        btnHelpMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MuestraTooltip(view ,Gravity.START,"Ingresa un correo electronico valido.");
            }
        });

        btnHelpContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MuestraTooltip(view ,Gravity.START,"Ingresa una contraseña valida.");
            }
        });

        imgCandado.setAnimation(animCaida);
        llLogIn.setAnimation(animSubir);


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
            llLogIn.setVisibility(View.INVISIBLE);
            linkRegiste.setVisibility(View.INVISIBLE);
        }else{
            pbValidando.setVisibility(View.INVISIBLE);
            llLogIn.setVisibility(View.VISIBLE);
            linkRegiste.setVisibility(View.VISIBLE);
        }
    }



    private void bindUI() {
        txtEmail = (EditText) findViewById(R.id.txtEmail_log);
        txtPassword = (EditText) findViewById(R.id.txtContrasena_log);
        switchRemember = (Switch) findViewById(R.id.swRecuerdame_log);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        linkRegiste = (CheckedTextView) findViewById(R.id.linkRegistrarse_log);
        linkResetContrasena = (CheckedTextView) findViewById(R.id.linkOlvidoContraseña_log);
        pbValidando = (ProgressBar) findViewById(R.id.login_progress);
        imgCandado = (ImageView) findViewById(R.id.imgCandado);
        llLogIn = (LinearLayout) findViewById(R.id.email_login_form);
        btnHelpMail = (Button) findViewById(R.id.btnHelpEmail);
        btnHelpContrasena = (Button) findViewById(R.id.btnHelpContrasena);

        animCaida = AnimationUtils.loadAnimation(this,R.anim.bajar);
        animSubir = AnimationUtils.loadAnimation(this,R.anim.subir);
    }

    private void setCredentialsIfExist() {
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            txtEmail.setText(email);
            txtPassword.setText(password);
            switchRemember.setChecked(true);
        }
    }

    private boolean login(String email, String password) {
        if (!Util.isValidEmail(email)) {

            txtEmail.setError("¡ERROR!: Email invalido.");
            // Toast.makeText(this, "Email is not valid, please try again", Toast.LENGTH_LONG).show();
            return false;
        } else if (!Util.isValidPassword(password)) {
            txtPassword.setError("¡ERROR!: Email invalido.");
            //Toast.makeText(this, "Password is not valid, 4 characters or more, please try again", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveOnPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            editor.apply();
        }
    }

    public class ValidarUsrAppHttp extends AsyncTask<ArrayList<String>,Integer,clsUsuarioApp> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MostrarEspera(true);
        }

        @Override
        protected clsUsuarioApp doInBackground(ArrayList<String>[] lists) {
            clsUsuarioApp usuarioApp = new clsUsuarioApp();
            ArrayList<String> listParametros= lists[0];
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/ValidaUsrApp";

                String strMailUsr = listParametros.get(0).toString();
                String strContrasenaUsr = listParametros.get(1).toString();

                ArrayList<clsParameter> ParametrosUsr = new ArrayList<clsParameter>();
                ParametrosUsr.add(new clsParameter("cMail", strMailUsr));
                ParametrosUsr.add(new clsParameter("cContrasena", strContrasenaUsr));

                String ResultadoUsr = SendToWCF.Send_Post(Url, ParametrosUsr);

                //Pudo establecer conexion
                if (!ResultadoUsr.equals("ErrorConexion") && !ResultadoUsr.equals("ErrorURL") && !ResultadoUsr.equals("ErrorJSON")) {
/*
                    JSONObject jsonObject = new JSONObject(Resultado);
                    JSONObject jsonObject2 = jsonObject.getJSONObject("ValidaUsrAppResult");
*/
                    Gson gsonUsr = new GsonBuilder().create();
                    ResultadoUsr = ResultadoUsr.replace("\\/","/").replace("\n","");
                    ResultadoUsr = ResultadoUsr.substring(22,ResultadoUsr.length()-1);
                    usuarioApp = gsonUsr.fromJson(ResultadoUsr, clsUsuarioApp.class);

                    //Inserto correctamente el usuario y obtobo el folio consecutivo de registro
                    if (usuarioApp.getId_usuarioapp().toString().length()>0){

                    }else{
                        usuarioApp.setId_usuarioapp("");
                        // usuarioApp.setNombre("No pudo obtener el folio del usuario.");
                        usuarioApp.setCelular("Error");
                        usuarioApp.setContrasena("Error");
                        usuarioApp.setMac_address("Error");
                        usuarioApp.setFecha_insert("");
                        usuarioApp.setKey("");
                    }


                } else {

                    usuarioApp.setId_usuarioapp("");
                    usuarioApp.setNombre("Error de conexión.");
                    usuarioApp.setCelular("Error");
                    usuarioApp.setContrasena("Error");
                    usuarioApp.setMac_address("Error");
                    usuarioApp.setFecha_insert("");
                    usuarioApp.setKey("");

                }

            }catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", e.toString());
                usuarioApp.setId_usuarioapp("");
                usuarioApp.setNombre("Error en el Servicio. "+e.toString());
                usuarioApp.setCelular("Error");
                usuarioApp.setContrasena("Error");
                usuarioApp.setMac_address("Error");
                usuarioApp.setFecha_insert("");
                usuarioApp.setKey("");

                return usuarioApp;
            }

            return usuarioApp;
        }

        @Override
        protected void onPostExecute(clsUsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            MostrarEspera(false);
            if (usuarioApp.getId_usuarioapp().toString().length()>0){
                clsUsuarioApp.upgradeUsuarioApp(usuarioApp);
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();//StringMD.getStringMessageDigest(editTextEmail.getText().toString() + editTextPassword.getText().toString(), StringMD.SHA1).toUpperCase();
                saveOnPreferences(email, password);
                goToMain();

            }else{
                Util.customSnackBar(usuarioApp.getNombre(),btnLogin,LoginActivity.this);
            }
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MenuActivity.class);
        //intent.putExtra("EMAIL", txtEmail.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void irARegistro() {
        Intent intent = new Intent(this, RegistroActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void irARestablecerContrasena() {
        Intent intent = new Intent(this, RestableceContrasenaActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

