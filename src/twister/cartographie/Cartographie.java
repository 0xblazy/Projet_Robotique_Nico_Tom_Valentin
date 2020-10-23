package twister.cartographie;


import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import twister.models.Parameters;
import twister.models.Robot;

public class Cartographie implements Behavior {
	
	private Robot robot;
	private MovePilot pilot;
	private boolean suppressed = false;
	
	private int height = Parameters.HEIGHT;
	private int width = Parameters.WIDTH;
	
	private int nbrOcc = 5;
	
	public Cartographie(Robot _robot, MovePilot _pilot) {
		this.robot = _robot;
		this.pilot = _pilot;
	}
	
	public static void prelevementcouleur() {
		System.out.println("color");
		Delay.msDelay(2000);
	}


	@Override
	public boolean takeControl() {
		return Button.LEFT.isDown();
	}


	@Override
	public void action() {
		this.suppressed = false;
		
		System.out.println("Début");
		
		for(int occ = 0; occ < this.nbrOcc; occ++) {
			if(occ == 4) {
				this.robot.moveForward(true);
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.robot.moveForward(true);
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("finir");
				return ;
			}
			for (int i = 0; i < this.height; i++) {
					System.out.println("========");
					System.out.println(i);
					this.robot.moveForward(true);
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			this.robot.turnRight(true);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.height--;
			System.out.println(this.height);
			for (int j = 0; j < this.width; j++) {
				   System.out.println(j);
				   this.robot.moveForward(true);
				   try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			this.width--;
			System.out.println(this.width);
			this.robot.turnRight(true);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.robot.moveForward(true);
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void suppress() {
		this.suppressed = true;
	}
		
}
