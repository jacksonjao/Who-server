package redes_taller3_jhonOsorio_13212008;

public class Main extends Thread {

	static Main main;
	Comunicacion comunicacion;
	Thread hilo;

	public Main() {
		// TODO Auto-generated constructor stub

		comunicacion = Comunicacion.getInstanciaComunicacion();

		comunicacion.start();
		hilo = new Thread(this);
		hilo.start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main = new Main();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				sleep(1000);
				System.out.println(comunicacion.getClientes());

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
