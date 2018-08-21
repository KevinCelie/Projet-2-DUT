package app.modele.statique;

import app.modele.alone.Inventaire;
import app.modele.deplacable.Joueur;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SacAToutou extends ObjetStatique {

	private Inventaire contenu;
	private BooleanProperty existe; //TODO : binder pr qu'il s'autodetruise
	
	public SacAToutou(Inventaire contenu) {
		this.contenu = contenu;
		this.existe = new SimpleBooleanProperty(true);
	}
	public SacAToutou() {
		//TODO : des objets random
		this.contenu = new Inventaire();
		this.existe = new SimpleBooleanProperty(true);
	}
	
	//------UTILITAIRES-------//
	@Override
	public boolean utiliser(Joueur j) {
		j.ajouterInventaire(this.contenu);
		this.existe.set(false);
		
		return true;
	}

}
