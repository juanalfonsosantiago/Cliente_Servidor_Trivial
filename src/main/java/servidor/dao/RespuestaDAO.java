package servidor.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.Query;
import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;

public class RespuestaDAO {

	
	/**
	 * introduce una respuesta con el método en la base de datos
	 * @param respuesta , la respuesta que se va a introducir
	 */
	public static void meterRespuesta(Respuesta respuesta) {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		sesion.persist(respuesta);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	/**
	 * Borra las respuestas de la base de datos con una sentencia DELETE
	 */
	public static void borrarRespuestas() {
		
		
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		
		//pide la secuencia y el objeto, si no queremos nada de vuelta ponemos null
		
		sesion.getTransaction().begin();
		String hql = "DELETE FROM Respuesta";
		sesion.createQuery(hql,null).executeUpdate();
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	/**
	 * Saca las respuestas asociadas a las preguntas que tocan en ese momento de la base de datos con un SELECT
	 * añadiendo en un ArrayList de respuestas la query y a continuación las desordena con el Collections.shuffle
	 * @param pregunta ,el parámetro de las preguntas donde se van a asociar las respuestas
	 * @return devuelve un Arraylist de respuesta asociadas a las preguntas de forma desordenada.
	 */
	public static ArrayList<Respuesta> sacarRespuestas(Pregunta pregunta) {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "SELECT r FROM  Respuesta r WHERE r.pregunta= :preguntaAsociada";
		Query query = sesion.createQuery(hql,Respuesta.class);
		query.setParameter("preguntaAsociada", pregunta);
		ArrayList<Respuesta> respuestaAsociadas= (ArrayList<Respuesta>) query.getResultList();
		sesion.getTransaction().commit();
		//desordena la lista de las preguntas del cliente
		Collections.shuffle(respuestaAsociadas);
		sesion.close();
		return respuestaAsociadas;
	}
	
	
	
}
