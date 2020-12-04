package twister.models;

import java.util.Random;

public interface ReglesJeu extends TwisterColor {
	Random rand = new Random();
	
	
	public static int getRandom(int[] array) {
	    int rnd = new Random().nextInt(array.length);
	    System.out.println(COLORS[rnd]);
	    return array[rnd];
	}
}
