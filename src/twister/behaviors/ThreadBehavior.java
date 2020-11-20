package twister.behaviors;

import lejos.robotics.subsumption.Behavior;

/**
 * Classe abstraite representant un Behavior contenant un Thread.
 * 
 * @author nicolas-carbonnier
 *
 */
public abstract class ThreadBehavior implements Behavior {
	protected Thread thread;
	
	/**
	 * Setter pour le Thread.
	 * 
	 * @param _thread Thread.
	 */
	public void setThread(Thread _thread) {
		this.thread = _thread;
	}
}
