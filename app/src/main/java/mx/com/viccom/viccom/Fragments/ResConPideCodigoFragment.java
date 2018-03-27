package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import mx.com.viccom.viccom.Activities.CodigoRegistroActivity;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.StringMD;
import mx.com.viccom.viccom.Utilities.Util;


public class ResConPideCodigoFragment extends Fragment {

    onPageChangedLisener Listener;

    //private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txtCodigo_res;
    private Button btnHelpCodigo_res;
    private ProgressBar pbProgresoResCodigo;
    private LinearLayout llCodigo;
    private RelativeLayout rlPrincipal;
    private Animation animEntrar;


    public ResConPideCodigoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /*    Bundle bundle = getArguments();
        if (bundle != null) {
            usuarioApp = bundle.getParcelable("USUARIOAPP");
        }*/
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_res_con_pide_codigo, container, false);
        txtCodigo_res = (EditText) view.findViewById(R.id.txtCodigo_res);
        btnHelpCodigo_res = (Button) view.findViewById(R.id.btnHelpCodigo_res);
        pbProgresoResCodigo = (ProgressBar) view.findViewById(R.id.pbProgresoResCodigo);
        llCodigo = (LinearLayout) view.findViewById(R.id.llCodigo);
        rlPrincipal = (RelativeLayout) view.findViewById(R.id.rlPrincipal);
        animEntrar = AnimationUtils.loadAnimation(getContext(),R.anim.entrar_fragment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Listener = (onPageChangedLisener) getActivity();
        } catch (ClassCastException e) {
            //
        }

        rlPrincipal.setAnimation(animEntrar);

        btnHelpCodigo_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         /*       Fragment fragment = new ResConPideCodigoFragment();
                Listener.onChangedPasFragment(fragment);*/

            }
        });
        txtCodigo_res.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(),"start "+start+" before "+before+" count "+count+ " secuence "+s,Toast.LENGTH_SHORT).show();
                if (s.length() == 6){
                    String strCodigo = Util.getCodigoRegistro();
                    String strTxtCod = txtCodigo_res.getText().toString();
                    if (strTxtCod.equals(strCodigo)){

                        Fragment fragment = new ResConCambioConFragment();
                        Listener.onChangedPasFragment(fragment);

                    }else {
                        Util.customSnackBar("Verifique el codigo",txtCodigo_res,getContext());
                    }

                }else {
                    //Campos_Requeridos();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });



    }
}
