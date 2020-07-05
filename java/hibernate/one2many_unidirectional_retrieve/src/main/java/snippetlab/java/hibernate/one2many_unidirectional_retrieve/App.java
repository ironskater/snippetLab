package snippetlab.java.hibernate.one2many_unidirectional_retrieve;

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
			int courseId;

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

			// get tempCourse1 by id
			courseId = tempCourse1.getId();

			session = factory.getCurrentSession();
			session.beginTransaction();

			Course tempCourse = session.get(Course.class,
											courseId);

			/**
			 * Hibernate: select 	course0_.id as id1_0_0_,
			 * 						course0_.instructor_id as instruct3_0_0_,
			 * 						course0_.title as title2_0_0_,
			 * 						instructor1_.id as id1_1_1_,
			 * 						instructor1_.email as email2_1_1_,
			 * 						instructor1_.first_name as first_na3_1_1_,
			 * 						instructor1_.instructor_detail_id as instruct5_1_1_,
			 * 						instructor1_.last_name as last_nam4_1_1_,
			 * 						instructor2_.id as id1_2_2_, instructor2_.hobby as hobby2_2_2_,
			 * 						instructor2_.youtube_channel as youtube_3_2_2_
			 * 				from course course0_
			 * 				left outer join instructor instructor1_
			 * 					on course0_.instructor_id=instructor1_.id
			 * 				left outer join instructor_detail instructor2_
			 * 					on instructor1_.instructor_detail_id=instructor2_.id
			 * 				where course0_.id=?
			 */
			System.out.println(tempCourse);
			/**
			 * Do second query due to Lazy-Fetch
			 *
			 * Hibernate: select 	reviews0_.course_id as course_i3_3_0_,
			 * 						reviews0_.id as id1_3_0_,
			 * 						reviews0_.id as id1_3_1_,
			 * 						reviews0_.comment as comment2_3_1_
			 * 				from review reviews0_
			 * 				where reviews0_.course_id=?
			 */
			System.out.println(tempCourse.getReviews()); // remember reviews are configured Lazy-Fetch

			session.getTransaction().commit();
			System.out.println("Done!!!!!!!!!!!!!!!!!!!!!!!!!1");
		}
		finally {
			session.close();
			factory.close();
		}
	}
}
