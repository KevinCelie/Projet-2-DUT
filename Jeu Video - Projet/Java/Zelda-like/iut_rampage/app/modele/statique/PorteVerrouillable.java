package app.modele.statique;

import app.modele.alone.Coordonnees;
import app.modele.deplacable.Joueur;

public class PorteVerrouillable extends Porte {
	
	protected boolean estVerrouille;
	private String clefRequise;

	public PorteVerrouillable(Coordonnees cdDepart, int id, String clefReq) {
		super(cdDepart, id);
		this.clefRequise = clefReq;
		this.estVerrouille = true;
	}
	
	//-----Utilitaires-----//
	public boolean deverrouille(String clef) {
		if (clef.equals(clefRequise)) {
			this.estVerrouille = false;
			return true;
		}
		else return false;
	}

	@Override
	public boolean utiliser(Joueur j) {
		if (estVerrouille) {
			for (String clef : j.getInventaire().getClefs())
				if (this.deverrouille(clef)) super.utiliser(j);
		}
		else super.utiliser(j);
		return false;
	}

}
