/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClientPackage;

/**
 *
 * @author Bruno César Klein
 */
import ServerPackage.ServerClass;
import java.io.*;
import static java.lang.Thread.MAX_PRIORITY;
import java.net.*;
import java.util.*;

// Client class
public class ClientClass implements Runnable{

	private int port, ClientName;

	public ClientClass(int port, int  ClientName) {
		this.port = port;
		this.ClientName = ClientName; 
	}
	
	public String[] searchServers() {
		int[] gateway = {192, 168, 0, 1};
		int[] mask = {255, 255, 255, 0};
		String[] ipAndPort = {};
		for (int i = 2; i < 255; i++) {
			for (int x = 5000; x <= 10000; x++) {
				try {
					Socket socket = new Socket(gateway[0] + "." + gateway[1] + "." + gateway[2] + "." + i, x);
					ipAndPort[0] = gateway[0] + "." + gateway[1] + "." + gateway[2] + "." + i;
					ipAndPort[1] = x + "";
					socket.close();
					return ipAndPort;
				} catch (IOException ex) {

				}
			}

		}
		ipAndPort[0] = "0";
		return null;
	}
	

	public void run() {
		String[] Response = searchServers();
		String ip = "localhost";
		if(!Response[0].equals("0")){
			ip = Response[0];
			this.port = Integer.parseInt(Response[1]);
		}
		
		// establish a connection by providing host and port
		// number
		System.out.println("chamando porta : " + port);
		try ( Socket socket = new Socket(ip, this.port)) {
			System.out.println("cliente criado num " + this.ClientName);
			// writing to server
			PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);

			// reading from server
			BufferedReader in
				= new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// object of scanner class
			Scanner sc = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {

				// reading from user
				line = sc.nextLine();

				// sending the user input to server
				out.println(line);
				out.flush();

				// displaying server reply
				System.out.println("Server replied "
					+ in.readLine());
			}

			// closing the scanner object
			sc.close();
		} catch (IOException e) {
			ServerClass Server = new ServerClass(this.port, this.ClientName);
			Thread t2 =  new Thread((Runnable) Server); 
			t2.setPriority(MAX_PRIORITY);
			System.out.println("chamando servidor");
			t2.start();
			//e.printStackTrace();
		}
	}
	
}
