package mx.com.viccom.viccom.Clases;

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
    private int cvv=0;
    private int tipo=0;
    private boolean predeterminado=false;

    public clsTarjeta(int id, String alias, String nombre, String numero, int mes, int ano, int cvv, int tipo, boolean predeterminado) {
        this.id = id;
        this.alias = alias;
        this.nombre = nombre;
        this.numero = numero;
        this.mes = mes;
        this.ano = ano;
        this.cvv = cvv;
        this.tipo = tipo;
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

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean isPredeterminado() {
        return predeterminado;
    }

    public void setPredeterminado(boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}

//Enlases de las imagenes tipo de tarjeta
//https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Old_Visa_Logo.svg/220px-Old_Visa_Logo.svg.png
//https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/MasterCard_early_1990s_logo.png/220px-MasterCard_early_1990s_logo.png

