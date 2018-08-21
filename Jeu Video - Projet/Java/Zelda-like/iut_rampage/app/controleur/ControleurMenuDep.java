package app.controleur;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class ControleurMenuDep implements Initializable {
	private MediaPlayer audio = new MediaPlayer(new Media(new File("iut_rampage/Images/menu.mp3").toURI().toString())); 
	@FXML
    private Button boutonNouvellePartie;
    @FXML
    private Button boutonQuitter;

    @FXML
    void demarrerNouvellePartie(ActionEvent event) {
    	try {
    		
	    	FXMLLoader loader = new FXMLLoader();
	    	URL urlJeu = new File("iut_rampage/app/vue/VueGraphique.fxml").toURI().toURL();
			loader.setLocation(urlJeu);
			
			Pane paneJeu;
			paneJeu=loader.load();
			
			Scene sc = boutonNouvellePartie.getScene();
			sc.setRoot(paneJeu);

			//Creation du controleur et lancement de la partie
			ControleurJeu controler = loader.getController();
			audio.stop();
			controler.demarrerPartie();
			
			
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    @FXML
    void chargerUnePartie(ActionEvent event) {
    	
    }
    
    @FXML
    void chargerOptions(ActionEvent event) {
    }

    @FXML
    void quitter(ActionEvent event) {
    	Stage stage = (Stage) boutonNouvellePartie.getScene().getWindow();
    	stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		audio.setAutoPlay(true);
		audio.setCycleCount(100);
		
	}

}

