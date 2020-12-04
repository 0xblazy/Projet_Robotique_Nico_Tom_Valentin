package twister.threads;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.subsumption.Arbitrator;
import twister.behaviors.ThreadBehavior;
import twister.models.Board;
import twister.models.Parameters;
import twister.models.Robot;
import twister.models.communication.Recepteur;

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
			
			// Menu du robot après l'avoir calibre et cartographie
			System.out.println("Le robot est prêt pour jouer, que voulez vous faire ?\n"
					+ "RIGHT: Lancer la partie\n"
					+ "DOWN: Afficher le plateau\n"
					+ "LEFT: Quitter le programme");
			int choix = Button.waitForAnyPress();
			while (choix != Button.ID_RIGHT && choix != Button.ID_DOWN && choix != Button.ID_LEFT) {
				System.out.println("Choix invalide, veuillez le refaire");
				choix = Button.waitForAnyPress();
			}
			switch (choix) {
				// Lancement de la partie
				case Button.ID_RIGHT:
					this.robot.setX(4);
					this.robot.setY(0);
					this.robot.setDirection(Parameters.DOWN);
					Game game = new Game(this.robot, this, this.behaviors);
					try {
						game.start();
						
						synchronized (this) {
							try {
								this.wait();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								break;
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						Sound.buzz();
						
						this.colorSensor.close();
						game.interrupt();
						
						if (this.arby != null) {
							this.arby.stop();
						}
						
						Motor.B.stop(true);
						Motor.C.stop(true);
						
						Button.waitForAnyPress();
						System.exit(0);
					}
					break;
				// Affichage du plateau
				case Button.ID_DOWN:
					System.out.println(this.board);
					break;
				// Fin du programme
				case Button.ID_LEFT:
					run = false;
					break;
			}
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
				+ "  BAS : Type 2\n"
				+ "  GAUCHE: EN ATTENTE DE RECEPTION\n");
		
		int check = Button.waitForAnyPress();
		switch (check) {
			case Button.ID_UP: 
				System.out.println("Type 1 choisi");
				return new Cartography(this.robot, this);	
			case Button.ID_DOWN:
				System.out.println("Type 2 choisi");
				return null;				
			case Button.ID_LEFT:
				System.out.println("Lancement du transfert MACHINALE");
				Recepteur.receptionJeu();
			default : 
				System.out.println("error");
				return null;
		}
	}
}
