package twister.models.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import twister.models.ReglesJeu;

/**
 * Classe recepteur du projet
 * @author val
 *
 */


public class Recepteur implements ReglesJeu {
	
	/**
	 * @author val
	 * Fonction de recepetion
	 */
	public static void receptionJeu() {
		// TODO Auto-generated method stub
		String connected = "Connecté";
		String waiting = "En attente";
		
		try {
			//LCD.drawString(waiting, 0, 0);
			//LCD.refresh();

			BTConnector bt = new BTConnector();
			NXTConnection btc = bt.waitForConnection(100000, NXTConnection.PACKET);

			if (btc !=null) {
			LCD.clear();
			LCD.drawString(connected, 0, 0);
			LCD.refresh();

			InputStream is = btc.openInputStream();
			//OutputStream os = btc.openOutputStream();
			DataInputStream dis = new DataInputStream(is);
			//DataOutputStream dos = new DataOutputStream(os);

			int valeur = dis.read();
			

			dis.close();
			//dos.close();
			btc.close();
			System.out.println(valeur);
			Button.RIGHT.waitForPressAndRelease();
			LCD.clear();
			} else {
				System.out.println("Pas de connexion");
				Button.RIGHT.waitForPressAndRelease();
			}
		} catch (Exception e) {
		}
	}
}