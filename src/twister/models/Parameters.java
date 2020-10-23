package twister.models;

/**
 * Interface regroupant les parametres du jeu. <br>
 * Dimensions du plateau, diametre des roues...
 * 
 * @author nicolas-carbonnier
 */
public interface Parameters {
	
	/**
	 * Hauteur du plateau. <br>
	 * height = {@value}
	 */
	static final int height = 7;
	/**
	 * Largeur du plateau. <br>
	 * width = {@value}
	 */
	static final int width = 5;
	/**
	 * Taille d'une case en mm. <br>
	 * size = {@value}
	 */
	static final int size = 135;
	
	/**
	 * Diametre des roues en mm. <br>
	 * wheelDiameter = {@value}
	 */
	static final double wheelDiameter = 56.;
	/**
	 * Decalage des roues par rapport a l'axe central en mm. <br>
	 * wheelOffset = {@value}
	 */
	static final double wheelOffset = 60.;
	/**
	 * Vitesse lineaire lorsque le robot avance ou recule. <br>
	 * moveLinearSpeed = {@value}
	 */
	static final double moveLinearSpeed = 70.;
	/**
	 * Vitesse de rotation lorsque le robot avance ou recule. <br>
	 * moveAngularSpeed = {@value}
	 */
	static final double moveAngularSpeed = 90.;
	/**
	 * Vitesse lineaire lorsque le robot tourne. <br>
	 * turnLinearSpeed = {@value}
	 */
	static final double turnLinearSpeed = 20.;
	/**
	 * Vitesse de rotation lorsque le robot tourne. <br>
	 * turnAngularSpeed = {@value}
	 */
	static final double turnAngularSpeed = 60.;
	
}
