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
public class Move extends ThreadBehavior {
	
	private Robot robot;
	private MovePilot pilot;
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
			//System.out.println("Avance");
			pilot.travel(Parameters.SIZE);
			// Définition des nouvelles coordonnées
			switch (this.robot.getDirection()) {
				case Parameters.UP:
					this.robot.setY(this.robot.getY() - 1);
					break;
				case Parameters.DOWN:
					this.robot.setY(this.robot.getY() + 1);
					break;
				case Parameters.RIGHT:
					this.robot.setX(this.robot.getX() + 1);
					break;
				case Parameters.LEFT:
					this.robot.setX(this.robot.getX() - 1);
					break;
			}
		} else {
			//System.out.println("Recule");
			pilot.travel(-Parameters.SIZE);
			// Définition des nouvelles coordonnées
			switch (this.robot.getDirection()) {
				case Parameters.UP:
					this.robot.setY(this.robot.getY() + 1);
					break;
				case Parameters.DOWN:
					this.robot.setY(this.robot.getY() - 1);
					break;
				case Parameters.RIGHT:
					this.robot.setX(this.robot.getX() - 1);
					break;
				case Parameters.LEFT:
					this.robot.setX(this.robot.getX() + 1);
					break;
			}
		}
		while(pilot.isMoving()) Thread.yield();
		
		// Repasse les booleens de deplacement a faux
		if (this.robot.moveForward()) this.robot.moveForward(false);
		if (this.robot.moveBackward()) this.robot.moveBackward(false);
		
		if (this.thread != null) {
			System.out.println("Thread present");
			synchronized (this.thread) {
				System.out.println("Thread notifie");
				this.thread.notify();
			}
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
