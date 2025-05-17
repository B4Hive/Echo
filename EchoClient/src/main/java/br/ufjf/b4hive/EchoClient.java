package br.ufjf.b4hive;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    @SuppressWarnings({"resource", "ConvertToTryWithResources"})
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("ERROR: java EchoClient.java <host>");
            //System.exit(1);
            Scanner scanner = new Scanner(System.in);
            //add verification to check if the input is a valid IP address and loop until it is
            args[0] = scanner.nextLine();
            scanner.close();
        }

        String screenName = args[0];
        String host = args[0];
        int port = 4444;

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
            in.close();
        }
        System.err.println("Closing connection to " + host);
    }
}