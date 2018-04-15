package mx.com.viccom.viccom.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import mx.com.viccom.viccom.Activities.MenuActivity;
import mx.com.viccom.viccom.Clases.clsHisRecibos;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.Clases.clsResultadoWCF;
import mx.com.viccom.viccom.Clases.clsUsuarioApp;
import mx.com.viccom.viccom.Fragments.MisCuentasFragment;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;
import mx.com.viccom.viccom.Utilities.Util;

public class Notificaciones extends Service {

    private clsUsuarioApp usuarioApp = new clsUsuarioApp();
    private ActualizaHttp actualizaHttp;

    int counter = 0;
    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();


    NotificationCompat.Builder mNotificationBuilder;
    private static final int ID_NOTIFICACION_RECIBO_VENCIDO = 1;
    private static final int ID_NOTIFICACION_POR_VENCER = 2;
    private static final int ID_NOTIFICACION_RECIBO_NUEVO = 3;

   // private ActualizaHttp actualizaHttp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent,int flag,int idProcess){
            doSomethingRepeatedly();

        try {
            usuarioApp=(clsUsuarioApp) intent.getExtras().get("USUARIOAPP");

            ArrayList<clsParameter> Parametros = new ArrayList<>();
            Parametros.add(new clsParameter("p1", usuarioApp.getId_usuarioapp()));

            actualizaHttp = new ActualizaHttp();
            actualizaHttp.execute(Parametros);
        /*    new DoBackgroundTask().execute(new URL(
                    "http://www.google.com/imagen1.png"), new URL(
                    "http://www.google.com/imagen2.png"), new URL(
                    "http://www.google.com/imagen3.png"), new URL(
                    "http://www.google.com/imagen4.png"));*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return START_STICKY;
    }


    private int DuermePor5Segundos() {
        try {
            // Simulamos la descarga de un fichero
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return 100;
    }



   public class ActualizaHttp extends AsyncTask<ArrayList<clsParameter>,Integer,ArrayList<clsRecibos>> {
       private clsResultadoWCF o_resultadoWCF= new clsResultadoWCF();

       @Override
       protected void onPreExecute() {
           super.onPreExecute();


       }

        @Override
        protected ArrayList<clsRecibos> doInBackground(ArrayList<clsParameter>[] Parametros) {

            for (int i = 0; i < 5; i++) {
                DuermePor5Segundos();
            }

            ArrayList<clsRecibos> listRecibosReturn = new ArrayList<clsRecibos>();
            clsParameter HttpResult = new clsParameter();

            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getReciboUsrApp";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {


                    Gson gson = new GsonBuilder().serializeNulls().create();
                    HttpResult.setParameter_name(HttpResult.getParameter_name().replace("\\/","/"));
                    //String jSonResult =HttpResult.getParameter_name().substring(24,HttpResult.getParameter_name().length()-1);
                    listRecibosReturn = gson.fromJson(HttpResult.getParameter_name(), new TypeToken<ArrayList<clsRecibos>>(){}.getType());


                }
                else {
                    o_resultadoWCF.setId(0);
                    o_resultadoWCF.setComando("getReciboUsrApp");
                    o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con error");
                    o_resultadoWCF.setError_number(1);
                    o_resultadoWCF.setError_menssage("Ha ocurrido un error de conexion.");
                    o_resultadoWCF.setFolio_registro("");
                    o_resultadoWCF.setFecha(Util.getFechaActual());
                    // HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");
                }



            }catch (Exception e) {//4
                e.printStackTrace();
                o_resultadoWCF.setId(0);
                o_resultadoWCF.setComando("getReciboUsrApp");
                o_resultadoWCF.setOperacion("Obtener el listado de cuentas ligadas a este usuario, Con error");
                o_resultadoWCF.setError_number(1);
                o_resultadoWCF.setError_menssage("Error!: "+e.toString());
                o_resultadoWCF.setFolio_registro("");
                o_resultadoWCF.setFecha(Util.getFechaActual());
                Log.e("Error", e.toString());

                HttpResult.setParameter_value("Error!: "+e.toString());


            }//4

            return listRecibosReturn;


        }

        @Override
        protected void onPostExecute(ArrayList<clsRecibos> ListRecibos) {
            super.onPostExecute(ListRecibos);

            if (!(o_resultadoWCF.getError_number()>0)){

                //refreshLayout.setRefreshing(false);

                if (ListRecibos.size() > 0){
                    MuestraLasNotificacion(ListRecibos);


                }else{

                }


            }else{

            }


        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
            //refreshLayout.setRefreshing(false);
        }
    }

    private ArrayList<clsRecibos> ArListRecibosVencidos = new ArrayList<clsRecibos>();

    private void MuestraLasNotificacion(ArrayList<clsRecibos> listRecibos) {

        for (clsRecibos recibo : listRecibos) {

            if (recibo.getVencido() == 1 && recibo.getPago_requerido() >0){
                ArListRecibosVencidos.add(recibo);
            }
        }


        if (ArListRecibosVencidos.size() >0){
            String strMensaje ="";
            if(ArListRecibosVencidos.size() == 1){
                strMensaje="Tienes 1 Recibo Vencido" ;
            }else{
                strMensaje="Tienes "+ArListRecibosVencidos.size()+" Recibs Vencidos";
            }

            NotificaRecibosVencidos(strMensaje);

        }


    }

    private void NotificaRecibosVencidos(String strMensaje) {
        SuenaNotificacion();
        mNotificationBuilder = new NotificationCompat.Builder(this);
        mNotificationBuilder.setAutoCancel(true);
        mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mNotificationBuilder.setTicker("Nueva notificacion");
        mNotificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        mNotificationBuilder.setWhen(System.currentTimeMillis());
        mNotificationBuilder.setContentTitle("Recibo vencido");
        mNotificationBuilder.setContentText(strMensaje);

        Intent intent = new Intent(this,MenuActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationBuilder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(ID_NOTIFICACION_RECIBO_VENCIDO,mNotificationBuilder.build());
    }

    private void SuenaNotificacion() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
    }


    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Log.d("MyService", String.valueOf(++counter));
            }

        }, 0, UPDATE_INTERVAL);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
