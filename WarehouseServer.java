package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.ReentrantLock;
import common.Protocol;

public class WarehouseServer {

    // warehouse stock variables
    private static int apples = 1000;
    private static int oranges = 1000;
    private String clientId = "UNKNOWN";
    // lock to protect stock updates
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            System.out.println("Warehouse Server running on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // create a new handler thread
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===================
    //  Inner Thread Class
    // ===================
    static class ClientHandler extends Thread {
        private Socket socket;
        private String clientId = "UNKNOWN";  // identity for logging

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request;

                // FIRST MESSAGE SHOULD IDENTIFY CLIENT
                request = in.readLine();
                if (request != null && request.startsWith("ID")) {
                    clientId = request.substring(3);   // e.g., "A", "B", "SUPPLIER"
                    System.out.println("Client identified as: " + clientId);
                }

                // Handle all future messages
                while ((request = in.readLine()) != null) {
                    System.out.println("[" + clientId + "] Received: " + request);

                    String response = processRequest(request);
                    out.println(response);
                }

            } catch (IOException e) {
                System.out.println("Client (" + clientId + ") disconnected.");
            }
        }

        private String processRequest(String req) {

            String[] parts = req.split(" ");
            String cmd = parts[0];

            lock.lock();  // protect apples & oranges
            try {
                switch (cmd) {

                    case Protocol.CHECK_STOCK:
                        return Protocol.stockMessage(apples , oranges );

                    case Protocol.BUY_APPLES:
                        int ba = Integer.parseInt(parts[1]);

                        if (ba <= 0) {
                            return "ERROR Amount must be positive";
                        }

                        if (ba > apples) {
                            return "ERROR Not enough apples in stock (" + apples + " available)";
                        }

                        apples -= ba;
                        return "OK BUY_APPLES " + ba;

                    case Protocol.BUY_ORANGES:
                        int bo = Integer.parseInt(parts[1]);

                        if (bo <= 0) {
                            return "ERROR Amount must be positive";
                        }

                        if (bo > oranges) {
                            return "ERROR Not enough oranges in stock (" + oranges + " available)";
                        }

                        oranges -= bo;
                        return "OK BUY_ORANGES " + bo;

                    case Protocol.ADD_APPLES:
                        int aa = Integer.parseInt(parts[1]);

                        if (aa <= 0) {
                            return "ERROR Amount must be positive";
                        }

                        apples += aa;
                        return "OK ADD_APPLES " + aa;

                    case Protocol.ADD_ORANGES:
                        int ao = Integer.parseInt(parts[1]);

                        if (ao <= 0) {
                            return "ERROR Amount must be positive";
                        }

                        oranges += ao;
                        return "OK ADD_ORANGES " + ao;

                    default:
                        return "ERROR Unknown command";
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
