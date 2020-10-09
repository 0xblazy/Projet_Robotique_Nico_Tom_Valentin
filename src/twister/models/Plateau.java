package twister.models;

public class Plateau {
	

	public static int longueur = 7;
	public static int largeur = 5;
	public static int[][] plateau = new int[largeur][longueur];
	//LongueurEtLargeur 
	
	
	//applique une couleur à une case du plateau 
	public void setColor(int x, int y, int c) {
		this.plateau[x][y]= c;
	}
	
	
	public int getColor(int x, int y) {
		return this.plateau[x][y];
	}
	
	
	
}
