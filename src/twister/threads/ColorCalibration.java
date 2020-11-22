package twister.threads;

import lejos.hardware.Button;
import twister.models.Robot;
import twister.models.TwisterColor;

/**
 * Thread utilise pour calibrer les couleurs utilisees par le Robot.
 * 
 * @author nicolas-carbonnier
 */
public class ColorCalibration extends Thread {

	/**
	 * Robot a calibrer.
	 */
	private Robot robot;
	/**
	 * Menu qui a appele ColorCalibration.
	 */
	private Menu menu;
	
	/**
	 * Nombre de prise de chaque couleur.
	 */
	private int prises;
	/**
	 * Code RBG de la couleur detectee.
	 */
	private int[] detectedColor;
	
	/**
	 * Constructeur.
	 * 
	 * @param _robot Robot a calibrer.
	 * @param _menu Menu qui a appele ColorCalibration.
	 * @param _prises Nombre de prise de chaque couleur.
	 */
	public ColorCalibration(Robot _robot, Menu _menu, int _prises) {
		this.robot = _robot;
		this.menu = _menu;
		this.prises = _prises;
	}
	
	@Override
	public void run() {
		// Recuperation des codes RGB utilises par le Robot
		int[][] rgbs = this.robot.getRgbs();
		
		System.out.println("Appuyez sur le bouton ENTER pour lancer la calibration");
		Button.ENTER.waitForPress();
		
		// Boucle sur chaque couleur
		for (int i = 0 ; i < rgbs.length ; i++) {
			int r = 0;
			int g = 0;
			int b = 0;
			
			// Boucle sur le nombre de prises
			for (int j = 0 ; j < this.prises ; j++) {
				System.out.println("Prise " + (j + 1) + "/" + this.prises + " pour " + TwisterColor.COLORS[i]);
				System.out.println("Appuyez sur RIGHT pour scanner");
				Button.RIGHT.waitForPress();
				
				this.robot.calibrateColor(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
				int[] detectedColors = this.detectedColor;
				r += detectedColors[0];
				g += detectedColors[1];
				b += detectedColors[2];
			}
			
			// Calcul de la moyenne des codes RGB
			r = r / this.prises;
			g = g / this.prises;
			b = b / this.prises;
			
			System.out.println(TwisterColor.COLORS[i] + ": " + r + ", " + g + ", " + b + "\n");
			this.robot.setRgb(i, new int[]{r, g, b});
		}
		
		this.robot.setColorCalibrated(true);
		System.out.println("Calibration des couleurs terminee");
		
		//System.out.println("foo");
		if (this.menu != null) {
			//System.out.println("Thread present");
			synchronized (this.menu) {
				//System.out.println("Thread notifie");
				this.menu.notify();
			}
		}
	}
	
	/**
	 * Setter pour detectedColor.
	 * 
	 * @param _detectedColor Code RGB de la couleur detectee.
	 */
	public void setDetectedColor(int[] _detectedColor) {
		this.detectedColor = _detectedColor;
	}
}
