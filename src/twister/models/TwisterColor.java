package twister.models;

/**
 * Interface regroupant les codes couleurs du jeu.
 * 
 * @author nicolas-carbonnier
 */
public interface TwisterColor {

	/**
	 * Entiers representant les couleurs. <br>
	 * <br>
	 * BLACK: 0<br>
	 * WHITE: 1 <br>
	 * RED: 2<br>
	 * ORANGE: 3<br>
	 * BLUE: 5 <br>
	 * GREEN: 6
	 */
	static final int[] COLORS_CODE = {
			0, 	// BLACK
			1, 	// WHITE
			2, 	// RED
			3, 	// ORANGE
			4, 	// BLUE
			5 	// GREEN
		};
	
	/**
	 * Codes RGB des couleurs.<br>
	 * <br>
	 * BLACK: 0,0,0<br>
	 * WHITE: 255,255,255<br>
	 * RED: 255,0,0<br>
	 * ORANGE: 255,128,0<br>
	 * BLUE: 0,0,255<br>
	 * GREEN: 0,255,0
	 */
	static final int[][] RGBs = {
			{0,0,0}, 		// BLACK
			{255,255,255}, 	// WHITE
			{255,0,0}, 		// RED
			{255,128,0}, 	// ORANGE
			{0,0,255}, 		// BLUE
			{0,255,0} 		// GREEN
		};
	
	/**
	 * Chaines de caracteres des couleurs.
	 */
	static final String[] COLORS = {
			"Black",
			"White",
			"Red",
			"Orange",
			"Blue",
			"Green"
		};
	
}
