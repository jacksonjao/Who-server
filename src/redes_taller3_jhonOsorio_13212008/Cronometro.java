package redes_taller3_jhonOsorio_13212008;



import java.text.DecimalFormat;

public class Cronometro extends Thread {
	int mili,seg,min;
	DecimalFormat dosUnidades = new DecimalFormat("00");
	DecimalFormat tresUnidades = new DecimalFormat("000");
	String milisegundos;
	String segundos;
	String minutos;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		milisegundos = tresUnidades.format(mili);
		segundos= dosUnidades.format(seg);
		minutos= dosUnidades.format(min);
		super.run();
		while (!this.isInterrupted()){

			try {
				sleep(1);
				mili++;
				milisegundos = tresUnidades.format(mili);
				if(mili==999){
					mili=0;
					seg+=1;
					segundos= dosUnidades.format(seg);
				}

				if(seg==59){
					seg=0;
					min+=01;
					minutos= dosUnidades.format(min);
				}



			} catch (InterruptedException e) {
				// TODO: handle exception
                this.interrupt();
			}

		}

	}
	public int getMilisegundos() {
		return Integer.parseInt(milisegundos);
	}
	public int getSegundos() {
		return Integer.parseInt(segundos);
	}
	public int getMinutos() {
		return Integer.parseInt(minutos) ;
	}


	public String getMilisegundosCero() {
		return milisegundos;
	}
	public String getSegundosCero() {
		return segundos;
	}
	public String getMinutosCero() {
		return minutos ;
	}


	public void setMili(int mili) {
		this.mili = mili;
	}

	public void setSeg(int seg) {
		this.seg = seg;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
}
