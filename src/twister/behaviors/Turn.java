package twister.behaviors;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import twister.models.Parameters;
import twister.models.Robot;

/**
 * Behaviors charge de faire tourner le robot de 90° vers la gauche ou la droite.
 * 
 * @author nicolas-carbonnier
 */
public class Turn implements Behavior {
	
	private Robot robot;
	private MovePilot pilot;
	private boolean suppressed = false;
	
	/**
	 * Constructeur.
	 * 
	 * @param _robot Robot.
	 * @param _pilot Pilote.
	 */
	public Turn(Robot _robot, MovePilot _pilot) {
		this.robot = _robot;
		this.pilot = _pilot;
	}

	@Override
	public boolean takeControl() {
		return (this.robot.turnLeft() || this.robot.turnRight());
	}

	@Override
	public void action() {
		this.suppressed = false;
		
		pilot.setLinearSpeed(Parameters.turnLinearSpeed);
		pilot.setAngularSpeed(Parameters.turnAngularSpeed);
		while(pilot.isMoving())Thread.yield();
		
		// A gauche si turnLeft, a droite sinon
		if (this.robot.turnLeft()) {			
			pilot.rotate(-90);
		} else {
			pilot.rotate(90);
		}
		while(pilot.isMoving())Thread.yield();

		// Repasse les booleens de rotation a faux
		if (this.robot.turnLeft()) this.robot.turnLeft(false);
		if (this.robot.turnRight()) this.robot.turnRight(false);
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
