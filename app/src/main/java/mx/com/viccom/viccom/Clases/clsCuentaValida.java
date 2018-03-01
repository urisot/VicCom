package mx.com.viccom.viccom.Clases;

/**
 * Created by Admin on 28/02/2018.
 */

public class clsCuentaValida {

    private boolean UsrValido = false;


    public clsCuentaValida() {
    }

    public clsCuentaValida(boolean usrValido) {
        UsrValido = usrValido;
    }

    public boolean isUsrValido() {
        return UsrValido;
    }

    public void setUsrValido(boolean usrValido) {
        UsrValido = usrValido;
    }
}

