package mx.com.viccom.viccom.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import mx.com.viccom.viccom.Clases.clsMetodoPago;


/**
 * Created by Admin on 28/02/2018.
 */

public class MetodoPagoViewPagerAdapter extends FragmentStatePagerAdapter {
    private int cantidadItems;//cantidad fragments por implementacion
    private enCambioFragmento listener;// escucha para diferentes fragments

    public MetodoPagoViewPagerAdapter(FragmentManager fragmentManager, int cantidadItems, enCambioFragmento enCambioFragmentoListener ) {
        super(fragmentManager);
        this.cantidadItems = cantidadItems;
        this.listener = enCambioFragmentoListener;
    }


    @Override
    public int getCount() {
        return cantidadItems;
    }

    @Override
    public Fragment getItem(int position) {
        return listener.cambioFragment(position);
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        Log.e("getItemPosition","getItemPosition");
        // listener.autoGuardado(((DataUpdate)object).getData());
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * al cambiar de fragments se usa la posicion para evaluar que tipo de 'instancia' de fragment
     * regresara segun la implementación.
     */
    public interface enCambioFragmento{
        Fragment cambioFragment(int position);
        void autoGuardado(clsMetodoPago data);
    }
  /*  private clsMetodoPago o_MetodoPago = new clsMetodoPago();
    private int intNumPaginas;

    public adr_MetodoPagoViewPager(FragmentManager fragmentManager,int intNumPaginas,clsMetodoPago o_MetodoPago) {
        super(fragmentManager);
        this.intNumPaginas = intNumPaginas;
        this.o_MetodoPago=o_MetodoPago;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();

        switch (position){
            case 0 :
                fragment = new fnt_SelectMetPagoList();
                return fragment;
            case 1 :
                switch (o_MetodoPago.getId()){
                    case 0 :
                        fragment = new fnt_AgregarTarjeta();
                        return fragment;
                    case 1 :
                        fragment = new fnt_AgregarCuentaPayPal();
                        return fragment;
                    case 2 :
                        fragment = new fnt_AgregarTarjeta();
                        return fragment;

                }
                //fragment = new fnt_AgregarTarjeta();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return intNumPaginas;
    }*/
}
