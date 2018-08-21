package app.controleur;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import app.modele.Partie;
import app.modele.alone.Coordonnees;
import app.modele.alone.Direction;
import app.modele.alone.Inventaire;
import app.modele.arme.Excalivier;
import app.modele.arme.Munitions;
import app.modele.deplacable.Deplacable;
import app.modele.deplacable.ObjetPoussable;
import app.modele.statique.PorteVerrouillable;
import app.modele.statique.SacAToutou;
import app.modele.tuile.Tuile;
import app.vue.CarteVue;
import app.vue.DeplacableVue;
import app.vue.HudVue;
import app.vue.JoueurVue;
import app.vue.PaneDeplacablesVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ControleurJeu implements Initializable {

	private MediaPlayer musicExploration = new MediaPlayer(new Media(new File("iut_rampage/Images/Journey To The West.mp3").toURI().toString())); 
	private MediaPlayer musicDonjon = new MediaPlayer(new Media(new File("iut_rampage/Images/musique donjon.mp3").toURI().toString())); 

	//Attributs loopGame
	private Timeline loopGame;
	private int temps;
	private int oldVie;
	private BooleanProperty deplaceNord, deplaceOuest, deplaceSud, deplaceEst, attaque;
	private int tps;
	private boolean ssj1=false;
	private boolean ssj3=false;
	private boolean peutAtkANouveau=true;
	private int tempsAtkANouveau;
	//modele
	private Partie partie;

	//vues
	public static JoueurVue jV;
	public static CarteVue cV;
	public static HudVue hV;
	public static PaneDeplacablesVue paneDV;

	//hashmap
	public static HashMap<Deplacable, DeplacableVue> hmDeplacables;

	@FXML
	private Pane pane;
	@FXML
	private BorderPane vueInventaire;

	//UTILITAIRES
	//Initialise la loopGame avec le nb d'iteration par secondes, et les methodes a appeler lors des keyframes
	public void initLoopGame() {
		loopGame = new Timeline();
		this.tempsAtkANouveau=0;
		temps=0; //un temps = un quarantieme de seconde
		loopGame.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.025),
				(ev ->{	
					if (temps == 0)
					{
							this.hV.lanceDialogue("iut_rampage/dialogues/dialogueDebutMomps.txt");
					}

					//empeche de spammer l'attaque
					if(this.peutAtkANouveau==false) {
						this.tempsAtkANouveau++;
						if(this.tempsAtkANouveau==20) {
							this.peutAtkANouveau=true;
							this.tempsAtkANouveau=0;
						}
					}

					//Mort perso
					if(this.partie.getJoueur().getVieProperty().getValue()<=0) {
						if(!this.partie.getNiveau().getBossEstLa().isEmpty()) {
							this.partie.getNiveau().getBossEstLa().get(0).meurt();
							this.paneDV.viderPane();
							this.partie.getNiveau().getBossEstLa().remove(0);
						}
						loopGame.stop();
						FXMLLoader loader = new FXMLLoader();
						URL urlJeu = null;
						try {
							urlJeu = new File("iut_rampage/app/vue/VueMort.fxml").toURI().toURL();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						loader.setLocation(urlJeu);

						Pane paneJeu = null;
						try {
							paneJeu=loader.load();
						} catch (IOException e) {
							e.printStackTrace();
						}
						musicExploration.stop();
						musicDonjon.stop();
						Scene sceneJeu = cV.getScene();
						sceneJeu.setRoot(paneJeu);

					}
					//attaque en cours
					if(this.attaque.getValue()==true) {
						tps++;
						if(tps==14) {
							tps=0;
							this.attaque.setValue(false);
						}
					}
					//Deplacements joueur
					if (deplaceNord.get()) {
						this.partie.getNiveau().deplacer(partie.getJoueur());
					}
					else if (deplaceEst.get()) {
						this.partie.getNiveau().deplacer(partie.getJoueur());
					}
					else if (deplaceSud.get()) {
						this.partie.getNiveau().deplacer(partie.getJoueur());
					}
					else if (deplaceOuest.get()) {
						this.partie.getNiveau().deplacer(partie.getJoueur());  
					}

					//Deplacements, attaques et suppression des deplacables
					this.partie.getNiveau().evolueProjectiles();
					this.partie.getNiveau().evolueEnnemis(temps);
					this.partie.getNiveau().enterrerLesMorts();
					this.partie.getNiveau().deplacerProjectilesM();


					//meldas
					if(!this.partie.getNiveau().getBossEstLa().isEmpty()) {

						if(this.oldVie!=this.partie.getNiveau().getBossEstLa().get(0).getVie()){
							paneDV.viderPane();
							this.partie.getNiveau().getBossEstLa().get(0).teleporteDelmas();
							if(this.partie.getNiveau().getBossEstLa().get(0).getVie()<=600) {
								DeplacableVue dV = paneDV.creerGrilain3Vue(this.partie.getNiveau().getBossEstLa().get(0));
								ControleurJeu.hmDeplacables.put(this.partie.getNiveau().getBossEstLa().get(0), dV);
							}
							else if(this.partie.getNiveau().getBossEstLa().get(0).getVie()<=1200) {
								DeplacableVue dV = paneDV.creerGrilain2Vue(this.partie.getNiveau().getBossEstLa().get(0));
								ControleurJeu.hmDeplacables.put(this.partie.getNiveau().getBossEstLa().get(0), dV);
							}
							else {
								DeplacableVue dV = paneDV.creerGrilainVue(this.partie.getNiveau().getBossEstLa().get(0));
								ControleurJeu.hmDeplacables.put(this.partie.getNiveau().getBossEstLa().get(0), dV);
							}
						}
						if(this.partie.getNiveau().getBossEstLa().get(0).getVie()<=600 && this.ssj1==false) {
							paneDV.viderPane();
							DeplacableVue dV = paneDV.creerGrilain3Vue(this.partie.getNiveau().getBossEstLa().get(0));
							ControleurJeu.hmDeplacables.put(this.partie.getNiveau().getBossEstLa().get(0), dV);
							this.ssj1=true;					}
						else if(this.partie.getNiveau().getBossEstLa().get(0).getVie()<=1200&&ssj3==false) {
							paneDV.viderPane();
							DeplacableVue dV = paneDV.creerGrilain2Vue(this.partie.getNiveau().getBossEstLa().get(0));
							ControleurJeu.hmDeplacables.put(this.partie.getNiveau().getBossEstLa().get(0), dV);
							ssj3=true;
						}

						if(this.partie.getNiveau().getBossEstLa().get(0).getVie()<=0) {
							this.partie.getNiveau().getBossEstLa().remove(0);
							loopGame.stop();
							FXMLLoader loader = new FXMLLoader();
							URL urlJeu = null;
							try {
								urlJeu = new File("iut_rampage/app/vue/VueWin.fxml").toURI().toURL();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							loader.setLocation(urlJeu);

							Pane paneJeu = null;
							try {
								paneJeu=loader.load();
							} catch (IOException e) {
								e.printStackTrace();
							}
							musicExploration.stop();
							musicDonjon.stop();
							Scene sceneJeu = cV.getScene();
							sceneJeu.setRoot(paneJeu);
						}
						if(!this.partie.getNiveau().getBossEstLa().isEmpty()) 
							oldVie=this.partie.getNiveau().getBossEstLa().get(0).getVie();
					}
					//this.partie.getJoueur().gagnerVie(1);
					temps++;
				})
				);
		loopGame.getKeyFrames().add(kf);
	}

	//Detecte les touches pressees au clavier
	@FXML
	public void keyPressedClavier(KeyEvent event) {
		if(!this.hV.getEnDialogueProp().get())
		{
			switch (event.getCode()) {	
			//DEPLACEMENTS

			case UP :
				if(!this.attaque.getValue()) {
					this.partie.getJoueur().setDirection(Direction.NORD);
					if(!this.vueInventaire.isVisible()) {
						this.annulerDeplacements();
						this.deplaceNord.set(true);
					}
				}
				break;
			case LEFT :
				if(!this.attaque.getValue()) {
					this.partie.getJoueur().setDirection(Direction.OUEST);
					if(!this.vueInventaire.isVisible()){
						this.annulerDeplacements();
						this.deplaceOuest.set(true);
					}
				}
				break;
			case DOWN :
				if(!this.attaque.getValue()) {
					this.partie.getJoueur().setDirection(Direction.SUD);
					if(!this.vueInventaire.isVisible()){
						this.annulerDeplacements();
						this.deplaceSud.set(true);
					}
				}
				break;
			case RIGHT :
				if(!this.attaque.getValue()) {
					this.partie.getJoueur().setDirection(Direction.EST);
					if(!this.vueInventaire.isVisible()){
						this.annulerDeplacements();
						this.deplaceEst.set(true);
					}
				}
				break;

				//ENVIRONNEMENT
			case F :
				if((!this.attaque.getValue()) && (!this.vueInventaire.isVisible())) {

					Coordonnees cd = partie.getNiveau().getTuileDevant(Coordonnees.plusVitesse(this.partie.getJoueur()), this.partie.getJoueur().getDirection());
					Tuile tuile = partie.getNiveau().getCarte().getRamMap()[cd.getTuileLigne()][cd.getTuileColonne()];
					for (ObjetPoussable o : this.partie.getNiveau().getObjetPoussable()) {
						if (cd.estDansCarre(o.getCoord()))
							o.deplacer(this.partie.getNiveau());
					}

					if (tuile.getObjStatique() != null) {
						//Renvoie true uniquement si l'objet est un SacAToutou
						if (tuile.getObjStatique().utiliser(partie.getJoueur())) {
							tuile.setObjetStatique(null);
							cV.updateTuile(cd, tuile);
						}
					}	
				}
				break;
			case I :
				loopGame.pause();
				hV.refreshArme();
				hV.getCenter().setVisible(true);
				break;
			case ESCAPE:
				if(hV.getCenter().isVisible()) {
					hV.getCenter().setVisible(false);
					loopGame.play();
				}
				break;

				//DEV (TODO : suppr)
			case NUMPAD1 : 
				partie.getNiveau().creerLaptopGarou(new Coordonnees(this.partie.getJoueur().getCoord()));
				break;
			case NUMPAD2 :
				partie.getNiveau().creerUniteCentrale(new Coordonnees(this.partie.getJoueur().getCoord()));
				break;
			case NUMPAD3 :
				partie.getNiveau().creerObjetPoussable(new Coordonnees(this.partie.getJoueur().getCoord()));
				break;
				//FIN TEST

				//COMBAT
			case SPACE : 
				partie.getJoueur().prendreArmeSuivante();
				break;
			case D :
				if(this.peutAtkANouveau) {
					this.peutAtkANouveau=false;
					this.annulerDeplacements();
					this.attaque.set(true);
					this.partie.getJoueur().attaquer();
					jV.lancerAnimAttaque(this.partie.getJoueur());
				}
				break;

			default:
				break;
			}
		}
	}

	private void annulerDeplacements() {
		this.deplaceNord.set(false);
		this.deplaceSud.set(false);
		this.deplaceOuest.set(false);
		this.deplaceEst.set(false);
	}

	@FXML
	public void keyReleasedClavier(KeyEvent event) {
		switch (event.getCode()) {	
		case UP :				
			this.deplaceNord.set(false);
			break;
		case LEFT :
			this.deplaceOuest.set(false);
			break;
		case DOWN :
			this.deplaceSud.set(false);
			break;
		case RIGHT :
			this.deplaceEst.set(false);
			break;
		default:
			break;
		}
	}

	private void ajouterListenersJoueur() {
		//Listeners scrolling joueur
		this.partie.getJoueur().getCoord().getYProperty().addListener((observable,oldValue, newValue) -> {
			paneDV.relocate(cV.getLayoutX(), cV.getLayoutY() - (newValue.doubleValue() - oldValue.doubleValue()) );
			cV.relocate(cV.getLayoutX(), cV.getLayoutY() - (newValue.doubleValue() - oldValue.doubleValue()) );
		});
		this.partie.getJoueur().getCoord().getXProperty().addListener((observable,oldValue, newValue) -> {
			paneDV.relocate(cV.getLayoutX() - (newValue.doubleValue() - oldValue.doubleValue()), cV.getLayoutY());
			cV.relocate(cV.getLayoutX() - (newValue.doubleValue() - oldValue.doubleValue()), cV.getLayoutY());
		});
		//Listener attaque
		this.attaque.addListener((observable,oldValue, newValue) -> {
			if (newValue.booleanValue())
				jV.lancerAnimAttaque(this.partie.getJoueur());
			else 
				jV.stopAnim(this.partie.getJoueur().getDirection());
		}); 
		//Listener deplacements
		this.deplaceNord.addListener((observable,oldValue, newValue) -> {
			if (newValue.booleanValue())
				jV.lancerAnim(1);
			else 
				jV.stopAnim(1);
		});
		this.deplaceEst.addListener((observable,oldValue, newValue) -> {
			if (newValue.booleanValue())
				jV.lancerAnim(2);
			else 
				jV.stopAnim(2);
		});
		this.deplaceSud.addListener((observable,oldValue, newValue) -> {
			if (newValue.booleanValue())
				jV.lancerAnim(3);
			else 
				jV.stopAnim(3);
		});
		this.deplaceOuest.addListener((observable,oldValue, newValue) -> {
			if (newValue.booleanValue())
				jV.lancerAnim(4);
			else 
				jV.stopAnim(4);
		});
	}

	private void ajouterListenersCartes() {
		//Listener changement de carte
		this.partie.getIndiceNiveauActuelProperty().addListener( (observable,oldValue, newValue) -> {

			paneDV.viderPane();
			//paneDV.setTaille(this.partie.getNiveau());
			cV.setCarte(this.partie.getNiveau().getCarte());

			//Listener projectiles
			this.partie.getNiveau().getDeplacables().addListener(new ProjectilesListener());

			//Listener pnjs
			this.partie.getNiveau().getPnjs().addListener(new PnjsListener());

			//Listener ennemis
			this.partie.getNiveau().getLaptop().addListener(new LaptopListener());
			this.partie.getNiveau().getUnite().addListener(new UniteListener());
			//Listener objets poussables
			this.partie.getNiveau().getObjetPoussable().addListener(new ObjetPousListener());

			//Listener bosses


			if(newValue.equals(1)) {
				this.musicExploration.stop();
				this.musicDonjon.setCycleCount(100);
				this.musicDonjon.setVolume(0.5);
				this.musicDonjon.play();
				this.partie.getNiveau().chargerDeplacableEtStatique("iut_rampage/matrice/donjon.txt");
			}
			else if(newValue.equals(2)) {
				this.partie.getNiveau().getBossEstLa().addListener(new DelmasListener());
				this.partie.getNiveau().creerMeldas(new Coordonnees(40 * Tuile.TAILLE, 38 * Tuile.TAILLE));
				this.partie.getNiveau().getProjectileM().addListener(new ProjectileMeldaslistener());
			}
		});
	}

	public void demarrerPartie () {
		//Creation de tout le modele
		this.partie = new Partie();

		this.ajouterListenersJoueur();
		this.ajouterListenersCartes();

		this.partie.setIndiceNiveauActuel(0);

		jV.setJoueur(partie.getJoueur());
		hV.setJoueur(this.partie.getJoueur(), new Image("/Images/animPerso.png"));

		this.partie.getNiveau().chargerDeplacableEtStatique("iut_rampage/matrice/b1rdc.txt");

		//TEST
		this.partie.getJoueur().getInventaire().ajouterArme(new Excalivier(this.partie));
		this.partie.getJoueur().getInventaire().ajouterArme(new Munitions(this.partie, 20));
		//FIN TEST

		this.partie.getNiveau().creerPnj(new Coordonnees(Tuile.TAILLE * 6, Tuile.TAILLE *5));

		this.hV.getEnDialogueProp().addListener((obs, oldV, newV) -> {
			if(newV)
			{
				loopGame.stop();
			}
			else
				loopGame.play();
		}); 

		this.initLoopGame();
		this.loopGame.play(); 

		musicExploration.setAutoPlay(true);
		musicExploration.setVolume(0.3);
		musicExploration.setCycleCount(100);

		this.cV.setTranslateX(450 );
		this.jV.setTranslateX(450);
		this.paneDV.setTranslateX(450);
		
		
		this.partie.getNiveau().getCarte().getTuile(44, 33).setObjetStatique(new PorteVerrouillable(new Coordonnees(44 * Tuile.TAILLE, 33*Tuile.TAILLE), 20, "Clef Donjon"));
		Inventaire i = new Inventaire();
		i.getClefs().add("Clef Donjon");
		this.partie.getNiveau().getCarte().getTuile(4, 26).setObjetStatique(new SacAToutou(i));
		ImageView im = new ImageView(new Image("Images/sacAToutou.png"));
		im.setX(4 * Tuile.TAILLE);
		im.setY(26 * Tuile.TAILLE);
		this.paneDV.getChildren().add(im);


	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Creation attributs loopGame
		this.deplaceNord = new SimpleBooleanProperty(false);
		this.deplaceEst = new SimpleBooleanProperty(false);
		this.deplaceSud = new SimpleBooleanProperty(false);
		this.deplaceOuest = new SimpleBooleanProperty(false);

		this.attaque = new SimpleBooleanProperty(false);


		cV = new CarteVue();
		pane.getChildren().add(cV);
		paneDV = new PaneDeplacablesVue();
		pane.getChildren().add(paneDV);
		jV = new JoueurVue();
		pane.getChildren().add(jV);
		hV = new HudVue();
		pane.getChildren().add(hV);
		hV.getCenter().setVisible(false);

		//Creation hashMap
		hmDeplacables = new HashMap<Deplacable, DeplacableVue>();

		//Le focus se met par default sur le elements les plus au dessus
		hV.setFocusTraversable(true);
		jV.setFocusTraversable(true);
		paneDV.setFocusTraversable(true);
		cV.setFocusTraversable(true);

		//Initialisation loopGame
		this.vueInventaire.setVisible(false);
		this.vueInventaire.toFront();
	}

}
