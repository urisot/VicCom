package mx.com.viccom.viccom.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import mx.com.viccom.viccom.Clases.clsMetodoPago;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int cantidadItems;//cantidad fragments por implementacion
    private enCambioFragmento listener;// escucha para diferentes fragments

    public ViewPagerAdapter(FragmentManager fragmentManager, int cantidadItems, enCambioFragmento enCambioFragmentoListener ) {
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

}
