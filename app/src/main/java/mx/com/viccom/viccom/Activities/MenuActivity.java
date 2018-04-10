package mx.com.viccom.viccom.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Fragments.ConfiguracionFragment;
import mx.com.viccom.viccom.Fragments.MiPerfilFragment;
import mx.com.viccom.viccom.Fragments.MisCuentasFragment;
import mx.com.viccom.viccom.Fragments.MisTarjetasFragment;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.Util;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout dragLayMenuLateral;
    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private String strMail="";
    private TextView txtNombre_mnu;
    private TextView txtEmail_mnu;
    private View viewMenuHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        usuarioApp = clsUsuarioApp.getUsrApp();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dragLayMenuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //obtener el view del encabesado del menu
        viewMenuHeader =  navigationView.getHeaderView(0);
        txtNombre_mnu = (TextView) viewMenuHeader.findViewById(R.id.txtNombre_mnu);
        txtEmail_mnu = (TextView) viewMenuHeader.findViewById(R.id.txtEmail_mnu);

        txtNombre_mnu.setText(usuarioApp.getNombre().toString());
        txtEmail_mnu.setText(usuarioApp.getMail().toString());


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dragLayMenuLateral, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dragLayMenuLateral.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        setFragmentByDefault();
    }
    private void irALogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void setFragmentByDefault() {
        Fragment fragment = new MisCuentasFragment();
        changeFragment(fragment, navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item) {

        if (fragment != null){
            Bundle argumentos = new Bundle();
            argumentos.putParcelable("USUARIOAPP",usuarioApp);
            fragment.setArguments(argumentos);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
        //getSupportActionBar().setTitle("");
    }


    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (dragLayMenuLateral.isDrawerOpen(GravityCompat.START)) {
            dragLayMenuLateral.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnuCerrarSesion) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this,R.style.AppCompatAlertDialogStyle);
            alertDialog.setMessage("¿Deseas salir de la aplicación?");
            alertDialog.setTitle("Cerrar sesion");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Util.removePasswordSharedPreferences(prefs);
                    irALogIn();

                }
            });
            alertDialog.show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean fragmentTransaction = false;
        Fragment fragment = new Fragment();

        switch (item.getItemId()) {

            case R.id.opt_Inicio:
                fragment = new MisCuentasFragment();

                fragmentTransaction = true;
                break;
            case R.id.opt_Targetas:
                fragment = new MisTarjetasFragment();

                fragmentTransaction = true;
                break;
            case R.id.opt_MiCuenta:
                fragment = new MiPerfilFragment();

                fragmentTransaction = true;
                break;

            case R.id.opt_Configuracion:
                fragment = new ConfiguracionFragment();

                fragmentTransaction = true;
                break;
        }

        if (fragmentTransaction) {
            changeFragment(fragment, item);
            dragLayMenuLateral.closeDrawer(GravityCompat.START);
        }
        return true;
    }

 /*   @Override
    public void onActualizaUsrApp(clsUsuarioApp o_usuario) {

    }

    @Override
    public void onActualizaNombreUsrApp(String nombre) {
        final clsUsuarioApp o_usuarioNew = usuarioApp;
        o_usuarioNew.setNombre(nombre);

        new ActualizaUsrAppHttp().execute(o_usuarioNew);

    }

    public class ActualizaUsrAppHttp extends AsyncTask<clsUsuarioApp,Integer,clsResultadoWCF> {
        clsUsuarioApp o_usuarioNew = new clsUsuarioApp();
        @Override
        protected clsResultadoWCF doInBackground(clsUsuarioApp... oUsuarioApps) {
            o_usuarioNew = oUsuarioApps[0];
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

              *//*  final clsUsuarioApp  oUsuarioApp = new clsUsuarioApp(
                        oResultadoWCF.getFolio_registro().toString()
                        ,txtNombre.getText().toString()
                        ,txtEmail.getText().toString()
                        ,""
                        ,""
                        ,""
                        ,oResultadoWCF.getFecha()
                        ,"" );*//*

                clsUsuarioApp.upgradeUsuarioApp(o_usuarioNew);

                txtNombre_mnu.setText(o_usuarioNew.getNombre().toString());

                //Util.customSnackBar("Registro exitoso",btnRegistrar,RegistroActivity.this);
                try {
                    HandlerThread.sleep(3000);
                } catch (Exception e){
                    e.printStackTrace();
                }

               // IrALogIn();
            }else {
                //Util.customSnackBar(oResultadoWCF.getError_menssage(),btnRegistrar,RegistroActivity.this);
            }

        }
    }*/

}
