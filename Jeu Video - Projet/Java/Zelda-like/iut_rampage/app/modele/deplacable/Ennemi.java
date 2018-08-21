package app.modele.deplacable;

import java.util.ArrayList;

import app.modele.alone.Coordonnees;

public abstract class Ennemi extends Pnj {

	//TODO champ de vision BFSSSSSSSS
	private int degats;
	private int vie;

	public Ennemi(Coordonnees cd, int dir, int vit, int vie, int dgts) {
		super(cd, dir, vit);
		
		this.degats = dgts;
		this.vie = vie;
	}

	//---------GETTERS---------//
	public int getDgts() {
		return degats;
	}
	public int getVie() {
		return vie;
	}

	//---------SETTERS---------//
	public void setDgts(int nbDegats) {
		this.degats = nbDegats;
	}
	public void setVie(int vie) {
		this.vie = vie;
	}
	
	//---------Utilitaires----------//
	public void perdreVie(int valeur) {
		if (this.vie - valeur <= 0) {
			this.vie = 0;
			this.meurt();
		}
		else
			this.vie -= valeur;
	}
	public void gagnerVie(int valeur) {
		this.vie += valeur;
	}
	
	public abstract ArrayList<Coordonnees> attaquer();
	

}