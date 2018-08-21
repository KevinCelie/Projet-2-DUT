
package app.modele.alone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import app.modele.Partie;
import app.modele.deplacable.Deplacable;
import app.modele.deplacable.Ennemi;
import app.modele.deplacable.GrilainMeldas;
import app.modele.deplacable.Joueur;
import app.modele.deplacable.LaptopGarou;
import app.modele.deplacable.ObjetPoussable;
import app.modele.deplacable.Pnj;
import app.modele.deplacable.Projectile;
import app.modele.deplacable.ProjectileMeldas;
import app.modele.deplacable.UniteCentrale;
import app.modele.statique.PorteTeleportable;
import app.modele.tuile.Tuile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Niveau {

	private Carte carte;

	private Joueur joueur;

	private ObservableList<Projectile> projectiles;
	private ObservableList<Pnj> pnjs;
	private ObservableList<LaptopGarou> laptop;
	private ObservableList<UniteCentrale> unite;
	private ObservableList<ObjetPoussable> poussePousse;
	private ObservableList<GrilainMeldas> bossEstLa;
	private ObservableList<ProjectileMeldas> projectilesM;


	public Niveau(Tuile[][] ram_map, Joueur j) {
		this.carte = new Carte(ram_map);
		this.joueur = j;

		this.projectiles = FXCollections.observableArrayList();
		this.pnjs = FXCollections.observableArrayList();
		this.laptop = FXCollections.observableArrayList();
		this.unite = FXCollections.observableArrayList();
		this.poussePousse = FXCollections.observableArrayList();
		this.bossEstLa= FXCollections.observableArrayList();
		this.projectilesM = FXCollections.observableArrayList();
	}

	//GETTERS
	public Carte getCarte() {
		return this.carte;
	}
	public ObservableList<Projectile> getDeplacables() {
		return this.projectiles;
	}
	public ObservableList<Pnj> getPnjs() {
		return this.pnjs;
	}
	public ObservableList<LaptopGarou> getLaptop() {
		return this.laptop;
	}
	public ObservableList<UniteCentrale> getUnite() {
		return this.unite;
	}
	public ObservableList<ObjetPoussable> getObjetPoussable () {
		return this.poussePousse;
	}
	public ObservableList<GrilainMeldas> getBossEstLa() {
		return this.bossEstLa;
	}
	public ObservableList<ProjectileMeldas> getProjectileM() {
		return this.projectilesM;
	}
	

	//CHARGEMENT
	public void chargerDeplacableEtStatique (String url) {

		BufferedReader in;
		String line;
		int idDeplacable, posLigne, posColonne ;

		try {
			in = new BufferedReader(new FileReader(url));

			try {
				while((line = in.readLine())!= null) {
					idDeplacable = -1;
					posLigne = -1;
					posColonne = -1;
					for (String id : line.split(" ")) {

						if (idDeplacable == -1)
							idDeplacable = Integer.parseInt(id);

						else if (posColonne == -1)
							posColonne = Integer.parseInt(id);

						else
							posLigne = Integer.parseInt(id);
					}

					if (idDeplacable == 1) 
					{
						this.creerLaptopGarou (new Coordonnees(posColonne * Tuile.TAILLE, posLigne * Tuile.TAILLE)); 
					}
					else if (idDeplacable == 2) 
					{
						this.creerUniteCentrale(new Coordonnees(posColonne * Tuile.TAILLE, posLigne * Tuile.TAILLE));
					}
					else if (idDeplacable == 5) 
					{
						this.creerObjetPoussable(new Coordonnees(posColonne * Tuile.TAILLE, posLigne * Tuile.TAILLE));
					}
					else if (idDeplacable == 6) 
					{
						this.creerPnj(new Coordonnees(posColonne * Tuile.TAILLE, posLigne * Tuile.TAILLE));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//CREATION
	public void creerPnj(Coordonnees cd) {
		Pnj p = new Pnj(new Coordonnees(cd), Direction.SUD, 5);
		this.pnjs.add(p);
	}
	public void creerLaptopGarou(Coordonnees cd) {
		LaptopGarou lG = new LaptopGarou(new Coordonnees(cd));
		this.laptop.add(lG);
	}
	public void creerUniteCentrale(Coordonnees cd) {
		UniteCentrale uC = new UniteCentrale(new Coordonnees(cd), this);
		this.unite.add(uC);
	}
	public void creerObjetPoussable(Coordonnees cd) {
		ObjetPoussable oP = new ObjetPoussable(new Coordonnees(cd), this.joueur);
		this.poussePousse.add(oP);
	}
	public void creerProjectile(Coordonnees cd, int dir) {
		Projectile p = new Projectile(new Coordonnees(cd), dir, 10, 10);
		this.projectiles.add(p);
	}
	public void creerMeldas(Coordonnees cd) {
		this.bossEstLa.add(new GrilainMeldas(cd,this));
	}

	public void creerPorteTeleportable(int ligne,int colonne,int id,Partie partie,int nbcarte,int xDestination,int yDestination) {
		this.carte.getRamMap()[ligne][colonne].setObjetStatique(new PorteTeleportable(new Coordonnees(ligne*Tuile.TAILLE, colonne*Tuile.TAILLE), 19,new Coordonnees(xDestination*Tuile.TAILLE,yDestination*Tuile.TAILLE),partie,nbcarte));
	}
	public void ajouterProjectileMeldas(ProjectileMeldas p) {
		ProjectileMeldas pM = p;
		this.projectilesM.add(pM);
	}
	
	//EVOLUTION
	//renvoie true si le deplacement a reussi
	public boolean deplacer(Deplacable deplacable) {
		if (this.estMarchable(Coordonnees.plusVitesse(deplacable), deplacable.getDirection())) {
			deplacable.deplacer();
			return true;
		}
		else return false;
	}

	public void deplacerProjectilesM() {
		for (int i = 0; i < this.projectilesM.size(); i++) {
			ProjectileMeldas p = this.projectilesM.get(i);	
			this.deplacer(p);
			if (!this.deplacer(p)) {
				this.attaquer(p.attaquer(), p.getDegats());
				p.meurt();
			}
		}
	}
	
	//deplace, inflige dgts et tue les projectiles
	public void evolueProjectiles() {
		for (Projectile p : this.projectiles) {
			//this.deplacer(p);
			if (!this.deplacer(p)) {
				this.attaquer(p.attaquer(), p.getDegats());
				p.meurt();
			}
		}
	}

	//deplace et fait attaquer les ennemis
	public void evolueEnnemis(int tps) {
		int[][] mapBfs = Bfs.lancerBfs(this.joueur.getCoord(), this.carte.getRamMap(),this);		
		for (int i = 0; i < this.laptop.size(); i++) {
			LaptopGarou lG = this.laptop.get(i);

			int colonneEnnemi = lG.getCoord().getCoordonneesMilieu().getTuileColonne();
			int ligneEnnemi = lG.getCoord().getCoordonneesMilieu().getTuileLigne();

			int distanceActuelle = mapBfs[ligneEnnemi][colonneEnnemi];

			//s'il est touche par le bfs du joueur et assez proche (= s'il est ds la meme salle/zone)
			if (0 <= distanceActuelle && distanceActuelle <= 20) {

				int distanceNord = mapBfs[ligneEnnemi-1][colonneEnnemi];
				int distanceEst = mapBfs[ligneEnnemi][colonneEnnemi+1];
				int distanceSud = mapBfs[ligneEnnemi+1][colonneEnnemi];
				int distanceOuest = mapBfs[ligneEnnemi][colonneEnnemi-1];

				//pointe par default vers le nord... (peut etre innaccessible : -1)
				int directionDeplacement = Direction.NORD;
				int longueurChemin = distanceNord;

				//...puis teste si une autre direction est meilleure
				if ((distanceEst >= 0 && distanceEst <= longueurChemin) || longueurChemin == -1 ) {
					directionDeplacement = Direction.EST;
					longueurChemin = distanceEst;
				}
				if ((distanceSud >= 0 && distanceSud <= longueurChemin) || longueurChemin == -1 ) {
					directionDeplacement = Direction.SUD;
					longueurChemin = distanceSud;						
				}
				if ((distanceOuest >= 0 && distanceOuest <= longueurChemin) || longueurChemin == -1 ) {
					directionDeplacement = Direction.OUEST;
					longueurChemin = distanceOuest;						
				}

				lG.setDirection(directionDeplacement);

				//il se deplace et s'il ne reussit pas...
				if(!this.deplacer(lG) && distanceActuelle > 1) {
					//Je recupere le centre de la tuile
					Coordonnees cdTuileMilieu = new Coordonnees(colonneEnnemi*Tuile.TAILLE, ligneEnnemi*Tuile.TAILLE).getCoordonneesMilieu();
					
					//...Alors il se rapproche du centre de la tuile
					if (lG.getCoord().getCoordonneesMilieu().getX() < cdTuileMilieu.getX()) {
						lG.setDirection(Direction.EST);
						this.deplacer(lG);
					}
					else if (lG.getCoord().getCoordonneesMilieu().getX() > cdTuileMilieu.getX()) {
						lG.setDirection(Direction.OUEST);
						this.deplacer(lG);
					}
					else if (lG.getCoord().getCoordonneesMilieu().getY() < cdTuileMilieu.getY()) {
						lG.setDirection(Direction.SUD);
						this.deplacer(lG);
					}
					else {
						lG.setDirection(Direction.NORD);
						this.deplacer(lG);
					}
				}

				//et s'il est assez proche alors attaque
				if (distanceActuelle <= 1 && tps%20 == 0) 
					this.attaquer(lG.attaquer(), lG.getDgts());
			}
		}
		for (int i = 0; i < this.unite.size(); i++) {
			UniteCentrale UC = this.unite.get(i);
			
			int colonneEnnemi = UC.getCoord().getCoordonneesMilieu().getTuileColonne();
			int ligneEnnemi = UC.getCoord().getCoordonneesMilieu().getTuileLigne();

			int distanceActuelle = mapBfs[ligneEnnemi][colonneEnnemi];
			
			int distanceNord = mapBfs[ligneEnnemi-1][colonneEnnemi];
			int distanceEst = mapBfs[ligneEnnemi][colonneEnnemi+1];
			int distanceSud = mapBfs[ligneEnnemi+1][colonneEnnemi];
			int distanceOuest = mapBfs[ligneEnnemi][colonneEnnemi-1];

			//pointe par default vers le nord... (peut etre innaccessible : -1)
			int directionDeplacement = Direction.NORD;
			int longueurChemin = distanceNord;

			//...puis teste si une autre direction est meilleure
			if ((distanceEst >= 0 && distanceEst <= longueurChemin) || longueurChemin == -1 ) {
				directionDeplacement = Direction.EST;
				longueurChemin = distanceEst;
			}
			if ((distanceSud >= 0 && distanceSud <= longueurChemin) || longueurChemin == -1 ) {
				directionDeplacement = Direction.SUD;
				longueurChemin = distanceSud;						
			}
			if ((distanceOuest >= 0 && distanceOuest <= longueurChemin) || longueurChemin == -1 ) {
				directionDeplacement = Direction.OUEST;
				longueurChemin = distanceOuest;						
			}

			UC.setDirection(directionDeplacement);
			
			if (0 <= distanceActuelle && distanceActuelle <= 20) {
				if (Math.abs(UC.getCoord().getX()-this.joueur.getCoord().getX()) < 40 || Math.abs(UC.getCoord().getY()-this.joueur.getCoord().getY()) < 40)
					UC.attaquer();
				UC.decrementerTimerAtt();	
			}
		}
	}

	public void enterrerLesMorts() {
		for (int i = 0; i < this.projectiles.size(); i++) {
			if(!this.projectiles.get(i).existe())
				this.projectiles.remove(i);
		}
		for (int i = 0; i < this.projectilesM.size(); i++) {
			if(!this.projectilesM.get(i).existe())
				this.projectilesM.remove(i);
		}
		for (int i = 0; i < this.pnjs.size(); i++) {
			if(!this.pnjs.get(i).existe())
				this.pnjs.remove(i);
		}
		for (int i = 0; i < this.laptop.size(); i++) {
			if(!this.laptop.get(i).existe())
				this.laptop.remove(i);
		}
		for (int i = 0; i < this.unite.size(); i++) {
			if(!this.unite.get(i).existe())
				this.unite.remove(i);
		}	
	}

	public void attaquer(ArrayList<Coordonnees> ptsAtt, int dgts) {
		for (Coordonnees ptAtt : ptsAtt) {
			
			if(ptAtt.estDansCarre(this.joueur.getCoord()))
				this.joueur.perdreVie(dgts);	
			for (int i = 0; i < this.projectiles.size(); i++) { //tue les projectiles qui se font attaquer
				if(ptAtt.estDansCarre(this.projectiles.get(i).getCoord()))
					this.projectiles.get(i).meurt();
			}
			/*
			for (int i = 0; i < this.projectilesM.size(); i++) { //tue les projectiles qui se font attaquer
				if(ptAtt.estDansCarre(this.projectilesM.get(i).getCoord()))
					this.projectilesM.get(i).meurt();
			}
			*/
			for (int i = 0; i < this.laptop.size(); i++) {
				if(ptAtt.estDansCarre(this.laptop.get(i).getCoord()))
					this.laptop.get(i).perdreVie(dgts);
			}
			for (int i = 0; i < this.bossEstLa.size(); i++) {
				if(ptAtt.estDansCarre(this.bossEstLa.get(i).getCoord()))
					this.bossEstLa.get(i).perdreVie(dgts);
			}
			for (int i = 0; i < this.unite.size(); i++) {
				if(ptAtt.estDansCarre(this.unite.get(i).getCoord()))
					this.unite.get(i).perdreVie(dgts);
			}
		}
	}
	
	public Coordonnees getTuileDevant(Coordonnees cd, int dir) {
		switch (dir) {
		case Direction.NORD :
			return cd.getCoordonneesMilieuHaut();
		case Direction.EST :
			return cd.getCoordonneesMilieuDroit();
		case Direction.SUD :
			return cd.getCoordonneesMilieuBas();
		case Direction.OUEST :
			return cd.getCoordonneesMilieuGauche();
		}
		return null;
	}

	//Retourne si les coins associes a la direction sont sur des cases marchables
	public boolean estMarchable(Coordonnees cd, int dir) {
		Coordonnees pC = cd;
		Coordonnees sC = cd;

		switch (dir) {
		case Direction.NORD :
			sC = cd.getCoordonneesCoinHautDroit();
			break;
		case Direction.EST :
			pC = cd.getCoordonneesCoinHautDroit();
			sC = cd.getCoordonneesCoinBasDroit();
			break;
		case Direction.SUD :
			pC = cd.getCoordonneesCoinBasGauche();
			sC = cd.getCoordonneesCoinBasDroit();
			break;
		case Direction.OUEST :
			sC = cd.getCoordonneesCoinBasGauche();
			break;
		}

		//vérifie que les coins sont ds un deplacable
		boolean estDsDeplacable = false;

		if (pC.estDansCarre(this.joueur.getCoord()) || sC.estDansCarre(this.joueur.getCoord()))
			estDsDeplacable = true;

		for (int i = 0; i < this.bossEstLa.size(); i++) {
			if (pC.estDansCarre(this.bossEstLa.get(i).getCoord()) || sC.estDansCarre(this.bossEstLa.get(i).getCoord()))
				estDsDeplacable = true;
		}
		for (int i = 0; i < this.pnjs.size(); i++) {
			if (pC.estDansCarre(this.pnjs.get(i).getCoord()) || sC.estDansCarre(this.pnjs.get(i).getCoord()))
				estDsDeplacable = true;
		}
		for (int i = 0; i < this.laptop.size(); i++) {
			if (pC.estDansCarre(this.laptop.get(i).getCoord()) || sC.estDansCarre(this.laptop.get(i).getCoord()))
				estDsDeplacable = true;
		}
		for (int i = 0; i < this.projectiles.size(); i++) {
			if (pC.estDansCarre(this.projectiles.get(i).getCoord()) || sC.estDansCarre(this.projectiles.get(i).getCoord()))
				estDsDeplacable = true;
		}
		for (int i = 0; i < this.poussePousse.size(); i++) {
			if (pC.estDansCarre(this.poussePousse.get(i).getCoord()) || sC.estDansCarre(this.poussePousse.get(i).getCoord()))
				estDsDeplacable = true;
		}
		for (int i = 0; i < this.unite.size(); i++) {
			if (pC.estDansCarre(this.unite.get(i).getCoord()) || sC.estDansCarre(this.unite.get(i).getCoord()))
				estDsDeplacable = true;
		}
		
		return 	this.carte.getRamMap()[pC.getTuileLigne()][pC.getTuileColonne()].getID() <= 12
				&& this.carte.getRamMap()[sC.getTuileLigne()][sC.getTuileColonne()].getID() <= 12
				&& !estDsDeplacable;
	}


}
