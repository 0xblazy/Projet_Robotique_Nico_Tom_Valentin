package twister.behaviors;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Behavior charge de quitter le programme lorsque l'on clique sur le bouton ESCAPE ou que la batterie est sous le seuil.
 * 
 * @author nicolas-carbonnier
 */
public class Quit implements Behavior {
	
	private EV3ColorSensor colorSensor;
	private Arbitrator arby;
	private int nb;
	
	/**
	 * Constructeur.
	 * 
	 * @param _colorSensor Capteur de couleur.
	 * @param _nb Niveau de batterie seuil.
	 */
	public Quit(EV3ColorSensor _colorSensor, int _nb) {
		this.colorSensor = _colorSensor;
		this.nb = _nb;
	}
	
	/**
	 * Defini l'Arbitrator.
	 * 
	 * @param _arby Arbitrator.
	 */
	public void setArby(Arbitrator _arby) {
		this.arby = _arby;
	}

	/**
	 * Defini la methode d'arret
	 * @return true 
	 */
	@Override
	public boolean takeControl(){
		return ((int)(Battery.getBatteryCurrent() * 100)) <= this.nb || Button.ESCAPE.isDown();
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
