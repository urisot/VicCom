package mx.com.viccom.viccom.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Fragments.TabDetCuentaFragment;
import mx.com.viccom.viccom.Fragments.TabDetReciboFragment;
import mx.com.viccom.viccom.Fragments.TabHisRecibosFragment;


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

        Bundle argumentos = new Bundle();
        switch (position){
            case 0 :
                fragment = new TabDetCuentaFragment();
                argumentos.putParcelable("RECIBO",o_recibo);
                fragment.setArguments(argumentos);
                return fragment;
            case 1 :
                fragment = new TabDetReciboFragment();
                argumentos.putParcelable("RECIBO",o_recibo);
                fragment.setArguments(argumentos);
                return fragment;
            case 2 :
                fragment = new TabHisRecibosFragment();
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
