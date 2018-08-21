package app.modele.deplacable;

import app.modele.alone.Niveau;
import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.tuile.Tuile;

public class ObjetPoussable extends Deplacable {

	public ObjetPoussable(Coordonnees cd, Joueur j) {
		super(cd, Direction.NORD, 50);
		this.getDirectionProperty().bind(j.getDirectionProperty());
	}


	public void deplacer (Niveau c) {
		//(Tuile.TAILLE / 50) = unite de deplacement, le cinquantieme de tuile
		Coordonnees cd = this.getCoord();
		int vitesse = this.getVitesse();
		if (c.estMarchable(Coordonnees.plusVitesse(this), this.getDirection())) {

			switch (this.getDirection()) {
			case Direction.NORD:
				cd.setCoordonnees(cd.getX(), cd.getY() - ((double)Tuile.TAILLE / 50 * vitesse));
				break;
			case Direction.EST:
				cd.setCoordonnees(cd.getX() + ((double)Tuile.TAILLE / 50 * vitesse), cd.getY());
				break;
			case Direction.SUD:
				cd.setCoordonnees(cd.getX(), (cd.getY() + ((double)Tuile.TAILLE / 50 * vitesse)));
				break;
			case Direction.OUEST:
				cd.setCoordonnees((cd.getX() - ((double)Tuile.TAILLE / 50 * vitesse)), cd.getY());
				break;
			}
		}
	}

}