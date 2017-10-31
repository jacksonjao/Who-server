package redes_taller3_jhonOsorio_13212008;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import envio.NumeroDeUsuarios;
import envio.UserPerdedor;
import envio.UserRanking;
import envio.Usuario;
import objetoXml.ObjetoXml;

public class Comunicacion extends Thread implements Observer {
	private ServerSocket serverSocket;
	 Cronometro cronometro;
	 int totalUsuarios;
	private static Comunicacion instanciaComunicacion;
	private ArrayList<ControlCliente> clientes;
	int termino;
	private String usuario, contrasena;
	private ObjetoXml objetoXml;
	ArrayList<UserRanking>userRankings;
	int usuarioListo;
	private int hijos;
	public Comunicacion() {
		// TODO Auto-generated constructor stub
     userRankings=new ArrayList<UserRanking>();
		clientes= new ArrayList<ControlCliente>();
		objetoXml = ObjetoXml.getInstance();
		objetoXml.escribirXml();
		new ArrayList<String>();
	try {
		serverSocket= new ServerSocket(8080);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}


	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(true){
			try {
				System.out.println("esperando");
				clientes.add(new ControlCliente(serverSocket.accept(),this));
				System.out.println("aceptado");
			} catch (Exception e) {
				System.out.println("aun no");
				// TODO: handle exception
			}
		}
		
	}



	public static Comunicacion getInstanciaComunicacion() {
		if(instanciaComunicacion==null){
			instanciaComunicacion= new Comunicacion();
		}
		return instanciaComunicacion;
	}



	@Override
	public void update(Observable o, Object arg) {
ControlCliente cliente= (ControlCliente)o;

if(arg instanceof NumeroDeUsuarios){
	totalUsuarios= ((NumeroDeUsuarios)arg).getCantidadUsuarios();
	
}


if(arg instanceof UserPerdedor){
UserPerdedor userPerdedor= (UserPerdedor) arg;
	for (int i = 0; i < clientes.size(); i++) {
		try {
			clientes.get(i).enviar(userPerdedor);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


if(arg instanceof Usuario){
	registrosActividades(cliente,arg);
	Usuario user=(Usuario) arg;
	System.out.println(user.getName());
	
}

if(arg instanceof UserRanking){
	userRankings.clear();

	objetoXml.ingresarPost(((UserRanking)arg).getUsuario(), ((UserRanking)arg).getTiempo());

		objetoXml.escribirXml();

		
		hijos = objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(1).getChildNodes().getLength();

		
		for (int i = 0; i < hijos; i++) {
			String[] partes= objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(1)
					.getChildNodes().item(i).getChildNodes().item(0).getTextContent().split(":");
			
		userRankings.add(new UserRanking(
				objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(1)
				.getChildNodes().item(i).getNodeName(), objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(1)
				.getChildNodes().item(i).getChildNodes().item(0).getTextContent(),partes[0] + partes[1] + partes[2]));
		
		}
	
		
		for (int i = 0; i < clientes.size(); i++) {
			try {
				clientes.get(i).enviar(userRankings);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
						
}



		if(arg instanceof String){
		String mensaje=(String) arg;
		System.out.println(mensaje);
		if(mensaje.contains("desconexion")){ 
			clientes.remove(cliente);
			System.out.println(mensaje);
		}
		
		if(arg.equals("listo")){
			try {
				usuarioListo+=1;
				
				cliente.enviar(""+usuarioListo);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		if(usuarioListo==totalUsuarios){
			   cronometro = new Cronometro();
		        cronometro.start();
			for (int i = 0; i < clientes.size(); i++) {
				try {
					clientes.get(i).enviar(""+usuarioListo);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			
			usuarioListo=0;
			}
		}
		
		if(arg.equals("termino")){
			termino+=1;
			try {
				cliente.enviar(cronometro.getMinutosCero() + ":" + cronometro.getSegundosCero() + ":" + cronometro.getMilisegundosCero());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if(termino<totalUsuarios){
			try {
				cliente.enviar("ganaste");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(termino==totalUsuarios){
			
			try {
				cliente.enviar("perdiste");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for (int i = 0; i < clientes.size(); i++) {
				try {
					clientes.get(i).enviar("terminaronTodos");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			
			
			
			termino=0;
		}
		
		
		
			
		}
	}



	public int getClientes() {
		return clientes.size();
	}


	public void registrosActividades(Object cliente,Object arg){
		ControlCliente conexion=(ControlCliente) cliente;
		hijos = objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(0).getChildNodes().getLength();

		if(arg instanceof Usuario){
		
			if (((Usuario) arg).getTipo().equals("registro")) {
				boolean agregarAregistro = true;
				usuario = ((Usuario) arg).getName();
				contrasena = ((Usuario) arg).getPassword();
				if (hijos == 0) {
					objetoXml.ingresarDatos(usuario, contrasena);
					objetoXml.escribirXml();
				}

				for (int i = 0; i < hijos; i++) {
					if ((usuario.equals(objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(0)
							.getChildNodes().item(i).getNodeName()))) {
						agregarAregistro = false;
						break;
					} else {
						agregarAregistro = true;
					}
				}

				if (!agregarAregistro) {
					System.out.println("existe");
					try {
						conexion.enviar("denegado");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("no existe y se agrego");
					objetoXml.ingresarDatos(usuario, contrasena);
					objetoXml.escribirXml();
					try {
						conexion.enviar("aprovado");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			if (((Usuario) arg).getTipo().equals("inicio")) {
				boolean agregarAregistro = true;
				usuario = ((Usuario) arg).getName();
				contrasena = ((Usuario) arg).getPassword();

				for (int i = 0; i < hijos; i++) {
					if ((usuario.equals(objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(0)
							.getChildNodes().item(i).getNodeName()))
							&& contrasena.equals(objetoXml.getDoc().getChildNodes().item(0).getChildNodes().item(0)
									.getChildNodes().item(i).getChildNodes().item(0).getTextContent())) {
						agregarAregistro = false;
						break;
					} else {
						agregarAregistro = true;
					}
				}

				if (!agregarAregistro) {
					System.out.println("existe inicio");
					try {
						conexion.enviar(usuario);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("no existe inicio");
					try {
						conexion.enviar("denegado");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		
	}
	

	
	

	
	
}
