package snippetlab.java.flink.data_server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class App
{
	public static void
		main( String[] args ) throws Exception
	{
		App server = new App();

		// server.createFixedData();
		server.createTimestampData();
	}

	private void
		createTimestampData() throws Exception
	{
		ServerSocket listener = new ServerSocket(9527);
		try{
				System.out.println("Start Listening...");
				Socket socket = listener.accept();
				System.out.println("Got new connection: " + socket.toString());
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					Random rand = new Random();
					while (true){
						int data = rand.nextInt(100);
						String dataWithTimestamp = "" + System.currentTimeMillis() + "," + data;
						System.out.println(dataWithTimestamp);
						/* <timestamp>,<random-number> */
						out.println(dataWithTimestamp);
						Thread.sleep(50);
					}

				} finally{
					System.out.println("Start closing server...");
					socket.close();
				}

		} catch(Exception e ){
			e.printStackTrace();
		} finally{

			listener.close();
		}
	}

	private void
		createFixedData() throws Exception
	{
		ServerSocket listener = new ServerSocket(9527);
		try{
				System.out.println("Start Listening...");
				Socket socket = listener.accept();
				System.out.println("Got new connection: " + socket.toString());

				BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data"));

				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					String line;
					while ((line = br.readLine()) != null){
						System.out.println(line);
						out.println(line);
						Thread.sleep(50);
					}

				} finally{
					System.out.println("Start closing server...");
					br.close();
					socket.close();
				}

		} catch(Exception e ){
			e.printStackTrace();
		} finally{
			listener.close();
		}
	}
}
