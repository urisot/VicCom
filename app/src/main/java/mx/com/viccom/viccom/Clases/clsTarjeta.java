package mx.com.viccom.viccom.Clases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.com.viccom.viccom.DB.BDManager;
import mx.com.viccom.viccom.Utilities.App;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsTarjeta {
    private int id = 0;
    private String alias="";
    private String nombre="";
    private String numero="";
    private int mes =0;
    private int ano =0;
    private int ccv=0;
    private int id_tipo=0;
    private int predeterminado=0;

    public clsTarjeta(int id, String alias, String nombre, String numero, int mes, int ano, int ccv, int id_tipo, int predeterminado) {
        this.id = id;
        this.alias = alias;
        this.nombre = nombre;
        this.numero = numero;
        this.mes = mes;
        this.ano = ano;
        this.ccv = ccv;
        this.id_tipo = id_tipo;
        this.predeterminado = predeterminado;
    }



    public int getPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(int predeterminado) {
        this.predeterminado = predeterminado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public static ArrayList<clsTarjeta> CargarListaTarjetasTodas(){
        ArrayList<clsTarjeta> listTarjetas = new ArrayList<clsTarjeta>();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {//6
            Cursor cursor = db.rawQuery("SELECT * FROM Opr_Tarjetas;", null);
            if (cursor.moveToFirst()) {
                // iteramos sobre el cursor de resultados,
                // y vamos rellenando el array que posteriormente devolveremos
                while (cursor.isAfterLast() == false) {

                    listTarjetas.add(new clsTarjeta(cursor.getInt(cursor.getColumnIndex("id"))
                            ,cursor.getString(cursor.getColumnIndex("alias"))
                            ,cursor.getString(cursor.getColumnIndex("nombre"))
                            ,cursor.getString(cursor.getColumnIndex("numero"))
                            ,cursor.getInt(cursor.getColumnIndex("mes"))
                            ,cursor.getInt(cursor.getColumnIndex("ano"))
                            ,cursor.getInt(cursor.getColumnIndex("ccv"))
                            ,cursor.getInt(cursor.getColumnIndex("tipo"))
                            ,cursor.getInt(cursor.getColumnIndex("predeterminado"))));
                    cursor.moveToNext();
                }
            }


            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listTarjetas;
    }
    public static boolean insertarTarjeta(clsTarjeta o_trjeta){
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("id", o_trjeta.getId());
            nuevoRegistro.put("alias", o_trjeta.getAlias());
            nuevoRegistro.put("nombre", o_trjeta.getNombre());
            nuevoRegistro.put("mes", o_trjeta.getMes());
            nuevoRegistro.put("ano", o_trjeta.getAno());
            nuevoRegistro.put("ccv", o_trjeta.getCcv());
            nuevoRegistro.put("tipo", o_trjeta.getId_tipo());
            nuevoRegistro.put("predeterminado", o_trjeta.getPredeterminado()+"");


            //Insertamos el registro en la base de datos
            db.insert("Opr_Recibos", null, nuevoRegistro);

            //db.execSQL(getInsertarNormalQuery(recibo));
            db.close();
        }
        return true;
    }
}

//Enlases de las imagenes tipo de tarjeta
//https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Old_Visa_Logo.svg/220px-Old_Visa_Logo.svg.png
//https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/MasterCard_early_1990s_logo.png/220px-MasterCard_early_1990s_logo.png

