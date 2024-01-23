package persistence;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.context.internal.ThreadLocalSessionContext;

/**
 * Clase de utilodades para Hibernate
 */
public class HibernateUtil {

	/** Session Factory */
	private static SessionFactory sessionFactory;

	/** Session */
	private static Session session;

	/**
	 * Constructor sobrecargado
	 */
	private HibernateUtil() {

	}

	/**
	 * Metodo que devuelve el objeto Session.
	 * 
	 * @return
	 *         <ul>
	 *         <li>Si la sesi�n no est� creada: la crea y la abre.</li>
	 *         <li>Si la sesi�n est� creada: simplemente devuelve la sesi�n
	 *         abierta.</li>
	 *         </ul>
	 */
	public static Session getSession() {
		if (sessionFactory == null) {
			session = getSessionFactory().openSession();
		}

		return session;
	}

	/**
	 * Mtodo que cierra el objeto Session de HibernateUtil y el SessionFactory
	 */
	public static void closeSession() {
		Session session = ThreadLocalSessionContext.unbind(sessionFactory);
		if (session != null) {
			session.close();
		}
		closeSessionFactory(sessionFactory);
	}

	/**
	 * 
	 * @return the sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure(new File("hibernate.cfg.xml"))
					.build();
			sessionFactory = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	/**
	 * Cierra la factoria de sesiones
	 * 
	 * @param sessionFactory sesiones a cerrar
	 */
	private static void closeSessionFactory(SessionFactory sessionFactory) {
		if ((sessionFactory != null) && (sessionFactory.isClosed() == false)) {
			sessionFactory.close();
		}
	}

}
