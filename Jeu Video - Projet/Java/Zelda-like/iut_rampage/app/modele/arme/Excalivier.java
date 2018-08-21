package app.modele.arme;

import java.util.ArrayList;

import app.modele.Partie;
import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.tuile.Tuile;


public class Excalivier extends Arme {

	public Excalivier(Partie carte) {
		super("Excalivier", 20, carte);
	}

	@Override
	public void attaquer(Coordonnees cdJoueur, int dir) {
	
		ArrayList<Coordonnees> ptsAtt = new ArrayList<Coordonnees>();
		
		if (dir == Direction.NORD) {
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuHaut().getX() - (Tuile.TAILLE/6), cdJoueur.getCoordonneesMilieuHaut().getY() - Tuile.TAILLE));
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuHaut().getX() + (Tuile.TAILLE/6), cdJoueur.getCoordonneesMilieuHaut().getY() - Tuile.TAILLE));
		}
		else if (dir == Direction.EST) {
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuDroit().getX() + Tuile.TAILLE, cdJoueur.getCoordonneesMilieuDroit().getY() - (Tuile.TAILLE/6)));
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuDroit().getX() + Tuile.TAILLE, cdJoueur.getCoordonneesMilieuDroit().getY() + (Tuile.TAILLE/6)));
		}
		else if (dir == Direction.SUD) {
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuBas().getX() - (Tuile.TAILLE/6), cdJoueur.getCoordonneesMilieuBas().getY() + Tuile.TAILLE));
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuBas().getX() + (Tuile.TAILLE/6), cdJoueur.getCoordonneesMilieuBas().getY() + Tuile.TAILLE));
		}
		else {
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuGauche().getX() - Tuile.TAILLE, cdJoueur.getCoordonneesMilieuHaut().getY() - (Tuile.TAILLE/6)));
			ptsAtt.add(new Coordonnees(cdJoueur.getCoordonneesMilieuGauche().getX() - Tuile.TAILLE, cdJoueur.getCoordonneesMilieuHaut().getY() + (Tuile.TAILLE/6)));
		}
		
		this.getCarte().getNiveau().attaquer(ptsAtt, getDgts());
		
	}


}