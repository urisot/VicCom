package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsParameter {
    public String parameter_name = "";
    public String parameter_value = "";

    public clsParameter(){
    }

    public clsParameter(String key, String value){
        parameter_name = key;
        parameter_value = value;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }


}
