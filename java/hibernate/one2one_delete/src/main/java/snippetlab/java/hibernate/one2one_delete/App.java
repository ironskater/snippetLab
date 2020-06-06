package snippetlab.java.hibernate.one2one_delete;

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
									.configure("hibernate.cfg.xml")
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

			// Delete ----------------------------------------------------------
			session = factory.getCurrentSession();

			session.beginTransaction();

			tempInstructor = session.get(	Instructor.class,
											tempInstructor.getId());

			System.out.println("Retrieve from DB: " + tempInstructor);

			if(tempInstructor != null) {
				System.out.println("===================== Deleting: " + tempInstructor);

				// NOTE: It will also DELETE associated "details" object
				// due to CascadeType.ALL
				session.delete(tempInstructor);
			}

			session.getTransaction().commit();

			System.out.println("DONE !!!!!!!!!!!!!!!!!!!!!");
		}
		finally {
			factory.close();
		}
	}
}














