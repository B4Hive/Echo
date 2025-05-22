package br.ufjf.b4hive;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("ConvertToTryWithResources")
public class EchoServer {

	public static void main(String[] args) throws Exception {
        InetAddress ipAddress = InetAddress.getLocalHost();
        String ip = ipAddress.getHostAddress();
        int port = 4444;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Started server on " + ip + ":" + port);
		System.out.println("Type 'quit' to stop the server.");

		Thread commandThread = new Thread(() -> handleCommands());
		commandThread.start();
        boolean running = true;
        while (running) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from client");

            Thread thread = new Thread(() -> handleClient(clientSocket));
            thread.start();
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
                s = parts[1].trim();
                if (s.equalsIgnoreCase("quit")) {
                    System.out.println("Client disconnected");
                    break;
                } else {
                    if(s.length() > 5 && s.substring(0, 5).equalsIgnoreCase("echo "))
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
				break;
			} else {
				System.err.println("Unknown command: " + command);
			}
		}
		scanner.close();
	}
}
