package snippetlab.java.hibernate.many2many_delete_student_only;

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
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			// prepare data to DB
			session.beginTransaction();

			Course tempCourse = new Course("Guitar");

			System.out.println("\nSaving the course...");
			session.save(tempCourse);
			System.out.println("Saved the course: " + tempCourse);

			Student tempStudent1 = new Student("Hyde", "Liao", "xxx@gmail.com");
			Student tempStudent2 = new Student("Cheryl", "Chen", "yyy@gmail.com");

			tempCourse.addStudent(tempStudent1);
			tempCourse.addStudent(tempStudent2);

			System.out.println("\nSaving students...");
			session.save(tempStudent1);
			session.save(tempStudent2);
			System.out.println("Saved students: " + tempCourse.getStudents());

			session.getTransaction().commit();

			// -----------------------------------------------------------------
			// Delete student "Hyde" database and don't delete course Guitar
			session = factory.getCurrentSession();
			session.beginTransaction();

			System.out.println("Deleting student: ..." + tempStudent1);
			/**
			 * Hibernate: delete from course_student where student_id=?
			 * Hibernate: delete from student where id=?
			 */
			session.delete(tempStudent1);

			session.getTransaction().commit();

			System.out.println("Done!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		finally {
			session.close();
			factory.close();
		}
	}
}
