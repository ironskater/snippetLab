package snippetlab.java.hibernate.one2many_unidirectional_cascade_delete;

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
									.addAnnotatedClass(Review.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			// Insert course and review data first
			session.beginTransaction();

			Course tempCourse1 = new Course("Guitar");
			Course tempCourse2 = new Course("Bass");

			tempCourse1.addReview(new Review("nice guitar lesson"));
			tempCourse1.addReview(new Review("good guitar lesson"));
			tempCourse2.addReview(new Review("nice bass lesson"));
			tempCourse2.addReview(new Review("good bass lesson"));

			session.save(tempCourse1);
			session.save(tempCourse2);

			session.getTransaction().commit();

			// delete tempCourse1
			session = factory.getCurrentSession();
			session.beginTransaction();

			/**
			 * delete course with associated reviews by cascade delete
			 *
			 * Hibernate: delete from review where id=?
			 * Hibernate: delete from review where id=?
			 * Hibernate: delete from course where id=?
			 */
			session.delete(tempCourse1);

			session.getTransaction().commit();
			System.out.println("Done!!!!!!!!!!!!!!!!!!!!!!!!!1");
		}
		finally {
			session.close();
			factory.close();
		}
	}
}
