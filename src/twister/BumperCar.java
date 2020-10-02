package twister;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BumperCar {

	public static void main(String[] args) {
		System.out.println("Appuyez pour demarrer");
		Button.waitForAnyPress();
		
		// Sensor initialization
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
		colorSensor.setFloodlight(Color.WHITE);
		float[] sample = new float[3];
		
		// Behaviors definition
		Behavior colorDetector = new ColorDetector(colorSensor, sample, 0);
		Behavior quit = new Quit(colorSensor);
		
		Behavior[] behaviors = {
				colorDetector,
				quit
			};
		
		// Arbitrator definition
		Arbitrator arby = new Arbitrator(behaviors);
		((Quit) quit).setArby(arby);
		arby.go();
	}

}
