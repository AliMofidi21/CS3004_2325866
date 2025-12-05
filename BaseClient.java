package client;

import java.io.*;
import java.net.*;

public class BaseClient {

    protected PrintWriter out;
    protected BufferedReader in;
    protected BufferedReader userInput;

    public void connect() throws IOException {
        Socket socket = new Socket("localhost", 5000);
        out = new PrintWriter(socket.getOutputStream(), true);
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connected to Warehouse Server!");
    }

    public void interactionLoop() throws IOException {

        while (true) {
            System.out.print("Enter command: ");
            String command = userInput.readLine();

            if (command == null || command.isEmpty()) continue;

            if (command.equalsIgnoreCase("EXIT")) {
                System.out.println("Closing client.");
                break;
            }

            // send to server
            out.println(command);

            // read server response
            String response = in.readLine();
            System.out.println("SERVER: " + response);
        }
    }
    
    public void sendIdentity(String id) {
        out.println("ID " + id);
    }

}
