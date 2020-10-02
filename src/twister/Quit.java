package twister;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Quit implements Behavior {
	
	private EV3ColorSensor colorSensor;
	private Arbitrator arby;
	
	// Constructor
	public Quit(EV3ColorSensor _colorSensor) {
		this.colorSensor = _colorSensor;
	}
	
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
