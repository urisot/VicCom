package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsDetRecibos {
    private int id;
    private String id_recibo;
    private int id_concepto;
    private boolean es_rezago;
    private String concepto;
    private float subtotal;
    private float iva;
    private float total;

    public clsDetRecibos() {

    }

    public clsDetRecibos(int id, String id_recibo, int id_concepto, boolean es_rezago, String concepto, float subtotal, float iva, float total) {
        this.id = id;
        this.id_recibo = id_recibo;
        this.id_concepto = id_concepto;
        this.es_rezago = es_rezago;
        this.concepto = concepto;
        this.subtotal = subtotal;
        this.iva = iva;
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

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public boolean isEs_rezago() {
        return es_rezago;
    }

    public void setEs_rezago(boolean es_rezago) {
        this.es_rezago = es_rezago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

