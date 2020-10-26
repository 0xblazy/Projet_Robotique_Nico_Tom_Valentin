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
	
	
	public int [][] casesDeLaCouleur(int couleur){
		int[][] liste = new int[17][2];
		int compteur = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (this.plateau[i][j] == couleur) {
					liste[compteur][0] = i;
					liste[compteur][1] = j;
					compteur++;
				}
				
			}
		}
		return liste;
	}
	
	
	
}
