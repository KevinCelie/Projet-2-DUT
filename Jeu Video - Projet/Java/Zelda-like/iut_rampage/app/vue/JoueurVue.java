package app.vue;

import app.modele.deplacable.Joueur;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JoueurVue extends ImageView {


	//private Joueur joueur; //Stocke car on sait jamais
	private Image tileSetJoueur;

	private Image persoFace=new Image("Images/persoF.gif");
	private Image persoDos=new Image("Images/persoD.gif");
	private Image persoDroite=new Image("Images/persoDr.gif");
	private Image persoGauche=new Image("Images/persoG.gif");



	private Image mompsFace=new Image("Images/harcMompsF.gif");
	private Image mompsDos=new Image("Images/harcMompsD.gif");

	private double X= this.getX();
	private double Y= this.getY();
	public JoueurVue() {
		super();

		//Chargement et definition de l'apparence du joueur
		this.tileSetJoueur = new Image("Images/animPerso.png"); //TODO : vrai chemin du tileSet du perso
		this.setImage(tileSetJoueur);
		this.setPreserveRatio(true);
		this.setFitWidth(50);
		//this.setViewport(new Rectangle2D(Tuile.TAILLE*((-1)%10), Tuile.TAILLE*(int)((n-1)/10), Tuile.TAILLE, Tuile.TAILLE));

	}


	public void setJoueur(Joueur j) {

		this.setViewport(new Rectangle2D(50 * (int)(j.getDirection()-1), 0, 50, 50));

		//Definition de la position du Joueur
		this.setX(j.getCoord().getX());
		this.setY(j.getCoord().getY());

	}

	//-----UTILITAIRES-----//
	public void lancerAnim(int dir) {
		this.setViewport(new Rectangle2D(0, 0, 50, 50));
		switch (dir) {	
		case 1 :				
			this.setImage(persoDos);
			break;
		case 4 :
			this.setImage(persoGauche);
			break;
		case 3 :
			this.setImage(persoFace);
			break;		
		case 2 :
			this.setImage(persoDroite);
			break;
		}
	}
	private Timeline GIFLoop ;
	private int temps;
	
	public void lancerAnimAttaque(Joueur j) {
		int dir = j.getDirection();
		int arme = j.getInventaire().getArmeActuelleProperty().get();
		
		switch(dir) {
		case 1 :				
			switch(arme) {
			case 0: 
				this.setImage(new Image("Images/atkfront.gif"));
				this.setViewport(new Rectangle2D(0, 0, 50, 100));
				this.setLayoutY(Y-50);
				break;
			case 1:
				break;
			case 2:
				break;
			}

			break;
		case 2 :
			switch(arme) {
			case 0:
				this.setViewport(new Rectangle2D(0, 0, 50, 100));
				this.setImage(new Image("Images/atkcoted.gif"));
				break;
			case 1:
				break;
			case 2:
				break;
			}
			break;
		case 3 :
			switch(arme) {
			case 0: 
				this.setViewport(new Rectangle2D(0, 0, 50, 100));
				this.setImage(new Image("Images/atkBack.gif"));
				break;
			case 1:
				break;
			case 2:
				break;
			}
			break;		
		case 4 :
			switch(arme) {
			case 0:
				this.setViewport(new Rectangle2D(0, 0, 100, 50));
				this.setImage(new Image("Images/atkcoteg.gif"));
				this.setFitWidth(100);
				this.setLayoutX(X-50); 
				break;
			case 1:
				break;
			case 2:
				break;
			}
			break;
		}
	}

	public void stopAnim(int dir) {
		this.setFitWidth(50);
		this.setImage(tileSetJoueur);
		this.setLayoutX(X);
		this.setLayoutY(Y);
		this.setViewport(new Rectangle2D(50 * (int)(dir-1), 0, 50, 50));
		this.setPreserveRatio(true);

	}
}
