package app.modele.statique;

import app.modele.Partie;
import app.modele.alone.Coordonnees;
import app.modele.deplacable.Joueur;

public class PorteTeleportable extends Porte {
	private Coordonnees cdArrive;
	private Partie partie;
	private int nbcarte;
	public PorteTeleportable(Coordonnees cdDepart, int id,Coordonnees cdArrive, Partie partie,int nbcarte) {
		super(cdDepart, id);
		this.cdArrive=cdArrive;
		this.partie=partie;
		this.nbcarte=nbcarte;
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean utiliser(Joueur j) {
		this.partie.setIndiceNiveauActuel(nbcarte);
		j.setCoord(cdArrive);
		return false;
	}
}
