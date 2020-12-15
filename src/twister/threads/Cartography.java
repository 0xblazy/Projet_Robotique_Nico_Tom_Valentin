package twister.threads;


import lejos.hardware.Button;
import twister.models.Parameters;
import twister.models.Robot;

/**
 * Thread utilise pour la Cartographie.
 * 
 * @author Aetra
 * @author nicolas-carbonnier
 */
public class Cartography extends Thread {
	
	/**
	 * Robot utilise pour la cartographie.
	 */
	private Robot robot;
	/**
	 * Menu qui a appele Cartography.
	 */
	private Menu menu;
	
	/**
	 * Hauteur du plateau.
	 * height = {@value}
	 */
	private int height = Parameters.HEIGHT;
	/**
	 * Largeur du plateau.
	 * width = {@value}
	 */
	private int width = Parameters.WIDTH;
	
	/**
	 * Nombre de repetition de schema de cartographie.
	 * nbrOcc = {@value}
	 */
	private int nbrOcc = Parameters.WIDTH;
	
	/**
	 * Contructeur.
	 * 
	 * @param _robot Robot utilise pour la cartographie.
	 * @param _menu Menu qui a appele Cartography.
	 */
	public Cartography(Robot _robot, Menu _menu) {
		this.robot = _robot;
		this.menu = _menu;
	}

	@Override
	public void run() {
		System.out.println("Appuyez sur le bouton ENTER pour lancer la cartographie");
		Button.ENTER.waitForPress();
		
		while (true) {
			for(int occ = 0; occ < this.nbrOcc; occ++) {
				this.robot.takeColor(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
				if(occ == 4) {
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					this.robot.takeColor(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					this.robot.takeColor(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					System.out.println("Cartographie terminee");
					break;
				}
				   
				for (int i = 0; i < this.height; i++) {
					//System.out.println("========");
					//System.out.println(i);
					this.robot.moveForward(true);
					synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
					
					if (i < this.height - 1) {
						this.robot.takeColor(true);
						synchronized (this) {
							try {
								this.wait();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								break;
							}
						}
					}
				}
				this.robot.turnRight(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
				this.height--;
				//System.out.println(this.height);
				for (int j = 0; j < this.width; j++) {
				   //System.out.println(j);
				   this.robot.moveForward(true);
				   synchronized (this) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							break;
						}
					}
				   
				   if (j < this.width - 1) {
					   this.robot.takeColor(true);
					   synchronized (this) {
							try {
								this.wait();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								break;
							}
						}
				   }
				   
				}
				this.width--;
				//System.out.println(this.width);
				this.robot.turnRight(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				
				this.robot.moveForward(true);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}				
			}
			break;
		}
		
		this.robot.getBoard().setCartographed(true);
		System.out.println(this.robot.getBoard());
		
		//System.out.println("foo");
		if (this.menu != null) {
			//System.out.println("Thread present");
			synchronized (this.menu) {
				//System.out.println("Thread notifie");
				this.menu.notify();
			}
		}
		Menu.envoieCarto();
	}
		
}
