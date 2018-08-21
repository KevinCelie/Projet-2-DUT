package app.vue;

import app.modele.alone.Niveau;
import app.modele.deplacable.Ennemi;
import app.modele.deplacable.GrilainMeldas;
import app.modele.deplacable.ObjetPoussable;
import app.modele.deplacable.Pnj;
import app.modele.deplacable.Projectile;
import app.modele.deplacable.ProjectileMeldas;
import app.modele.deplacable.Thunder;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class PaneDeplacablesVue extends Pane {

	private String cd = "/Images/cd.gif";
	private String squalala = "/Images/Laptopgarou.gif";
	private String thunder="/Images/thunder.gif";
	private Image thunderI=new Image(thunder);
	private String bouleFeu="/Images/bouleFeu.gif";
	private Image feu=new Image(bouleFeu);
	
	public PaneDeplacablesVue () {
		super();
	}
	
	public void viderPane () {
		while (this.getChildren().size() > 0) {
			this.getChildren().remove(0);
		}
	}
	
	public void setTaille(Niveau c) {
		this.setMinWidth(c.getCarte().getLargeurPixel());
		this.setMinHeight(c.getCarte().getHauteurPixel());
	}
	
	public DeplacableVue creerProjectileVue(Projectile p) {
		Image i = new Image(cd);
		DeplacableVue dV = new DeplacableVue(i, p);
		this.getChildren().add(dV);
		return dV;
	}
	
	public DeplacableVue creerPnjVue(Pnj p) {
		Image i = new Image("/Images/harcMompsF.gif");
		DeplacableVue dV = new DeplacableVue(i, p);
		this.getChildren().add(dV);
		return dV;
	}
	
	public DeplacableVue creerLaptopVue(Ennemi e) {
		Image i = new Image(squalala);
		DeplacableVue dV = new DeplacableVue(i, e);
		this.getChildren().add(dV);
		return dV;
	}
	public DeplacableVue creerUniteVue(Ennemi e) {
		Image i = new Image("/Images/unitecenrtrale.gif");
		DeplacableVue dV = new DeplacableVue(i, e);
		this.getChildren().add(dV);
		return dV;
	}

	public DeplacableVue creerObjetPousVue(ObjetPoussable o) {
		Image i = new Image("/Images/pierre.gif");
		DeplacableVue dV = new DeplacableVue(i, o);
		this.getChildren().add(dV);
		return dV;
	}
	

	public DeplacableVue creerGrilainVue(GrilainMeldas Gm) {
		Image i = new Image("/Images/Ssj0Delmas.gif");
		DeplacableVue dV = new DeplacableVue(i, Gm);
		this.getChildren().add(dV);
		return dV;
	}
	public DeplacableVue creerGrilain2Vue(GrilainMeldas Gm) {
		Image i = new Image("/Images/Ssj2Delmas.gif");
		DeplacableVue dV = new DeplacableVue(i, Gm);
		this.getChildren().add(dV);
		return dV;
	}
	public DeplacableVue creerGrilain3Vue(GrilainMeldas Gm) {
		Image i = new Image("/Images/Ssj3Delmas.gif");
		DeplacableVue dV = new DeplacableVue(i, Gm);
		this.getChildren().add(dV);
		return dV;
	}
	public DeplacableVue creerThunderVue(Thunder t) {
		Image i =thunderI;
		DeplacableVue dV = new DeplacableVue(i, t);
		this.getChildren().add(dV);
		return dV;
	}
	public DeplacableVue creerProjectileMVue(ProjectileMeldas pm) {
		Image i =feu;
		DeplacableVue dV = new DeplacableVue(i, pm);
		this.getChildren().add(dV);
		return dV;
	}

}
