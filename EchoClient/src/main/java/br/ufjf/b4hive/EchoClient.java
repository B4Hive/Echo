package br.ufjf.b4hive;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    @SuppressWarnings({ "ConvertToTryWithResources" })
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("ERROR: java EchoClient.java <host>");
            // System.exit(1);
            Scanner scanner = new Scanner(System.in);
            // add verification to check if the input is a valid IP address and loop until
            // it is
            args[0] = scanner.nextLine();
            scanner.close();
        }

        String host = args[0];
        int port = 4444;
        Socket socket = new Socket(host, port);
        Scanner scanner = new Scanner(System.in);
        Scanner incoming = new Scanner(socket.getInputStream());
        PrintWriter outgoing = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Connected to " + host + " on port " + port + "\nType 'quit' to close the connection");
        System.out.print("[CLIENT]: ");
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            outgoing.println("[" + host + "]: " + s);
            if (s.equalsIgnoreCase("quit"))
                break;

            if (incoming.hasNextLine()) {
                String response = incoming.nextLine();
                System.out.println("[SERVER:" + host + "]: " + response);
            } else {
                System.out.println("[SERVER:" + host + "]: Connection closed by server.");
                break;
            }

            System.out.print("[CLIENT]: ");
        }

        incoming.close();
        scanner.close();
        outgoing.close();
        socket.close();
        System.out.println("Closing connection to " + host);
    }
}