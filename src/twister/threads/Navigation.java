package twister.threads;

import twister.models.Board;
import twister.models.Parameters;
import twister.models.Robot;

/**
 * Thread utilise pour la navigation
 * 
 * @author Aetra
 * @author nicolas-carbonnier
 */
public class Navigation extends Thread {
	/**
	 * Robot utilise pour la navigation.
	 */
	private Robot robot;
	/**
	 * Game qui lance la navigation.
	 */
	private Game game;
	/**
	 * Plateau pour la navigation.
	 */
	private Board board;
	
	/**
	 * Couleur de destination.
	 */
	private int color;
	
	/**
	 * Constructeur.
	 * 
	 * @param _robot Robot utilise par la navigation.
	 * @param _board Plateau pour la navigation.
	 */
	public Navigation(Robot _robot, Board _board, Game _game, int _color) {
		this.robot = _robot;
		this.board = _board;
		this.game = _game;
		this.color = _color;
	}
	
	@Override
	public void run() {
		int[] destPos = this.board.caseLaPlusProche(this.robot, this.color);
		int xRobot = this.robot.getX();
		int yRobot = this.robot.getY();
		System.out.println("Destination: " + destPos[0] + ", " + destPos[1]);
		
		if (xRobot == destPos[0] && yRobot == destPos[1]) { // Robot déjà sur la case
			System.out.println("Le Robot est déjà sur la case !!!");
		} else {
			if (xRobot > destPos[0]) {
				while (this.robot.getDirection() != Parameters.LEFT) {
					this.robot.turnRight(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
				
				int valDest = Math.abs(xRobot - destPos[0]); 
				this.robot.setNbCases(valDest);
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
			else if (xRobot < destPos[0]) {
				while (this.robot.getDirection() != Parameters.RIGHT) {
					this.robot.turnRight(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
				
				int valDest = Math.abs(xRobot - destPos[0]); 
				this.robot.setNbCases(valDest);
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
			if (yRobot > destPos[1]) {
				while (this.robot.getDirection() != Parameters.UP) {
					this.robot.turnRight(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
				
				int valDest = Math.abs(yRobot - destPos[1]); 
				this.robot.setNbCases(valDest);
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}		
			}
			else if (yRobot < destPos[1]) {
				while (this.robot.getDirection() != Parameters.DOWN) {
					this.robot.turnRight(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				}
				
				int valDest = Math.abs(yRobot - destPos[1]); 
				this.robot.setNbCases(valDest);
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}	
			}
		}
	
		if (this.game != null) {
			//System.out.println("Thread present");
			synchronized (this.game) {
				//System.out.println("Thread notifie");
				this.game.notify();
			}
		}
	}
}
