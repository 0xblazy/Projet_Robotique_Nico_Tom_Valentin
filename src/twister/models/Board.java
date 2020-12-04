package twister.models;

import java.util.Arrays;

/**
 * Modele du board.
 * 
 * @author Aetra
 * @author nicolas-carbonnier
 */
public class Board {
	
	private int[][] board = new int[Parameters.HEIGHT][Parameters.WIDTH];
	private boolean cartographed = false;
	
	/**
	 * Setter pour la couleur.
	 *  
	 * @param _x Coordonnee x.
	 * @param _y Coordonnee y.
	 * @param _color Couleur.
	 */
	
	
	public void setBoard(int[][] board) {
		this.board = board;
	}
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
			liste = new int[10 ][2];
		}
		
		int compteur = 0;
		for (int j = 0; j < 7; j++) {
			for (int i = 0; i < 5; i++) {
				if (this.board[j][i] == couleur) {
					liste[compteur][0] = j;
					liste[compteur][1] = i;
					compteur++;
				}
			}
		}
		return liste;
	}
	
<<<<<<< HEAD
	//TODO : A TESTER 
	//Mï¿½thode qui prend toutes les cases de la couleur demandï¿½e, et ressort les coordonnï¿½es de la plus proche du robot 
=======

	//Méthode qui prend toutes les cases de la couleur demandée, et ressort les coordonnées de la plus proche du robot 
>>>>>>> branch 'dev' of https://github.com/nicolas-carbonnier/Projet_Robotique_Nico_Tom_Valentin.git
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
	 * Setter pour definir si le board a ï¿½tï¿½ cartographie ou non.
	 * 
	 * @param _cartographed Booleen.
	 */
	public void setCartographed(boolean _cartographed) {
		this.cartographed = _cartographed;
	}
	
	public boolean isCartographed() {
		return this.cartographed;
	}
	
	/**
	 * Naviguer dans le board
	 * @param r
	 * @param c
	 */
	
	public void navgigation(Robot r, int c) {
		int[] destPos = caseLaPlusProche(r, c);
		int xRobot = r.getX();
		int yRobot = r.getY();
		if (xRobot > destPos[0]) {
			int valDest = Math.abs(xRobot - destPos[0]); 
			r.setNbCases(valDest);
			r.moveForward(true);
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		else if (xRobot < destPos[0]) {
			int valDest = Math.abs(xRobot - destPos[0]); 
			r.setNbCases(valDest);
			r.moveForward(true);
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		if (yRobot > destPos[1]) {
			int valDest = Math.abs(yRobot - destPos[1]); 
			r.setNbCases(valDest);
			r.moveForward(true);
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}		
		}
		else if (yRobot < destPos[1]) {
			int valDest = Math.abs(yRobot - destPos[1]); 
			r.setNbCases(valDest);
			r.moveForward(true);
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}	
		}
		else {
			
		}
			
		
		
	}
	
}
