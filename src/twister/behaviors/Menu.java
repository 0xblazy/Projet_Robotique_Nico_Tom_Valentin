package twister.behaviors;

import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;
import twister.models.TwisterColor;

public class Menu implements Behavior, TwisterColor {

	@Override
	public boolean takeControl() {
		
		return false;
	}

	@Override
	public void action() {
		System.out.println("Choix de Cartographie:" + "\n" + "Boutton du haut : Type 1" + "\n" + "Boutton du bas : Type 2");
		
		int check = Button.waitForAnyPress();
		switch (check) {
		
		case Button.ID_UP: 
			System.out.println("Type 1");
			break;
			
		case Button.ID_DOWN:
			System.out.println("Type 2");
			break;
			
		default : 
			System.out.println("pute");

}
		Button.waitForAnyPress();
	}

	@Override
	public void suppress() {
		
	}

}
