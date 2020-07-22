import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadEchoServer {
	public static int DEFAULT_PORT = 8080;

	public static void main(String[] args) throws IOException {

		Socket clientSocket = null;
		try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);) {
			while (true) {
				clientSocket = serverSocket.accept();
				// MultiThread
				new Thread(new EchoServerHandler(clientSocket)).start();
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
