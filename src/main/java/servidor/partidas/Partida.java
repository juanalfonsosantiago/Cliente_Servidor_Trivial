package servidor.partidas;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Partida {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Jugador jugador;
	private int puntuacion;
	private LocalDateTime fecha;
	
	
	
	public Partida() {
		super();
	}
	
	
	

	public Partida(Jugador jugador, int puntuacion, LocalDateTime fecha) {
		super();
		this.jugador = jugador;
		this.puntuacion = puntuacion;
		this.fecha = fecha;
	}




	static public int anadirPunto() {
		
	
		
		return 0;
	}
	
	



	public Jugador getJugador() {
		return jugador;
	}


	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}


	public int getPuntuacion() {
		return puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	@Override
	public String toString() {
		return "El ID es " + id + ", el jugador es " + jugador + ", la puntuacion es " + puntuacion + ", la fecha es " + fecha ;
	}




	
	
	
	
	
	
	
}
