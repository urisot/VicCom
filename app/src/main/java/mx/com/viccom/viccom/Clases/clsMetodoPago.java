package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsMetodoPago {
    private int id=0;
    private String metodo="";
    private String descripcion="";
    private String imagen_url="";

    public clsMetodoPago() {

    }

    public clsMetodoPago(int id, String metodo, String descripcion, String imagen_url) {
        this.id = id;
        this.metodo = metodo;
        this.descripcion = descripcion;
        this.imagen_url = imagen_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }
}
