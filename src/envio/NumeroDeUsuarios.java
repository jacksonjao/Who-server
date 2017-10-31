package envio;

import java.io.Serializable;

/**
 * Created by Jhon on 23/05/16.
 */
public class NumeroDeUsuarios implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int cantidadUsuarios;

    public NumeroDeUsuarios(int cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }

    public int getCantidadUsuarios() {
        return cantidadUsuarios;
    }
}
