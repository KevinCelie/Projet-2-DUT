package app.modele.deplacable;

import java.util.ArrayList;

import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.tuile.Tuile;

public class LaptopGarou extends Ennemi {

	public LaptopGarou(Coordonnees cd) {
		super(cd, Direction.NORD, 5, 20, 3);
	}
	
	@Override
	public ArrayList<Coordonnees> attaquer() {
		
		ArrayList<Coordonnees> ptsAtt = new ArrayList<Coordonnees>();
		
		if (this.getDirection() == Direction.NORD) {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuHaut().getX(), this.getCoord().getCoordonneesMilieuHaut().getY() - (Tuile.TAILLE/2)));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuHaut().getX(), this.getCoord().getCoordonneesMilieuHaut().getY() - (Tuile.TAILLE/2)));
			return ptsAtt;
		}
		else if (this.getDirection() == Direction.EST) {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuDroit().getX() + (Tuile.TAILLE/2), this.getCoord().getCoordonneesMilieuDroit().getY()));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuDroit().getX() + (Tuile.TAILLE/2), this.getCoord().getCoordonneesMilieuDroit().getY()));
			return ptsAtt;
		}
		else if (this.getDirection() == Direction.SUD) {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuBas().getX(), this.getCoord().getCoordonneesMilieuBas().getY() + (Tuile.TAILLE/2)));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuBas().getX(), this.getCoord().getCoordonneesMilieuBas().getY() + (Tuile.TAILLE/2)));
			return ptsAtt;
		}
		else {
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuGauche().getX() - (Tuile.TAILLE/2), this.getCoord().getCoordonneesMilieuHaut().getY()));
			ptsAtt.add(new Coordonnees(this.getCoord().getCoordonneesMilieuGauche().getX() - (Tuile.TAILLE/2), this.getCoord().getCoordonneesMilieuHaut().getY()));
			return ptsAtt;
		}
	}

}
