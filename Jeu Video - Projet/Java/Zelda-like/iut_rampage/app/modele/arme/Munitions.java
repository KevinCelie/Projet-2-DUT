package app.modele.arme;

import app.modele.Partie;
import app.modele.alone.Coordonnees;

public class Munitions extends Arme {

	private int quantite;
	
	public Munitions(Partie carte) {
		super("CD Windows", 10, carte);
		this.quantite = 0;
	}
	public Munitions(Partie carte, int quantite) {
		super("CD Windows", 10, carte);
		this.quantite = quantite;
	}

	@Override
	public void attaquer(Coordonnees cdJoueur, int dir) {
		if (this.quantite > 0) {
			this.getCarte().getNiveau().creerProjectile(cdJoueur, dir);
			this.quantite--;
		}
	}
	
	public String toString() {
		return this.getNom() + " x " + this.quantite + " qui fait " + this.getDgts() + " degats";
	}

}