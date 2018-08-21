package app.modele.statique;

import app.modele.alone.Coordonnees;
import app.modele.deplacable.Joueur;
import app.modele.tuile.Tuile;

public class Porte extends ObjetStatique {
	
	private Coordonnees cdArrive;
	
	//cdDepart et id : pr calculer les coord d'arrivee
	public Porte(Coordonnees cdDepart, int id) {
		switch (id) {
		case 19:
			this.cdArrive = new Coordonnees(cdDepart.getX(), cdDepart.getY() - 3 * Tuile.TAILLE);
			break;
		case 21:
			this.cdArrive = new Coordonnees(cdDepart.getX() + 3 * Tuile.TAILLE, cdDepart.getY());
			break;
		case 15:
			this.cdArrive = new Coordonnees(cdDepart.getX(), cdDepart.getY() + 3 * Tuile.TAILLE);
			break;
		case 20:
			this.cdArrive = new Coordonnees(cdDepart.getX() - 3 * Tuile.TAILLE, cdDepart.getY());
			break;
		default:
			break;
		}
	}
	
	//-----UTILITAIRES-----//
	@Override
	public boolean utiliser(Joueur j) {
		j.setCoord(cdArrive);
		return false;
	}
	
}
