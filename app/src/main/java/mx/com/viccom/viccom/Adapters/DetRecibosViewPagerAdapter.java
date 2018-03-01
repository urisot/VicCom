package mx.com.viccom.viccom.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Fragments.DetCuentaFragment;
import mx.com.viccom.viccom.Fragments.DetReciboFragment;
import mx.com.viccom.viccom.Fragments.HisRecibosFragment;


/**
 * Created by Admin on 28/02/2018.
 */

public class DetRecibosViewPagerAdapter extends FragmentStatePagerAdapter {
    private clsRecibos o_recibo = new clsRecibos();
    private int intNumTabs;

    public DetRecibosViewPagerAdapter(FragmentManager fragmentManager, int intNumTabs, clsRecibos o_recibo) {
        super(fragmentManager);
        this.intNumTabs = intNumTabs;
        this.o_recibo = o_recibo;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
    /*    Bundle argumentos = new Bundle();
        argumentos.putParcelable("recibo",o_recibo);
        fragment.setArguments(argumentos);

*/
        Bundle argumentos = new Bundle();
        switch (position){
            case 0 :
                fragment = new DetCuentaFragment();
                argumentos.putParcelable("RECIBO",o_recibo);
                fragment.setArguments(argumentos);
                return fragment;
            case 1 :
                fragment = new DetReciboFragment();
                argumentos.putParcelable("RECIBO",o_recibo);
                fragment.setArguments(argumentos);
                return fragment;
            case 2 :
                fragment = new HisRecibosFragment();
                argumentos.putParcelable("RECIBO",o_recibo);
                fragment.setArguments(argumentos);
                return fragment;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return intNumTabs;
    }
}
