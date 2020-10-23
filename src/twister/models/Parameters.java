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
	 * HEIGHT = {@value}
	 */
	static final int HEIGHT = 7;
	/**
	 * Largeur du plateau. <br>
	 * WIDTH = {@value}
	 */
	static final int WIDTH = 5;
	/**
	 * Taille d'une case en mm. <br>
	 * SIZE = {@value}
	 */
	static final int SIZE = 135;
	
	/**
	 * Diametre des roues en mm. <br>
	 * WHEEL_DIAMETER = {@value}
	 */
	static final double WHEEL_DIAMETER = 56.;
	/**
	 * Decalage des roues par rapport a l'axe central en mm. <br>
	 * WHEEL_OFFSET = {@value}
	 */
	static final double WHEEL_OFFSET = 60.;
	/**
	 * Vitesse lineaire lorsque le robot avance ou recule. <br>
	 * MOVE_LINEAR_SPEED = {@value}
	 */
	static final double MOVE_LINEAR_SPEED = 70.;
	/**
	 * Vitesse de rotation lorsque le robot avance ou recule. <br>
	 * MOVE_ANGULAR_SPEED = {@value}
	 */
	static final double MOVE_ANGULAR_SPEED = 90.;
	/**
	 * Vitesse lineaire lorsque le robot tourne. <br>
	 * TURN_LINEAR_SPEED = {@value}
	 */
	static final double TURN_LINEAR_SPEED = 20.;
	/**
	 * Vitesse de rotation lorsque le robot tourne. <br>
	 * TURN_ANGULAR_SPEED = {@value}
	 */
	static final double TURN_ANGULAR_SPEED = 60.;
	
}
