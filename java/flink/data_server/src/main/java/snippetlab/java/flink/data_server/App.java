package snippetlab.java.flink.data_server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class App
{
	public static void
		main( String[] args ) throws Exception
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
