package servidor.utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;

public class GestionaFicheros {
	
	/**
	 * Método para leer un fichero y leer 1 pregunta y 4 respuestas (5 líneas) y añadírselo al ArrayList definitivo
	 * recorriendo distintos Arraylist y asignándo una pregunta y 4 respuestas, diciendo cual es la correcta con el boolean
	 * @param file ,el fichero que le vamos a pasar como parámetro
	 * @return devolvemos las preguntas que vamos a importar del fichero de texto
	 */
	static public ArrayList<Pregunta> cargarPreguntas(File file){
		ArrayList<Pregunta> preguntaImportar = new ArrayList<Pregunta>();
		
		try (FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);){
				
				while (br.ready()) {
					
				//Importante que el ArrayList esté dentro del bucle para que se resetee	
				String preguntaProvisional = br.readLine();
				ArrayList<Respuesta> darRespuesta = new ArrayList<Respuesta>();
				darRespuesta.add(new Respuesta(br.readLine(), true));
				darRespuesta.add(new Respuesta(br.readLine(), false));
				darRespuesta.add(new Respuesta(br.readLine(), false));
				darRespuesta.add(new Respuesta(br.readLine(), false));
					
				
					Pregunta preguntaAnyadir= new Pregunta(preguntaProvisional, darRespuesta);
					for (Respuesta respuesta : preguntaAnyadir.getRespuesta()) {
						respuesta.setPregunta(preguntaAnyadir);
					}
					preguntaImportar.add(preguntaAnyadir);
				}
				
				
				
				br.close();
				
				return preguntaImportar;
			} catch (FileNotFoundException e) {
				
				System.err.println("Señala que ha fallado un intento de abrir el archivo indicado por un nombre de ruta específico"+ e.getMessage());
			} catch (IOException e) {
				System.err.println("Error de entrada / salida" + e.getMessage());
			}
		
		return null;
		
	}

}
