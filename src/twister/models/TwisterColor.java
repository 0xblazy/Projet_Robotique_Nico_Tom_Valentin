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
	 * BLACK: 0 <br>
	 * WHITE: 1 <br>
	 * RED: 2 <br>
	 * ORANGE: 3 <br>
	 * BLUE: 4 <br>
	 * GREEN: 5
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
	 * Codes RGB des couleurs. <br>
	 * <br>
	 * BLACK: 0,0,0<br>
	 * WHITE: 255,255,255 <br>
	 * RED: 186,47,36 <br>
	 * ORANGE: 185,80,37 <br>
	 * BLUE: 31,61,77 <br>
	 * GREEN: 61,143,48 <br>
	 */
	static final int[][] RGBs = {
			{0,0,0}, 		// BLACK
			{255,255,255}, 	// WHITE
			{186,47,36}, 	// RED
			{185,80,37}, 	// ORANGE
			{31,61,77}, 	// BLUE
			{61,143,48} 	// GREEN
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
