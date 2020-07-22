import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolEchoServer {
	public static int DEFAULT_PORT = 8080;

	public static void main(String[] args) throws IOException {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		Socket clientSocket = null;
		try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);) {
			while (true) {
				clientSocket = serverSocket.accept();
				
				// Thread Pool
				threadPool.submit(new Thread(new EchoServerHandler(clientSocket)));
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
