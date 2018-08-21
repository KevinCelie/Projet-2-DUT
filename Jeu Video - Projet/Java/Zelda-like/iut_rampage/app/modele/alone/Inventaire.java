package app.modele.alone;

import app.modele.arme.Arme;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventaire {

	private IntegerProperty bitcoins;
	private ObservableList<String> clefs;
	private ObservableList<Arme> armes;
	private ObservableList<String> objetsQuetes;
	private IntegerProperty armeActuelle;

	//un inventaire vide
	public Inventaire () {
		this.bitcoins = new SimpleIntegerProperty(0);
		this.clefs = FXCollections.observableArrayList();
		this.armes = FXCollections.observableArrayList();
		this.objetsQuetes = FXCollections.observableArrayList();	
		this.armeActuelle = new SimpleIntegerProperty(0);
	}

	//GETTERS
	public ObservableList<String> getClefs() {
		return this.clefs;
	}
	public ObservableList<Arme> getArmes() {
		return this.armes;
	}
	public ObservableList<String> getObjetsQuetes() {
		return this.objetsQuetes;
	}
	public Arme getArmeActuelle () {
		return this.armes.get(this.armeActuelle.get());
	}
	public IntegerProperty getArmeActuelleProperty () {
		return this.armeActuelle;
	}
	public IntegerProperty getBitcoinsProperty() {
		return this.bitcoins;
	}

	//UTILITAIRES
	public void ajouterInventaire(Inventaire inv) {
		this.ajouterBitcoins(inv.bitcoins.get());
		for (String c : inv.clefs)
			if (!this.clefs.contains(c)) this.clefs.add(c);
		for (String o : inv.objetsQuetes)
			if (!this.objetsQuetes.contains(o)) this.objetsQuetes.add(o);
		for (Arme a : inv.armes)
			if (!this.armes.contains(a)) this.armes.add(a);
		System.out.println(this);
	}

	public boolean ajouterBitcoins(int quantite) {
		this.bitcoins.set(this.bitcoins.get() + quantite);
		return true;
	}
	
	public boolean retirerBitcoins(int quantite) {
		if (this.bitcoins.get() - quantite >= 0) {
			this.bitcoins.set(this.bitcoins.get() - quantite);
			return true;
		}
		else {
			System.out.println("Pas assez de money");
			return false;
		}
	}

	public void ajouterArme(Arme arme) {
		this.armes.add(arme);
	}

	public void changerArme(int indiceArmeActuelle) {
		if (0 <= indiceArmeActuelle && indiceArmeActuelle < this.armes.size())
			this.armeActuelle.set(indiceArmeActuelle);
	}
	
	public void prendreArmeSuivante() {
		if (this.armeActuelle.get() == this.armes.size()-1)
			this.changerArme(0);
		else
			this.changerArme(this.armeActuelle.get()+1);
	}
	
	public void utiliserArme(Coordonnees cdJoueur, int dir) {
		if (this.getArmeActuelle() != null)
			this.getArmeActuelle().attaquer(cdJoueur, dir);
	}
	
 	public String toString() {
		String retour = "BITCOINS : " +this.bitcoins.get();
		retour += ", CLEFS :";
		for (String c : this.clefs)
			retour += " " + "(" + c + ")";
		retour += ", OBJETSQUETE :";
		for (String o : this.objetsQuetes)
			retour += " " + "(" + o + ")";
		retour += ", ARMES :";
		for (Arme a : armes)
			retour += " " + "(" + a + ")";
		retour += ".";
		return retour;
	}

}