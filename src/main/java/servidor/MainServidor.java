package servidor;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


import servidor.dao.HibernateUtil;
import servidor.dao.JugadorDAO;
import servidor.dao.PartidaDAO;
import servidor.dao.PreguntaDAO;
import servidor.dao.RespuestaDAO;
import servidor.partidas.Jugador;
import servidor.partidas.Partida;
import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;
import servidor.utilidades.GestionaFicheros;
import servidor.utilidades.Utilidades;

public class MainServidor {
	
	private ArrayList<Pregunta>preguntas= new ArrayList<Pregunta>();
	
	// los Scanner estáticos para introducir líneas y números.
	static Scanner scLine = new Scanner(System.in);
	static Scanner scInt = new Scanner(System.in);

	// el número que vamos a utilizar para el menú o switch a prueba de errores.
	static int valorNUmericoScanner = 1;
	// número con el que vamos a comparar el número del menú para prueba de errores.
	static String valorDePrueba;


	public static void main(String[] args) {
		
		HibernateUtil.getSessionFactory().getCurrentSession();

		
		
		
		//comienza el while del menú, si es 0 termina el programa, si es distinto entra en las opciones del menú 
				while (valorNUmericoScanner != 0) {

					// características de lo que pide el menú
					System.out.println("1. Leer preguntas de fichero:\r\n" + "2. Consultar histórico de jugadas\r\n" + "3. Consultar el top10 de jugadas:\r\n"
							+ "4. Resetear resultados:\r\n" + "5. Preguntas más difíciles y más fáciles:\r\n" + "6. Creación de jugadore:\r\n"
							+ "7. Arrancar el servidor:\r\n" + "0. Salir.");

					// bucle del menú a prueba de errores
					while (true) {

						// le damos valor al isNumericInt para que esté a prueba de errores
						valorDePrueba = scLine.nextLine();

						if (Utilidades.isNumericInt(valorDePrueba) == true) {

							valorNUmericoScanner = Integer.valueOf(valorDePrueba);
							// le decimos entre que números queremos que esté el menú y no de error
							if (valorNUmericoScanner >= 0 && valorNUmericoScanner <= 7) {

								break;
							} else {
								// si se pasa de esos valores imprime esto
								System.out.println("número no válido");
							}

						} else {
							// si no cumple con los números y es una letra imprime esto
							System.out.println("caracter no válido");
						}
						// acaba el while true a prueba de errores
					}

					// comienza el menú
					switch (valorNUmericoScanner) {

					case 1:
						importarTexto();

						break;

					case 2:

						consultarPartidas();

						break;

					case 3:

						topDiezPartidas();

						break;

					case 4:
						borrarPartidas();

						break;

					case 5:
						numeroFallos();

						break;

					case 6:

						crearJugadore();
						break;

					case 7:

						iniciarServidor();
						break;


					case 0:
						finalizarPrograma();
						break;

					} // llave final del menú o switch

					// cierre del primer while true o bucle principal
				}
		
		
		
	}
	
	

	
	
	
	//1
	
	/**
	 * Pregunta de que fichero de texto quiere importar las preguntas
	 * El usuario introduce el archivo
	 * Carga las preguntas del fichero
	 * Borra todo lo anterior y recorre las preguntas y respuestas
	 * Introduce las preguntas y respuestas en la base de datos
	 */
	static public void importarTexto() {
		System.out.println("¿De que fichero quiere importar las preguntas?");
		String fichero = Utilidades.soloString();
		File file = new File(fichero);
		ArrayList<Pregunta> preguntas = GestionaFicheros.cargarPreguntas(file);
		RespuestaDAO.borrarRespuestas();
		PreguntaDAO.borrarPregunta();
		for (Pregunta pregunta : preguntas) {
			for (Respuesta respuesta : pregunta.getRespuesta()) {
						
				//debido al cascade no es necesario añadirlo porque ya añade las respuesta a las preguntas
						//RespuestaDAO.meterRespuesta(respuesta);
			}
			PreguntaDAO.meterPregunta(pregunta);
		}
	}
	
	
	
	//2
	/**
	 * Llama al método de listarPartida en la clase PartidaDAO
	 * y muestra el histórico de partidas con la información generada en la tabla Partida
	 * Recorre el ArrayList y lo muestra por consola
	 */
	static public void consultarPartidas() {
		ArrayList<Partida> partidaListada = PartidaDAO.listarPartida();
		System.out.println("El histórico de partidas es: ");
		for (Partida p : partidaListada ) {

				
				System.out.println(p);
		}
		System.out.println("");
	}
	
	
	//3
	/**
	 * Método para mostrar la puntuación de las 10 partidas con más puntuación
	 * Llama al método de la clase PartidaDAO y recorre las partidas desde el ArrayList 
	 */
	static public void topDiezPartidas() {
		
		ArrayList<Partida> topPartidas = (ArrayList<Partida>) PartidaDAO.maxPuntuacion();	
		System.out.println("El top 10 de partidas es: ");
		for (Partida p : topPartidas ) {

				
				System.out.println(p);
		}
		System.out.println("");
	
	}
	
	
	
	//4
	/**
	 * LLama al método de borrarPartida de la clase PartidaDAO
	 * y borra las partidas generadas en la base de datos reseteando así el historial
	 */
	static public void borrarPartidas()
	{
		PartidaDAO.borrarPartida();
		System.out.println("Se ha borrado el historial");
	}
	
	//5
	/**
	 * Método que llama desde la clase PreguntaDAO el cual recorre el ArrayList y dice cuales son las preguntas
	 * con más fallos 
	 */
	static public void numeroFallos() {
		
		ArrayList<Pregunta> preguntasAcertadas = (ArrayList<Pregunta>) PreguntaDAO.maxFallos();	
		System.out.println("Las preguntas más acertadas son: ");
		for (Pregunta p : preguntasAcertadas ) {

				
				System.out.println(p);
		}
		System.out.println("");
	
	}
	
	
	//6
	/**
	 * Pide por consola el nombre y la contraseña que queremos hacer para el registro del jugadore
	 * Llamamos al método de meterJugadore de la clase JugadorDAO
	 * donde lo introducimos en la base de datos.
	 */
	static public void crearJugadore() {
		System.out.println("Introduzca el nombre");
		String nombre =Utilidades.soloString();
		System.out.println("Introduzca una contraseña");
		String password = Utilidades.soloString();
		
		if(JugadorDAO.buscarUsuario(nombre, password)==null) {
		
		
		JugadorDAO.meterJugadore(nombre, password);
		}else {System.out.println("El jugador ya se encuentra en el sistema");}
	}
	
	
	
	//7
	
	/**
	 * Inicia el servidor llamándolo desde la clase Servidor
	 */
	static public void iniciarServidor() {
		System.out.println("Servidor iniciado");
		Servidor servidor= new Servidor();
		servidor.start();
		
	}
	
	//0
	
	/**
	 * Finaliza el programa e informa de ello.
	 */
	static public void finalizarPrograma() {
		System.out.println("Programa finalizado");
		
	}
	

}
