package twister;

import lejos.hardware.Button;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class ColorDetector implements Behavior {

	private EV3ColorSensor colorSensor;
	private SampleProvider colorSample;
	private float[] sample;
	private int offset;
	
	// Constructor
	public ColorDetector(EV3ColorSensor _colorSensor, float[] _sample, int _offset) {
		this.colorSensor = _colorSensor;
		this.sample = _sample;
		this.offset = _offset;
		
		this.colorSample = this.colorSensor.getRGBMode();
	}
	
	// Determine the color detected by sensor
	private int getColor(int[] _rgb) {
		return 0;
	}
	
	@Override
	public boolean takeControl() {
		return Button.RIGHT.isDown();
	}

	@Override
	public void action() {
		this.colorSample.fetchSample(this.sample, this.offset);
		int rgb[] = new int[3];
		for (int i = 0 ; i < 3 ; i++) {
			rgb[i] = (int) (this.sample[i] * 1000);
			if (rgb[i] > 255) rgb[i] = 255;
		}
		
		System.out.println("Red:" + rgb[0]);
		System.out.println("Green:" + rgb[1]);
		System.out.println("Blue:" + rgb[2]);
		System.out.println("Color: " + this.getColor(rgb));
		System.out.println();
	}

	@Override
	public void suppress() {}
}
