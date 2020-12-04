package twister.models;

import java.util.Random;

public interface ReglesJeu extends TwisterColor {
	Random rand = new Random();
	
	
	public static int getRandom() {
	    int rnd = 1 + new Random().nextInt(COLORS_CODE.length - 1);
	    System.out.println(COLORS[rnd]);
	    return COLORS_CODE[rnd];
	}
	
	
	
	
}

