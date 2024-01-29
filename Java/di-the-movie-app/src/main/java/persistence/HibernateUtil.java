package persistence;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Clase de utilidades para Hibernate
 */
public class HibernateUtil {

	/** Session Factory */
	private static SessionFactory sessionFactory;

	/** Session */
	private static Session session;

	/**
	 * Constructor privado para implementar el patrón Singleton
	 */
	private HibernateUtil() {
	}

	/**
	 * Método que devuelve el objeto Session.
	 * 
	 * @return Sesión abierta
	 */
	public static Session getSession() {
		if (session == null || !session.isOpen()) {
			session = getSessionFactory().openSession();
		}

		return session;
	}

	/**
	 * Método que cierra el objeto Session de HibernateUtil y el SessionFactory
	 */
	public static void closeSession() {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	/**
	 * @return El sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			initializeSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * Inicializa el sessionFactory
	 */
	private static synchronized void initializeSessionFactory() {
		if (sessionFactory == null) {
			StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure(new File("hibernate.cfg.xml"))
					.build();
			sessionFactory = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		}
	}

	/**
	 * Cierra la factoría de sesiones
	 */
	public static synchronized void closeSessionFactory() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}
