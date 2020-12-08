package twister.models;

import java.util.Arrays;

/**
 * Modele du board.
 * 
 * @author Aetra
 * @author nicolas-carbonnier
 * @author TomySchef54
 */
public class Board {
	
	private int[][] board = new int[Parameters.HEIGHT][Parameters.WIDTH];
	private boolean cartographed = false;	
	
	@Override
	public String toString() {
		String s = "";
		
		for (int j = 0 ; j < this.board.length ; j++) {
			s += Arrays.toString(this.board[j]) + "\n";
		}
		
		return s;
	}
	
	/**
	 * Cherche les cases de la couleur demandee.
	 * 
	 * @param couleur Code de la couleur demandee.
	 * @return Tableau a deux dimensions contenant les coordonnees des cases de la couleur demandee.
	 */
	private int [][] casesDeLaCouleur(int couleur){
		int[][] liste = null;
		if (couleur == 1) {
			liste = new int[2][2];
		}
		if (couleur == 2) {
			liste = new int[1][2];
		}
		if (couleur == 3) {
			liste = new int[4][2];
		}
		if (couleur == 4) {
			liste = new int[18][2];
		}
		if (couleur == 5) {
			liste = new int[10][2];
		}
		
		int compteur = 0;
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 5; i++) {
				if (this.board[j][i] == couleur) {
					liste[compteur][0] = i;
					liste[compteur][1] = j;
					compteur++;
				}
			}
		}
		return liste;
	}
	
	/**
	 * Cherche la case la plus proche du Robot pour la couleur demandee.
	 * 
	 * @param r Robot dont on cherche la case la plus proche.
	 * @param c Code de la couleur demandee.
	 * @return Coordonnees de la case la plus proche du Robot pour la couleur demandee.
	 */
	public int [] caseLaPlusProche(Robot r, int c){
		int xRobot = r.getX();
		int yRobot = r.getY();
		int val = 42;
		int [] good  = new int[2];
		int[][] liste = casesDeLaCouleur(c);
		for (int i = 0; i < liste.length ; i++) {
			int val1 = Math.abs(liste[i][1] - yRobot) + Math.abs(liste[i][0] - xRobot);
			if (val1 < val) {
				val = val1;
				good[0]= liste[i][0];
				good[1]= liste[i][1];
			}
		}
		return good;
	}
	
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
	 * Setter pour le board.
	 * 
	 * @param _board Board.
	 */
	public void setBoard(int[][] _board) {
		this.board = _board;
	}
	
	/**
	 * Setter pour definir si le board a ete cartographie ou non.
	 * 
	 * @param _cartographed Vrai si le plateau a ete cartographier, faux sinon.
	 */
	public void setCartographed(boolean _cartographed) {
		this.cartographed = _cartographed;
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
	
	/**
	 * Getter pour cartographed.
	 * 
	 * @return Vrai si le plateau a ete cartographier, faux sinon.
	 */
	public boolean isCartographed() {
		return this.cartographed;
	}	
}
