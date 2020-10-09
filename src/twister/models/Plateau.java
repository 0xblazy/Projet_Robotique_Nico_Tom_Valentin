package twister.models;

public class Plateau {
	

	public static int longueur = 7;
	public static int largeur = 5;
	public static int[][] plateau = new int[largeur][longueur];
	//LongueurEtLargeur 
	
	
	/**
	 * Methode setColor 
	 * @param x
	 * @param y
	 * @param c
	 */
	public void setColor(int x, int y, int c) {
		this.plateau[x][y]= c;
	}
	
	/**
	 * Methode getColor
	 * @param x
	 * @param y
	 * @return
	 */
	public int getColor(int x, int y) {
		return this.plateau[x][y];
	}
	
	/**
	 * Methode get
	 * @return longueur
	 */
	public int getLongueur() {
		return this.longueur;
	}
	
	/**
	 * Methode get
	 * @return largeur
	 */
	public int getLargeur() {
		return this.largeur;
	}
	
}
