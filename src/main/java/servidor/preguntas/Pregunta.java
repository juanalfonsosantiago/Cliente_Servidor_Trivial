package servidor.preguntas;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Pregunta {

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String pregunta;
	
	@OneToMany (mappedBy = "pregunta",cascade=CascadeType.ALL,fetch = FetchType.EAGER) 
	private List<Respuesta> respuesta = new ArrayList(); 
	private int numFallos;
	private int numAciertos;
	
	
	
	
	
	
	
	
	
	




	public Pregunta() {
		super();
	}



	


	public Pregunta(String pregunta, List<Respuesta> respuesta) {
		super();
		this.pregunta = pregunta;
		this.respuesta = respuesta;
	}






	static public String getOpcionCorrecta() {
		
		
		return null;
	}
	
	
	
	
	
	static public boolean isCorrecta(int index) {
		
		
		return false;
		
	}







	public int getId() {
		return id;
	}







	public void setId(int id) {
		this.id = id;
	}







	public String getPregunta() {
		return pregunta;
	}







	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}












	public List<Respuesta> getRespuesta() {
		return respuesta;
	}







	public void setRespuesta(List<Respuesta> respuesta) {
		this.respuesta = respuesta;
	}







	public int getNumFallos() {
		return numFallos;
	}







	public void setNumFallos(int numFallos) {
		this.numFallos = numFallos;
	}







	public int getNumAciertos() {
		return numAciertos;
	}







	public void setNumAciertos(int numAciertos) {
		this.numAciertos = numAciertos;
	}






	@Override
	public String toString() {
		return "La pregunta " + pregunta +" tiene esta cantidad de fallos:" + numFallos;
	}
	
	
	
	
	
	
	
}
