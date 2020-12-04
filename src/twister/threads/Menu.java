package twister.threads;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;
import twister.behaviors.ThreadBehavior;
import twister.models.Board;
import twister.models.Robot;
/**
 * Thread utilise pour le menu.
 * Lance les autres Threads.
 * 
 * @author nicolas-carbonnier
 * @author TomySchef54
 *
 */
public class Menu extends Thread {
	private ThreadBehavior[] behaviors;
	private Arbitrator arby;
	private Board board;
	private Robot robot;
	private EV3ColorSensor colorSensor;
	private boolean run;
	
	/**
	 * Constructeur.
	 * 
	 * @param _behaviors Tableau des behaviors contenus dans l'Arbitrator.
	 * @param _arby Arbitrator.
	 * @param _board Board sur lequel est le Robot.
	 * @param _robot Robot.
	 * @param _colorSensor Capteur de couleur.
	 */
	public Menu(ThreadBehavior[] _behaviors, Arbitrator _arby, Board _board, Robot _robot, EV3ColorSensor _colorSensor) {
		this.behaviors = _behaviors;
		this.arby = _arby;
		this.board = _board;
		this.robot = _robot;
		this.colorSensor = _colorSensor;
	}
	
	@Override
	public void run() {
		this.run = true;
		//System.out.println("Menu lance");
		
		while(this.run) {
			
			/*// Test deplacement plusieurs cases
			for (ThreadBehavior behavior : this.behaviors) {
				behavior.setThread(this);
			}
			
			for (int i = 0 ; i < 4 ; i++) {
				System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());
				this.robot.setNbCases(3);
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
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
			System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());*/
			
			
			// Tant que les couleurs du Robot ne sont pas calibrees
			while (!this.robot.isColorCalibrated()) {
				System.out.println("Le robot n'est pas encore calibre");
				ColorCalibration colorCalibration = new ColorCalibration(this.robot, this, 5);
				try {
					for (ThreadBehavior behavior : this.behaviors) {
						behavior.setThread(colorCalibration);
					}
					colorCalibration.start();
					
					// Attend la fin de la calibration
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					//System.out.println("bar");
					if (this.robot.isColorCalibrated()) {
						colorCalibration.interrupt();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					Sound.buzz();
					
					this.colorSensor.close();
					colorCalibration.interrupt();
					
					if (this.arby != null) {
						this.arby.stop();
					}
					
					Button.waitForAnyPress();
					System.exit(0);
				}
			}
			
			// Tant que le Board n'est pas cartographie
			while (!this.board.isCartographed()) {
				
				// Choix de la cartographie
				Cartography cartography = this.choixCarto();
				if (cartography != null) {
					try {
						for (ThreadBehavior behavior : this.behaviors) {
							behavior.setThread(cartography);
						}
						cartography.start();
						//System.out.println("Test");
						
						// Attend la fin de la cartographie
						synchronized (this) {
							try {
								this.wait();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								break;
							}
						}
						//System.out.println("bar");
						//System.out.println(board.isCartographed());
						if (this.board.isCartographed()) {
							cartography.interrupt();
							
							Motor.B.stop(true);
							Motor.C.stop(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
						Sound.buzz();
						
						this.colorSensor.close();
						cartography.interrupt();
						
						if (this.arby != null) {
							this.arby.stop();
						}
						
						Motor.B.stop(true);
						Motor.C.stop(true);
						
						Button.waitForAnyPress();
						System.exit(0);
					}
				} else {
					System.out.println("Aucune cartographie selectionnee, appuyez pour continuer");
					Button.waitForAnyPress();
				}
			}
			
			this.run = false;
		}
		
		System.out.println("Fin du programme, appuyez sur ESCAPE pour sortir");
		Button.ESCAPE.waitForPress();
	}
	
	/**
	 * Menu de selection du type de Cartographie.
	 * 
	 * @return Cartographie choisie.
	 */
	private Cartography choixCarto() {
		System.out.println("Choix de Cartographie :\n"
				+ "  HAUT : Type 1\n"
				+ "  BAS : Type 2 ");
		
		int check = Button.waitForAnyPress();
		switch (check) {
			case Button.ID_UP: 
				System.out.println("Type 1 choisi");
				return new Cartography(this.robot, this);	
			case Button.ID_DOWN:
				System.out.println("Type 2 choisi");
				return null;	
			default : 
				System.out.println("error");
				return null;
		}
	}
}
