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
		System.out.println("Type 'exit' to stop the server.");

		Thread commandThread = new Thread(() -> handleCommands());
		commandThread.start();
        boolean running = true;
        while (running) {
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            Thread thread = new Thread(() -> handleClient(clientSocket));
            thread.start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String s;
            while (in.hasNextLine()) {
                s = in.nextLine().trim();
                out.println(s.toUpperCase());
            }
            System.err.println("Closing connection with client");
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

	private static void handleCommands() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String command = scanner.nextLine();
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("Stopping server...");
				System.exit(0);
				break;
			} else {
				System.out.println("Unknown command: " + command);
			}
		}
		scanner.close();
	}
}
