package br.ufjf.b4hive;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("ERROR: java EchoClient <host>");
            System.exit(1);
        }

        String screenName = args[0];
        String host = args[0];
        int port = 4444;

        try (Socket socket = new Socket(host, port)) {
            Scanner in;
            try (Scanner stdin = new Scanner(System.in)) {
                in = new Scanner(socket.getInputStream());
                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
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
                    System.err.println("Closing connection to " + host);
                }
            }
            in.close();
        }
    }
}