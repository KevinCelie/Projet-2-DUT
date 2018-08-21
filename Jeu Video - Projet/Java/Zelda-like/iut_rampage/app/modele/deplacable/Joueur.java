package app.modele.deplacable;

import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.alone.Inventaire;
import app.modele.tuile.Tuile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Joueur extends Deplacable{
	
	private Inventaire inventaire;
	private String nom; //Invariant : entre 1 et 30
	private IntegerProperty vie; //Invariant : entre 0 et 100
	
	//Prend un nom et une position dans son constructeur
	public Joueur (String n) {
		super(new Coordonnees(Tuile.TAILLE * 6 , Tuile.TAILLE * 6), Direction.NORD, 10);
		this.inventaire = new Inventaire();
		this.vie = new SimpleIntegerProperty(100);
		
		//exception inutile
		if (n.length() < 1 || n.length() > 30)
			new IllegalArgumentException("Nom joueur vide ou trop long");
		else
			this.nom = n;
	}
	
	// --------GETTERS--------//
	public IntegerProperty getVieProperty () {
		return this.vie;
	}
	public String getNom() {
		return nom;
	}
	public Inventaire getInventaire() {
		return this.inventaire;
	}
	
	//Utilitaires
	public void perdreVie(int valeur) {
		if (this.vie.get() - valeur < 0) this.vie.set(0);
		else this.vie.set(vie.get()-valeur);
	}
	public void gagnerVie(int valeur) {
		if (this.vie.get() + valeur > 100) this.vie.set(100);
		else this.vie.set(vie.get()+valeur);
	}
	public void ajouterInventaire(Inventaire inv) {
		this.inventaire.ajouterInventaire(inv);
	}
	public void attaquer() {
		this.inventaire.utiliserArme(this.getCoord(), this.getDirection());
	}
	public void prendreArmeSuivante() {
		this.inventaire.prendreArmeSuivante();
	}
	
	public void afficherInventaire() {
		System.out.println(this.inventaire);
	}
}