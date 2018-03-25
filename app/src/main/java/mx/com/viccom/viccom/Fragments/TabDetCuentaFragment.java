package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDetCuentaFragment extends Fragment {

    private clsRecibos o_recibo = new clsRecibos();

    private TextView txtNombreCta;
    private TextView txtDireccion;
    private TextView txtColonia;
    private TextView txtMesAdeudo;
    private TextView txtPeriodoFac;
    private TextView txtVencimiento;
    private TextView txtTarifa;
    private TextView txtConsumoFac;

    public TabDetCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_det_cuenta, container, false);
        txtNombreCta = (TextView) view.findViewById(R.id.txtUsuario_det);
        txtDireccion = (TextView) view.findViewById(R.id.txtDireccion_det);
        txtColonia = (TextView) view.findViewById(R.id.txtColonia_det);
        txtMesAdeudo = (TextView) view.findViewById(R.id.txtMesAdeudo_det);
        txtPeriodoFac = (TextView) view.findViewById(R.id.txtPeriodo_det);
        txtVencimiento = (TextView) view.findViewById(R.id.txtVencimiento_det);
        txtTarifa = (TextView) view.findViewById(R.id.txtTarifa_det);
        txtConsumoFac = (TextView) view.findViewById(R.id.txtConsumoFac_det);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtNombreCta.setText("["+o_recibo.getId_cuenta()+"] "+o_recibo.getRazon_social());
        txtDireccion.setText(o_recibo.getDireccion().toString());
        txtColonia.setText(o_recibo.getColonia().toString());
        txtMesAdeudo.setText(o_recibo.getMeses_rezago()+"");
        txtPeriodoFac.setText(o_recibo.getCiclo_facturado().toString());
        txtVencimiento.setText(o_recibo.getFecha_vencimiento().toString());
        txtTarifa.setText(o_recibo.getTarifa().toString());
        txtConsumoFac.setText(o_recibo.getConsumo_act()+" m³ ("+o_recibo.getTipocalculo()+")");

    }

}
