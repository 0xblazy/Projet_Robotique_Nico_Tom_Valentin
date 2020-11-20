package twister.behaviors;

import lejos.hardware.Battery;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;

/**
 * Behavior charge de quitter le programme lorsque l'on clique sur le bouton ESCAPE ou que la batterie est sous le seuil.
 * 
 * @author nicolas-carbonnier
 */
public class Quit extends ThreadBehavior {
	
	private EV3ColorSensor colorSensor;
	private Arbitrator arby;
	private float lowLevel;
	private boolean suppressed = false;
	
	/**
	 * Constructeur.
	 * 
	 * @param _colorSensor Capteur de couleur.
	 * @param _lowLevel Niveau de batterie seuil.
	 */
	public Quit(EV3ColorSensor _colorSensor, float _lowLevel) {
		this.colorSensor = _colorSensor;
		this.lowLevel = _lowLevel;
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
	 * Defini la methode de prise de controle.
	 * @return true 
	 */
	@Override
	public boolean takeControl(){
		return (Button.ESCAPE.isDown() || Battery.getBatteryCurrent() <= this.lowLevel);
	}

	@Override
	public void action() {
		this.suppressed = false;
		
		if (Battery.getBatteryCurrent() <= this.lowLevel) { // 2 bips si le programme s'arrête à cause de la batterie, un seul sinon
			Sound.twoBeeps();
		} else {
			Sound.beep();
		}
		
		this.colorSensor.close();
		this.thread.interrupt();
		
		if (arby != null) {
			arby.stop();
		}
		
		Motor.B.stop(true);
		Motor.C.stop(true);
		
		System.exit(0);
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}
}
