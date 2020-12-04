package twister.threads;

import lejos.hardware.Button;
import twister.behaviors.ThreadBehavior;
import twister.models.Parameters;
import twister.models.ReglesJeu;
import twister.models.Robot;

/**
 * Thread utilise par Game.
 * 
 * @author nicolas-carbonnier
 */
public class Game extends Thread {
	/**
	 * Robot utilise pour la partie.
	 */
	private Robot robot;
	/**
	 * Menu qui a appele Game.
	 */
	private Menu menu;
	
	/**
	 * Behaviors.
	 */
	private ThreadBehavior[] behaviors;
	
	/**
	 * Constructeur.
	 * 
	 * @param _robot Robot utilise pour la partie.
	 * @param _menu Menu qui a appele Game.
	 * @param _nav Navigation utilisee par le Robot.
	 */
	public Game(Robot _robot, Menu _menu, ThreadBehavior[] _behaviors) {
		this.robot = _robot;
		this.menu = _menu;
		this.behaviors = _behaviors;
	}
	
	@Override
	public void run() {
		System.out.println("Appuyez sur le bouton ENTER pour lancer la partie");
		Button.ENTER.waitForPress();
		
		// Boucle de jeu
		boolean running = true;
		while (running) {
			System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());
			System.out.println("Direction: " + Parameters.DIRECTIONS[this.robot.getDirection()]);
			
			// Choisi une couleur aleatoire et lance la navigation
			int new_color = ReglesJeu.getRandom();
			Navigation nav = new Navigation(this.robot, this.robot.getBoard(), this, new_color);
			for (ThreadBehavior behavior : this.behaviors) {
				behavior.setThread(nav);
			}
			nav.start();
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
			
			// Menu pour continuer la partie ou non
			System.out.println("Voulez-vous continuer ?\n"
					+ "ENTER: Oui\n"
					+ "LEFT: Non");
			int choix = Button.waitForAnyPress();
			while (choix != Button.ID_ENTER && choix!= Button.ID_LEFT) {
				System.out.println("Choix invalide, veuillez le refaire");
				choix = Button.waitForAnyPress();
			}
			switch (choix) {
				case Button.ID_ENTER:
					running = true;
					break;
				case Button.ID_LEFT:
					running = false;
					break;
			}
		}
		
		if (this.menu != null) {
			//System.out.println("Thread present");
			synchronized (this.menu) {
				//System.out.println("Thread notifie");
				this.menu.notify();
			}
		}
	}
}
