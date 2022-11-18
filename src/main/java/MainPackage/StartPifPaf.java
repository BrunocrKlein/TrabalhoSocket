package MainPackage;


import ClientPackage.ClientClass;
import static InterfacePackage.ScreenPifPaf.identificador;
import static java.lang.Thread.NORM_PRIORITY;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Bruno César Klein
 */
public class StartPifPaf {

	public static int identificador = 0;

	public static void main(String[] args) {

		identificador += 1;
		ClientClass Client = new ClientClass(5012, identificador);
		Thread t4 = new Thread((Runnable) Client);
		t4.setPriority(NORM_PRIORITY);
		t4.start();

	}

}
