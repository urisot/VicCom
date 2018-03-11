package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.com.viccom.viccom.Adapters.MetodosPagoRecyclerViewAdapter;
import mx.com.viccom.viccom.Clases.clsMetodoPago;
import mx.com.viccom.viccom.Interfaces.onClickLisener;
import mx.com.viccom.viccom.Interfaces.onPageChangedLisener;
import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMetodoPagoFragment extends Fragment {
    private onPageChangedLisener Listener;
    private clsMetodoPago o_metodoPago = new clsMetodoPago();
    private RecyclerView mRecyclerView;
    private ArrayList<clsMetodoPago> ArListMetodosPago = new ArrayList<clsMetodoPago>();
    private MetodosPagoRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public SelectMetodoPagoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_metodo_pago, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recSelectFormaPago);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Listener = (onPageChangedLisener) getActivity();
        } catch (ClassCastException e) {
        }
        ArListMetodosPago = getMetodosPago();

        mLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new MetodosPagoRecyclerViewAdapter(ArListMetodosPago, R.layout.tarjetas_bancarias_recycler_view_item, new onClickLisener() {
            @Override
            public void onClick() {

            }

            @Override
            public void onItemListClick(Object registro) {
                o_metodoPago = (clsMetodoPago) registro;
                Listener.onChanged(o_metodoPago);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private ArrayList<clsMetodoPago> getMetodosPago() {
        return new ArrayList<clsMetodoPago>() {{
            add(new clsMetodoPago(0,"Tarjeta Bancaria", "pagar mediante Tarjeta bancaria","https://veterinariagoicochea.com/wp-content/uploads/2014/10/visa-mastercard-logo.jpg"));
            add(new clsMetodoPago(1,"PayPal", "Pagar mediante cuenta de PayPal","https://www.paypalobjects.com/webstatic/mktg/logo/pp_cc_mark_37x23.jpg"));
            add(new clsMetodoPago(2,"Mercado Pago", "pagar mediante cuenta de mercado pago","https://is2-ssl.mzstatic.com/image/thumb/Purple128/v4/4a/75/89/4a758961-743b-1a47-4ff3-d0b20684f274/AppIcon-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-5.png/246x0w.jpg"));

        }};
    }

}
