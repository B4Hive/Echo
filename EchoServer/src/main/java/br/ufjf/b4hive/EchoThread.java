package br.ufjf.b4hive;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoThread extends Thread {
	private final Socket clientSocket;

	public EchoThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	@SuppressWarnings("ConvertToTryWithResources")
	public void run() {
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

}
