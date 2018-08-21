package app.modele.deplacable;

import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.tuile.Tuile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Deplacable {

	private Coordonnees cd;
	private IntegerProperty direction;
	private int vitesse;
	private boolean existe;

	public Deplacable (Coordonnees cd, int dir, int vit) {
		this.cd = cd;
		this.direction = new SimpleIntegerProperty(dir);
		this.vitesse = vit;
		this.existe = true;
	}

	//----------GETTERS----------//
	public Coordonnees getCoord() {
		return cd;
	}
	public IntegerProperty getDirectionProperty() {
		return direction;
	}
	public int getDirection() {
		return direction.get();
	}
	public int getVitesse() {
		return this.vitesse;
	}
	public boolean existe() {
		return this.existe;
	}

	//----------SETTERS-----------//
	public void setCoord(Coordonnees cd) {
		this.cd.setCoordonnees(cd.getX(), cd.getY());
	}
	public void setDirection(int dir) {
		this.direction.set(dir);
	}
	public void setVitesse(int vit) {
		this.vitesse = vit;
	}
	public void meurt () {
		this.existe = false;
	}

	//----------Utilitaires---------//
	
	public void deplacer () {
		//(Tuile.TAILLE / 50) = unite de deplacement, le cinquantieme de tuile
		switch (this.direction.get()) {
		case Direction.NORD:
			this.cd.setCoordonnees(cd.getX(), cd.getY() - ((double)Tuile.TAILLE / 50 * this.vitesse));
			break;
		case Direction.EST:
			this.cd.setCoordonnees(cd.getX() + ((double)Tuile.TAILLE / 50 * this.vitesse), cd.getY());
			break;
		case Direction.SUD:
			this.cd.setCoordonnees(cd.getX(), (cd.getY() + ((double)Tuile.TAILLE / 50 * this.vitesse)));
			break;
		case Direction.OUEST:
			this.cd.setCoordonnees((cd.getX() - ((double)Tuile.TAILLE / 50 * this.vitesse)), cd.getY());
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Cd : " + this.cd + " Vit : " + this.vitesse;
	}
}
