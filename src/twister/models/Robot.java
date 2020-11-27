package twister.models;

/**
 * Modele du Robot
 * 
 * @author nicolas-carbonnier
 */
public class Robot {

	/**
	 * Coordonnees du Robot.
	 */
	private int x,y;
	private int direction;
	
	/**
	 * Board sur lequel est le Robot.
	 */
	private Board board;
	
	/**
	 * Booleens pour définir les actions du Robot.
	 */
	private boolean moveForward = false;
	private boolean moveBackward = false;
	private boolean turnLeft = false;
	private boolean turnRight = false;
	private boolean takeColor = false;
	private boolean calibrateColor = false;
	
	/**
	 * Nombre de cases a se déplacer.
	 * nbCases = {@value}
	 */
	private int nbCases = 1;
	
	/**
	 * Booleen pour definir si les couleurs utilisees par le Robot ont ete calibrees.
	 */
	private boolean colorCalibrated = false;
	
	/**
	 * Tableau des codes RGB utilises par le Robot.
	 */
	private int[][] rgbs = TwisterColor.RGBs;
	
	/**
	 * Constructeur.
	 * 
	 * @param _x Coordonnee x du Robot.
	 * @param _y Coordonnee y du Robot.
	 * @param _direction Direction du Robot.
	 * @param _board Board sur lequel est le Robot.
	 */
	public Robot(int _x, int _y, int _direction, Board _board) {
		this.x = _x;
		this.y = _y;
		this.direction = _direction;
		this.board = _board;
	}
	
	/**
	 * Setter pour x.
	 * 
	 * @param _x Coordonnee x du Robot.
	 */
	public void setX(int _x) {
		this.x = _x;
	}
	
	/**
	 * Setter pour y.
	 * 
	 * @param _y Coordonnee y du Robot.
	 */
	public void setY(int _y) {
		this.y = _y;
	}
	
	/**
	 * Setter pour la direction.
	 * 
	 * @param _direction Direction du Robot.
	 */
	public void setDirection(int _direction) {
		this.direction = _direction;
	}
	
	/**
	 * Setter pour le board.
	 * 
	 * @param _board Board sur lequel est le Robot.
	 */
	public void setBoard(Board _board) {
		this.board = _board;
	}
	
	/**
	 * Setter pour moveForward
	 * 
	 * @param _moveForward Vrai si le Robot doit avancer, faux sinon.
	 */
	public void moveForward(boolean _moveForward) {
		this.moveForward = _moveForward;
	}
	
	/**
	 * Setter pour moveBackward
	 * 
	 * @param _moveBackward Vrai si le Robot doit reculer, faux sinon.
	 */
	public void moveBackward(boolean _moveBackward) {
		this.moveBackward = _moveBackward;
	}
	
	/**
	 * Setter pour turnLeft
	 * 
	 * @param _turnLeft Vrai si le Robot doit tourner a gauche, faux sinon.
	 */
	public void turnLeft(boolean _turnLeft) {
		this.turnLeft = _turnLeft;
	}
	
	/**
	 * Setter pour turnRight
	 * 
	 * @param _turnRight Vrai si le Robot doit tourner a droite, faux sinon.
	 */
	public void turnRight(boolean _turnRight) {
		this.turnRight = _turnRight;
	}
	
	/**
	 * Setter pour takeColor.
	 * 
	 * @param _takeColor Vrai si le Robot doit prendre la couleur, faux sinon.
	 */
	public void takeColor(boolean _takeColor) {
		this.takeColor = _takeColor;
	}
	
	/**
	 * Setter pour calibrateColor.
	 * 
	 * @param _calibrateColor Vrai si le Robot doit calibrer une couleur, faux sinon.
	 */
	public void calibrateColor(boolean _calibrateColor) {
		this.calibrateColor = _calibrateColor;
	}
	
	/**
	 * Setter pour nbCases.
	 * 
	 * @param _nbCases Nombre de cases a se deplacer.
	 */
	public void setNbCases(int _nbCases) {
		this.nbCases = _nbCases;
	}
	
	/**
	 * Setter pour colorCalibrated.
	 * 
	 * @param _colorCalibrated Vrai si les couleurs utilisees par le Robot ont ete calibrees, faux sinon.
	 */
	public void setColorCalibrated(boolean _colorCalibrated) {
		this.colorCalibrated = _colorCalibrated;
	}
	
	/**
	 * Setter pour rgbs
	 * @param _rgbs Tableau contenant tous les codes RGB qui seront utilises par le Robot.
	 */
	public void setRgbs(int[][] _rgbs) {
		this.rgbs = _rgbs;
	}
	
	/**
	 * Setter pour une couleur de rgbs donnee.
	 * 
	 * @param _color Code d'une couleur donnee.
	 * @param _rgb Code RGB a appliquer pour cette couleur.
	 */
	public void setRgb(int _color, int[] _rgb) {
		this.rgbs[_color] = _rgb;
	}
	
	/**
	 * Getter pour x.
	 * 
	 * @return Coordonnee x du Robot.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter pour y.
	 * 
	 * @return Coordonnee y du Robot.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Getter pour la direction.
	 * 
	 * @return Direction du Robot.
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Getter pour le Board.
	 * 
	 * @return Board sur lequel est le Robot.
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Getter pour moveForward.
	 * 
	 * @return Vrai si le Robot doit avancer, faux sinon.
	 */
	public boolean moveForward() {
		return this.moveForward;
	}
	
	/**
	 * Getter pour moveBackward.
	 * 
	 * @return Vrai si le Robot doit reculer, faux sinon.
	 */
	public boolean moveBackward() {
		return this.moveBackward;
	}
	
	/**
	 * Getter pour turnLeft.
	 * 
	 * @return Vrai si le Robot doit touner a gauche, faux sinon.
	 */
	public boolean turnLeft() {
		return this.turnLeft;
	}
	
	/**
	 * Getter pour turnRight.
	 * 
	 * @return Vrai si le Robot doit tourner a droite, faux sinon.
	 */
	public boolean turnRight() {
		return this.turnRight;
	}
	
	/**
	 * Getter pour takeColor.
	 * 
	 * @return Vrai si le Robot doit prendre la couleur, faux sinon.
	 */
	public boolean takeColor() {
		return this.takeColor;
	}
	
	/**
	 * Getter pour calibrateColor.
	 * 
	 * @return Vrai si le Robot doit calibrer une couleur, faux sinon.
	 */
	public boolean calibrateColor() {
		return this.calibrateColor;
	}
	
	/**
	 * Getter pour nbCases.
	 * 
	 * @return Nombre de cases a se deplacer.
	 */
	public int getNbCases() {
		return this.nbCases;
	}
	
	/**
	 * Getter pour colorCalibrated.
	 * 
	 * @return Vrai si les couleurs utilisees par le Robot ont ete calibrees, faux sinon.
	 */
	public boolean isColorCalibrated() {
		return this.colorCalibrated;
	}
	
	/**
	 * Getter pour rgbs.
	 * 
	 * @return Tableau contenant tous les codes RGB qui seront utilises par le Robot.
	 */
	public int[][] getRgbs() {
		return this.rgbs;
	}
	
	/**
	 * Getter pour le code RGB de _color.
	 * 
	 * @param _color Code de la couleur demandee.
	 * @return Code RGB de la couleur demandee.
	 */
	public int[] getRgb(int _color) {
		return this.rgbs[_color];
	}
	
}
