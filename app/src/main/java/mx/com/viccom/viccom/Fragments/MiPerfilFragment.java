package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Interfaces.backProces;
import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiPerfilFragment extends Fragment {

   // backProces Listener;

    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private EditText txtEmil;
    private EditText txtNombre;
    private Button btnActualizar;
    private Button btnCambiarCon;

    public MiPerfilFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        txtEmil = (EditText) view.findViewById(R.id.txtEmail_mp);
        txtNombre = (EditText) view.findViewById(R.id.txtNombre_mp);
        btnActualizar = (Button) view.findViewById(R.id.btnActualizar_mp);
        btnCambiarCon = (Button) view.findViewById(R.id.btnCambiarCon_mp);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   /*     try {
            Listener = (backProces) getActivity();
        } catch (ClassCastException e) {
        }*/

        txtEmil.setText(usuarioApp.getMail());
        txtNombre.setText(usuarioApp.getNombre());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
  /*      if (!TextUtils.isEmpty(txtNombre.getText())){
            if (!usuarioApp.getNombre().equals(txtNombre.getText())){
                String strNombre = txtNombre.getText().toString();
                Listener.onActualizaNombreUsrApp(strNombre);
            }
        }*/

       // Toast.makeText(getContext(),"on destroy view",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getContext(),"on destroy",Toast.LENGTH_SHORT).show();
    }
}
