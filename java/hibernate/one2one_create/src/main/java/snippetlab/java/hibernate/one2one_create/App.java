package snippetlab.java.hibernate.one2one_create;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App
{
	public static void
		main( String[] args )
	{
		// create session factory
		/**
		 * [Trouble shooting] could not locate cfg.xml for hibernate
		 *
		 * "/home/hyde/ssd/atelier/snippetLab/java/hibernate/general/src/hibernate.cfg.xml" can't be used because
		 * because of configure() method waits for class path resource (loaded by a classloader) or URL.
		 *
		 * Refer to https://stackoverflow.com/questions/47069676/could-not-locate-cfg-xml-for-hibernate
		 */
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			Instructor tempInstructor = new Instructor("Hyde", "Holmes", "hyde@mail.com");

			InstructorDetail tempInstructorDetail = new InstructorDetail("hyde channel", "play football");

			tempInstructor.setInstructorDetail(tempInstructorDetail);

			session.beginTransaction();

			session.save(tempInstructor);

			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
}
