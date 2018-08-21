package app;

import java.io.File;
import java.net.URL;

import app.controleur.ControleurMenuDep;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			URL urlMenuDep = new File("iut_rampage/app/vue/VueMenuDep.fxml").toURI().toURL();
			loader.setLocation(urlMenuDep);
			
			Pane paneMenuDep = new Pane(); 
			paneMenuDep=loader.load();
			Scene sceneMenuDep = new Scene(paneMenuDep,1600,900);
			
			primaryStage.setScene(sceneMenuDep);
			primaryStage.setResizable(false);
			
			//passage de la fenetre et de la scene au controleur
			ControleurMenuDep controler = loader.getController();
			
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	public static void main(String[] args) {
		launch(args);
	}
}