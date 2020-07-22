import java.io.*;
import java.net.*;


public class EchoServer {
	public static int DEFAULT_PORT = 8080;

	public static void main(String[] args) throws IOException {
        try (
            ServerSocket serverSocket =
                new ServerSocket(DEFAULT_PORT);
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
