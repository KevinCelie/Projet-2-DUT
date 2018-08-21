package app.vue;

import app.modele.alone.Carte;
import app.modele.alone.Coordonnees;
import app.modele.tuile.Tuile;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

public class CarteVue extends TilePane {

	//private Carte carte; //Stockee car on sait jamais
	private Image tileset;

	public CarteVue () {
		super();
		tileset = new Image("/Images/tileset.png");
	}

	//SETTERS
	public void setCarte(Carte c) {
		this.chargerCarte(c.getRamMap(), c.getLargeurPixel(), c.getHauteurPixel());
	}
	
	//UTILITAIRES
	//Vide la tilepane, la redimensionne et la reremplit
	private void  chargerCarte(Tuile[][] ram_map, int largeurEnPixel, int hauteurEnPixel) {

		while (this.getChildren().size() > 0) {
			this.getChildren().remove(0);
		}

		this.setMinWidth(largeurEnPixel);
		this.setMinHeight(hauteurEnPixel);

		for (Tuile ligne[] : ram_map) {
			for (Tuile n : ligne) {
				TuileVue tuile = new TuileVue(tileset);
				tuile.setViewport(n.getID());
				this.getChildren().add(tuile);
				tuile.setPreserveRatio(true);
				tuile.setFitWidth(Tuile.TAILLE);
				
			}
		}
	}
	public void updateTuile(Coordonnees cd, Tuile t) {
		TuileVue im = (TuileVue) this.getChildren().get((int) (cd.getTuileLigne() * (this.getWidth() / Tuile.TAILLE) + cd.getTuileColonne()));
		im.setViewport(t.getID());
	}
}
