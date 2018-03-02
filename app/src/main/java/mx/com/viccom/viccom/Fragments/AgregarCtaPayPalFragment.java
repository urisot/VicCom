package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarCtaPayPalFragment extends Fragment {


    public AgregarCtaPayPalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agregar_cta_pay_pal, container, false);
        return view;
    }

}
