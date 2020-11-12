package twister.behaviors;

import java.lang.reflect.Parameter;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import twister.models.Parameters;
import twister.models.Robot;
import twister.models.TwisterColor;

/**
 * Behavior charge de detecter la couleur via le EV3ColorSensor.
 * 
 * @author nicolas-carbonnier
 */
public class ColorDetector implements Behavior, TwisterColor {

	private Robot robot;
	private EV3ColorSensor colorSensor;
	private SampleProvider colorSample;
	private float[] sample;
	private int offset;
	private Thread thread;
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
		// Calcul de la distance euclidienne avec la couleur BLACK et definition de la couleur par d�faut
		double distance = Math.sqrt(
				(RGBs[0][0] - _rgb[0])*(RGBs[0][0] - _rgb[0]) + 
				(RGBs[0][1] - _rgb[1])*(RGBs[0][1] - _rgb[1]) + 
				(RGBs[0][2] - _rgb[2])*(RGBs[0][2] - _rgb[2]));
		int color = COLORS_CODE[0];
		
		// Test de chaque couleur pour determiner la bonne
		for (int i = 1 ; i < RGBs.length ; i++) {
			double d = Math.sqrt(
				(RGBs[i][0] - _rgb[0])*(RGBs[i][0] - _rgb[0]) + 
				(RGBs[i][1] - _rgb[1])*(RGBs[i][1] - _rgb[1]) + 
				(RGBs[i][2] - _rgb[2])*(RGBs[i][2] - _rgb[2]));
			if (d < distance) {
				distance = d;
				color = COLORS_CODE[i];
			}
		}
		
		return color;
	}
	
	public void setThread(Thread _thread) {
		this.thread = _thread;
	}
	
	@Override
	public boolean takeControl() {
		return this.robot.takeColor();
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
		
		//System.out.println("Red:" + rgb[0]);
		//System.out.println("Green:" + rgb[1]);
		//System.out.println("Blue:" + rgb[2]);
		
		System.out.println(Parameters.DIRECTIONS[this.robot.getDirection()]);
		System.out.println("X: " + this.robot.getX() + " Y: " + this.robot.getY());
		
		int color = this.getColor(rgb);
		System.out.println("Color: " + color + " (" + COLORS[color] + ")");
		System.out.println();
		
		this.robot.takeColor(false);
		
		if (this.thread != null) {
			//System.out.println("Thread present");
			synchronized (this.thread) {
				//System.out.println("Thread notifie");
				thread.notify();
			}
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}
}
