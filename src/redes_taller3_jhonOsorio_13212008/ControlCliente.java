package redes_taller3_jhonOsorio_13212008;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ControlCliente extends Observable implements Runnable {
	private Socket socket;
	private Thread hilo;
	private boolean conectado;
	private Observer jefe;

	public ControlCliente(Socket socket, Observer jefe) {
		this.socket = socket;
		this.jefe = jefe;
		hilo = new Thread(this);
		hilo.start();
		conectado = true;
	}

	@Override
	public void run() {
		while (conectado) {

			try {
		
				jefe.update(this, recibir());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				conectado = false;
				jefe.update(this, "desconexion");

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void enviar(Object obj) throws IOException {
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(obj);
		oos.flush();
	}

	public Object recibir() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		Object object = ois.readObject();
		
		return object;

	}

}
