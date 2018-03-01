package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsHisRecibos {
    private int id =0;
    private String id_recibo="";
    private String ciclo_facturado="";
    private String periodo ="";
    private int consumo=0;
    private float total=0;

    public clsHisRecibos() {

    }

    public clsHisRecibos(int id, String id_recibo, String ciclo_facturado, String periodo, int consumo, float total) {
        this.id = id;
        this.id_recibo = id_recibo;
        this.ciclo_facturado = ciclo_facturado;
        this.periodo = periodo;
        this.consumo = consumo;
        this.total = total;
    }

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

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

