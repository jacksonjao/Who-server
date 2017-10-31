package envio;

import java.io.Serializable;

public class UserRanking implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String usuario, tiempo, tiempoTotal;


public UserRanking(String usuario, String tiempo, String tiempoTotal) {
	
	this.usuario = usuario;
	this.tiempo = tiempo;
	this.tiempoTotal = tiempoTotal;
}

public String getUsuario() {
	return usuario;
}

public String getTiempo() {
	return tiempo;
}

public String getTiempoTotal() {
	return tiempoTotal;
}




}
