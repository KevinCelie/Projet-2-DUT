package app.modele.arme;

import app.modele.Partie;
import app.modele.alone.Coordonnees;
import app.modele.alone.Niveau;

public abstract class Arme {

	private String nom;
	private int degats;
	private Partie carte;

	public Arme(String nom, int degats, Partie carte) {
		super();
		this.nom = nom;
		this.degats = degats;
		this.carte = carte;
	}
	
	//GETTERS
	public String getNom() {
		return this.nom;
	}
	
	//UTILITAIRES
	public String toString() {
		return this.nom + " qui fait " + this.degats + " degats";
	}
	public int getDgts() {
		return this.degats;
	}
	protected Partie getCarte() {
		return this.carte;
	}
	
	public abstract void attaquer(Coordonnees cdJoueur, int dir);

}