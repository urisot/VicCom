package mx.com.viccom.viccom.Clases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import mx.com.viccom.viccom.DB.BDManager;
import mx.com.viccom.viccom.Utilities.App;


/**
 * Created by Admin on 28/02/2018.
 */

public class clsUsuarioApp implements Parcelable {
    private String id_usuarioapp="";
    private String nombre="";
    private String mail="";
    private String celular="";
    private String contrasena="";
    private String mac_address="";
    private String fecha_insert="";
    private String key="";

    public clsUsuarioApp() {

    }



    public clsUsuarioApp(String id_usuarioapp, String nombre, String mail, String celular, String contrasena, String mac_address, String fecha_insert,String key) {
        this.id_usuarioapp = id_usuarioapp;
        this.nombre = nombre;
        this.mail = mail;
        this.celular = celular;
        this.contrasena = contrasena;
        this.mac_address = mac_address;
        this.fecha_insert = fecha_insert;
        this.key=key;
    }

    protected clsUsuarioApp(Parcel in) {
        id_usuarioapp = in.readString();
        nombre = in.readString();
        mail = in.readString();
        celular = in.readString();
        contrasena = in.readString();
        mac_address = in.readString();
        fecha_insert = in.readString();
        key = in.readString();
    }


    public static final Creator<clsUsuarioApp> CREATOR = new Creator<clsUsuarioApp>() {
        @Override
        public clsUsuarioApp createFromParcel(Parcel in) {
            return new clsUsuarioApp(in);
        }

        @Override
        public clsUsuarioApp[] newArray(int size) {
            return new clsUsuarioApp[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getId_usuarioapp() {
        return id_usuarioapp;
    }

    public void setId_usuarioapp(String id_usuarioapp) {
        this.id_usuarioapp = id_usuarioapp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getFecha_insert() {
        return fecha_insert;
    }

    public void setFecha_insert(String fecha_insert) {
        this.fecha_insert = fecha_insert;
    }

    public static boolean upgradeUsuarioApp (clsUsuarioApp o_usuario){
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

    /*        String StrDelete = "DELETE FROM Sys_Usuarios WHERE mail != '"+o_usuario.getMail()+"';";
            db.execSQL(StrDelete);*/

            String strWhereDel = "mail != '"+o_usuario.getMail()+"'";
            db.delete("Sys_Usuarios",strWhereDel,null);



            Cursor c = db.rawQuery("SELECT * FROM Sys_Usuarios WHERE mail = '" +o_usuario.getMail()+"';", null);
            //si no existe un usuario con este correo lo registra
            if(!c.moveToFirst()){
                ContentValues nuevoRegistro = new ContentValues();

                nuevoRegistro.put("id_usuarioapp", o_usuario.getId_usuarioapp());
                nuevoRegistro.put("nombre", o_usuario.getNombre());
                nuevoRegistro.put("mail", o_usuario.getMail());
                nuevoRegistro.put("celular", o_usuario.getCelular());
                nuevoRegistro.put("contrasena", o_usuario.getContrasena());
                nuevoRegistro.put("mac_address", o_usuario.getMac_address());
                nuevoRegistro.put("fecha_insert", o_usuario.getFecha_insert());
                nuevoRegistro.put("key", o_usuario.getKey());
                //Insertamos el registro en la base de datos
                db.insert("Sys_Usuarios", null, nuevoRegistro);
            }else{
                ContentValues actualizaRegistro = new ContentValues();

                actualizaRegistro.put("id_usuarioapp", o_usuario.getId_usuarioapp());
                actualizaRegistro.put("nombre", o_usuario.getNombre());
                actualizaRegistro.put("mail", o_usuario.getMail());
                actualizaRegistro.put("celular", o_usuario.getCelular());
                actualizaRegistro.put("contrasena", o_usuario.getContrasena());
                actualizaRegistro.put("mac_address", o_usuario.getMac_address());
                actualizaRegistro.put("fecha_insert", o_usuario.getFecha_insert());
                actualizaRegistro.put("key", o_usuario.getKey());

                String strWhere = "mail='" +o_usuario.getMail()+"'";

                db.update("Sys_Usuarios", actualizaRegistro, strWhere, null);
            }


            db.close();
        }

        return true;

    }
    public static clsUsuarioApp getUsrAppByMail (String strMail){
        clsUsuarioApp usuarioAppReturn = new clsUsuarioApp();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery("SELECT * FROM Sys_Usuarios WHERE mail='" +strMail+"';", null);
            //si no existe un usuario con este correo lo registra
            if(c.moveToFirst()){
                usuarioAppReturn.setId_usuarioapp(c.getString(c.getColumnIndex("id_usuarioapp")));
                usuarioAppReturn.setNombre(c.getString(c.getColumnIndex("nombre")));
                usuarioAppReturn.setMail(c.getString(c.getColumnIndex("mail")));
                usuarioAppReturn.setCelular(c.getString(c.getColumnIndex("celular")));
                usuarioAppReturn.setContrasena(c.getString(c.getColumnIndex("contrasena")));
                usuarioAppReturn.setMac_address(c.getString(c.getColumnIndex("mac_address")));
                usuarioAppReturn.setFecha_insert(c.getString(c.getColumnIndex("fecha_insert")));
                usuarioAppReturn.setKey(c.getString(c.getColumnIndex("key")));
            }
            db.close();
            if (!c.isClosed()) c.close();
        }

        return usuarioAppReturn;

    }

    public static clsUsuarioApp getUsrApp (){
        clsUsuarioApp usuarioAppReturn = new clsUsuarioApp();
        BDManager manejador = new BDManager(App.getContext(), "DBPagos", null, 1);
        SQLiteDatabase db = manejador.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery("SELECT * FROM Sys_Usuarios;", null);
            //si no existe un usuario con este correo lo registra
            if(c.moveToFirst()){
                usuarioAppReturn.setId_usuarioapp(c.getString(c.getColumnIndex("id_usuarioapp")));
                usuarioAppReturn.setNombre(c.getString(c.getColumnIndex("nombre")));
                usuarioAppReturn.setMail(c.getString(c.getColumnIndex("mail")));
                usuarioAppReturn.setCelular(c.getString(c.getColumnIndex("celular")));
                usuarioAppReturn.setContrasena(c.getString(c.getColumnIndex("contrasena")));
                usuarioAppReturn.setMac_address(c.getString(c.getColumnIndex("mac_address")));
                usuarioAppReturn.setFecha_insert(c.getString(c.getColumnIndex("fecha_insert")));
                usuarioAppReturn.setKey(c.getString(c.getColumnIndex("key")));
            }
            db.close();
            if (!c.isClosed()) c.close();
        }

        return usuarioAppReturn;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_usuarioapp);
        parcel.writeString(nombre);
        parcel.writeString(mail);
        parcel.writeString(celular);
        parcel.writeString(contrasena);
        parcel.writeString(mac_address);
        parcel.writeString(fecha_insert);
        parcel.writeString(key);
    }
}

