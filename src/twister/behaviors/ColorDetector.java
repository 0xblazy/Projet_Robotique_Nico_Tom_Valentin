package twister.behaviors;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import twister.models.TwisterColor;

/**
 * Behavior charg� de d�tecter la couleur via le EV3ColorSensor.
 * 
 * @author nicolas-carbonnier
 */
public class ColorDetector implements Behavior, TwisterColor {

	private EV3ColorSensor colorSensor;
	private SampleProvider colorSample;
	private float[] sample;
	private int offset;
	
	/**
	 * Constructeur.
	 * 
	 * @param _colorSensor Capteur de couleur.
	 * @param _sample Tableau de stockage des donn�es obtenues par les capteurs.
	 * @param _offset D�calage dans le tableau _sample.
	 */
	public ColorDetector(EV3ColorSensor _colorSensor, float[] _sample, int _offset) {
		this.colorSensor = _colorSensor;
		this.sample = _sample;
		this.offset = _offset;
		
		this.colorSample = this.colorSensor.getRGBMode();
	}
	
	/**
	 * D�termine la couleur pass�e en param�tre en calculant la distance euclidienne avec chaque couleur du Twister.
	 * 
	 * @param _rgb Code RGB fournis par le EV3ColorSensor.
	 * @return Code de la couleur d�tect�e.
	 */
	private int getColor(int[] _rgb) {
		// Calcul de la distance euclidienne avec la couleur BLACK et d�finition de la couleur par d�faut
		double distance = Math.sqrt(
				(RGBs[0][0] - _rgb[0])*(RGBs[0][0] - _rgb[0]) + 
				(RGBs[0][1] - _rgb[1])*(RGBs[0][1] - _rgb[1]) + 
				(RGBs[0][2] - _rgb[2])*(RGBs[0][2] - _rgb[2]));
		int color = COLORS_CODE[0];
		
		// Test de chaque couleur pour d�terminer la bonne
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
	
	@Override
	public boolean takeControl() {
		return Button.RIGHT.isDown();
	}

	@Override
	public void action() {
		this.colorSample.fetchSample(this.sample, this.offset);
		int rgb[] = new int[3];
		for (int i = this.offset ; i < 3 + this.offset ; i++) {
			rgb[i] = (int) (this.sample[i] * 1000);
			if (rgb[i] > 255) rgb[i] = 255;
		}
		
		System.out.println("Red:" + rgb[0]);
		System.out.println("Green:" + rgb[1]);
		System.out.println("Blue:" + rgb[2]);
		
		int color = this.getColor(rgb);
		System.out.println("Color: " + color + " (" + COLORS[color] + ")");
		System.out.println();
	}

	@Override
	public void suppress() {}
}
