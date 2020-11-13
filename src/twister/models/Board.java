package twister.models;

import java.util.Arrays;

/**
 * Modele du board.
 * 
 * @author Aetra
 * @author nicolas-carbonnier
 */
public class Board {
	
	private final int[][] board = new int[Parameters.HEIGHT][Parameters.WIDTH];
	private boolean cartographed = false;
	
	/**
	 * Setter pour la couleur.
	 *  
	 * @param _x Coordonnee x.
	 * @param _y Coordonnee y.
	 * @param _color Couleur.
	 */
	public void setColor(int _x, int _y, int _color) {
		this.board[_y][_x] = _color;
	}
	
	/**
	 * Getter pour la couleur.
	 * 
	 * @param _x Coordonnee x.
	 * @param _y Coordonne y.
	 * @return Couleur.
	 */
	public int getColor(int _x, int _y) {
		return this.board[_y][_x];
	}		
	
	@Override
	public String toString() {
		String s = "";
		
		for (int j = 0 ; j < this.board.length ; j++) {
			s += Arrays.toString(this.board[j]) + "\n";
		}
		
		return s;
	}
	
	public int [][] casesDeLaCouleur(int couleur){
		int[][] liste = new int[17][2];
		int compteur = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if (this.board[j][i] == couleur) {
					liste[compteur][0] = i;
					liste[compteur][1] = j;
					compteur++;
				}
			}
		}
		return liste;
	}
	
	//TODO : A TESTER 
	//Méthode qui prend toutes les cases de la couleur demandée, et ressort la plus proche du robot 
	public int [][] caseLaPlusProche(Robot r, int c){
		int xRobot = r.getX();
		int yRobot = r.getY();
		int val = 42;
		int [][] good  = new int[1][2];
		int[][] liste = casesDeLaCouleur(c);
		for (int i = 0; i < liste.length ; i++) {
			int val1 = Math.abs(liste[i][1] - yRobot) + Math.abs(liste[i][0] - xRobot);
			if (val1 < val) {
				val = val1;
				good[1][0]= liste[i][0];
				good[1][1]= liste[i][1];
				System.out.println(val1);
			}	
		}
		return good;
	}
	
	/**
	 * Setter pour definir si le board a été cartographie ou non.
	 * 
	 * @param _cartographed Booleen.
	 */
	public void setCartographed(boolean _cartographed) {
		this.cartographed = _cartographed;
	}
	
	public boolean isCartographed() {
		return this.cartographed;
	}
	
}
