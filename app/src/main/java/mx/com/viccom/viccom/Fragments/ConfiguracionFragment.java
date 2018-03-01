package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment {

    clsUsuarioApp usuarioApp = new clsUsuarioApp();
    public ConfiguracionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("USUARIOAPP");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        return view;
    }

}
