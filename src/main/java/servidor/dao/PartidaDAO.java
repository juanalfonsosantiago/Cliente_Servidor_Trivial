package servidor.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.Query;
import servidor.partidas.Partida;
import servidor.preguntas.Pregunta;


public class PartidaDAO {

	
	
	/**
	 * Crea una partida con los parámetros de partida de la clase Partida y lo persiste en la base de datos
	 * @param partida ,el parámetro partida de la clase Partida que le vamos a dar en la base de datos
	 */
	public static void meterPartida(Partida partida) {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		sesion.persist(partida);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	
	/**
	 * Borra todas las partidas con una sentencia DELETE
	 */
	public static void borrarPartida() {
		
		
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "DELETE FROM Partida";
		//pide la secuencia y el objeto, si no queremos nada de vuelta ponemos null
		sesion.createQuery(hql,null).executeUpdate();
		sesion.getTransaction().commit();
		sesion.close();
		
	}
	
	/**
	 * Método para listar las partidas que hay hechas
	 * Se seleccionan todas las partidas con un SELECT y se guarda la query en un Arraylist de la clase Partida
	 * @return devuelve un Arralist de la clase Partida con todas las partidas de la base de datos
	 */
	public static ArrayList<Partida> listarPartida() {
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "SELECT p FROM  Partida p";
		Query query = sesion.createQuery(hql,Partida.class);
		ArrayList<Partida> listaPartida= (ArrayList<Partida>) query.getResultList();

		sesion.getTransaction().commit();
	
	
		
		sesion.close();
		return listaPartida;
	}
	
	
	/**
	 * Seleccionamos mediante la sentencia SELECT la puntuación de la partida de las preguntas ordenadas de mayor a menor
	 * Guardamos el resultado de la sentencia en un ArrayList.
	 * @return le devolvemos el ArrayList con el que hemos trabajado con las máximas puntuaciones
	 */
	public static List<Partida> maxPuntuacion() {
		List<Partida> maxPuntuacion = new ArrayList<Partida>();
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		String hql = "SELECT p FROM Partida p ORDER BY p.puntuacion DESC LIMIT 10";
		Query query = sesion.createQuery(hql, Partida.class);
		maxPuntuacion = query.getResultList();
		sesion.getTransaction().commit();
		sesion.close();
		return maxPuntuacion;
		
	}
	
}
