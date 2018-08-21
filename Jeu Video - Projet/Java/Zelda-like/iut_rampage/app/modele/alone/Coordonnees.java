package app.modele.alone;

import app.modele.deplacable.Deplacable;
import app.modele.tuile.Tuile;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Coordonnees {

	private DoubleProperty x, y;
	
	public Coordonnees(int x, int y) {
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
	}
	public Coordonnees(double x, double y) {
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
	}
	public Coordonnees(Coordonnees coord) {
		this.x = new SimpleDoubleProperty(coord.getX());
		this.y = new SimpleDoubleProperty(coord.getY());
	}
	
	//-----GETTERS-----//
	public DoubleProperty getXProperty() {
		return x;
	}
	public DoubleProperty getYProperty() {
		return y;
	}
	public double getX() {
		return x.get();
	}
	public double getY() {
		return y.get();
	}

	//-----SETTERS-----//
	public void setX(double x) {
		this.x.set(x);
	}
	public void setY(double y) {
		this.y.set(y);
	}
	
	//------UTILITAIRES----------//
	public void setCoordonnees(double x, double y) {
		this.x.set(x);
		this.y.set(y);
	}
	public void setCoordonnes(int x, int y) {
		this.x.set((double) x);
		this.y.set((double) y);
	}
	
	//Point specifique d'une tuile (en recevant son origine)
	public Coordonnees getCoordonneesCoinHautDroit() {
		return new Coordonnees(this.x.get() + (Tuile.TAILLE-1), this.y.get());
	}
	public Coordonnees getCoordonneesCoinBasDroit() {
		return new Coordonnees(this.x.get() + (Tuile.TAILLE-1), this.y.get() + (Tuile.TAILLE-1));
	}
	public Coordonnees getCoordonneesCoinBasGauche() {
		return new Coordonnees(this.x.get(), this.y.get() + (Tuile.TAILLE-1));
	}
	public Coordonnees getCoordonneesMilieuHaut() {
		return new Coordonnees(this.x.get() + (Tuile.TAILLE / 2 -1), this.y.get());
	}
	public Coordonnees getCoordonneesMilieuDroit() {
		return new Coordonnees(this.x.get() + (Tuile.TAILLE -1), this.y.get() + (Tuile.TAILLE / 2 -1));
	}
	public Coordonnees getCoordonneesMilieuBas() {
		return new Coordonnees(this.x.get() + (Tuile.TAILLE / 2 -1), this.y.get() + (Tuile.TAILLE-1));
	}
	public Coordonnees getCoordonneesMilieuGauche() {
		return new Coordonnees(this.x.get(), this.y.get() + (Tuile.TAILLE / 2 -1));
	}
	public Coordonnees getCoordonneesMilieu() {
		return new Coordonnees(this.getX() + (Tuile.TAILLE /2), this.getY() + (Tuile.TAILLE/2));
	}
	
	//Les coord en tuile de la coord
	public int getTuileLigne() {
		return (int)(this.y.get() - this.y.get() % Tuile.TAILLE) / Tuile.TAILLE;
	}
	public int getTuileColonne() {
		return (int)(this.x.get() - this.x.get() % Tuile.TAILLE) / Tuile.TAILLE;
	}
	
	//Les methodes "hitbox"
	public boolean estDansCarre(Coordonnees origineCarre) {
		if (this.getX() >= origineCarre.getX() && this.getX() <= origineCarre.getCoordonneesCoinHautDroit().getX() &&
			this.getY() >= origineCarre.getY() && this.getY() <= origineCarre.getCoordonneesCoinBasGauche().getY())
			return true;
		else return false;
	}
	
	public static Coordonnees plusVitesse(Deplacable d) {
		Coordonnees cdRetour = new Coordonnees(0,0);
		if (d.getDirection() == Direction.NORD) {
			cdRetour.setCoordonnees(d.getCoord().getX(), d.getCoord().getY() - ((double)Tuile.TAILLE / 50 * d.getVitesse()));
		}
		else if (d.getDirection() == Direction.EST) {
			cdRetour.setCoordonnees(d.getCoord().getX() + ((double)Tuile.TAILLE / 50 * d.getVitesse()), d.getCoord().getY());
		}
		else if (d.getDirection() == Direction.SUD) {
			cdRetour.setCoordonnees(d.getCoord().getX(), d.getCoord().getY() + ((double)Tuile.TAILLE / 50 * d.getVitesse()));
		}
		else {
			cdRetour.setCoordonnees(d.getCoord().getX() - ((double)Tuile.TAILLE / 50 * d.getVitesse()), d.getCoord().getY());
		}
		return cdRetour;
	}
	
	public String toString () {
		return "x : " + this.x.get() + " y : " + this.y.get();
		
	}
	

}