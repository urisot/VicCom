package mx.com.viccom.viccom.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import mx.com.viccom.viccom.Fragments.MisFormasPagoFragment;
import mx.com.viccom.viccom.R;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strMail = bundle.getString("EMAIL");
            usuarioApp = clsUsuarioApp.getUsrAppByMail(strMail);

        }

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
            case R.id.opt_FormasPago:
                fragment = new MisFormasPagoFragment();

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
}
