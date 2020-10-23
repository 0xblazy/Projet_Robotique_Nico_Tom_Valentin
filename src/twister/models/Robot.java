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
	// Booleens pour connaître les actions du Robot
	private boolean moveForward = false;
	private boolean moveBackward = false;
	private boolean turnLeft = false;
	private boolean turnRight = false;
	private boolean takeColor = false;
	
	/**
	 * Constructeur.
	 * 
	 * @param _x Coordonnee x du Robot.
	 * @param _y Coordonnee y du Robot.
	 */
	public Robot(int _x, int _y) {
		this.x = _x;
		this.y = _y;
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
	 * Setter pour y
	 * 
	 * @param _y Coordonnee y du Robot.
	 */
	public void setY(int _y) {
		this.y = _y;
	}
	
	/**
	 * Setter pour moveForward
	 * 
	 * @param _moveForward
	 */
	public void moveForward(boolean _moveForward) {
		this.moveForward = _moveForward;
	}
	
	/**
	 * Setter pour moveBackward
	 * 
	 * @param _moveBackward
	 */
	public void moveBackward(boolean _moveBackward) {
		this.moveBackward = _moveBackward;
	}
	
	/**
	 * Setter pour turnLeft
	 * 
	 * @param _turnLeft
	 */
	public void turnLeft(boolean _turnLeft) {
		this.turnLeft = _turnLeft;
	}
	
	/**
	 * Setter pour turnRight
	 * 
	 * @param _turnRight
	 */
	public void turnRight(boolean _turnRight) {
		this.turnRight = _turnRight;
	}
	
	/**
	 * Setter pour takeColor.
	 * 
	 * @param _takeColor
	 */
	public void takeColor(boolean _takeColor) {
		this.takeColor = _takeColor;
	}
	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean moveForward() {
		return this.moveForward;
	}
	
	public boolean moveBackward() {
		return this.moveBackward;
	}
	
	public boolean turnLeft() {
		return this.turnLeft;
	}
	
	public boolean turnRight() {
		return this.turnRight;
	}
	
	public boolean takeColor() {
		return this.takeColor;
	}
	
}
