package twister.behaviors;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import twister.models.Robot;
import twister.models.TwisterColor;
import twister.threads.ColorCalibration;

/**
 * Behavior charge de detecter la couleur via le EV3ColorSensor.
 * 
 * @author nicolas-carbonnier
 */
public class ColorDetector extends ThreadBehavior {

	private Robot robot;
	private EV3ColorSensor colorSensor;
	private SampleProvider colorSample;
	private float[] sample;
	private int offset;
	private boolean suppressed = false;
	
	/**
	 * Constructeur.
	 * 
	 * @param _colorSensor Capteur de couleur.
	 * @param _sample Tableau de stockage des donnees obtenues par les capteurs.
	 * @param _offset Decalage dans le tableau _sample.
	 */
	public ColorDetector(Robot _robot, EV3ColorSensor _colorSensor, float[] _sample, int _offset) {
		this.robot = _robot;
		this.colorSensor = _colorSensor;
		this.sample = _sample;
		this.offset = _offset;
		
		this.colorSample = this.colorSensor.getRGBMode();
	}
	
	/**
	 * Determine la couleur passee en parametre en calculant la distance euclidienne avec chaque couleur du Twister.
	 * 
	 * @param _rgb Code RGB fournis par le EV3ColorSensor.
	 * @return Code de la couleur detectee.
	 */
	private int getColor(int[] _rgb) {
		// Recuperation des codes RGB utilises par le Robot
		int[][] rgbs = this.robot.getRgbs();
		
		// Calcul de la distance euclidienne avec la couleur BLACK et definition de la couleur par d�faut
		double distance = Math.sqrt(
				(rgbs[0][0] - _rgb[0])*(rgbs[0][0] - _rgb[0]) + 
				(rgbs[0][1] - _rgb[1])*(rgbs[0][1] - _rgb[1]) + 
				(rgbs[0][2] - _rgb[2])*(rgbs[0][2] - _rgb[2]));
		int color = TwisterColor.COLORS_CODE[0];
		
		// Test de chaque couleur pour determiner la bonne
		for (int i = 1 ; i < rgbs.length ; i++) {
			double d = Math.sqrt(
				(rgbs[i][0] - _rgb[0])*(rgbs[i][0] - _rgb[0]) + 
				(rgbs[i][1] - _rgb[1])*(rgbs[i][1] - _rgb[1]) + 
				(rgbs[i][2] - _rgb[2])*(rgbs[i][2] - _rgb[2]));
			if (d < distance) {
				distance = d;
				color = TwisterColor.COLORS_CODE[i];
			}
		}
		
		return color;
	}
	
	@Override
	public boolean takeControl() {
		return (this.robot.takeColor() || this.robot.calibrateColor());
	}

	@Override
	public void action() {
		this.suppressed = false;
		this.colorSample.fetchSample(this.sample, this.offset);
		int rgb[] = new int[3];
		for (int i = this.offset ; i < 3 + this.offset ; i++) {
			rgb[i] = (int) (this.sample[i] * 1000);
			if (rgb[i] > 255) rgb[i] = 255;
		}
		
		System.out.println("RGB: " + rgb[0] + ", " + rgb[1] + ", " + rgb[2]);
		
		// Si le Robot doit prendre la couleur, sinon si le Robot doit calibrer une couleur
		if (this.robot.takeColor()) {
			//System.out.println(Parameters.DIRECTIONS[this.robot.getDirection()]);
			System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());
			
			int color = this.getColor(rgb);
			System.out.println("Color: " + color + " (" + TwisterColor.COLORS[color] + ")");
			this.robot.getBoard().setColor(this.robot.getX(), this.robot.getY(), color);
		} else if (this.robot.calibrateColor()) {
			if (this.thread instanceof ColorCalibration) {
				((ColorCalibration) this.thread).setDetectedColor(rgb);
			}
		}
		
		System.out.println();
		
		if (this.robot.takeColor()) this.robot.takeColor(false);
		if (this.robot.calibrateColor()) this.robot.calibrateColor(false);
		
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
