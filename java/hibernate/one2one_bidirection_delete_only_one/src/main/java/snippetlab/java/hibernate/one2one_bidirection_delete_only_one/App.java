package snippetlab.java.hibernate.one2one_bidirection_delete_only_one;

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


			// -------------------------- delete instructorDetail and not affect instructor


			session = factory.getCurrentSession();
			session.beginTransaction();

			int instructorDetailId = tempInstructor.getInstructorDetail().getId();

			tempInstructorDetail = session.get(	InstructorDetail.class,
												instructorDetailId);

			System.out.println("==================== The instructorDetail from DB: " + tempInstructorDetail);
			System.out.println("==================== Deleting tempInstructorDetail: " + tempInstructorDetail);

			// !!!!!!!!!!!!!!!! remove the associated object reference
			// !!!!!!!!!!!!!!!! break bi-directional link
			tempInstructorDetail.getInstructor().setInstructorDetail(null);

			// only delete instructorDetail and not affect instructor
			session.delete(tempInstructorDetail);

			session.getTransaction().commit();

			System.out.println("Done!!!!!!!!!!!!!");
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
