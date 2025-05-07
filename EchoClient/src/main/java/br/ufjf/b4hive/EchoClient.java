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
        // lê da entrada padrão stdin, envia, escreve resposta
        while (stdin.hasNextLine()) {
            // leitura
            String s = stdin.nextLine();
            // verifica se o usuário digitou "exit" para encerrar o programa
            if (s.equalsIgnoreCase("exit")) {
                break;
            }
            // envio pelo socket
            out.println("[" + screenName + "]: " + s);
            // pega resposta
            System.out.println(in.nextLine());
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