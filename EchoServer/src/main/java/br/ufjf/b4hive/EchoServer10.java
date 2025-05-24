package br.ufjf.b4hive;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

@SuppressWarnings("ConvertToTryWithResources")
public class EchoServer10 {

    private static final int MAX_CLIENTS = 10;
    private static final Semaphore semaphore = new Semaphore(MAX_CLIENTS);

    public static void main(String[] args) throws Exception {
        InetAddress ipAddress = InetAddress.getLocalHost();
        String ip = ipAddress.getHostAddress();
        int port = 4444;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Started server on " + ip + ":" + port);
        System.out.println("Type 'quit' to stop the server.");

        Thread commandThread = new Thread(EchoServer10::handleCommands);
        commandThread.start();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            if (semaphore.tryAcquire()) {
                System.out.println("Accepted connection from client");
                Thread thread = new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } finally {
                        semaphore.release(); // libera a vaga ao terminar
                    }
                });
                thread.start();
            } else {
                System.out.println("Rejected connection - max clients reached");
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    out.println("ERROR: Maximum number of clients (10) reached. Try again later.");
                } catch (IOException ignored) {}
                clientSocket.close();
            }
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            Scanner incoming = new Scanner(clientSocket.getInputStream());
            PrintWriter outgoing = new PrintWriter(clientSocket.getOutputStream(), true);
            String s;
            while (incoming.hasNextLine()) {
                s = incoming.nextLine().trim();
                String[] parts = s.split("]:");
                s = parts.length > 1 ? parts[1].trim() : s;
                if (s.equalsIgnoreCase("quit")) {
                    System.out.println("Client disconnected");
                    break;
                } else {
                    if (s.toLowerCase().startsWith("echo "))
                        outgoing.println(s.substring(5).toUpperCase());
                    else
                        outgoing.println("ERROR: Use 'echo <message>' to send a message");
                }
            }
            System.out.println("Closing connection with client");
            incoming.close();
            outgoing.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void handleCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Stopping server...");
                System.exit(0);
            } else {
                System.err.println("Unknown command: " + command);
            }
        }
    }
}
