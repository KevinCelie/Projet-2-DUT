package app.vue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import app.modele.arme.Arme;
import app.modele.deplacable.Joueur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HudVue extends BorderPane{
	
	private ProgressBar vie = new ProgressBar();
	private Pane inventaireVue = new Pane();
	private Pane boiteDialog = new Pane();

	private HBox hB = new HBox(); //ds la pane InventaireVue

	private VBox vBGauche = new VBox(); //ds la colonne gauche de la HBox
	private Label nomJoueur = new Label();
	private ImageView imageJoueur = new ImageView();

	private VBox vBMilieu = new VBox(); //ds la colonne milieu de la HBox
	private Label pDV = new Label();
	private Label argent = new Label();
	private Label armeActuelle = new Label();
	private TextArea tA = new TextArea();

	private VBox vBDroite = new VBox(); //ds la colonne droite de la HBox
	private Label arme = new Label("Armes :");
	private ListView<Arme> lVArme = new ListView<>();
	private Label clef = new Label("Clefs :");
	private ListView<String> lVClef = new ListView<>();

	private TextArea tF = new TextArea();
	
	private BufferedReader in;
	private Timeline dialogueLoop;
	private int temps;
	private String line;
	private int indice = 100000;
	
	private BooleanProperty enDialogue;

	private boolean passerDialogue;


	public HudVue () {
		super();
		
		this.enDialogue = new SimpleBooleanProperty(false);
		
		this.setOnKeyPressed((key) -> {
			this.passerDialogue = true;
		});


		//-----TOP-----//
		this.setTop(vie);


		//-----CENTER-----//
		this.setCenter(inventaireVue);
		inventaireVue.getChildren().add(hB);
		inventaireVue.setOpacity(0.96);
		inventaireVue.setStyle("-fx-background-color: #02212f; ");


		//---------------Premier colonne de la HBox----------//
		hB.getChildren().add(vBGauche);
		vBGauche.getChildren().add(nomJoueur);
		vBGauche.getChildren().add(imageJoueur);

		vBGauche.setStyle("-fx-alignment: center;"
				+ "-fx-border-color: #84c0c4; "
				+ "-fx-border-width: 5px; "
				+ "-fx-border-radius: 10 0 0 10;");

		nomJoueur.setTextFill(Paint.valueOf("#84c0c4"));
		nomJoueur.setStyle("-fx-font-weight: bold; ");


		//---------------Deuxième colonne de la Hbox--------------//
		hB.getChildren().add(vBMilieu);
		vBMilieu.getChildren().add(pDV);
		vBMilieu.getChildren().add(argent);
		vBMilieu.getChildren().add(armeActuelle);
		vBMilieu.getChildren().add(tA);

		vBMilieu.setStyle("-fx-alignment: center;"
				+ "-fx-border-color: #84c0c4;"
				+ "-fx-border-width: 5px; ");

		pDV.setTextFill(Paint.valueOf("#84c0c4"));
		pDV.setStyle("-fx-font-weight: bold; ");

		argent.setTextFill(Paint.valueOf("#84c0c4"));
		argent.setStyle("-fx-font-weight: bold; ");

		armeActuelle.setTextFill(Paint.valueOf("#84c0c4"));
		armeActuelle.setStyle("-fx-font-weight: bold; ");

		tA.setWrapText(true);
		tA.setStyle("-fx-text-fill: black; -fx-font-size: 16; -fx-font-weight: bold;");
		tA.setOpacity(100);


		//---------------Troisième colonne de la Hbox------------//
		hB.getChildren().add(vBDroite);
		vBDroite.getChildren().add(arme);
		vBDroite.getChildren().add(lVArme);
		vBDroite.getChildren().add(clef);
		vBDroite.getChildren().add(lVClef);

		vBDroite.setStyle("-fx-alignment: center;"
				+ "-fx-border-color: #84c0c4;"
				+ "-fx-border-width: 5px;"
				+ "-fx-border-radius: 0 10 10 0;");

		arme.setStyle("-fx-font-weight: bold");
		arme.setTextFill(Paint.valueOf("#84c0c4"));
		lVArme.setStyle("-fx-text-fill: #84c0c4; -fx-font-size: 16; -fx-font-weight: bold;");

		clef.setStyle("-fx-font-weight:bold");
		clef.setTextFill(Paint.valueOf("#84c0c4"));
		lVClef.setStyle("-fx-text-fill: #84c0c4; -fx-font-size: 16; -fx-font-weight: bold;");


		//---------------Partie basse du BorderPane-------------------//
		this.setBottom(boiteDialog);
		boiteDialog.getChildren().add(tF);

		tF.setStyle("-fx-border-color: #84c0c4;\n"
				+ "-fx-border-width: 10;\n"
				+ "-fx-border-style: solid;\n"
				+ "-fx-border-radius: 5; \n"
				+ "-fx-background-color: #02212f; \n"
				+ "-fx-text-fill: black; \n"
				+ "-fx-font-weight:bold;");
		tF.setFont(new Font(20));

		vie.setPadding(new Insets(10,0,10,10));
		vie.setMinHeight(50);

		nomJoueur.setFont(new Font(30));

		pDV.setFont(new Font(20));

		argent.setFont(new Font(20));

		armeActuelle.setFont(new Font(20));

		tA.setDisable(true);
		tF.setDisable(true);

		clef.setFont(new Font(20));

		arme.setFont(new Font(20));
	}
	public void refreshArme() {
		lVArme.refresh();
	}
	public void setJoueur(Joueur j , Image im) {

		Scene sc = this.getScene();

		inventaireVue.setTranslateX(sc.getWidth() / 100 * 7 );
		inventaireVue.setTranslateY(sc.getWidth() / 100 * 5 );
		inventaireVue.setMaxWidth(sc.getWidth() / 100 * 80);

		//reglage graphique par rapport a la taille de la fenetre
		vBGauche.setMinWidth(sc.getWidth() / 100 * 40);
		vBGauche.setMaxWidth(sc.getWidth() / 100 * 40);
		vBMilieu.setMinWidth(sc.getWidth() / 100 * 20);
		vBMilieu.setMaxWidth(sc.getWidth() / 100 * 20);
		vBDroite.setMinWidth(sc.getWidth() / 100 * 20);
		vBDroite.setMaxWidth(sc.getWidth() / 100 * 20);

		vie.setMinWidth(sc.getWidth()/3);
		tA.setMinHeight(sc.getHeight() / 100 * 50);

		lVArme.setMaxHeight(sc.getHeight() / 100 * 20);
		lVClef.setMaxHeight(sc.getHeight() / 100 * 35);

		tF.setMinWidth(sc.getWidth() / 100 * 75);
		tF.setMaxHeight(sc.getHeight() / 100 * 20);

		imageJoueur.setFitWidth(sc.getWidth()/3);



		//set vBox Gauche
		nomJoueur.setText(j.getNom());

		imageJoueur.setImage(im);
		imageJoueur.setViewport(new Rectangle2D(100, 0, 50, 50));
		imageJoueur.setPreserveRatio(true);

		//set Vbox milieu
		pDV.setText("Santé : " + j.getVieProperty().get());

		argent.setText("Bitcoins : " + j.getInventaire().getBitcoinsProperty().get());

		armeActuelle.setText("Arme : ");

		tA.setText("Apres une journee eprouvante de cours, le jeune Dave s est par maladresse assoupi devant son ordinateur. A son reveil, l environnement a grandement change et l IUT aussi, lord Grilain Meldas en a pris le controle et a donne vie a� la plupart des machines de l IUT et les a place sous ses ordres .\n" +
				"Notre jeune héros arrivera t-il a� s en sortir ?");

		//set Vbox Droite
		lVArme.setItems(j.getInventaire().getArmes());
		lVClef.setItems(j.getInventaire().getClefs());

		//set Partie basse de la borderpane

		tF.relocate(sc.getWidth() / 100 * 10, tF.getLayoutY());
		tF.setOpacity(100);
		tF.setVisible(false);


		vie.setProgress(j.getVieProperty().get() / 100);

		j.getVieProperty().addListener( (observable,oldValue, newValue) -> { vie.setProgress(newValue.doubleValue()/100);
		pDV.setText("Sante : " + newValue.intValue());
		} );

		j.getInventaire().getBitcoinsProperty().addListener( (observable, oldValue, newValue) -> {
			argent.setText("Bitcoins : " + newValue.intValue());
		});
		j.getInventaire().getArmeActuelleProperty().addListener( (observable,oldV, newV ) -> {
			armeActuelle.setText("Arme : " + j.getInventaire().getArmeActuelle());
		});
	}

	public TextArea getTF() {
		return this.tF;
	}

	
	public void lanceDialogue (String url) {
		this.tF.setVisible(true);
		this.tF.setWrapText(true);
		this.requestFocus();
		enDialogue.set(true);
		this.afficheDialogue(url);
		dialogueLoop.play();
	}

	public void afficheDialogue(String url) {

		dialogueLoop = new Timeline();
		temps=0;
		dialogueLoop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.010), 
				(ev ->
				{	
					if (temps == 0) 
					{
						try 
						{
							this.in = new BufferedReader(new FileReader(url));	
						} 
						catch (FileNotFoundException e2) 
						{ 
							e2.printStackTrace(); 
						}
					}

					if (line == null || line.toCharArray().length <= indice && passerDialogue ) 
					{
						this.tF.setText("");
						passerDialogue = false;
						indice = 0;
						
						try 
						{
							this.line = in.readLine();
						}
						catch (IOException e) { e.printStackTrace(); }
						
						
						if (line == null)
						{
							this.setFocusTraversable(true);
							this.tF.setVisible(false);
							this.enDialogue.set(false);
							dialogueLoop.stop();
						}
						
						else if (line.equals("/Momps/")) 
						{
							tF.setStyle("-fx-border-color: #84c0c4;\n"
							+ "-fx-border-width: 10;\n"
							+ "-fx-border-style: solid;\n"
							+ "-fx-border-radius: 5; \n"
							+ "-fx-background-color: #02212f; \n"
							+ "-fx-text-fill: black; \n"
							+ "-fx-font-weight:bold; \n"
							+ "-fx-font-style: normal");

							//TODO mettre FanArt Momps

							
							try {
								this.line = in.readLine();
							} catch (IOException e) { e.printStackTrace(); }
						}
						else if (line.equals("/Joueur/"))
						{
							tF.setStyle("-fx-border-color: #84c0c4;\n"
									+ "-fx-border-width: 10;\n"
									+ "-fx-border-style: solid;\n"
									+ "-fx-border-radius: 5; \n"
									+ "-fx-background-color: #02212f; \n"
									+ "-fx-text-fill: black; \n"
									+ "-fx-font-weight:bold; \n"
									+ "-fx-font-style: normal");
							//TODO mettre FanArt Joueur
							
							try 
							{
								this.line = in.readLine();
							} catch (IOException e) { e.printStackTrace(); }
						}
						else if (line.equals("/Ordi/"))
						{
							tF.setStyle("-fx-border-color: #84c0c4;\n"
									+ "-fx-border-width: 10;\n"
									+ "-fx-border-style: solid;\n"
									+ "-fx-border-radius: 5; \n"
									+ "-fx-background-color: #02212f; \n"
									+ "-fx-text-fill: black; \n"
									+ "-fx-font-weight:bold; \n"
									+ "-fx-font-style: italic");
							//TODO mettre FanArt Joueur
							
							try 
							{
								this.line = in.readLine();
							} catch (IOException e) { e.printStackTrace(); }
						}
						
						
					}
					else if(temps % 2 == 0 && line.toCharArray().length > indice )
					{
						this.tF.setText(this.tF.getText() + line.charAt(indice));
						indice++;
					}





					temps++;

				})
				);
		dialogueLoop.getKeyFrames().add(kf);
	}
	public BooleanProperty getEnDialogueProp() {
		return this.enDialogue;
	}

}
