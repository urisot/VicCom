package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsResultadoWCF {
    private int id=0;
    private String comando = "";
    private String operacion = "";
    private int error_number = 0;
    private String error_menssage = "";
    private String folio_registro = "";
    private String fecha = "";

    public clsResultadoWCF() {}

    public clsResultadoWCF(int id, String comando, String operacion, int error_number, String error_menssage, String folio_registro, String fecha) {
        this.id = id;
        this.comando = comando;
        this.operacion = operacion;
        this.error_number = error_number;
        this.error_menssage = error_menssage;
        this.folio_registro = folio_registro;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public int getError_number() {
        return error_number;
    }

    public void setError_number(int error_number) {
        this.error_number = error_number;
    }

    public String getError_menssage() {
        return error_menssage;
    }

    public void setError_menssage(String error_menssage) {
        this.error_menssage = error_menssage;
    }

    public String getFolio_registro() {
        return folio_registro;
    }

    public void setFolio_registro(String folio_registro) {
        this.folio_registro = folio_registro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

