package app.modele.deplacable;

import java.util.ArrayList;

import app.controleur.ThunderListener;
import app.modele.alone.Carte;
import app.modele.alone.Coordonnees;
import app.modele.alone.Niveau;
import app.modele.tuile.Tuile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class GrilainMeldas extends Ennemi {

	private String nom;
	private Niveau c;
	private int nbrAtk;
	private int temps;
	private Timeline atkLoop;
	private ObservableList<Thunder> eclair;
	private ArrayList<Coordonnees>CoordEclair;
	private ArrayList<Coordonnees>CoordTeleportable;
	public GrilainMeldas(Coordonnees cd,Niveau c) {
		super(cd,1, 10, 1600, 20);
		this.nom="Grilain Meldas";
		this.nbrAtk=0;
		this.eclair= FXCollections.observableArrayList();
		this.CoordEclair= new ArrayList<Coordonnees>();
		this.CoordTeleportable=new ArrayList<Coordonnees>();
		//cree en 40 /38
		this.initialiseListe();
		this.c=c;
		eclair.addListener(new ThunderListener());
		this.atkLooper();
		atkLoop.play();
	}
	private void initialiseListe() {
		for(int i=35;i<=45;i++) {
			for(int j=34;j<=44;j++) {
				if(!(i<38&&j<37||i>42&&j>41||i<38&&j>41||i>42&&j<37))
					this.CoordTeleportable.add(new Coordonnees(i*Tuile.TAILLE,j*Tuile.TAILLE));
			}
		}
	}
	public void atkLooper() {
		atkLoop = new Timeline();
		this.temps=0;
		atkLoop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.025), 
				(ev ->{	

					int i=0;
					if(temps%75==0) {
						this.attaquer();
					}
					for(int j=0;j<eclair.size();j++) {
						this.eclair.get(j).reduitVie();
					}
					for(int k=0;k<CoordEclair.size();k++) {
						this.CoordEclair.remove(k);
					}
					while(i<this.eclair.size()) {
						if(!this.eclair.get(i).existe()) {
							this.eclair.remove(i);
						}
						else
							i++;
					}
					if(this.getVie()<=0) {
						this.atkLoop.stop();
					}
					temps++;
				})
				);
		atkLoop.getKeyFrames().add(kf);
	}

	public void randomThunder(int nb) {
		int signe1;
		int signe2;
		int random1 ,random2;
		for(int i=0;i<nb;i++) {
			signe1=(int) ((Math.random()*10%2));
			signe2=(int) ((Math.random()*10%2));
			do {
				random1=(int) ((Math.random()*10%4));
				random1=random1*Tuile.TAILLE;
				random2=(int) ((Math.random()*10%4));
				random2=random2*Tuile.TAILLE;

			}while(random1==0 && random2==0);
			if(signe1==0&&signe2==0) {
				this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()-random1,this.getCoord().getY()-random2)));
				this.CoordEclair.add(new Coordonnees(this.getCoord().getX()-random1,this.getCoord().getY()-random2));
			}
			if(signe1==1&&signe2==0) {
				this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+random1,this.getCoord().getY()-random2)));
				this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+random1,this.getCoord().getY()-random2));
			}
			if(signe1==0&&signe2==1) {
				this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()-random1,this.getCoord().getY()+random2)));
				this.CoordEclair.add(new Coordonnees(this.getCoord().getX()-random1,this.getCoord().getY()+random2));
			}
			if(signe1==1&&signe2==1) {
				this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+random1,this.getCoord().getY()+random2)));
				this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+random1,this.getCoord().getY()+random2));
			}
		}
		this.c.attaquer(this.CoordEclair, 5);

	}
	private void projectileAll() {
		ProjectileMeldas p = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()-1,this.getCoord().getY()), 4, 10);
		c.ajouterProjectileMeldas(p);
		ProjectileMeldas p1 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()+1,this.getCoord().getY()), 2, 10);
		c.ajouterProjectileMeldas(p1);
		ProjectileMeldas p2 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX(),this.getCoord().getY()-1), 1, 10);
		c.ajouterProjectileMeldas(p2);
		ProjectileMeldas p3 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX(),this.getCoord().getY()+1), 3, 10);
		c.ajouterProjectileMeldas(p3);
	}
	@Override
	public ArrayList<Coordonnees> attaquer() {
		this.nbrAtk++;
		if(this.getVie()>1200) {
			if(this.nbrAtk%2==0) {
				this.projectileAll();
			}
			else {
				this.randomThunder(5);
			}
		}
		else if(this.getVie()>600) {
			if(this.nbrAtk%2==0) {
				this.randomThunder(10);
			}
			else {
				this.projectileAll();
				ProjectileMeldas p = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()-1*Tuile.TAILLE,this.getCoord().getY()-1*Tuile.TAILLE), 4, 10);
				c.ajouterProjectileMeldas(p);
				ProjectileMeldas p1 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()-1*Tuile.TAILLE,this.getCoord().getY()+1*Tuile.TAILLE), 4, 10);
				c.ajouterProjectileMeldas(p1);
				ProjectileMeldas p2 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()+1*Tuile.TAILLE,this.getCoord().getY()-1*Tuile.TAILLE), 2, 10);
				c.ajouterProjectileMeldas(p2);
				ProjectileMeldas p3 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()+1*Tuile.TAILLE,this.getCoord().getY()+1*Tuile.TAILLE), 2, 10);
				c.ajouterProjectileMeldas(p3);
				ProjectileMeldas p4 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()-1*Tuile.TAILLE,this.getCoord().getY()-1*Tuile.TAILLE), 1, 10);
				c.ajouterProjectileMeldas(p4);
				ProjectileMeldas p5 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()+1*Tuile.TAILLE,this.getCoord().getY()-1*Tuile.TAILLE), 1, 10);
				c.ajouterProjectileMeldas(p5);
				ProjectileMeldas p6 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()-1*Tuile.TAILLE,this.getCoord().getY()+1*Tuile.TAILLE), 3, 10);
				c.ajouterProjectileMeldas(p6);
				ProjectileMeldas p7 = new ProjectileMeldas(new Coordonnees(this.getCoord().getX()+1*Tuile.TAILLE,this.getCoord().getY()+1*Tuile.TAILLE), 3, 10);
				c.ajouterProjectileMeldas(p7);
			}
		}
		else {
			if(this.nbrAtk%4==0) {
				for(int i=-3;i<=3;i++) {
					if(i%2==1||i%2==-1) {


						for(int j=-3;j<=3;j=j+2) {
							this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE)));
							this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE));
						}
					}
					else {

						for(int j=-2;j<=2;j=j+2) {
							if(!(i==0&&j==0)) {
								this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE)));
								this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE));
							}
						}
					}
				}
				this.c.attaquer(this.CoordEclair, 5);
			}
			else if(this.nbrAtk%4==1||this.nbrAtk%4==3) {
				this.randomThunder(20);
			}
			else {
				for(int i=-3;i<=3;i++) {
					if(i%2==1||i%2==-1) {

						for(int j=-2;j<=2;j=j+2) {
							this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE)));
							this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE));

						}
					}
					else {
						for(int j=-3;j<=3;j=j+2) {
							this.eclair.add(new Thunder(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE)));
							this.CoordEclair.add(new Coordonnees(this.getCoord().getX()+i*Tuile.TAILLE,this.getCoord().getY()+j*Tuile.TAILLE));

						}
					}
				}
				this.c.attaquer(this.CoordEclair, 5);
			}
		}
		return null;
	}

	public ObservableList<Thunder> getEclair(){
		return this.eclair;
	}
	public void teleporteDelmas() {
		Coordonnees cd;
		do {
			int random=(int) ((Math.random()*100)%85);
			cd=this.CoordTeleportable.get(random);
		}while(cd==this.getCoord());
		this.setCoord(cd);
	}
	public void meurt() {
		this.setVie(0);
	}
}
