package twister.cartography;


import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
import twister.models.Parameters;
import twister.models.Robot;

public class Cartography extends Thread {
	
	private Robot robot;
	private MovePilot pilot;
	
	private int height = Parameters.HEIGHT;
	private int width = Parameters.WIDTH;
	
	private int nbrOcc = 5;
	
	public Cartography(Robot _robot, MovePilot _pilot) {
		this.robot = _robot;
		this.pilot = _pilot;
	}
	
	public static void prelevementcouleur() {
		System.out.println("color");
		Delay.msDelay(2000);
	}

	@Override
	public void run() {
		System.out.println("Appuyez sur le bouton GAUCHE pour lancer la cartographie");
		Button.LEFT.waitForPress();
		
		while (true) {
			for(int occ = 0; occ < this.nbrOcc; occ++) {
				if(occ == 4) {
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					System.out.println("finir");
					break;
				}
				for (int i = 0; i < this.height; i++) {
					System.out.println("========");
					System.out.println(i);
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
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
				
				this.height--;
				System.out.println(this.height);
				for (int j = 0; j < this.width; j++) {
				   System.out.println(j);
				   this.robot.moveForward(true);
				   synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				   
				}
				this.width--;
				System.out.println(this.width);
				this.robot.turnRight(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
			}
			break;
		}
	}
		
}
