package servidor.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import jakarta.persistence.Query;
import servidor.partidas.Partida;
import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;

public class PreguntaDAO {

	
	/**
	 * Crea las preguntas en la base de datos con los parámetros de pregunta de la clase Pregunta y lo persiste en la base de datos
	 * @param pregunta , el parámetro pregunta de la clase Pregunta que le vamos a dar en la base de datos.
	 */
	public static void meterPregunta(Pregunta pregunta) {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		sesion.persist(pregunta);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	/**
	 * Borra todas las preguntas con una sentencia DELETE
	 */
	public static void borrarPregunta() {
		
		
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "DELETE FROM Pregunta";
		//el delete no devuelve nada y para que no sea deprecated se pone un null
		sesion.createQuery(hql,null).executeUpdate();
		sesion.getTransaction().commit();

		sesion.close();
	}
	
	/**
	 * Método para sacar preguntas aleatorias mediante un SELECT se añade a un ArrayList
	 * y se desordena mediante el método de Collections.shuffle , recorremos esas preguntas con una longitud menor a 5
	 * ya que el índice es 0, nos sacará 5 preguntas aleatorias
	 * @return devolvemos las 5 preguntas aleatorias que le hemos asignado de la query SELECT
	 */
	public static ArrayList<Pregunta> sacarPreguntasAleatorias() {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "SELECT p FROM  Pregunta p";
		Query query = sesion.createQuery(hql,Pregunta.class);
		List<Pregunta> preguntaCliente= (ArrayList<Pregunta>) query.getResultList();

		sesion.getTransaction().commit();
		//desordena la lista de las preguntas del cliente
		Collections.shuffle(preguntaCliente);
		ArrayList<Pregunta> cincoPreguntasAleatorias= new ArrayList<Pregunta>();
		
		for(int i=0; i<5; i++) {
			cincoPreguntasAleatorias.add(preguntaCliente.get(i));
		}
		
		sesion.close();
		return cincoPreguntasAleatorias;
	}
	
	/**
	 * El método para actualizar si se acierta o se falla en una pregunta
	 * y sumarlo dependiendo si es correcta o no con el boolean esCorrecto
	 * @param pregunta ,el parámetro de la pregunta donde sumamos aciertos o fallos
	 * @param esCorrecto ,el estado del boolean de si es correcta se suma acierto si es false se suma fallo
	 */
	public static void actualizarAciertosFallos(Pregunta pregunta, boolean esCorrecto) {
		
		if(esCorrecto==true) {
			pregunta.setNumAciertos(pregunta.getNumAciertos()+1);
		}else {
			pregunta.setNumFallos(pregunta.getNumFallos()+1);
		}
		
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		//si existe sobreescribe el que estaba antes por este con el merge
		//si tenía 3 fallos y la versión nueva tiene 4, se queda la de 4
		sesion.merge(pregunta);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	/**
	 * Seleccionamos mediante la sentencia SELECT el número de fallos de las preguntas ordenadas de mayor a menor
	 * Guardamos el resultado de la sentencia en un ArrayList.
	 * @return le devolvemos el ArrayList con el que hemos trabajado
	 */
	public static List<Pregunta> maxFallos() {
		List<Pregunta> numAciertos = new ArrayList<Pregunta>();
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "SELECT p FROM Pregunta p ORDER BY p.numFallos DESC";
		Query query = sesion.createQuery(hql, Pregunta.class);
		numAciertos = query.getResultList();
		sesion.getTransaction().commit();
		sesion.close();
		return numAciertos;
		
	}
	
	
}
