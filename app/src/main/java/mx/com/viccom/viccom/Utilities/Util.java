package mx.com.viccom.viccom.Utilities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import mx.com.viccom.viccom.DB.BDManager;
import mx.com.viccom.viccom.Interfaces.onClickLisener;
import mx.com.viccom.viccom.R;

/**
 * Created by Admin on 28/02/2018.
 */

public class Util{
    public static final int RESULTADO_OK = 1;
    public static final int RESULTADO_CANCEL = 2;
    public static final int SOLICITUD_AGREGAR_RECIBOS = 3;
    public static final int SOLICITUD_AGREGAR_TARJETA = 4;
    public static final int SOLICITUD_AGREGAR_FORMA_PAGO = 5;
    public static final int SOLICITUD_CAMBIO_CONTRASENA = 6;
    public static final int SOLICITUD_VER_DETALLE_CUENTA = 7;
    public static final int SOLICITUD_SELECCIONA_MES_VENCIMIENTO = 8;
    public static final int SOLICITUD_SELECCIONA_ANO_VENCIMIENTO = 9;
    public static final int SOLICITUD_LEER_CODIGO = 10;
    public static final int ZXING_CAMERA_PERMISSION = 1;


    public static String getUserMailPrefs(SharedPreferences preferences) {
        return preferences.getString("email", "");
    }

    public static String getUserPassPrefs(SharedPreferences preferences) {
        return preferences.getString("pass", "");
    }

    public static void removeSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.apply();
    }

    public static boolean isValidEmail(String email)
    {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password)
    {
        return password.length() >= 4;
    }

    public static String getDireccionWCF() {
        String strDirWCF = "";
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT valor FROM Cfg_Parametros where parametro ='DIRECION_WCF';", null);
            if (c.moveToFirst()) {
                strDirWCF = c.getString(0).toString().trim();

            }
            if (!c.isClosed()) c.close();
            db.close();
        }

        return strDirWCF;
    }
    public static String getCodigoRegistro() {
        String strDirWCF = "";
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT valor FROM Cfg_Parametros where parametro ='CODIGO_REGISTRO';", null);
            if (c.moveToFirst()) {
                strDirWCF = c.getString(0).toString().trim();

            }
            if (!c.isClosed()) c.close();
            db.close();
        }

        return strDirWCF;
    }
    public static String setCodigoRegistro(String strCodigo) {
        String strDirWCF = "";
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("SELECT valor FROM Cfg_Parametros where parametro ='CODIGO_REGISTRO';", null);
            if(!c.moveToFirst()){
                ContentValues nuevoRegistro = new ContentValues();

                nuevoRegistro.put("parametro", "CODIGO_REGISTRO");
                nuevoRegistro.put("valor", strCodigo);

                //Insertamos el registro en la base de datos
                db.insert("Cfg_Parametros", null, nuevoRegistro);
            }else{
                ContentValues actualizaRegistro = new ContentValues();

                actualizaRegistro.put("valor", strCodigo);

                String strWhere = "parametro = 'CODIGO_REGISTRO'";

                db.update("Cfg_Parametros", actualizaRegistro, strWhere, null);
            }
            if (!c.isClosed()) c.close();
            db.close();
        }

        return strDirWCF;
    }

    public static String getFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String date = dateFormat.format(new Date());
        String photoCode = date;
        return photoCode;
    }

    public static String getCodigoAleatorio() {
        String strCodigo = "";
        Random aleatorio = new Random(System.currentTimeMillis());
        // Producir nuevo int aleatorio entre 0 y 999999
        int intAletorio = aleatorio.nextInt(999999);
        // Más código

        // Refrescar datos aleatorios
        aleatorio.setSeed(System.currentTimeMillis());
        try {
          /*  Formatter fmt = new Formatter();
            fmt.format("%08d", intAletorio);
            strCodigo = fmt.toString();*/
            strCodigo = intAletorio+"";
        } catch (Exception e) {
            e.printStackTrace();
            strCodigo = "000000";
        }


        // ... o mejor
        // aleatorio.setSeed(aleatorio.getLong());
        return strCodigo;
    }

/*    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return TODO;
        }
        String celPhone = mTelephonyManager.getLine1Number();

        if (celPhone == null) {
            //macAddress = "Device don't have mac address or wi-fi is disabled";
            celPhone="";
        }
        return celPhone;
    }*/

    public static String getMacAddress(Context context) {

        WifiManager wimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            //macAddress = "Device don't have mac address or wi-fi is disabled";
            macAddress="";
        }
        return macAddress;
    }

    public static void customSnackBar(String texto, View view, Context context){//2
        Snackbar snackbar = Snackbar.make(view, texto, Snackbar.LENGTH_LONG);//3
        //snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbar.show();
    }

    public static void customSnackBar(String texto, View view, Context context, final onClickLisener lisener){//2
        Snackbar snackbar = Snackbar.make(view, texto, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.aceptar), new View.OnClickListener() {//3
                    @Override
                    public void onClick(View view) {//4
                        lisener.onClick();
                    }//4
                });//3
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        snackbar.show();
    }

    public static ArrayList<String> getMesesVencimiento() {
            return new ArrayList<String>() {{
                add(new String("01"));
                add(new String("02"));
                add(new String("03"));
                add(new String("04"));
                add(new String("05"));
                add(new String("06"));
                add(new String("07"));
                add(new String("08"));
                add(new String("09"));
                add(new String("10"));
                add(new String("11"));
                add(new String("12"));

            }};
        }
    public static ArrayList<String> getAnoVencimiento() {
        ArrayList<String> arrayListReturn = new  ArrayList<String>();
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);

        for(int i=0;i<=10;i++)
        {
            arrayListReturn.add(new String(año+""));
            año ++;

        }

        return arrayListReturn;
    }

}
