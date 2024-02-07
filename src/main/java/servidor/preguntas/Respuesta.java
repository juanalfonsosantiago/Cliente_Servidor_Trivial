package servidor.preguntas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Respuesta {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private String respuestaTexto;
	private boolean correcta;
	@ManyToOne
	@JoinColumn(name="respuesta")
	private Pregunta pregunta;
	
	
	
	public Respuesta() {
		super();
	}



	public Respuesta(String respuesta, boolean correcta) {
		super();
		this.respuestaTexto = respuesta;
		this.correcta = correcta;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getRespuesta() {
		return respuestaTexto;
	}



	public void setRespuesta(String respuesta) {
		this.respuestaTexto = respuesta;
	}



	public boolean isCorrecta() {
		return correcta;
	}



	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
	}



	public Pregunta getPregunta() {
		return pregunta;
	}



	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
	
	
	
	
	
	
}
