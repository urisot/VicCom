package mx.com.viccom.viccom.Interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by Admin on 28/02/2018.
 */

public interface onPageChangedLisener {
    public void onChangedPasObject(Object Datos);
    public void onChangedPasPage(int page);
    public void onChangedPasFragment(Fragment fragment);
}
