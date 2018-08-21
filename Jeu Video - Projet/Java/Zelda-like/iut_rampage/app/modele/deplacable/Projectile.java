package app.modele.deplacable;

import java.util.ArrayList;

import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.tuile.Tuile;

public class Projectile extends Deplacable {
	
	private int dgts;
	
	public Projectile (Coordonnees cd, int dir, int vit, int dgts) {
		super(cd, dir, vit);
		this.dgts = dgts;
	}
	
	public int getDegats() {
		return this.dgts;
	}
	
	public ArrayList<Coordonnees> attaquer() {
		ArrayList<Coordonnees> ptsAtt = new ArrayList<Coordonnees>();
		
		if (this.getDirection() == Direction.NORD) {
			ptsAtt.add(new Coordonnees(this.getCoord().getX(), this.getCoord().getY() - (Tuile.TAILLE/2)));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinHautDroit().getX(), this.getCoord().getCoordonneesCoinHautDroit().getY() - (Tuile.TAILLE/2)));
			return ptsAtt;
		}
		else if (this.getDirection() == Direction.EST) {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinHautDroit().getX() + (Tuile.TAILLE/2), this.getCoord().getCoordonneesCoinHautDroit().getY()));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinBasDroit().getX() + (Tuile.TAILLE/2), this.getCoord().getCoordonneesCoinBasDroit().getY()));
			return ptsAtt;
		}
		else if (this.getDirection() == Direction.SUD) {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinBasDroit().getX(), this.getCoord().getCoordonneesCoinBasDroit().getY() + (Tuile.TAILLE/2)));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinBasGauche().getX(), this.getCoord().getCoordonneesCoinBasGauche().getY() + (Tuile.TAILLE/2)));
			return ptsAtt;
		}
		else { //Direction.OUEST
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesCoinBasGauche().getX() - (Tuile.TAILLE/2), this.getCoord().getCoordonneesCoinBasGauche().getY()));
			ptsAtt.add(new Coordonnees(this.getCoord().getX() - (Tuile.TAILLE/2), this.getCoord().getY()));
			return ptsAtt;
		}
	}
}