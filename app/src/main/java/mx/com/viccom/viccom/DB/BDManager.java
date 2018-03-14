package mx.com.viccom.viccom.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 28/02/2018.
 */

public class BDManager  extends SQLiteOpenHelper {

    public BDManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BDManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    public static String sqlCreaRecibos = "CREATE TABLE IF NOT EXISTS Opr_Recibos ("+
            "id integer primary key,"+// primary key
            "id_recibo text null,"+
            "id_padron integer null,"+
            "id_cuenta integer null,"+
            "razon_social text null," +
            "direccion text null," +
            "colonia text null,"+
            "poblacion text null,"+
            "localizacion text null," +
            "tipocalculo text null," +
            "servicio text null,"+
            "tarifa text null,"+
            "clsusuario text null,"+
            "anomalia text null," +
            "id_medidor text null," +
            "estatus text null,"+
            "meses_rezago int null,"+
            "promedio_act int null,"+
            "consumo_act int null,"+
            "fecha_factura_act text null,"+
            "fecha_vencimiento text null,"+
            "ciclo_facturado text null,"+
            "periodo text null," +
            "total REAL NULL);";

    public static String sqlCreaDetRecibos = "CREATE TABLE IF NOT EXISTS Opr_DetRecibos ("+
            "id integer primary key,"+// primary key
            "id_factura text null,"+
            "es_rezago integer null,"+
            "id_concepto integer null,"+
            "concepto text null,"+
            "subtotal REAL NULL,"+
            "iva REAL NULL,"+
            "total REAL NULL);";
    String sqlCreaCfgConfig          = "CREATE TABLE Cfg_Parametros (id integer primary key,parametro text not null,valor text not null);";

    String sqlCreaSysUsuarios        = "CREATE TABLE Sys_Usuarios (" +
            "id integer primary key," +
            "id_usuarioapp text not null" +
            ",nombre text not null" +
            ",mail text null" +
            ",celular text null" +
            ",contrasena text null" +
            ",mac_address text null" +
            ",key text null" +
            ",fecha_insert text null);";

    String sqlCreaOprTarjetas        = "CREATE TABLE Opr_Tarjetas (" +
            "id integer primary key" +
            ",alias text not null" +
            ",nombre text not null" +
            ",numero text null" +
            ",mes text null" +
            ",ano text null" +
            ",ccv text null" +
            ",tipo text null" +
            ",predeterminado int null);";

    String sqlCreaCat_TiposTarjeta        = "CREATE TABLE Cat_TiposTarjetas (" +
            "id integer primary key," +
            "descripcion text not null" +
            ",url text not null);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(sqlCreaPadron);
        sqLiteDatabase.execSQL(sqlCreaCfgConfig);
        sqLiteDatabase.execSQL(sqlCreaSysUsuarios);
        sqLiteDatabase.execSQL(sqlCreaRecibos);
        sqLiteDatabase.execSQL(sqlCreaDetRecibos);
        sqLiteDatabase.execSQL(sqlCreaOprTarjetas);
        sqLiteDatabase.execSQL(sqlCreaCat_TiposTarjeta);
        sqLiteDatabase.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('DIRECION_WCF','http://201.144.165.83/apicomapa/ComapaVic_OS.svc/');");

        sqLiteDatabase.execSQL("INSERT INTO Cat_TiposTarjetas(descripcion,url) VALUES ('Visa','https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Old_Visa_Logo.svg/220px-Old_Visa_Logo.svg.png');");
        sqLiteDatabase.execSQL("INSERT INTO Cat_TiposTarjetas(descripcion,url) VALUES ('Mastercard','https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/MasterCard_early_1990s_logo.png/220px-MasterCard_early_1990s_logo.png');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {//Pendiente de Corregir
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS OPR_PADRON");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cfg_Parametros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Sys_Usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Opr_Recibos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Opr_DetRecibos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Opr_Tarjetas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cat_TiposTarjetas");

        // sqLiteDatabase.execSQL(sqlCreaPadron);
        sqLiteDatabase.execSQL(sqlCreaCfgConfig);
        sqLiteDatabase.execSQL(sqlCreaSysUsuarios);
        sqLiteDatabase.execSQL(sqlCreaRecibos);
        sqLiteDatabase.execSQL(sqlCreaDetRecibos);
        sqLiteDatabase.execSQL(sqlCreaOprTarjetas);
        sqLiteDatabase.execSQL(sqlCreaCat_TiposTarjeta);
        sqLiteDatabase.execSQL("INSERT INTO Cfg_Parametros(parametro,valor) VALUES ('DIRECION_WCF','http://201.144.165.83/apicomapa/ComapaVic_OS.svc/');");

        sqLiteDatabase.execSQL("INSERT INTO Cat_TiposTarjetas(descripcion,url) VALUES ('Visa','https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Old_Visa_Logo.svg/220px-Old_Visa_Logo.svg.png');");
        sqLiteDatabase.execSQL("INSERT INTO Cat_TiposTarjetas(descripcion,url) VALUES ('Mastercard','https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/MasterCard_early_1990s_logo.png/220px-MasterCard_early_1990s_logo.png');");

    }
}
