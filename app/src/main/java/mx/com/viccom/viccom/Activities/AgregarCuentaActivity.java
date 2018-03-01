package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsCuentaValida;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.Util;

public class AgregarCuentaActivity extends AppCompatActivity {
    private String URL_WCF = "";
    private Button btnValidarCta;
    private EditText txtNumCuenta;
    private EditText txtNombreTitular;
    private ProgressBar progressBar_ac;
    private ImageButton btnCerrar;
    private clsUsuarioApp usuarioApp = new clsUsuarioApp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cuenta);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
        /*   String strMail = bundle.getString("USUARIOAPP");
            usuarioApp = clsUsuarioApp.getUsrAppByMail(strMail);*/
            usuarioApp = bundle.getParcelable("USUARIOAPP");

        }

        txtNumCuenta =(EditText) findViewById(R.id.txtNumeroCuenta_ac);
        txtNombreTitular =(EditText) findViewById(R.id.txtNombreTitular_ac);
        btnValidarCta =(Button) findViewById(R.id.btnValidarCta_ac);
        progressBar_ac =(ProgressBar) findViewById(R.id.progressBar_ac);
        btnCerrar =(ImageButton) findViewById(R.id.btn_cerrar_ac);


        URL_WCF = Util.getDireccionWCF();

        btnValidarCta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNumCta = txtNumCuenta.getText().toString();
                String strNombreTitular =txtNombreTitular.getText().toString();

                if (!TextUtils.isEmpty(strNumCta)) {
                    if(!TextUtils.isEmpty(strNombreTitular)){

                        progressBar_ac.setVisibility(View.VISIBLE);
                        ArrayList<clsParameter> Parametros = new ArrayList<>();
                        Parametros.add(new clsParameter("p1", strNumCta));
                        Parametros.add(new clsParameter("p2", strNombreTitular));

                        //ValidaCuentaHttp validaCuentaHttp = new ValidaCuentaHttp();
                        //validaCuentaHttp.execute(Parametros);*/
                        new ValidaCuentaHttp().execute(Parametros);

                    }
                    else{
                        // customSnackBar("¡ERROR!: Espesifique el nombre del titular.",txtNombreTitular);
                        txtNombreTitular.setError("¡ERROR!: Espesifique el nombre del titular.");
                    }
                }else{
                    //customSnackBar("¡ERROR!: Espesifique el numero de cuenta.",txtNumCuenta);
                    txtNumCuenta.setError("¡ERROR!: Espesifique el numero de cuenta.");
                }
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(Util.RESULTADO_CANCEL, intent);
                finish();
            }
        });
    }



    public class ValidaCuentaHttp extends AsyncTask<ArrayList<clsParameter>,Void,clsCuentaValida> {

        @Override
        protected clsCuentaValida doInBackground(ArrayList<clsParameter>[] Parametros) {
            clsCuentaValida cuentaValida = new clsCuentaValida(false);
            clsParameter HttpResult = new clsParameter();
            //private Boolean boolResul=Boolean.FALSE;
            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getValidaUsrPadron";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {


                    Gson gson = new GsonBuilder().serializeNulls().create();
                    String jSonResult =HttpResult.getParameter_name().substring(29,HttpResult.getParameter_name().length()-2);
                    cuentaValida = gson.fromJson(jSonResult, clsCuentaValida.class);

                    if (cuentaValida.isUsrValido()){
                        Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/InsCuentasUsrApp";

                        ArrayList<clsParameter> liasParametros = new ArrayList<clsParameter>();
                        //liasParametros.add(new clsParameter("pass", "BA587B93B5E6A993F5A3D"));
                        liasParametros.add(new clsParameter("pass", usuarioApp.getKey().toString()));
                        liasParametros.add(new clsParameter("cIdUsuarioApp", usuarioApp.getId_usuarioapp().toString()));
                        liasParametros.add(new clsParameter("nIdCueta", txtNumCuenta.getText().toString()));



                        String Resultado = SendToWCF.Send_Post(Url, liasParametros);

                        if (!Resultado.equals("ErrorConexion") && !Resultado.equals("ErrorURL") && !Resultado.equals("ErroJSON")) {

                       /*     if (Resultado.contains("EXITO")) {
                                publishProgress(new clsParameter("Success", Resultado));
                                ListId_OtSubidos.add(RegistroOrden.getId_orden());
                            } else {
                                publishProgress(new clsParameter("Error", Resultado));
                                ListId_OtNoSubidos.add(RegistroOrden.getId_orden());
                            }*/

                        } else {
                         /*   publishProgress(new clsParameter("Error", Resultado));
                            ListId_OtNoSubidos.add(RegistroOrden.getId_orden());
                            //IdsNoSubidos.add(drLecturas.get(i).getId_padron());*/
                        }

                    }else{


                    }

                }
                else
                    HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");


            }catch (Exception e) {//4
                e.printStackTrace();
                Log.e("Error", e.toString());
                HttpResult.setParameter_name("Error");
                HttpResult.setParameter_value("Error!: "+e.toString());


            }//4

            return cuentaValida;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(clsCuentaValida clsCuentaValida) {
            super.onPostExecute(clsCuentaValida);
            if (clsCuentaValida.isUsrValido()){
                Intent intent = getIntent();
                setResult(Util.RESULTADO_OK, intent);
                finish();//finishing activity

                //Util.customSnackBar("Usuario valido.",txtNombreTitular,act_AgregarCuenta.this);
            }else{
                progressBar_ac.setVisibility(View.INVISIBLE);
                Util.customSnackBar("Usuario invalido.",txtNombreTitular,AgregarCuentaActivity.this);
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    public class ActualizaHttp extends AsyncTask<ArrayList<clsParameter>,Integer,ArrayList<clsRecibos>>{
        @Override
        protected ArrayList<clsRecibos> doInBackground(ArrayList<clsParameter>[] Parametros) {
            ArrayList<clsRecibos> listRecibosReturn = new ArrayList<clsRecibos>();
            clsParameter HttpResult = new clsParameter();

            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getDatosPadron";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {


                    Gson gson = new GsonBuilder().serializeNulls().create();
                    HttpResult.setParameter_name(HttpResult.getParameter_name().replace("\\/","/"));
                    String jSonResult =HttpResult.getParameter_name().substring(24,HttpResult.getParameter_name().length()-1);
                    listRecibosReturn = gson.fromJson(jSonResult, new TypeToken<ArrayList<clsRecibos>>(){}.getType());


                }
                else
                    HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");
                return listRecibosReturn;

            }catch (Exception e) {//4
                e.printStackTrace();
                Log.e("Error", e.toString());
                HttpResult.setParameter_name("Error");
                HttpResult.setParameter_value("Error!: "+e.toString());

                return listRecibosReturn;
            }//4



        }

        @Override
        protected void onPostExecute(ArrayList<clsRecibos> ListRecibos) {
            super.onPostExecute(ListRecibos);
            progressBar_ac.setVisibility(View.INVISIBLE);

            // Adaptador.notifyDataSetChanged();
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
        }
    }
  /*  public void ValidaUsrPadron(final ArrayList<clsParameter> parametros){//2
        new AsyncTask<Void,Void,clsParameter>(){//3

            @Override
            protected clsParameter doInBackground(Void... params) {//4

                clsParameter resultado = new clsParameter();
                String Url = URL_WCF + "getValidaUsrPadron";

                resultado.setParameter_name(SendToWCF.Send_Post(Url, parametros));

                if(!resultado.getParameter_name().equals("ErrorConexion") && !resultado.getParameter_name().equals("ErrorURL") && !resultado.getParameter_name().equals("ErroJSON")){

                    //Log.e("Resultado Verificacion",resultado.getParameter_name());
                    try {
                        JSONObject Resultado = new JSONObject(resultado.getParameter_name());
                        JSONArray Registros = Resultado.getJSONArray("ValidarUsrPswResult");

                        //JSONArray Registros = Resultado.getJSONArray(resultado.getParameter_name());

                        if(Registros.length()!=0){
                            Log.e("nombre",Registros.getJSONObject(0).getString("nombre"));
                            Log.e("usuario",Registros.getJSONObject(0).getString("usuario"));
                            Log.e("valido",Registros.getJSONObject(0).getString("valido"));

                            resultado.setParameter_name("OK");
                            resultado.setParameter_value(Registros.getJSONObject(0).getString("valido"));

                            return resultado;

                        }else{
                            MuestraEspera(false);
                            resultado.setParameter_name("Error");
                            resultado.setParameter_value("Verifica la contraseña.");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    resultado.setParameter_name("ErrorNoConectoWCF");
                    MuestraEspera(false);
                }

                return resultado;
            }//4

            @Override
            protected void onPostExecute(clsParameter result){//4
                if(result.getParameter_name().equals("OK")){

                    if(result.getParameter_value().equals("SI")){

                        BDManager Manejador = new BDManager(getApplicationContext(),"DBOrdenesTrabajo",null,1);
                        SQLiteDatabase db = Manejador.getReadableDatabase();

                        if(db!=null){
                            //db.execSQL("UPDATE OPR_ORDENES SET id_capturo = '" + UsuarioActual.getId_usuario() + "' where id_estatus in (82,9,10);");
                            OPR_ORDENES.CerrarOrdenesComoNoEjecutadas(UsuarioActual.getId_usuario());
                            db.execSQL("UPDATE OPR_ORDENES SET id_capturo = '" + UsuarioActual.getId_usuario() + "';");
                            db.execSQL("UPDATE Opr_Materiales SET id_utilizo = '" + UsuarioActual.getId_usuario() + "';");
                            db.close();
                        }

                        // Intent intent = new Intent();
                        Intent intent = getIntent();
                        intent.putExtra("Nombre", UsuarioActual.getNombre());
                        setResult(Rutinas.RESULTADO_OK, intent);
                        finish();//finishing activity



                    }else if(result.getParameter_value().equals("NO")){
                        //customSnackBar("Verifica la contraseña.",btnAcceso);
                        et_Contrasena.setError("Verifica la contraseña..");
                        MuestraEspera(false);
                    }

                    //if(result)
                    //Intent intento = new Intent(LoginScreen.this,);

                }else if(result.getParameter_name().equals("ErrorNoConectoWCF")){

                    customSnackBar("No se pudo establecer conexion con el servidor",btnAcceso);
                    MuestraEspera(false);

                }
                else if(result.getParameter_name().equals("Error")){
                    customSnackBar(result.getParameter_value(),btnAcceso);
                    MuestraEspera(false);
                }

            }//4

        }.execute(null,null,null);//3
    }//2*/

   /* public void customSnackBar(String Text,View v){//2
        Snackbar snackbar = Snackbar.make(v, Text, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.aceptar), new View.OnClickListener() {//3
                    @Override
                    public void onClick(View view) {//4

                    }//4
                });//3
        snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        snackbar.show();
    }*/
}
