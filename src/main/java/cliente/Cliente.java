package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import servidor.utilidades.Utilidades;

public class Cliente {
	
	static final int puerto= 55515;
	static final String ip = "localhost";
	
	
	public static void main(String[] args) 
	{
		
	
		
		try {
			Socket socket = new Socket(ip, puerto);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			
			
			//Recibe el usuario y contraseña
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			
			
			
			//Mete el usuario y la contraseña con el scanner
			pw.println(Utilidades.soloString());
			System.out.println(br.readLine());
			pw.println(Utilidades.soloString());
			
			
			String resultadoLogin=br.readLine();
			
			if(resultadoLogin.equals("Usuario no encontrado")) {
				
				
				System.out.println(resultadoLogin);
				socket.close();
			}
			
		
			
			for(int i=0; i<5; i++) {
				
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				System.out.println(br.readLine());
				
				
				//La respuesta que envia
				pw.println(elegirRespuesta());
				
				
				System.out.println(br.readLine());
			}
			
		
			System.out.println(br.readLine());
			
				
	
			
			socket.close();
			
		} catch (UnknownHostException e) {
			System.err.println("Lanzado para indicar que no se pudo determinar la dirección IP de un host" + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error de entrada / salida"+ e.getMessage());
		
		

		}
		
	}
	
	
	/**
	 * Método para poner a prueba de errores la elección del cliente en su respuesta del 1 al 4
	 * @return devuelve la respuesta del cliente
	 */
	public static int elegirRespuesta() {

		int respuesta = 0;

		while (true) {

			respuesta = Utilidades.numeroPruebaErrores();

			if (respuesta == 1 || respuesta == 2 || respuesta == 3 || respuesta == 4) {

				return respuesta;
			}else {
				System.out.println("Introduce un número del 1 al 4");
				}

		}

	}
	
	
	
}
