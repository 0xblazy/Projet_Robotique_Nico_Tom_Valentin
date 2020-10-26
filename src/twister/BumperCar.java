package twister;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import twister.behaviors.ColorDetector;
import twister.behaviors.Move;
import twister.behaviors.Quit;
import twister.behaviors.Turn;
import twister.cartography.Cartography;
import twister.models.Parameters;
import twister.models.Plateau;
import twister.models.Robot;

/**
 * Classe main du projet.
 * 
 * @author nicolas-carbonnier
 */
public class BumperCar {

	/**
	 * Appelee lors du lancement du programme.
	 * 
	 * @param args Arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		System.out.println("Appuyez pour demarrer");
		System.out.println("Batterie : " + Battery.getBatteryCurrent());
		Button.waitForAnyPress();
		
		// Initialisation du robot
		MovePilot pilot = new MovePilot(new WheeledChassis(new Wheel[] {
				WheeledChassis.modelWheel(Motor.B, Parameters.WHEEL_DIAMETER).offset(-Parameters.WHEEL_OFFSET),
				WheeledChassis.modelWheel(Motor.C, Parameters.WHEEL_DIAMETER).offset(Parameters.WHEEL_OFFSET)
		}, 2));
		Robot robot = new Robot(0, 4);
		
		// Initialisation du plateau
		Plateau board = new Plateau();
		
		// Initialisation des capteurs
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		colorSensor.setFloodlight(Color.WHITE);
		float[] sample = new float[3];
		
		// Initialisation de la cartographie
		Cartography cartography = new Cartography(robot, pilot);
		
		// Definition des Behavior
		Behavior move = new Move(robot, pilot);
		Behavior turn = new Turn(robot, pilot);
		Behavior colorDetector = new ColorDetector(colorSensor, sample, 0);
		Behavior quit = new Quit(colorSensor, 0.05f);
		
		Behavior[] behaviors = {
				move,
				turn,
				colorDetector,
				quit
		};
		
		choixCarto();
		
		System.out.println("cartographie choisie, appuyez pour continuer");
		Button.waitForAnyPress();
		
		// Definition de l'Arbitrator
		Arbitrator arby = new Arbitrator(behaviors);
		((Quit) quit).setArby(arby);
		
		// Lancement de l'Arbitrator et coupure du programme en cas d'erreur
		try {
			((Move) move).setThread(cartography);
			((Turn) turn).setThread(cartography);
			((ColorDetector) colorDetector).setThread(cartography);
			((Quit) quit).setThread(cartography);
			cartography.start();
			arby.go();			
		} catch (Exception e) {
			e.printStackTrace();
			Sound.buzz();
			
			colorSensor.close();
			cartography.interrupt();
			
			if (arby != null) {
				arby.stop();
			}
			
			Button.waitForAnyPress();
			System.exit(0);
		}
	}
	
	
	public static void choixCarto() {
		System.out.println("Choix de Cartographie:" + "\n" + "Boutton du haut : Type 1" + "\n" + "Boutton du bas : Type 2");
		
		int check = Button.waitForAnyPress();
		switch (check) {
		
		case Button.ID_UP: 
			System.out.println("Type 1 choisi");
			//TODO : init carto 1
			break;
			
		case Button.ID_DOWN:
			System.out.println("Type 2 choisi");
			//TODO : init carto 2
			break;
			
		default : 
			System.out.println("pute");

}

}
	

}
