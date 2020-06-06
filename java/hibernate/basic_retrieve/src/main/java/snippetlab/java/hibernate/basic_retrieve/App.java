package snippetlab.java.hibernate.basic_retrieve;

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
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		try {
			// create a student object
			System.out.println("Creating new student object...");
			Student tempStudent1 = new Student("hyde1", "firstHibernate", "yaya");

			// start a transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the student...");
			session.save(tempStudent1);

			// commit transaction
			// After executing save(), DB still not has value until completing commit()
			session.getTransaction().commit();

			// -----------------------------------------------------------------

			// find studenet by id
			session = factory.getCurrentSession();

			// Different to Normal SQL, in hibernate, even though doing querying, it still need a transaction.
			session.beginTransaction();

			System.out.println("find myStudent by Id: " + tempStudent1.getId());
			Student myStudent = session.get(Student.class,
											tempStudent1.getId());

			// Different to save()
			// myStudent already has values before doing commit()
			session.getTransaction().commit();

			System.out.println("Done!!!!");
			System.out.println("myStudent: " + myStudent.toString());
		}
		finally {
			factory.close();
		}
	}
}
