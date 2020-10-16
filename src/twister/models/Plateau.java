package twister.models;

public class Plateau {
	

	public final int hauteur = 7;
	public final int largeur = 5;
	public final int[][] plateau = new int[largeur][hauteur];
	//HauteurEtLargeur 
	
	
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
	
	/**
	 * Methode get
	 * @return hauteur
	 */
	public int getHauteur() {
		return this.hauteur;
	}
	
	/**
	 * Methode get
	 * @return largeur
	 */
	public int getLargeur() {
		return this.largeur;
	}
	
}
