package mx.com.viccom.viccom.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import mx.com.viccom.viccom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResConPideEmailFragment extends Fragment {

    private EditText txt_email_res;
    private Button btnHelpEmail_res,btnEnviar_res;

    public ResConPideEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_con_pide_email, container, false);
        txt_email_res = (EditText) view.findViewById(R.id.txt_email_res);
        btnHelpEmail_res = (Button) view.findViewById(R.id.btnHelpEmail_res);
        btnEnviar_res = (Button) view.findViewById(R.id.btnEnviar_res);
        return view;
    }

}
