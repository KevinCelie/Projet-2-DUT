package app.controleur;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ControleurMort implements Initializable{
	private int temps;
	Image gif1=new Image("Images/gif_mort1.gif");
	Image gif2=new Image("Images/gif_mort2.gif");
	private Timeline mortLoop;
	private MediaPlayer audio = new MediaPlayer(new Media(new File("iut_rampage/Images/Crimson Roboto.mp3").toURI().toString())); 
	
	@FXML
	private ImageView gif;
	
	@FXML
	private Pane pane;

	@FXML
	public void keyType(KeyEvent event) {
		if(gif.getImage().equals(gif2)) {
			FXMLLoader loader = new FXMLLoader();
			URL urlJeu = null;
			try {
				urlJeu = new File("iut_rampage/app/vue/VueMenuDep.fxml").toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			loader.setLocation(urlJeu);
			Pane paneJeu = null;
			try {
				paneJeu = loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Scene sceneJeu = this.pane.getScene();

			audio.stop();
			//Recuperation de la scene et association a la fenetre
			sceneJeu.setRoot(paneJeu);

		}
	}

	public void mortLoop() {
		mortLoop = new Timeline();
		temps=0;
		mortLoop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(
				Duration.seconds(0.025), 
				(ev ->{	
					if(temps==160)
						gif.setImage(gif2);
					temps++;

				})
				);
		mortLoop.getKeyFrames().add(kf);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//MediaPlayer audio = new MediaPlayer(new Media (new File("chemin audio").toURI().toString()));
		audio.setStartTime(new Duration(7000));
		audio.setAutoPlay(true);
		audio.setCycleCount(100);
		gif.setImage(gif1);
		gif.setFitHeight(900);
		gif.setFitWidth(1600);
		gif.relocate(325, 0);

		this.mortLoop();
		this.mortLoop.play();
		gif.setFocusTraversable(true);
	}

}




