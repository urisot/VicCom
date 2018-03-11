package mx.com.viccom.viccom.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.Picker;
import mx.com.viccom.viccom.Utilities.Util;

public class AgregarTarjetasPagoActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtNumeroTar;
    private TextView pikerMesVence;
    private TextView pikerAnoVence;
    private EditText txtCodigoSeg;
    private ImageView imgTipoTarjeta;
    private Switch SwPredeterminar;
    private Button btnGuardar,btnEscanearTarjeta;

    private static final int REQUEST_SCAN = 101;
    private static final int REQUEST_AUTOTEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjetas_pago);

        txtNombre = (EditText) findViewById(R.id.txtNombreTarjeta);
        txtNumeroTar = (EditText) findViewById(R.id.txtNumeroTarjeta);
        pikerMesVence = (TextView) findViewById(R.id.txtMesVence);
        pikerAnoVence = (TextView) findViewById(R.id.txtAnoVence);
        txtCodigoSeg = (EditText) findViewById(R.id.txtCodigoSeguridad);
        imgTipoTarjeta = (ImageView) findViewById(R.id.imgTipoTarjeta);
        SwPredeterminar = (Switch) findViewById(R.id.swPredeterminado);
        btnGuardar = (Button) findViewById(R.id.btnGradarTarjeta);
        btnEscanearTarjeta = (Button) findViewById(R.id.btnEscanearTarjeta);


        pikerMesVence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(AgregarTarjetasPagoActivity.this, Picker.class);
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
                Intent intento = new Intent(AgregarTarjetasPagoActivity.this, Picker.class);
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

        btnEscanearTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgregarTarjetasPagoActivity.this, CardIOActivity.class)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                        .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
                        .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "es")
                        .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                        .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
                startActivityForResult(intent, REQUEST_SCAN);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

        if ((requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST) && data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
        {
            //((TextView) findViewById(R.id.tvCardDetail)).setVisibility(View.VISIBLE);
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
            {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                txtNumeroTar.setText(scanResult.cardNumber.toString());

                if (scanResult.cardholderName != null) {
                    txtNombre.setText(scanResult.cardholderName);
                }
                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                //resultDisplayStr = "Card Number: " + scanResult.cardNumber.toString()+ "\n";//scanResult.getRedactedCardNumber() + "\n";
                //Log.d(TAG, "Card Number " + resultDisplayStr);
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid())
                {
                    pikerMesVence.setText(scanResult.expiryMonth +"");
                    pikerAnoVence.setText(scanResult.expiryYear+"");
                    //resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
                if (scanResult.cvv != null)
                {
                    // Never log or display a CVV
                    txtCodigoSeg.setText(scanResult.cvv);
                    //resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }
               /* if (scanResult.postalCode != null)
                {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }*/
            }
            else
            {
                resultDisplayStr = "Scan was canceled.";
            }
            //((TextView) findViewById(R.id.tvCardDetail)).setText(resultDisplayStr);
        }

    }
}
