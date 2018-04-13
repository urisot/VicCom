package mx.com.viccom.viccom.Clases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;



import java.util.ArrayList;
import java.util.List;

import mx.com.viccom.viccom.DB.BDManager;
import mx.com.viccom.viccom.Utilities.App;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsRecibos  implements Parcelable{
    private int id =0;
    private String id_recibo="";
    private int id_padron=0;
    private int id_cuenta=0;
    private String razon_social="";
    private String direccion="";
    private String colonia="";
    private String poblacion="";
    private String localizacion="";
    private String tipocalculo="";
    private String servicio="";
    private String tarifa="";
    private String clsusuario="";
    private String anomalia="";
    private String id_medidor="";
    private String estatus="";
    private int meses_rezago=0;
    private int promedio_act=0;
    private int consumo_act=0;
    private String fecha_factura_act="";
    private String fecha_vencimiento="";
    private String ciclo_facturado="";
    private String periodo="";
    private float total=0;
    private float pago_requerido=0;
    private int vencido=0;

    public clsRecibos() {

    }

    public clsRecibos(int id, String id_recibo, int id_padron, int id_cuenta, String razon_social, String direccion, String colonia, String poblacion, String localizacion, String tipocalculo, String servicio, String tarifa, String clsusuario, String anomalia, String id_medidor, String estatus, int meses_rezago, int promedio_act, int consumo_act, String fecha_factura_act, String fecha_vencimiento, String ciclo_facturado, String periodo, float total, float pago_requerido, int vencido) {
        this.id = id;
        this.id_recibo = id_recibo;
        this.id_padron = id_padron;
        this.id_cuenta = id_cuenta;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.colonia = colonia;
        this.poblacion = poblacion;
        this.localizacion = localizacion;
        this.tipocalculo = tipocalculo;
        this.servicio = servicio;
        this.tarifa = tarifa;
        this.clsusuario = clsusuario;
        this.anomalia = anomalia;
        this.id_medidor = id_medidor;
        this.estatus = estatus;
        this.meses_rezago = meses_rezago;
        this.promedio_act = promedio_act;
        this.consumo_act = consumo_act;
        this.fecha_factura_act = fecha_factura_act;
        this.fecha_vencimiento = fecha_vencimiento;
        this.ciclo_facturado = ciclo_facturado;
        this.periodo = periodo;
        this.total = total;
        this.pago_requerido = pago_requerido;
        this.vencido = vencido;
    }

    protected clsRecibos(Parcel in) {
        id = in.readInt();
        id_recibo = in.readString();
        id_padron = in.readInt();
        id_cuenta = in.readInt();
        razon_social = in.readString();
        direccion = in.readString();
        colonia = in.readString();
        poblacion = in.readString();
        localizacion = in.readString();
        tipocalculo = in.readString();
        servicio = in.readString();
        tarifa = in.readString();
        clsusuario = in.readString();
        anomalia = in.readString();
        id_medidor = in.readString();
        estatus = in.readString();
        meses_rezago = in.readInt();
        promedio_act = in.readInt();
        consumo_act = in.readInt();
        fecha_factura_act = in.readString();
        fecha_vencimiento = in.readString();
        ciclo_facturado = in.readString();
        periodo = in.readString();
        total = in.readFloat();
        pago_requerido = in.readFloat();
        vencido = in.readInt();
    }

    public static final Parcelable.Creator<clsRecibos> CREATOR = new Parcelable.Creator<clsRecibos>() {
        @Override
        public clsRecibos createFromParcel(Parcel in) {
            return new clsRecibos(in);
        }

        @Override
        public clsRecibos[] newArray(int size) {
            return new clsRecibos[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(String id_recibo) {
        this.id_recibo = id_recibo;
    }

    public int getId_padron() {
        return id_padron;
    }

    public void setId_padron(int id_padron) {
        this.id_padron = id_padron;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getTipocalculo() {
        return tipocalculo;
    }

    public void setTipocalculo(String tipocalculo) {
        this.tipocalculo = tipocalculo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getClsusuario() {
        return clsusuario;
    }

    public void setClsusuario(String clsusuario) {
        this.clsusuario = clsusuario;
    }

    public String getAnomalia() {
        return anomalia;
    }

    public void setAnomalia(String anomalia) {
        this.anomalia = anomalia;
    }

    public String getId_medidor() {
        return id_medidor;
    }

    public void setId_medidor(String id_medidor) {
        this.id_medidor = id_medidor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getMeses_rezago() {
        return meses_rezago;
    }

    public void setMeses_rezago(int meses_rezago) {
        this.meses_rezago = meses_rezago;
    }

    public int getPromedio_act() {
        return promedio_act;
    }

    public void setPromedio_act(int promedio_act) {
        this.promedio_act = promedio_act;
    }

    public int getConsumo_act() {
        return consumo_act;
    }

    public void setConsumo_act(int consumo_act) {
        this.consumo_act = consumo_act;
    }

    public String getFecha_factura_act() {
        return fecha_factura_act;
    }

    public void setFecha_factura_act(String fecha_factura_act) {
        this.fecha_factura_act = fecha_factura_act;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getCiclo_facturado() {
        return ciclo_facturado;
    }

    public void setCiclo_facturado(String ciclo_facturado) {
        this.ciclo_facturado = ciclo_facturado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public float getTotal() {

        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPago_requerido() {
        return pago_requerido;
    }

    public void setPago_requerido(float pago_requerido) {
        this.pago_requerido = pago_requerido;
    }

    public int getVencido() {
        return vencido;
    }

    public void setVencido(int vencido) {
        this.vencido = vencido;
    }

    public static Creator<clsRecibos> getCREATOR() {
        return CREATOR;
    }

    public static ArrayList<clsRecibos> CargarListaRecibosPorCuenta(int intIdCuenta){
        ArrayList<clsRecibos> listRecibos = new ArrayList<clsRecibos>();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {//6
            Cursor cursor = db.rawQuery("SELECT * FROM Opr_Recibos WHERE id_cuenta= "+intIdCuenta+";", null);
            if (cursor.moveToFirst()) {
                // iteramos sobre el cursor de resultados,
                // y vamos rellenando el array que posteriormente devolveremos
                while (cursor.isAfterLast() == false) {

                    listRecibos.add(new clsRecibos(cursor.getInt(cursor.getColumnIndex("id"))
                            ,cursor.getString(cursor.getColumnIndex("id_recibo"))
                            ,cursor.getInt(cursor.getColumnIndex("id_padron"))
                            ,cursor.getInt(cursor.getColumnIndex("id_cuenta"))
                            ,cursor.getString(cursor.getColumnIndex("razon_social"))
                            ,cursor.getString(cursor.getColumnIndex("direccion"))
                            ,cursor.getString(cursor.getColumnIndex("colonia"))
                            ,cursor.getString(cursor.getColumnIndex("poblacion"))
                            ,cursor.getString(cursor.getColumnIndex("localizacion"))
                            ,cursor.getString(cursor.getColumnIndex("tipocalculo"))
                            ,cursor.getString(cursor.getColumnIndex("servicio"))
                            ,cursor.getString(cursor.getColumnIndex("tarifa"))
                            ,cursor.getString(cursor.getColumnIndex("clsusuario"))
                            ,cursor.getString(cursor.getColumnIndex("anomalia"))
                            ,cursor.getString(cursor.getColumnIndex("id_medidor"))
                            ,cursor.getString(cursor.getColumnIndex("estatus"))
                            ,cursor.getInt(cursor.getColumnIndex("meses_rezago"))
                            ,cursor.getInt(cursor.getColumnIndex("promedio_act"))
                            ,cursor.getInt(cursor.getColumnIndex("consumo_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_factura_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_vencimiento"))
                            ,cursor.getString(cursor.getColumnIndex("ciclo_facturado"))
                            ,cursor.getString(cursor.getColumnIndex("periodo"))
                            ,cursor.getFloat(cursor.getColumnIndex("total"))
                            ,cursor.getFloat(cursor.getColumnIndex("pago_requerido"))
                            ,cursor.getInt(cursor.getColumnIndex("vencido"))));
                    cursor.moveToNext();
                }
            }


            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listRecibos;
    }

    public static ArrayList<clsRecibos> CargarListaRecibosTodos(){
        ArrayList<clsRecibos> listRecibos = new ArrayList<clsRecibos>();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {//6
            Cursor cursor = db.rawQuery("SELECT * FROM Opr_Recibos ;", null);
            if (cursor.moveToFirst()) {
                // iteramos sobre el cursor de resultados,
                // y vamos rellenando el array que posteriormente devolveremos
                while (cursor.isAfterLast() == false) {

                    listRecibos.add(new clsRecibos(cursor.getInt(cursor.getColumnIndex("id"))
                            ,cursor.getString(cursor.getColumnIndex("id_recibo"))
                            ,cursor.getInt(cursor.getColumnIndex("id_padron"))
                            ,cursor.getInt(cursor.getColumnIndex("id_cuenta"))
                            ,cursor.getString(cursor.getColumnIndex("razon_social"))
                            ,cursor.getString(cursor.getColumnIndex("direccion"))
                            ,cursor.getString(cursor.getColumnIndex("colonia"))
                            ,cursor.getString(cursor.getColumnIndex("poblacion"))
                            ,cursor.getString(cursor.getColumnIndex("localizacion"))
                            ,cursor.getString(cursor.getColumnIndex("tipocalculo"))
                            ,cursor.getString(cursor.getColumnIndex("servicio"))
                            ,cursor.getString(cursor.getColumnIndex("tarifa"))
                            ,cursor.getString(cursor.getColumnIndex("clsusuario"))
                            ,cursor.getString(cursor.getColumnIndex("anomalia"))
                            ,cursor.getString(cursor.getColumnIndex("id_medidor"))
                            ,cursor.getString(cursor.getColumnIndex("estatus"))
                            ,cursor.getInt(cursor.getColumnIndex("meses_rezago"))
                            ,cursor.getInt(cursor.getColumnIndex("promedio_act"))
                            ,cursor.getInt(cursor.getColumnIndex("consumo_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_factura_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_vencimiento"))
                            ,cursor.getString(cursor.getColumnIndex("ciclo_facturado"))
                            ,cursor.getString(cursor.getColumnIndex("periodo"))
                            ,cursor.getFloat(cursor.getColumnIndex("total"))
                            ,cursor.getFloat(cursor.getColumnIndex("pago_requerido"))
                            ,cursor.getInt(cursor.getColumnIndex("vencido"))));
                    cursor.moveToNext();
                }
            }


            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listRecibos;
    }

    public static List<clsRecibos> CargarRecibosPorCuenta(int intIdCuenta){
        ArrayList<clsRecibos> listRecibos = new ArrayList<clsRecibos>();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        if (db != null) {//6
            Cursor cursor = db.rawQuery("SELECT * FROM Opr_Recibos WHERE id_cuenta= "+intIdCuenta+";", null);
            if (cursor.moveToFirst()) {
                // iteramos sobre el cursor de resultados,
                // y vamos rellenando el array que posteriormente devolveremos
                while (cursor.isAfterLast() == false) {

                   /* id = cursor.getInt(cursor.getColumnIndex("id"));
                    nombre = cursor.getString(cursor.getColumnIndex("razon_social"));
                    cuenta = cursor.getInt(cursor.getColumnIndex("id_cuenta"));
                    importe = cursor.getFloat(cursor.getColumnIndex("total"));
                    vencimiento = cursor.getString(cursor.getColumnIndex("fecha_vencimiento"));
                    fecha_fac = cursor.getString(cursor.getColumnIndex("fecha_factura_act"));*/

                    listRecibos.add(new clsRecibos(cursor.getInt(cursor.getColumnIndex("id"))
                            ,cursor.getString(cursor.getColumnIndex("id_recibo"))
                            ,cursor.getInt(cursor.getColumnIndex("id_padron"))
                            ,cursor.getInt(cursor.getColumnIndex("id_cuenta"))
                            ,cursor.getString(cursor.getColumnIndex("razon_social"))
                            ,cursor.getString(cursor.getColumnIndex("direccion"))
                            ,cursor.getString(cursor.getColumnIndex("colonia"))
                            ,cursor.getString(cursor.getColumnIndex("poblacion"))
                            ,cursor.getString(cursor.getColumnIndex("localizacion"))
                            ,cursor.getString(cursor.getColumnIndex("tipocalculo"))
                            ,cursor.getString(cursor.getColumnIndex("servicio"))
                            ,cursor.getString(cursor.getColumnIndex("tarifa"))
                            ,cursor.getString(cursor.getColumnIndex("clsusuario"))
                            ,cursor.getString(cursor.getColumnIndex("anomalia"))
                            ,cursor.getString(cursor.getColumnIndex("id_medidor"))
                            ,cursor.getString(cursor.getColumnIndex("estatus"))
                            ,cursor.getInt(cursor.getColumnIndex("meses_rezago"))
                            ,cursor.getInt(cursor.getColumnIndex("promedio_act"))
                            ,cursor.getInt(cursor.getColumnIndex("consumo_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_factura_act"))
                            ,cursor.getString(cursor.getColumnIndex("fecha_vencimiento"))
                            ,cursor.getString(cursor.getColumnIndex("ciclo_facturado"))
                            ,cursor.getString(cursor.getColumnIndex("periodo"))
                            ,cursor.getFloat(cursor.getColumnIndex("total"))
                            ,cursor.getFloat(cursor.getColumnIndex("pago_requerido"))
                            ,cursor.getInt(cursor.getColumnIndex("vencido"))));

      /*              clsRecibos recibo = new clsRecibos(id,"",0,cuenta,nombre,""
                            ,"","","","","","",""
                            ,"","","","",0,2
                            ,2,2,2,2,"",""
                            ,fecha_fac,vencimiento,"","",importe,importe);
                    listRecibos.add(recibo);*/

              /*     listRecibos.add(new clsRecibos(id,"",0,cuenta,nombre,""
                            ,"","","","","","",""
                            ,"","","","",0,2
                            ,2,2,2,2,"",""
                            ,fecha_fac,vencimiento,"","",importe,importe));*/

                    cursor.moveToNext();
                }
            }

            if(!cursor.isClosed())cursor.close();

            //Cerramos la base de datos
            db.close();
        }//6
        return listRecibos;

    }

    public static boolean insertarRecibos(clsRecibos o_recibo){
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("id_recibo", o_recibo.getId_recibo());
            nuevoRegistro.put("id_padron", o_recibo.getId_padron()+"");
            nuevoRegistro.put("id_cuenta", o_recibo.getId_cuenta()+"");
            nuevoRegistro.put("razon_social", o_recibo.getRazon_social());
            nuevoRegistro.put("direccion", o_recibo.getDireccion());
            nuevoRegistro.put("colonia", o_recibo.getColonia());
            nuevoRegistro.put("poblacion", o_recibo.getPoblacion());
            nuevoRegistro.put("localizacion", o_recibo.getLocalizacion());
            nuevoRegistro.put("tipocalculo", o_recibo.getTipocalculo());
            nuevoRegistro.put("servicio", o_recibo.getServicio());
            nuevoRegistro.put("tarifa", o_recibo.getTarifa());
            nuevoRegistro.put("clsusuario", o_recibo.getClsusuario());
            nuevoRegistro.put("anomalia", o_recibo.getAnomalia());
            nuevoRegistro.put("id_medidor", o_recibo.getId_medidor());
            nuevoRegistro.put("estatus", o_recibo.getEstatus());
            nuevoRegistro.put("meses_rezago", o_recibo.getMeses_rezago()+"");
            nuevoRegistro.put("promedio_act",o_recibo.getPromedio_act()+"");
            nuevoRegistro.put("consumo_act",o_recibo.getConsumo_act()+"");
            nuevoRegistro.put("fecha_factura_act", o_recibo.getFecha_factura_act());
            nuevoRegistro.put("fecha_vencimiento", o_recibo.getFecha_vencimiento());
            nuevoRegistro.put("ciclo_facturado", o_recibo.getCiclo_facturado());
            nuevoRegistro.put("periodo", o_recibo.getPeriodo());
            nuevoRegistro.put("total", o_recibo.getTotal()+"");
            nuevoRegistro.put("pago_requerido", o_recibo.getPago_requerido()+"");
            nuevoRegistro.put("vencido", o_recibo.getVencido()+"");

            //Insertamos el registro en la base de datos
            db.insert("Opr_Recibos", null, nuevoRegistro);

            //db.execSQL(getInsertarNormalQuery(recibo));
            db.close();
        }
        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(id_recibo);
        parcel.writeInt(id_padron);
        parcel.writeInt(id_cuenta);
        parcel.writeString(razon_social);
        parcel.writeString(direccion);
        parcel.writeString(colonia);
        parcel.writeString(poblacion);
        parcel.writeString(localizacion);
        parcel.writeString(tipocalculo);
        parcel.writeString(servicio);
        parcel.writeString(tarifa);
        parcel.writeString(clsusuario);
        parcel.writeString(anomalia);
        parcel.writeString(id_medidor);
        parcel.writeString(estatus);
        parcel.writeInt(meses_rezago);
        parcel.writeInt(promedio_act);
        parcel.writeInt(consumo_act);
        parcel.writeString(fecha_factura_act);
        parcel.writeString(fecha_vencimiento);
        parcel.writeString(ciclo_facturado);
        parcel.writeString(periodo);
        parcel.writeFloat(total);
        parcel.writeFloat(pago_requerido);
        parcel.writeFloat(vencido);
    }
}

