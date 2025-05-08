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
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on " + ip +":"+ port + "\nPress CTRL+C to stop the server");

        boolean running = true;
        while (running) {
            // espera blocante até alguma requisição de conexão
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            // Cria as “streams” para o socket (buffer)
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // espera a leitura do dado (até terminar conexão)
            String s;
            while (in.hasNextLine()) {
                s = in.nextLine().trim();
                // Se o cliente enviar "exit", fecha a conexão
                if (s.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                } else {
                    out.println(s.toUpperCase());
                }
            }

            // Fecha a conexão (e o socket)
            System.err.println("Closing connection with client");
            out.close();
            in.close();
        }
        System.err.println("Closing server");
    }
}