package twister.behaviors;

import lejos.robotics.navigation.MovePilot;
import twister.models.Parameters;
import twister.models.Robot;

/**
 * Behaviors charge de faire tourner le robot de 90° vers la gauche ou la droite.
 * 
 * @author nicolas-carbonnier
 */
public class Turn extends ThreadBehavior {
	
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
		
		pilot.setLinearSpeed(Parameters.TURN_LINEAR_SPEED);
		pilot.setAngularSpeed(Parameters.TURN_ANGULAR_SPEED);
		while(pilot.isMoving())Thread.yield();
		
		// Si le Robot doit tourner a gauche, sinon si il doit tourner a Droite
		if (this.robot.turnLeft()) {
			//System.out.println("Gauche");
			pilot.rotate(-90);
			
			// Recule pour compenser la rotation
			pilot.setLinearSpeed(Parameters.MOVE_LINEAR_SPEED);
			pilot.setAngularSpeed(Parameters.MOVE_ANGULAR_SPEED);
			pilot.travel(-Parameters.SIZE);
			// Définition de la nouvelle direction
			switch (this.robot.getDirection()) {
				case Parameters.UP:
					this.robot.setDirection(Parameters.LEFT);
					break;
				case Parameters.DOWN:
					this.robot.setDirection(Parameters.RIGHT);
					break;
				case Parameters.RIGHT:
					this.robot.setDirection(Parameters.UP);
					break;
				case Parameters.LEFT:
					this.robot.setDirection(Parameters.DOWN);
					break;
			}
		} else if (this.robot.turnRight()) {
			//System.out.println("Droite");
			// Avance d'une case pour compenser la rotation
			pilot.setLinearSpeed(Parameters.MOVE_LINEAR_SPEED);
			pilot.setAngularSpeed(Parameters.MOVE_ANGULAR_SPEED);
			pilot.travel(Parameters.SIZE);
			
			pilot.setLinearSpeed(Parameters.TURN_LINEAR_SPEED);
			pilot.setAngularSpeed(Parameters.TURN_ANGULAR_SPEED);
			pilot.rotate(80);
			// Définition de la nouvelle direction
			switch (this.robot.getDirection()) {
				case Parameters.UP:
					this.robot.setDirection(Parameters.RIGHT);
					break;
				case Parameters.DOWN:
					this.robot.setDirection(Parameters.LEFT);
					break;
				case Parameters.RIGHT:
					this.robot.setDirection(Parameters.DOWN);
					break;
				case Parameters.LEFT:
					this.robot.setDirection(Parameters.UP);
					break;
			}
		}
		while(pilot.isMoving())Thread.yield();

		// Repasse les booleens de rotation a faux
		if (this.robot.turnLeft()) this.robot.turnLeft(false);
		if (this.robot.turnRight()) this.robot.turnRight(false);
		
		if (this.thread != null) {
			//System.out.println("Thread present");
			synchronized (this.thread) {
				//System.out.println("Thread notifie");
				this.thread.notify();
			}
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
