package br.ufjf.b4hive;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		InetAddress ipAddress = InetAddress.getLocalHost();
		String ip = ipAddress.getHostAddress();
		int port = 4444;
		@SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Started server on " + ip + ":" + port + "\nPress CTRL+C to stop the server");
		boolean running = true;
		while (running) {
			Socket clientSocket = serverSocket.accept();
			System.err.println("Accepted connection from client");
			EchoThread thread = new EchoThread(clientSocket);
			thread.start();
		}
	}
	
}