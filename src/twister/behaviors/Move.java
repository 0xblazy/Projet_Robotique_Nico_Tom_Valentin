package twister.behaviors;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import twister.models.Parameters;
import twister.models.Robot;

/**
 * Behavior charge de faire avancer ou reculer le robot d'une case.
 * 
 * @author nicolas-carbonnier
 */
public class Move implements Behavior {
	
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
	public Move(Robot _robot, MovePilot _pilot) {
		this.robot = _robot;
		this.pilot = _pilot;
	}
	
	public void setThread(Thread _thread) {
		this.thread = _thread;
	}

	@Override
	public boolean takeControl() {
		return (this.robot.moveForward() || this.robot.moveBackward());
	}

	@Override
	public void action() {
		this.suppressed = false;
		
		pilot.setLinearSpeed(Parameters.MOVE_LINEAR_SPEED);
		pilot.setAngularSpeed(Parameters.MOVE_ANGULAR_SPEED);
		
		// En avant si moveForward, en arriere sinon
		if (this.robot.moveForward()) {
			System.out.println("Avance");
			pilot.travel(Parameters.SIZE);
		} else {
			System.out.println("Recule");
			pilot.travel(-Parameters.SIZE);
		}
		while(pilot.isMoving()) Thread.yield();
		
		// Repasse les booleens de deplacement a faux
		if (this.robot.moveForward()) this.robot.moveForward(false);
		if (this.robot.moveBackward()) this.robot.moveBackward(false);
		
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
