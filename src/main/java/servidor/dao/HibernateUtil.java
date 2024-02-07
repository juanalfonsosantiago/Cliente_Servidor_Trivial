package servidor.dao;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import servidor.partidas.Jugador;
import servidor.partidas.Partida;
import servidor.preguntas.Pregunta;
import servidor.preguntas.Respuesta;



public class HibernateUtil {
	
	
	private static SessionFactory sessionFactory;
	
	/**
	 * Configura y establece cual va a ser el motor de la base de datos
	 * establece cual va a ser el puerto y de que base de datos vamos a trabajar
	 * se añaden las clases que se van a usar en la base de datos con hibernate
	 * @return retorna una sesión de un objeto global seguro entre hilos que se instancia una sóla vez 
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
		Configuration config = new Configuration();
		Properties properties = new Properties();
		properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
		
		//cual va a ser la direccion de acceso a mi base de datos
		properties.put(Environment.URL,"jdbc:mysql://localhost:3306/trivial");
		properties.put(Environment.USER, "root");
		properties.put(Environment.FORMAT_SQL, "true");  //dar formato al sql en la consola
		properties.put(Environment.PASS, "root");
		properties.put(Environment.DIALECT,
		"org.hibernate.dialect.MySQL8Dialect");
		
		// ver posibles errores que hay
		properties.put(Environment.SHOW_SQL, "true");
		
		//el contexto donde estoy lanzando desde donde lanzo las consultas desde el hilo primigenio
		properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
		
		// forma de automatizar que hacemos con las tablas si existen, la elimina y luego la crea en este caso, hay varios casos
	//	properties.put(Environment.HBM2DDL_AUTO, "create-drop");
		properties.put(Environment.HBM2DDL_AUTO, "update");
		
		config.setProperties(properties);
		
		//añadir la clase donde voy añadir registros
		config.addAnnotatedClass(Partida.class);
		config.addAnnotatedClass(Jugador.class);
		config.addAnnotatedClass(Pregunta.class);
		config.addAnnotatedClass(Respuesta.class);
		
	
		
		
		
		ServiceRegistry serviceRegistry = new
				StandardServiceRegistryBuilder().applySettings(config.getProperties()
				).build();
				sessionFactory =
				config.buildSessionFactory(serviceRegistry);
				}
				return sessionFactory;
				}

}
