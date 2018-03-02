package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.viccom.viccom.Activities.AgregarFormaPagoActivity;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisFormasPagoFragment extends Fragment {


    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private RecyclerView recViewFormasPago;
    private FloatingActionButton fabAgregarFP;
    public MisFormasPagoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mis_formas_pago, container, false);
        recViewFormasPago = (RecyclerView) view.findViewById(R.id.recMisFormasPAgo);
        fabAgregarFP = (FloatingActionButton) view.findViewById(R.id.fabAgregarFormasPago);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fabAgregarFP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getActivity(),AgregarFormaPagoActivity.class);
                //intento.putExtra("USUARIOAPP",usuarioApp);
                startActivityForResult(intento, Util.SOLICITUD_AGREGAR_RECIBOS);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
