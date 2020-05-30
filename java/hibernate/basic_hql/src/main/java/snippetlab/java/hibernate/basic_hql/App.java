package snippetlab.java.hibernate.basic_hql;

import java.util.List;

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
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			// create a student object
			System.out.println("Creating new student object...");

			Student tempStudent1 = new Student("hyde1", "firstHibernate", "yaya");
			Student tempStudent2 = new Student("hyde2", "secondHibernate", "jaja");
			Student tempStudent3 = new Student("hyde3", "thirdHibernate", "kaka");

			// start a transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the student...");
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);

			// commit transaction
			session.getTransaction().commit();

			// Query --------------------------------------------------
			session = factory.getCurrentSession();
			session.beginTransaction();

			// Query ALL
			List<Student> theStudents = session.createQuery("from Student").getResultList();

			for(Student stu : theStudents)
			{
				System.out.println("=========================================");
				System.out.println(stu.toString());
				System.out.println("=========================================");
			}

			// Query specified
			Student theStudent = (Student)session.createQuery("from Student s where s.lastName = 'secondHibernate'").getSingleResult();

			System.out.println("\n\n=========================================");
			System.out.println(theStudent.toString());
			System.out.println("=========================================");

			session.getTransaction().commit();

			// update ----------------------------------------------------------
			session = factory.getCurrentSession();
			session.beginTransaction();

			session.createQuery("update Student set email = 'updateEmail' where firstName = 'hyde2'").executeUpdate();

			session.getTransaction().commit();

			System.out.println("Done!!!!");
		}
		finally {
			factory.close();
		}
	}
}
