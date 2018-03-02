package mx.com.viccom.viccom.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import mx.com.viccom.viccom.Adapters.MetodoPagoViewPagerAdapter;
import mx.com.viccom.viccom.Clases.clsMetodoPago;
import mx.com.viccom.viccom.Fragments.AgregarCtaMerPagoFragment;
import mx.com.viccom.viccom.Fragments.AgregarCtaPayPalFragment;
import mx.com.viccom.viccom.Fragments.AgregarTarjetaFragment;
import mx.com.viccom.viccom.Fragments.SelectMetodoPagoFragment;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;

public class AgregarFormaPagoActivity extends AppCompatActivity  implements onPageChangedLisener {
    private clsMetodoPago o_MetodoPago = new clsMetodoPago();
    private ViewPager viewPager;
    private ImageButton btnCerrar;
    private ImageButton btnAtras;
    private ImageButton btnAdelante;
    private TextView txtContadorPag;
    private MetodoPagoViewPagerAdapter metodoPagoViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_forma_pago);
        // Activar flecha ir atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.vPagFormasPag);
  /*      btnAtras = (ImageButton) findViewById(R.id.btnAtras_mp);
        btnAdelante = (ImageButton) findViewById(R.id.btnAdelante_mp);
        btnCerrar = (ImageButton) findViewById(R.id.btnCerrar_fp);
        txtContadorPag = (TextView) findViewById(R.id.txtContadorPag_mp);*/

        //adr_metodoPagoViewPager = new adr_MetodoPagoViewPager(getSupportFragmentManager(),2,o_MetodoPago);

        metodoPagoViewPagerAdapter = new MetodoPagoViewPagerAdapter(getSupportFragmentManager(), 2, new MetodoPagoViewPagerAdapter.enCambioFragmento() {
            @Override
            public Fragment cambioFragment(int position) {
                Fragment fragmento = new Fragment();
        /*        Bundle argumentos = new Bundle();
                argumentos.putParcelable("Data",Registro);*/

                switch (position){
                    case 0:
                        fragmento = new SelectMetodoPagoFragment();
                        //fragmento.setArguments(argumentos);
                        break;
                    case 1:
                        switch (o_MetodoPago.getId()){
                            case 0 :
                                fragmento = new AgregarTarjetaFragment();
                                return fragmento;
                            case 1 :
                                fragmento = new AgregarCtaPayPalFragment();
                                return fragmento;
                            case 2 :
                                fragmento = new AgregarCtaMerPagoFragment();
                                return fragmento;
                        }
                        //fragmento = new fnt_AgregarTarjeta();
                        //fragmento.setArguments(argumentos);
                        break;

                }
                return fragmento;
            }

            @Override
            public void autoGuardado(clsMetodoPago data) {

            }
        });

        viewPager.setAdapter(metodoPagoViewPagerAdapter);
    }

    @Override
    public void onChanged(Object Datos) {
        this.o_MetodoPago = (clsMetodoPago) Datos;
        metodoPagoViewPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);

    }
}
