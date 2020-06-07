package snippetlab.java.hibernate.one2one_bidirection;

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

			System.out.println("==================== the instructor Id: " + tempInstructor.getId());
			System.out.println("==================== the instructorDetail Id: " + tempInstructor.getInstructorDetail().getId());

			session.getTransaction().commit();

			// -------------------------- get from instructorDetail
			session = factory.getCurrentSession();
			session.beginTransaction();

			int theId = tempInstructor.getInstructorDetail().getId();

			InstructorDetail tempInstructorDetail2 =
				session.get(InstructorDetail.class, theId);

			System.out.println("===============================================================");
			System.out.println("tempInstructorDetail2: " + tempInstructorDetail2);
			System.out.println("===============================================================");
			System.out.println("associated tempInstructor: " + tempInstructorDetail2.getInstructor());
			System.out.println("===============================================================");

			session.getTransaction().commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			session.close();
			factory.close();
		}
	}
}
