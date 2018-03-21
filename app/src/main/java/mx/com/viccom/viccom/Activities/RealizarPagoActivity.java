package mx.com.viccom.viccom.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.R;

public class RealizarPagoActivity extends AppCompatActivity {

    private clsRecibos o_recibo = new clsRecibos();

    public static final String PAYPAL_CLIENT_ID = "ATrIrsFs4P13PfFOE_0VvTa0IvvewRii4Jl9xZQ0-MHXLZax-pz5Tmvtfw2rL0iEzw8eO6hcb8_yXmLs";
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // cuenta sandbox para pruebas
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PAYPAL_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private Button btnPagar;
    private TextView txtImporte,txtComicion;
    private String Importe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pago);

        // Activar flecha ir atrás
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Revisa tu pago");


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");
        }

        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        startService(intent);


        txtImporte = (TextView) findViewById(R.id.txtImporteRequerido);
        txtComicion = (TextView) findViewById(R.id.txtComiciones);
        btnPagar = (Button) findViewById(R.id.btnPagarPayPal);


        if (o_recibo.getTotal() != 0 ){
            txtImporte.setText(o_recibo.getTotal()+"");
        }

        txtComicion.setText(CalculaComicion(o_recibo.getTotal()));

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcesoAplicaPago();
            }
        });

    }
    private String CalculaComicion(float importe){
       // String strReturn ="";

        float floPorComicion = 0;
        double douImpComFija = 4.64;
        double douImComicion = 0;


        floPorComicion = Math.round(importe * 0.458);
        douImComicion = Math.round((floPorComicion + douImpComFija) * 0.16);

        return  douImComicion+"";
    }

    private void ProcesoAplicaPago() {
        double douImporte = Double.parseDouble(txtImporte.getText().toString()) + Double.parseDouble(txtComicion.getText().toString());
        Importe = douImporte+"";

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(Importe))
                ,"MXN"
                ,"Pagar recibo de agua"
                ,PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this,PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK){

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String detallePago = confirmation.toJSONObject().toString(4);
                        startActivity( new Intent(this,ResultadoPagoActivity.class)
                                .putExtra("DETALLEPAGO",detallePago)
                                .putExtra("IMPORTE",Importe));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
            else if (requestCode == RESULT_CANCELED)
                Toast.makeText(this,"Cancelado",Toast.LENGTH_SHORT).show();


        }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this,"Ivalido",Toast.LENGTH_SHORT).show();


        }




    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
}
