package app.modele.statique;

import app.modele.deplacable.Joueur;

public class MachineACafe extends ObjetStatique {

	@Override
	public boolean utiliser(Joueur j) {
		if (j.getInventaire().retirerBitcoins(5))
			j.gagnerVie(5);
		return false;
	}

}
