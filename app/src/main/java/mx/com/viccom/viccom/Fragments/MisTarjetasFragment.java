package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.com.viccom.viccom.Activities.AgregarFormaPagoActivity;
import mx.com.viccom.viccom.Activities.AgregarTarjetasPagoActivity;
import mx.com.viccom.viccom.Adapters.TarjetasPagoRecyclerViewAdapter;
import mx.com.viccom.viccom.Clases.clsTarjeta;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Interfaces.onClickLisener;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisTarjetasFragment extends Fragment {


    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private RecyclerView mRecyclerView;
    private FloatingActionButton fabAgregarFP;

    private ArrayList<clsTarjeta> alTarjetas = new ArrayList<clsTarjeta>();
    private TarjetasPagoRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public MisTarjetasFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mis_tarjetas_pago, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recMisTarjetasBancarias);
        fabAgregarFP = (FloatingActionButton) view.findViewById(R.id.fabAgregarTarjetas);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new TarjetasPagoRecyclerViewAdapter(alTarjetas, R.layout.tarjetas_bancarias_recycler_view_item, new onClickLisener() {
            @Override
            public void onClick() {

            }

            @Override
            public void onItemListClick(Object registro) {

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        fabAgregarFP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intento = new Intent(getActivity(),AgregarTarjetasPagoActivity.class);
                //intento.putExtra("USUARIOAPP",usuarioApp);
                startActivityForResult(intento, Util.SOLICITUD_AGREGAR_TARJETA);
            }
        });

        ActualizarDatos();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Util.SOLICITUD_AGREGAR_TARJETA){
            if(resultCode == Util.RESULTADO_OK ){
                ActualizarDatos();
            }
        }
    }

    private void ActualizarDatos() {
        alTarjetas = clsTarjeta.CargarListaTarjetasTodas();
    }

}
