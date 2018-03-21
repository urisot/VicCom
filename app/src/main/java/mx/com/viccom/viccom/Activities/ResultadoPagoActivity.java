package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mx.com.viccom.viccom.R;

public class ResultadoPagoActivity extends AppCompatActivity {

    private TextView txtId,txtImporteApli,txtEstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_pago);
        txtId =(TextView) findViewById(R.id.txtId);
        txtImporteApli =(TextView) findViewById(R.id.txtImorteaplicado);
        txtEstatus =(TextView) findViewById(R.id.txtEstatus);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("DETALLEPAGO"));
            MuestraDetalle(jsonObject.getJSONObject("response"),intent.getStringExtra("IMPORTE"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void MuestraDetalle(JSONObject response, String importe) {

        try {
            txtId.setText(response.getString("id"));
            txtEstatus.setText(response.getString("state"));
            txtImporteApli.setText(String.format("$%s",importe));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
