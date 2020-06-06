package snippetlab.java.hibernate.basic_connection;

import java.sql.DriverManager;

public class App
{
	public static void
		main( String[] args )
	{
		// String jdbcUrl = "jdbc:mysql://localhost:3306/hb_student_tracker";
		// String user = "hyde";
		// String pw = "hyde";

		String jdbcUrl = "jdbc:postgresql://localhost:5432/gamebox";
		String user = "hyde";
		String pw = "hyde";

		try
		{
			System.out.println("Connecting to Database: " + jdbcUrl);

			DriverManager.getConnection(jdbcUrl, user, pw);

			System.out.println("Connnection successful!!!");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
