package servidor.partidas;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Jugador {

	@Id
	private String nombre;
	private String pass;
	private LocalDateTime fechaRegistro;
	private int maxPuntuacion;
	private int numPartidas;
	
	
	
	
	public Jugador() {
		super();
	}

	
	

	public Jugador(String nombre, String pass, LocalDateTime fechaRegistro) {
		super();
		this.nombre = nombre;
		this.pass = pass;
		this.fechaRegistro = fechaRegistro;
	}

	


	static public boolean actualizarMaxPuntuacion(int puntuacion) {
		
		
		
		return false;
	}
	
	
	
	
	static public int anadirPartida() {
		
		
		
		return 0;
	
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getPass() {
		return pass;
	}




	public void setPass(String pass) {
		this.pass = pass;
	}




	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}




	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}




	public int getMaxPuntuacion() {
		return maxPuntuacion;
	}




	public void setMaxPuntuacion(int maxPuntuacion) {
		this.maxPuntuacion = maxPuntuacion;
	}




	public int getNumPartidas() {
		return numPartidas;
	}




	public void setNumPartidas(int numPartidas) {
		this.numPartidas = numPartidas;
	}




	@Override
	public String toString() {
		return "El nombre es: " + nombre ;
	}
	
	
	
	
	
}
