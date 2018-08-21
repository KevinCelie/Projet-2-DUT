package app.vue;

import app.modele.deplacable.Deplacable;
import app.modele.tuile.Tuile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeplacableVue extends ImageView {

	private Image apparence;
	
	public DeplacableVue (Image image, Deplacable deplacable) {
		super(image);
		this.apparence = image;
		this.setX(deplacable.getCoord().getX());
		this.setY(deplacable.getCoord().getY());
		this.setFitHeight(Tuile.TAILLE);
		this.setFitWidth(Tuile.TAILLE);
	}

}
