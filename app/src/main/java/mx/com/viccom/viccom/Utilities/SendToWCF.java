package mx.com.viccom.viccom.Utilities;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsParameter;

/**
 * Created by Admin on 28/02/2018.
 */

public class SendToWCF {
    public static String Send_Get(String Url,clsParameter clsParameter){
        String Get_Parameters = "?";
        Get_Parameters+= clsParameter.parameter_name.replaceAll(" ","+")+"="+ clsParameter.parameter_value.replaceAll(" ","+");
        String Resultado = "";

        URL url;
        Log.e("Debug",Url+Get_Parameters);
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(Url+Get_Parameters);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            BufferedReader r = new BufferedReader(isw);
            StringBuilder total = new StringBuilder();

            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Resultado = total.toString();

            return Resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }


    }

    public static String Send_Get(String Url,ArrayList<clsParameter> clsParameters){
        String Get_Parameters = "?";

        for(int i = 0; i< clsParameters.size(); i++ ){
            Get_Parameters+= clsParameters.get(i).parameter_name.replaceAll(" ","+")+"="+ clsParameters.get(i).parameter_value.replaceAll(" ","+");
            if(i!= clsParameters.size()-1)
                Get_Parameters+="&";
        }
        String Resultado = "";

        URL url;
        Log.e("Debug",Url+Get_Parameters);
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(Url+Get_Parameters);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            BufferedReader r = new BufferedReader(isw);
            StringBuilder total = new StringBuilder();

            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Resultado = total.toString();

            return Resultado;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }


    }

    public static String Send_Post(String Url,ArrayList<clsParameter> clsParameters){
        Log.e("Debug",Url);
        URL url;
        String Resultado = "";

        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection=null;

        try {
            url = new URL(Url);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Host","android.schoolportal.gr");
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();

            for(int i = 0; i< clsParameters.size(); i++ ){

                if(clsParameters.get(i).parameter_name.contains("array:")){
                    JSONArray jsonArray = new JSONArray(clsParameters.get(i).parameter_value);
                    jsonParam.put(clsParameters.get(i).parameter_name.replace("array:",""),jsonArray);

                }
                else if(clsParameters.get(i).parameter_name.contains("obj:")){
                    JSONObject jsonObj = new JSONObject(clsParameters.get(i).parameter_value);
                    jsonParam.put(clsParameters.get(i).parameter_name.replace("obj:",""),jsonObj);
                }
                else
                    jsonParam.put(clsParameters.get(i).parameter_name.replaceAll(" ", "+"), clsParameters.get(i).parameter_value.replaceAll(" ", "+"));

            }

            DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream());
            String str = jsonParam.toString();
            Log.e("ObjetoEnviado ",str);
            byte[] data=str.getBytes("UTF-8");
            //printout.writeChars(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            printout.write(data);
            printout.flush ();
            printout.close ();

            int HttpResult =urlConnection.getResponseCode();

            if(HttpResult == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"UTF-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                //System.out.println(""+sb.toString());
                return ""+sb.toString();

            }else{
                //ystem.out.println(urlConnection.getResponseMessage());
                Resultado = "ErrorConexion";
                return Resultado;

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

            Resultado = "ErrorURL";
            return Resultado;

        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Resultado = "ErroJSON";
            return Resultado;
        }catch (IOException e) {

            e.printStackTrace();
            return "ErrorConexion";

        }finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

    }

}

