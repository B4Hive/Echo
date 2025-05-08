package br.ufjf.b4hive;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        InetAddress ipAddress = InetAddress.getLocalHost();
        String ip = ipAddress.getHostAddress();
        int port = 4444;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.err.println("Started server on " + ip + ":" + port + "\nPress CTRL+C to stop the server");
            boolean running = true;
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Accepted connection from client");
                try (Scanner in = new Scanner(clientSocket.getInputStream())) {
                    PrintWriter out;
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    String s;
                    while (in.hasNextLine()) {
                        s = in.nextLine().trim();
                        if (s.equalsIgnoreCase("exit")) {
                            running = false;
                            break;
                        } else {
                            out.println(s.toUpperCase());
                        }
                    }
                    System.err.println("Closing connection with client");
                    out.close();
                }
            }
        }
        System.err.println("Closing server");
    }
}