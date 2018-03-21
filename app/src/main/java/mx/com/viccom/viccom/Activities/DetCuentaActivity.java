package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.com.viccom.viccom.Adapters.DetRecibosViewPagerAdapter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.R;

public class DetCuentaActivity extends AppCompatActivity {

    private clsRecibos o_recibo = new clsRecibos();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RelativeLayout rl_tituloTab;
    private ImageButton btnCerrar;
    private TextView txtImporte;
    private Button btnPagar;
    private LinearLayout llResumenDet;
    private ImageView imgLogoCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_cuenta);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");

        }


        tabLayout = (TabLayout) findViewById(R.id.tabLayDetalle);
        viewPager = (ViewPager) findViewById(R.id.vPagDetUsr);
        rl_tituloTab = (RelativeLayout) findViewById(R.id.rl_tituloTabs);
        btnCerrar = (ImageButton) findViewById(R.id.btn_cerrar_det);
        txtImporte = (TextView) findViewById(R.id.txt_TotalAdeudo_det);
        btnPagar = (Button) findViewById(R.id.btnPagar_det);
        llResumenDet = (LinearLayout) findViewById(R.id.llResumen_det);
        imgLogoCom = (ImageView) findViewById(R.id.imgLogoCom);

        txtImporte.setText("$ "+Math.round(o_recibo.getTotal()) +".00");

        if (!(Math.round(o_recibo.getTotal())>0)){
            btnPagar.setVisibility(View.INVISIBLE);
        }


        DetRecibosViewPagerAdapter adrDetRecibosViewPager = new DetRecibosViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),o_recibo);

        viewPager.setAdapter(adrDetRecibosViewPager);
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int intTab = tab.getPosition();
                viewPager.setCurrentItem(intTab);
                switch (intTab) {
                    case 0:
                        rl_tituloTab.setBackgroundResource(R.color.tab1);
                        tabLayout.setBackgroundResource(R.color.tab1);
                        imgLogoCom.setImageResource(R.drawable.ic_logocomapaazul);
                       // Picasso.with(DetCuentaActivity.this).load(R.drawable.ic_logocomapaazul).into(imgLogoCom);
                        // llResumenDet.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        rl_tituloTab.setBackgroundResource(R.color.tab2);
                        tabLayout.setBackgroundResource(R.color.tab2);
                        imgLogoCom.setImageResource(R.drawable.ic_logocomablanco);
                       // Picasso.with(DetCuentaActivity.this).load(R.drawable.ic_logocomablanco).into(imgLogoCom);
                        //llResumenDet.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        rl_tituloTab.setBackgroundResource(R.color.tab1);
                        tabLayout.setBackgroundResource(R.color.tab1);
                        imgLogoCom.setImageResource(R.drawable.ic_logocomapaazul);
                        //Picasso.with(DetCuentaActivity.this).load(R.drawable.ic_logocomapaazul).into(imgLogoCom);
                        //llResumenDet.setVisibility(View.GONE);
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (!(o_recibo.getTotal() == null)){
                    if (o_recibo.getTotal() >0){
                        PagarRecibo(o_recibo);
                    }
                //}

                //Toast.makeText(DetCuentaActivity.this, "Pagar recibo de: "+o_recibo.getRazon_social(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void PagarRecibo( clsRecibos o_recibo) {
        Intent intent = new Intent(DetCuentaActivity.this, RealizarPagoActivity.class);
        intent.putExtra("RECIBO", o_recibo);
        startActivity(intent);
    }
}

