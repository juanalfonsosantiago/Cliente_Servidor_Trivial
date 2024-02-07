package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.PrinterLocation;

import servidor.dao.JugadorDAO;
import servidor.dao.PartidaDAO;
import servidor.dao.PreguntaDAO;
import servidor.dao.RespuestaDAO;
import servidor.partidas.Jugador;
import servidor.partidas.Partida;
import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;

public class PartidaCliente extends Thread{

	
	private Socket socket ;
	private Partida partida;
	private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
	static private Semaphore semaforo=new Semaphore(20);
	
	
	
	
	public PartidaCliente(Socket socket) {
		super();
		this.socket = socket;
		
	}

	
	/**
	 * método run para crear hilos donde utilizamos un semáforo de 
	 * como mucho 20 clientes que se pueden conectar a la vez al servidor
	 * pregunta al usuario su nombre y contraseña, y recibe los dstos del cliente
	 * muestra las preguntas y respuestas y si es correcta o no
	 * suma el número de aciertos y de fallos
	 * crea una partida y la registra en la base de datos
	 */
	public void run() {
		
		
		
		try {
			semaforo.acquire();
			int aciertos=0;
			preguntas = PreguntaDAO.sacarPreguntasAleatorias();
			InputStream ips = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			
			OutputStream ops = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(ops,true);
			
			pw.println("Hola, comienza el juego");
			pw.println("Introduce tu usuario");
			
			
			
			String usuario = br.readLine();
			pw.println("Introduce tu contraseña");
			String password = br.readLine();
			
			
			//Aquí comparamos usuario y contraseña con la base de datos
			 Jugador concursante=JugadorDAO.buscarUsuario(usuario, password);
			 if(concursante==null) {
				 
				 pw.println("Usuario no encontrado");
				 
				 
					semaforo.release();
					
					socket.close();
			 }else {pw.println("Usuario encontrado");}
			
			for(int i=0; i<5; i++) {
				
				//aquí imprime la pregunta
				pw.println(preguntas.get(i).getPregunta());
				ArrayList<Respuesta> respuestasMostradas= new ArrayList<Respuesta>();
				respuestasMostradas= RespuestaDAO.sacarRespuestas(preguntas.get(i));
				for(int j=0; j<4; j++) {
					pw.println(j+1+ " "+respuestasMostradas.get(j).getRespuesta());
				}
				

				//recibo la respuesta del cliente
				//ASUMIMOS DEL 1 AL 4
				int respuestaCliente = Integer.valueOf(br.readLine())-1;
				if(respuestasMostradas.get(respuestaCliente).isCorrecta()) {
					aciertos++;
					PreguntaDAO.actualizarAciertosFallos(preguntas.get(i), true);
					pw.println("Es correcta ,su puntuación actual es: " + aciertos);
				}else {
					Respuesta respuestaCorregida = new Respuesta();
					PreguntaDAO.actualizarAciertosFallos(preguntas.get(i), false);
					for (Respuesta respuesta : respuestasMostradas) {
						if(respuesta.isCorrecta()) {
							respuestaCorregida=respuesta;
						}
					}
					pw.println("Es incorrecta, la respuesta correcta era: "+ respuestaCorregida.getRespuesta()+" ,su puntuación actual es:" + aciertos );
				
				}
				
				
			}
			
			
			Partida partida = new Partida(concursante,aciertos,LocalDateTime.now());
			PartidaDAO.meterPartida(partida);
			JugadorDAO.actualizaMaximaPuntuacion(concursante, aciertos);
			JugadorDAO.actualizarNumeroPartidas(concursante);
			pw.println("Su puntuación total es: " + aciertos + " y su número de partidas ha sido: "+ concursante.getNumPartidas());
			semaforo.release();
			
			socket.close();
		} catch (IOException e) {
			
			System.err.println("Error de entrada / salida" + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("Se lanza cuando un hilo está esperando, inactivo o ocupado de otro modo y el hilo se interrumpe, ya sea antes o durante la actividad."+ e.getMessage());
		}
		
		
		
	}
	
	
	
	

			//CONTROLAR ERRORES DEL CLIENTE INTRODUCIENDO DEL 1 AL 4
		
	
	



	public Socket getSocket() {
		return socket;
		
	}




	public void setSocket(Socket socket) {
		this.socket = socket;
	}







	public Partida getPartida() {
		return partida;
	}




	public void setPartida(Partida partida) {
		this.partida = partida;
	}




	public ArrayList<Pregunta> getPreguntas() {
		return preguntas;
	}




	public void setPreguntas(ArrayList<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}







	
	
	
	
	
	
}
