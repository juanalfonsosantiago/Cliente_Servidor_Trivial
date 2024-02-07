package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import servidor.preguntas.Pregunta;

public class Servidor extends Thread{

	
	private static final int puerto = 55515;
	private ArrayList<Pregunta>preguntas= new ArrayList<Pregunta>();
	
	
	public Servidor() {
		super();
	}


	public Servidor(ArrayList<Pregunta> preguntas) {
		super();
		this.preguntas = preguntas;
	}

	/**
	 * Método run para usar hilos en el servidor
	 * Contiene un while true, para tener arrancado el servidor,
	 * donde se pueden aceptar clientes e inicie el servidor para él
	 */
	public void run() {
		
		try {
			ServerSocket server = new ServerSocket(puerto);
			
			while (true) {
				Socket cliente = server.accept();
				System.out.println("He recibido la conexión de un cliente");
				System.out.println(cliente.getInetAddress().toString() + ":" + cliente.getPort());
				PartidaCliente partidaCliente=  new PartidaCliente(cliente);
				partidaCliente.start();
			

		
			}
		} catch (IOException e) {
			
			System.err.println("Error de entrada salida" + e.getMessage());
		}
		
		
		
	}
	
	
	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}





	public void setPreguntas(ArrayList<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	
	
	
}
