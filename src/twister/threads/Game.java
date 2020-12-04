package twister.threads;

import lejos.hardware.Button;
import twister.models.Board;
import twister.models.ReglesJeu;
import twister.models.Robot;
import twister.models.TwisterColor;

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
	 * Plateau de la partie.
	 */
	private Board board;
	
	/**
	 * Constructeur.
	 * 
	 * @param _robot Robot utilise pour la partie.
	 * @param _menu Menu qui a appele Game.
	 * @param _board Plateau de la partie.
	 */
	public Game(Robot _robot, Menu _menu, Board _board) {
		this.robot = _robot;
		this.menu = _menu;
		this.board = _board;
	}
	
	@Override
	public void run() {
		System.out.println("Appuyez sur le bouton ENTER pour lancer la partie");
		Button.ENTER.waitForPress();
		
		// Boucle de jeu
		boolean running = true;
		while (running) {
			System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());
			
			// Choisi une couleur aleatoire et cherche la case la plus proche
			int new_color = ReglesJeu.getRandom();
			int[] caseLaPlusProche = this.robot.getBoard().caseLaPlusProche(this.robot, new_color);
			System.out.println(TwisterColor.COLORS[new_color] + " le plus proche: " + caseLaPlusProche[0] + ", " + caseLaPlusProche[1]);
			
			
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
