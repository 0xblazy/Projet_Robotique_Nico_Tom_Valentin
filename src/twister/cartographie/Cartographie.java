package twister.cartographie;


import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Cartographie implements Behavior {
	static int angle = 360;
	static int caseh = 7;
	static int caseL = 5;
	
	static int nbrOcc = 5;
	static Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 56.).offset(-60);
	static Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 56.).offset(60);
	static Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2}, 2); // le 2ème param. est le nb de roues
	static MovePilot pilot = new MovePilot(chassis);
	public static void main(String[] args) {
		cartographie();
	}
	
	public static void angle() {		
		pilot.setLinearSpeed(20.); // unit per second
		pilot.setAngularSpeed(60.); // degré/sec
		while(pilot.isMoving())Thread.yield();
		pilot.rotate(80);
		while(pilot.isMoving())Thread.yield();
	}
	
	public static void avancer() {
		pilot.setLinearSpeed(70.); // unit per second
		pilot.setAngularSpeed(90.); // degré/sec
		pilot.travel(135); // en unités (mm si diamètre roue)
		while(pilot.isMoving())Thread.yield();

	}
	
	
	public static void cartographie () {
		Button.waitForAnyPress();
		for(int occ = 0; occ < Cartographie.nbrOcc; occ++) {
			if(occ == 4) {
				avancer();
				avancer();
				System.out.println("finir");
				return ;
			}
			for (int i = 0; i < caseh; i++) {
					System.out.println(i);
					avancer();
			}
			angle();
			Cartographie.caseh--;
			System.out.println(Cartographie.caseh);
			for (int j = 0; j < caseL; j++) {
				   System.out.println(j);
				   avancer();
			}
			Cartographie.caseL--;
			System.out.println(Cartographie.caseL);
			angle();
			avancer();
		}
		
	}
	
	public static void prelevementcouleur() {
		System.out.println("color");
		Delay.msDelay(2000);
	}


	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
