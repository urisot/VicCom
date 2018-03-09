package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment {

    clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txtTextoAencriptar;
    private TextView txtEncriptado,txtDesencriptado;
    private Button btnEncripta,btnDesencripta;
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
        txtTextoAencriptar = (EditText) view.findViewById(R.id.txtTextoencriptar);
        txtEncriptado = (TextView) view.findViewById(R.id.txtResultado);
        txtDesencriptado = (TextView) view.findViewById(R.id.txtTextoOriginal);
        btnEncripta = (Button) view.findViewById(R.id.btnEncriptar);
        btnDesencripta = (Button) view.findViewById(R.id.btndesencriptar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnEncripta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
