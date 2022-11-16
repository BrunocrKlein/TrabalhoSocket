/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerPackage;

import ClientPackage.ClientClass;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.NORM_PRIORITY;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Bruno César Klein
 */
public class ServerClass implements Runnable {

	private int port, ClientName;

	public ServerClass(int port, int ClientName) {
		this.port = port;
		this.ClientName = ClientName;
	}

	@Override
	public void run() {
		int Control = 0;
		ServerSocket server = null;
		try {

			// server is listening on port 1234
			server = new ServerSocket(this.port);
			server.setReuseAddress(true);
			System.out.println(server);
			System.out.println("servidor criado");
			if (Control == 0) {
				Control++;
				ClientClass Client = new ClientClass(this.port, this.ClientName);
				Thread t3 = new Thread((Runnable) Client);
				t3.setPriority(NORM_PRIORITY);
				t3.start();
			}
			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();
				System.out.println("estou no loop");

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected"
						+ client.getInetAddress()
								.getHostAddress());

				// create a new thread object
				ClientHandler clientSock
						= new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {

		private final Socket clientSocket;

		// Constructor
		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			BufferedReader in = null;
			try {

				// get the outputstream of client
				out = new PrintWriter(
						clientSocket.getOutputStream(), true);

				// get the inputstream of client
				in = new BufferedReader(
						new InputStreamReader(
								clientSocket.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {

					// writing the received message from
					// client
					System.out.printf(
							" Sent from the client: %s\n",
							line);
					out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
