package snippetlab.java.hibernate.eager_fetch;

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
									.addAnnotatedClass(Course.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			int theId;
			Instructor instructor;
			InstructorDetail instructorDetail;
			Course course;

			// insert an instructor data
			session.beginTransaction();

			instructor = new Instructor();
			instructor.setFirstName("Hyde");
			instructor.setLastName("Liao");

			instructorDetail = new InstructorDetail();
			instructorDetail.setHobby("play football");

			course = new Course("math");

			instructor.setInstructorDetail(instructorDetail);
			instructor.add(course);

			session.save(instructor);
			session.save(course);

			theId = instructor.getId();

			System.out.println("=================== The id: " + theId);

			session.getTransaction().commit();

			session.close();

			// get the instructor from DB
			session = factory.getCurrentSession();
			session.beginTransaction();

			Instructor tempInstructor = session.get(Instructor.class,
													theId);
			session.getTransaction().commit();

			session.close();

			System.out.println("=============================================");
			System.out.println("Instructor: " + tempInstructor);
			System.out.println("=============================================");
			System.out.println("Courses: " + tempInstructor.getCourses());
			System.out.println("=============================================");

			System.out.println("Done!!!!!!!!!!!!!!!!!!!!!!!!!1");
		}
		finally {
			session.close();
			factory.close();
		}
	}
}
