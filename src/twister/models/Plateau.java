package twister.models;

public class Plateau {
	
	public final int[][] plateau = new int[Parameters.WIDTH][Parameters.HEIGHT];	
	
	/**
	 * Methode setColor 
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setColor(int x, int y, int c) {
		this.plateau[x][y] = c;
	}
	
	/**
	 * Methode getColor
	 * @param x
	 * @param y
	 * @return plateau
	 */
	public int getColor(int x, int y) {
		return this.plateau[x][y];
	}
	
}
