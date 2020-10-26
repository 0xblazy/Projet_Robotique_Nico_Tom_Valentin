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
	private Thread thread;
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
	
	public void setThread(Thread _thread) {
		this.thread = _thread;
	}

	@Override
	public boolean takeControl() {
		return (this.robot.turnLeft() || this.robot.turnRight());
	}

	@Override
	public void action() {
		this.suppressed = false;
		
		pilot.setLinearSpeed(Parameters.TURN_LINEAR_SPEED);
		pilot.setAngularSpeed(Parameters.TURN_ANGULAR_SPEED);
		while(pilot.isMoving())Thread.yield();
		
		// A gauche si turnLeft, a droite sinon
		if (this.robot.turnLeft()) {
			System.out.println("Gauche");
			pilot.rotate(-80);
		} else {
			System.out.println("Droite");
			pilot.rotate(80);
		}
		while(pilot.isMoving())Thread.yield();

		// Repasse les booleens de rotation a faux
		if (this.robot.turnLeft()) this.robot.turnLeft(false);
		if (this.robot.turnRight()) this.robot.turnRight(false);
		
		if (this.thread != null) {
			System.out.println("Thread present");
			synchronized (this.thread) {
				System.out.println("Thread notifie");
				thread.notify();
			}
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
