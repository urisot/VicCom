package mx.com.viccom.viccom.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.viccom.viccom.R;

public class Picker extends AppCompatActivity {

    private ListView lvopciones;
    private TextView lblBuscar;
    private EditText Buscador;
    private Button btnCancelar;
    private ArrayList<String> Data = new ArrayList<String>();
    private ArrayAdapter<String> Adaptador;
    private TextView Titulo;
    private boolean boolMostrarBusqueda = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        lvopciones = (ListView)findViewById(R.id.lvopciones);
        btnCancelar = (Button)findViewById(R.id.btn_Cancel);
        lblBuscar = (TextView) findViewById(R.id.txt_Buscar);
        Buscador = (EditText)findViewById(R.id.et_Buscar);
        Titulo = (TextView)findViewById(R.id.cabecera);


        Bundle manejadord = getIntent().getExtras();
        Data = manejadord.getStringArrayList("DATA");
        Titulo.setText(manejadord.getString("TITULO","Seleccione una opcion"));
        boolMostrarBusqueda = manejadord.getBoolean("BUSCAR",true);

        if (!boolMostrarBusqueda){
            lblBuscar.setVisibility(View.GONE);
            Buscador.setVisibility(View.GONE);
        }


        Adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Data);
        lvopciones.setAdapter(Adaptador);
        lvopciones.setTextFilterEnabled(true);

        lvopciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Buscador.getWindowToken(), 0);
                Intent intent = getIntent();// new Intent();
                intent.putExtra("OPCION", Adaptador.getItem(position));
                setResult(Util.RESULTADO_OK, intent);
                finish();//finishing activity
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(Buscador.getWindowToken(), 0);
                setResult(Util.RESULTADO_CANCEL);
                finish();//finishing activity
            }
        });

        Buscador.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                Picker.this.Adaptador.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
}
