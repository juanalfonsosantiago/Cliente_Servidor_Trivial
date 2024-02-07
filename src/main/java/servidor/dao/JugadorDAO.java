package servidor.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.Query;
import servidor.partidas.Jugador;

public class JugadorDAO {

	
	
	/**
	 * Crea un jugador con los parámetros de nombre y password(pass) y lo persiste en la base de datos
	 * @param nombre ,el parámetro del nombre que le vamos a dar al jugadore en la base de datos
	 * @param pass ,el parámetro de la contraseña que le vamos a dar para que pueda jugar al trivial al iniciar sesion en el servidor
	 */
	public static void meterJugadore(String nombre, String pass) {
		Jugador jugadorIntroducido = new Jugador(nombre, pass, LocalDateTime.now());
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		sesion.persist(jugadorIntroducido);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	/**
	 * Busca un jugador en la base de datos con los parámetros nombre y contrasenya
	 * y busca esos parámetros en esos campos en la base de datos de la tabla Jugador
	 * @param nombre ,el nombre que se busca con el select en la base de datos en la tabla Jugador
	 * @param contrasenya ,la contraseña que se busca con el select en la base de datos en la tabla Jugador
	 * @return devuelve el jugador buscado en la query del SELECT con los parámetros buscados
	 */
	public static Jugador buscarUsuario(String nombre, String contrasenya) {
		
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		Jugador jugadorQuery = new Jugador(); 
		sesion.getTransaction().begin();
		//nombre Jugador con mayuscula porque es el nombre de la clase o entidad
		//:nombre y :passworrd son el nombre de los parámetros que pasamos y el j.nombre y j.pass es la sentencia sql +
		// el nombre de la variable o atributo en la clase Jugador
		String hql = "SELECT j FROM Jugador j WHERE j.nombre = :nombre AND  j.pass = :password";
		Query query= sesion.createQuery(hql, Jugador.class);
		query.setParameter("nombre", nombre);
		query.setParameter("password", contrasenya);
		//te devuelve un jugador , coge el usuario y contraseña
		try{
			jugadorQuery = (Jugador)query.getSingleResult();
			sesion.getTransaction().commit();
			
			
		}catch(Exception e){
				jugadorQuery=null;
				sesion.getTransaction().rollback();
		}
		

		sesion.close();
		return jugadorQuery;
		
	}
	
	/**
	 * Método para actualizar el número de partidas en un jugador
	 * Se le añade una partida más cada vez que juegue y se guarda en la base de datos
	 * @param jugador ,es el jugador al que actualizamos en número de partidas en la base de datos
	 */
	public static void actualizarNumeroPartidas(Jugador jugador) {
	
		
		jugador.setNumPartidas(jugador.getNumPartidas()+1);
		Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		sesion.getTransaction().begin();
		sesion.merge(jugador);
		sesion.getTransaction().commit();
		sesion.close();
		
	}
	
	/**
	 * Método para actualizar la puntuación máxima dentro de la tabla jugador
	 * se hace un merge para actualizar en la tabla de la base de datos
	 * @param jugador ,el jugador que le vamos a actualizar la puntuación en la base de datos
	 * @param maximaPuntuacion ,la puntuación máxima que vamos a actualizar en la base de datos
	 */
	public static void actualizaMaximaPuntuacion(Jugador jugador, int maximaPuntuacion) {
		
		if(jugador.getMaxPuntuacion()<maximaPuntuacion) {
			jugador.setMaxPuntuacion(maximaPuntuacion);
			Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
			sesion.getTransaction().begin();
			sesion.merge(jugador);
			sesion.getTransaction().commit();
			sesion.close();
		}
		
		
	}
	
}
