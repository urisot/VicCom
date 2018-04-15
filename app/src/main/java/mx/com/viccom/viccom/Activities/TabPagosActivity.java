package mx.com.viccom.viccom.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import mx.com.viccom.viccom.Adapters.PagosViewPagerAdapter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Fragments.PagoPeyPalFragment;
import mx.com.viccom.viccom.Fragments.PagoTarjetaFragment;
import mx.com.viccom.viccom.R;

public class TabPagosActivity extends AppCompatActivity {

    private clsRecibos o_recibo = new clsRecibos();

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private TextView txtCuenta_pg,txtNombre_pg,txtMesdeConsumo_pg,txtMetros_pg;


    PagoTarjetaFragment pagoTarjetaFragment;
    PagoPeyPalFragment pagoPeyPalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_pagos);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");
        }

        viewPager = (ViewPager) findViewById(R.id.vPagPagos);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        txtCuenta_pg = (TextView) findViewById(R.id.txtCuenta_pg);
        txtNombre_pg = (TextView) findViewById(R.id.txtNombre_pg);
        txtMesdeConsumo_pg = (TextView) findViewById(R.id.txtMesdeConsumo_pg);
        txtMetros_pg = (TextView) findViewById(R.id.txtMetros_pg);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnu_p_tarjeta:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.mnu_p_paypal:
                                viewPager.setCurrentItem(1);
                                break;
                   /*         case R.id.action_contact:
                                viewPager.setCurrentItem(2);
                                break;*/
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(viewPager);

        if (!o_recibo.equals(null)){

            txtCuenta_pg.setText("["+o_recibo.getId_cuenta()+"]");
            txtNombre_pg.setText(o_recibo.getRazon_social());
            txtMesdeConsumo_pg.setText(o_recibo.getConsumo_act()+" m³ ("+o_recibo.getTipocalculo()+")");
            txtMetros_pg.setText(o_recibo.getCiclo_facturado().toString());

        }

    }
    private void setupViewPager(ViewPager viewPager) {
        PagosViewPagerAdapter adapter = new PagosViewPagerAdapter(getSupportFragmentManager());

        Bundle argumentos = new Bundle();
        argumentos.putParcelable("RECIBO",o_recibo);


        pagoTarjetaFragment = new PagoTarjetaFragment();
        pagoPeyPalFragment = new PagoPeyPalFragment();

        pagoTarjetaFragment.setArguments(argumentos);
        pagoPeyPalFragment.setArguments(argumentos);

        adapter.addFragment(pagoTarjetaFragment);
        adapter.addFragment(pagoPeyPalFragment );

        viewPager.setAdapter(adapter);
    }

}
