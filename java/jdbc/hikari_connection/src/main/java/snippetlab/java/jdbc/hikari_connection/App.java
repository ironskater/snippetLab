package snippetlab.java.jdbc.hikari_connection;

import java.sql.Connection;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
		DataSource source = null;

		try
		{
			System.out.println("Connecting to Database: " + jdbcUrl);

			/**
			 * The basic service for managing a set of JDBC drivers.
			 * NOTE: The javax.sql.DataSource interface, new in the JDBC 2.0 API,
			 * provides another way to connect to a data source.
			 * The use of a DataSource object is the preferred means of connecting to a data source.
			 */
			// DriverManager.getConnection(jdbcUrl, user, pw);

 			/**
			  * This interface is implemented by a driver vendor.
			  * Here using Hikari connection pool
			  * https://www.baeldung.com/hikaricp
			  */
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcUrl);
			config.setUsername(user);
			config.setPassword(pw);

			source = new HikariDataSource(config);
			Connection conn = source.getConnection();

			System.out.println(conn);

			System.out.println("Connnection successful!!!");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			HikariDataSource ds = (HikariDataSource)source;
			ds.close();
		}
	}
}
