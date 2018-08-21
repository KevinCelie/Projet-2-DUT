package app.modele.deplacable;

import app.modele.alone.Coordonnees;
import javafx.animation.Timeline;

public class Thunder extends Deplacable{
	private Coordonnees c;
	private int degat;
	private Timeline atkLoop;
	private int temps;
	public Thunder(Coordonnees c) {
		super(c,1,10);
		this.degat=10;
		this.temps=10;
	}
	public void reduitVie() {
		this.temps--;
		if(this.temps==0)
			this.meurt();
	}
	public int getDegat() {
		return this.degat;
	}
	

}
