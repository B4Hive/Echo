package br.ufjf.b4hive;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        // verifica se o usuário digitou os parâmetros corretos
        if (args.length != 1) {
            System.err.println("ERROR: java EchoClient <host>");
            System.exit(1);
        }

        String screenName = args[0];
        String host = args[0];
        int port = 4444;

        // conecta ao servidor e abre os streams
        Socket socket = new Socket(host, port);
        Scanner stdin = new Scanner(System.in);
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.err.println("Connected to " + host + " on port " + port + "\nType 'exit' to close the connection");

        System.err.print("[CLIENT]: ");
        while (stdin.hasNextLine()) {
            String s = stdin.nextLine();
            if (s.equalsIgnoreCase("exit")) {
                out.println(s);
                break;
            }
            out.println(s);
            String response = in.nextLine();
            System.out.println("[" + screenName + "]: " + response);
            System.err.print("[CLIENT]: ");
        }

        // encerra os sockets
        System.err.println("Closing connection to " + host);
        out.close();
        stdin.close();
        in.close();
        socket.close();
    }
}