package twister;

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
import twister.behaviors.ColorDetector;
import twister.behaviors.Move;
import twister.behaviors.Quit;
import twister.behaviors.ThreadBehavior;
import twister.behaviors.Turn;
import twister.models.Board;
import twister.models.Parameters;
import twister.models.Robot;
import twister.threads.Menu;

/**
 * Classe main du projet.
 * 
 * @author nicolas-carbonnier
 * @author Aetra
 * @author TomySchef54
 */
public class BumperCar {

	/**
	 * Appelee lors du lancement du programme.
	 * 
	 * @param args Arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		//System.out.println("Appuyez pour demarrer");
		//System.out.println("Batterie : " + Battery.getBatteryCurrent());
		//Button.waitForAnyPress();
		
		// Initialisation du plateau
		Board board = new Board();
		
		// Initialisation du robot
		MovePilot pilot = new MovePilot(new WheeledChassis(new Wheel[] {
				WheeledChassis.modelWheel(Motor.B, Parameters.WHEEL_DIAMETER).offset(-Parameters.WHEEL_OFFSET),
				WheeledChassis.modelWheel(Motor.C, Parameters.WHEEL_DIAMETER).offset(Parameters.WHEEL_OFFSET)
		}, 2));
		Robot robot = new Robot(4, 0, Parameters.DOWN, board);
		
		// Initialisation des capteurs
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		colorSensor.setFloodlight(Color.WHITE);
		float[] sample = new float[3];
		
		// Definition des Behavior
		ThreadBehavior move = new Move(robot, pilot);
		ThreadBehavior turn = new Turn(robot, pilot);
		ThreadBehavior colorDetector = new ColorDetector(robot, colorSensor, sample, 0);
		ThreadBehavior quit = new Quit(colorSensor, 0.05f);
		
		ThreadBehavior[] behaviors = {
				move,
				turn,
				colorDetector,
				quit
		};
		
		// Definition de l'Arbitrator
		Arbitrator arby = new Arbitrator(behaviors);
		((Quit) quit).setArby(arby);
		
		// Initialisation du Menu
		Menu menu = new Menu(behaviors, arby, board, robot, colorSensor);
		
		// Lancement du Menu et de l'Arbitrator et coupure du programme en cas d'erreur
		try {
			menu.start();
			arby.go();
		} catch (Exception e) {
			e.printStackTrace();
			Sound.buzz();
			
			colorSensor.close();
			menu.interrupt();
			
			if (arby != null) {
				arby.stop();
			}
			
			Motor.B.stop(true);
			Motor.C.stop(true);
			
			Button.waitForAnyPress();
			System.exit(0);
		}
	}
}
