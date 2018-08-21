package app.modele.deplacable;

import java.util.ArrayList;

import app.modele.alone.Niveau;
import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;

public class UniteCentrale extends Ennemi {

	private Niveau carte;
	private int timerAtt;
	
	public UniteCentrale(Coordonnees cd, Niveau carte) {
		super(cd, Direction.NORD, 0, 80, 15);
		this.carte = carte;
		this.timerAtt = 0;
	}
	
	public void decrementerTimerAtt() {
		if (this.timerAtt > 0)
			this.timerAtt--;
	}
	
	@Override
	public ArrayList<Coordonnees> attaquer() {
		if (timerAtt == 0) {
			this.carte.creerProjectile(this.getCoord(), this.getDirection());
			this.timerAtt = 20; //delai avant la prochaine attaque
		}
		return null;
	}

}