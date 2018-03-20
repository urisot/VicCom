package mx.com.viccom.viccom.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.com.viccom.viccom.R;

public class RealizarPagoActivity extends AppCompatActivity {

    public static final String PAYPAL_CLIENT_ID = "ATrIrsFs4P13PfFOE_0VvTa0IvvewRii4Jl9xZQ0-MHXLZax-pz5Tmvtfw2rL0iEzw8eO6hcb8_yXmLs";
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pago);

    }
}
