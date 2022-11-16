/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BroadCastPackage;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Bruno César Klein
 */
public class BroadCastClass implements Runnable {

	public static ArrayList<Socket> socketClients = new ArrayList<>();
	public String msg = " ";
	public Socket s;

	public BroadCastClass(String message, Socket socket) {
		this.s = socket;
		this.msg = message;
	}

	public ArrayList<Socket> getSocket() {
		return socketClients;
	}

	public void run() {
		try {
			//foreach loop
			for (Socket soc : socketClients) {
				if (soc != this.s) {
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					dos.writeUTF(msg);
					dos.flush();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
