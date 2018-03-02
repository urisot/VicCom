package mx.com.viccom.viccom.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.Picker;
import mx.com.viccom.viccom.Utilities.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarTarjetaFragment extends Fragment {

    private EditText txtNombre;
    private EditText txtNumeroTar;
    private TextView pikerMesVence;
    private TextView pikerAnoVence;
    private EditText txtCodigoSeg;
    private ImageView imgTipoTarjeta;
    private Switch SwPredeterminar;
    private Button btnGuardar;

    public AgregarTarjetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_tarjeta, container, false);

        txtNombre = (EditText) view.findViewById(R.id.txtNombreTarjeta);
        txtNumeroTar = (EditText) view.findViewById(R.id.txtNumeroTarjeta);
        pikerMesVence = (TextView) view.findViewById(R.id.txtMesVence);
        pikerAnoVence = (TextView) view.findViewById(R.id.txtAnoVence);
        txtCodigoSeg = (EditText) view.findViewById(R.id.txtCodigoSeguridad);
        imgTipoTarjeta = (ImageView) view.findViewById(R.id.imgTipoTarjeta);
        SwPredeterminar = (Switch) view.findViewById(R.id.swPredeterminado);
        btnGuardar = (Button) view.findViewById(R.id.btnGradarTarjeta);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        pikerMesVence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getActivity(), Picker.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                  Al_DP_Data= Util.getMesesVencimiento();

                manejadordp.putStringArrayList("DATA", Al_DP_Data);
                manejadordp.putString("TITULO", "Seleccione el mes de vecimiento");
                manejadordp.putBoolean("BUSCAR", false);
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Util.SOLICITUD_SELECCIONA_MES_VENCIMIENTO);


            }
        });

        pikerAnoVence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(getActivity(), Picker.class);
                Bundle manejadordp = new Bundle();
                ArrayList<String> Al_DP_Data = new ArrayList<String>();

                Al_DP_Data= Util.getAnoVencimiento();

                manejadordp.putStringArrayList("DATA", Al_DP_Data);
                manejadordp.putString("TITULO", "Seleccione el año de vencimiento");
                manejadordp.putBoolean("BUSCAR", false);
                intento.putExtras(manejadordp);
                startActivityForResult(intento, Util.SOLICITUD_SELECCIONA_ANO_VENCIMIENTO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Util.SOLICITUD_SELECCIONA_MES_VENCIMIENTO){
            if(resultCode == Util.RESULTADO_OK ){
                String strMes = data.getStringExtra("OPCION");
                pikerMesVence.setText(strMes);
                //Id_TServicio = Catalogo.GetId("Cat_Servicios",Servicio,"id_Servicio");
            }
        }

        if(requestCode == Util.SOLICITUD_SELECCIONA_ANO_VENCIMIENTO){
            if(resultCode == Util.RESULTADO_OK ){
                String strAno = data.getStringExtra("OPCION");
                pikerAnoVence.setText(strAno);
                //Id_TServicio = Catalogo.GetId("Cat_Servicios",Servicio,"id_Servicio");
            }
        }

    }

}

