package twister.menu;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class menu {

	/**
	 * Appelee lors du lancement du programme.
	 * 
	 * @param args Arguments de la ligne de commande.
	 */
	public static void main(String[] args) {
		choixCarto();

	}
	
	
	
	public static void choixCarto() {
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
}