package twister.models.communication;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import twister.models.ReglesJeu;
import twister.threads.Cartography;

/**
 * Classe emetteur du projet
 * @author val
 *
 */

public class Emetteur implements ReglesJeu{
	ResourceBundle bundle = ResourceBundle.getBundle("domaine.properties.config");
	String adresseRobot2 = bundle.getString("adresseCo");
	static String connected = "Connecté";
	String waiting = "En attente";
	
	public static void emettreCartographie(Cartography carto) {
		EV3 ev = LocalEV3.get();
		System.out.println("--"+ev.getName()+"--");
		Button.RIGHT.waitForPressAndRelease();
		try {
			
			//LCD.drawString(waiting, 0, 0);
			//LCD.refresh();
			//droite = 00:16:53:43:4E:26
			//gauche = 00:16:53:43:8E:49
			BTConnector bt = new BTConnector();
			BTConnection btc = bt.connect("00:16:53:43:9E:2F", NXTConnection.PACKET);//le premier paramètre est l'adresse du récepteur affiché sur l'écra de l'émetteur après association (pair) bluetooth

			LCD.clear();
			LCD.drawString(connected, 0, 0);
			LCD.refresh();
			Cartography cartoSend= carto;
			//InputStream is = btc.openInputStream();
			OutputStream os = btc.openOutputStream();
			//DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			System.out.println("\n\nEnvoi");
			writeObject(dos, cartoSend); // écrit une valeur dans le flux
			dos.flush(); // force lenvoi
			System.out.println("\nEnvoyé");
			//dis.close();
			dos.close();
			btc.close();
			LCD.clear();
	} catch (Exception e) {
	}
	}
	
    public static void writeObject(DataOutputStream dos, Object o) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(20000);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(bout));) {
            out.writeObject(o);
        }
        dos.writeInt(bout.size());
        dos.write(bout.toByteArray());
        dos.flush();
    }
	/** 
	 * Fonction permettant de se connecter
	 * @author val
	 */
	public static int emettreJeu() {

		EV3 ev = LocalEV3.get();
		System.out.println("--"+ev.getName()+"--");
		Button.RIGHT.waitForPressAndRelease();
		try {
			
			//LCD.drawString(waiting, 0, 0);
			//LCD.refresh();
			//droite = 00:16:53:43:4E:26
			//gauche = 00:16:53:43:8E:49
			BTConnector bt = new BTConnector();
			BTConnection btc = bt.connect("00:16:53:43:9E:2F", NXTConnection.PACKET);//le premier paramètre est l'adresse du récepteur affiché sur l'écra de l'émetteur après association (pair) bluetooth

			LCD.clear();
			LCD.drawString(connected, 0, 0);
			LCD.refresh();
			int colorSend = ReglesJeu.getRandom();
			//InputStream is = btc.openInputStream();
			OutputStream os = btc.openOutputStream();
			//DataInputStream dis = new DataInputStream(is);
			DataOutputStream dos = new DataOutputStream(os);
			System.out.println("\n\nEnvoi");
			dos.write(colorSend); // écrit une valeur dans le flux
			dos.flush(); // force lenvoi
			System.out.println("\nEnvoyé");
			//dis.close();
			dos.close();
			btc.close();
			LCD.clear();
			return colorSend;
			
		} catch (Exception e) {
			return 0;
		}
		
	}

	
	public static void main(String[] args) {
		int colorSens = emettreJeu();

	}
}
