package Communication;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to chat server.");

            // Thread to listen for messages from the server
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main loop to send messages
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}