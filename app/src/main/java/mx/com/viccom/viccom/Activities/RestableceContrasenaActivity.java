package mx.com.viccom.viccom.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import mx.com.viccom.viccom.Adapters.ViewPagerAdapter;
import mx.com.viccom.viccom.Clases.clsMetodoPago;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Fragments.AgregarCtaMerPagoFragment;
import mx.com.viccom.viccom.Fragments.AgregarCtaPayPalFragment;
import mx.com.viccom.viccom.Fragments.AgregarTarjetaFragment;
import mx.com.viccom.viccom.Fragments.MisCuentasFragment;
import mx.com.viccom.viccom.Fragments.ResConCambioConFragment;
import mx.com.viccom.viccom.Fragments.ResConPideCodigoFragment;
import mx.com.viccom.viccom.Fragments.ResConPideEmailFragment;
import mx.com.viccom.viccom.Fragments.SelectMetodoPagoFragment;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;

public class RestableceContrasenaActivity extends AppCompatActivity implements onPageChangedLisener {
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablece_contrasena);

        setFragmentByDefault();

      /*  viewPager = (ViewPager) findViewById(R.id.vPagRestableceContraseña);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3, new ViewPagerAdapter.enCambioFragmento() {
            @Override
            public Fragment cambioFragment(int position) {
                Fragment fragmento = new Fragment();


                switch (position){
                    case 0:
                        fragmento = new ResConPideEmailFragment();
                        //fragmento.setArguments(argumentos);
                        break;
                    case 1:
                        fragmento = new ResConPideCodigoFragment();
                        //fragmento.setArguments(argumentos);
                        break;
                    case 2:
                        fragmento = new ResConCambioConFragment();
                        //fragmento.setArguments(argumentos);
                        break;


                }
                return fragmento;
            }

            @Override
            public void autoGuardado(clsMetodoPago data) {

            }
        });

        viewPager.setAdapter(viewPagerAdapter);*/
    }
    private void setFragmentByDefault() {
        Fragment fragment = new ResConPideEmailFragment();
        changeFragment(fragment);
    }
    private void changeFragment(Fragment fragment) {

       if (fragment != null){
            Bundle argumentos = new Bundle();
            argumentos.putParcelable("USUARIOAPP",usuarioApp);
            fragment.setArguments(argumentos);
        }

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
        //viewPager.setCurrentItem(page);
    }

    @Override
    public void onChangedPasFragment(Fragment fragment) {
        changeFragment(fragment);
    }
}
