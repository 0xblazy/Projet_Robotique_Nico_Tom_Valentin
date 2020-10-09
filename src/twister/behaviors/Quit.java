package twister.behaviors;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Behavior charge de quitter le programme lorsque l'on clique sur le bouton ESCAPE.
 * 
 * @author nicolas-carbonnier
 */
public class Quit implements Behavior {
	
	private EV3ColorSensor colorSensor;
	private Arbitrator arby;
	
	/**
	 * Constructeur.
	 * 
	 * @param _colorSensor Capteur de couleur.
	 */
	public Quit(EV3ColorSensor _colorSensor) {
		this.colorSensor = _colorSensor;
	}
	
	/**
	 * Defini l'Arbitrator.
	 * 
	 * @param _arby Arbitrator.
	 */
	public void setArby(Arbitrator _arby) {
		this.arby = _arby;
	}

	@Override
	public boolean takeControl(){
		return Button.ESCAPE.isDown();
	}

	@Override
	public void action() {
		colorSensor.close();
		
		if (arby != null) {
			arby.stop();
		}
		
		System.exit(0);
	}

	@Override
	public void suppress() {}

}
