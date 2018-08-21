package app.modele;

import java.util.ArrayList;

import app.modele.alone.Carte;
import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.alone.Inventaire;
import app.modele.alone.Niveau;
import app.modele.deplacable.Joueur;
import app.modele.deplacable.Pnj;
import app.modele.statique.PorteVerrouillable;
import app.modele.statique.SacAToutou;
import app.modele.tuile.Tuile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Partie {

	private ArrayList<Niveau> listeDeCartes;
	private IntegerProperty indiceCarteActuelle;
	private Joueur joueur;
	private Pnj momps;


	public Partie() {
		this.listeDeCartes = new ArrayList<Niveau>();
		this.indiceCarteActuelle = new SimpleIntegerProperty(-1);
		this.joueur = new Joueur("Dave Lopper");
		
		this.listeDeCartes.add(new Niveau(Carte.chargerMatrice("iut_rampage/matrice/b1rdc.json"),this.joueur));
		this.listeDeCartes.get(0).creerPorteTeleportable(33, 42, 19, this, 1,24,24);
		this.listeDeCartes.add(new Niveau(Carte.chargerMatrice("iut_rampage/matrice/donjon.json"),this.joueur));
		this.listeDeCartes.get(1).creerPorteTeleportable(2, 25, 19, this, 2, 42, 42);
		this.listeDeCartes.add(new Niveau(Carte.chargerMatrice("iut_rampage/matrice/batailleGrilain.json"),this.joueur));
		
		

	}
	
	//GETTERS
	public Niveau getNiveau() {
		return this.listeDeCartes.get(this.indiceCarteActuelle.get());
	}
	public IntegerProperty getIndiceNiveauActuelProperty() {
		return this.indiceCarteActuelle;
	}
	public Joueur getJoueur() {
		return joueur;
	}
	public void creerGrilain() {
		this.listeDeCartes.get(2).creerMeldas(new Coordonnees(38,38));
	}
	public void setNiveau(String url) {
		this.listeDeCartes.add(new Niveau(Carte.chargerMatrice("iut_rampage/matrice/" + url + ".json"), this.joueur));
	}
	public void setIndiceNiveauActuel(int indice) {
		this.indiceCarteActuelle.set(indice);
	}


	//UTILITAIRES
	//retourne si le deplacement a reussi


}





