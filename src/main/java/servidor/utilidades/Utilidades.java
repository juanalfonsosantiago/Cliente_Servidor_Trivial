package servidor.utilidades;

import java.util.Scanner;

public class Utilidades {
	
	/**
	 * Nos servirá para que cada vez que introduzcamos un caracter String y queramos un número esté a prueba de errores
	 * Está en forma de bucle hasta que introduzcamos un número valido, sino retornará false.
	 * @return nos dará el número que introducimos a prueba de errores para que no sea algo distinto a un número.
	 */
	static public int numeroPruebaErrores() {
		
		Scanner scInt = new Scanner(System.in);
		String provisional = scInt.nextLine();
		while(isNumericInt(provisional)==false) {
			provisional = scInt.nextLine();
		}
		return Integer.valueOf(provisional);
		
		
	}
	
	/**
	 * Método para poner a prueba de errores el código.
	 * @param x el String que utilizaremos para introducir en el Integer a probar.
	 * @return si se produce error retorna un boolean false, sino retorna true.
	 */
	static public boolean isNumericInt(String x) {

		try {

			Integer.valueOf(x);
		} catch (NumberFormatException e) {
			System.err.println("Se lanza para indicar que la aplicación ha intentado"
					+ " convertir una cadena a uno de los tipos numéricos, pero que la cadena no tiene el formato apropiado.");
			return false;

		}
		return true;
	}
	
	
	/**
	 * Método para introducir Scanner en cualqjuier sitio de tipo String
	 * @return devuelve una variable de tipo Scanner de tipo String para introducir por consola
	 */
	public static String soloString() {
		
	 Scanner scLine = new Scanner(System.in);
	 return scLine.nextLine();
	 
	}
	
	
	
	
	
	
	
	
	

}
