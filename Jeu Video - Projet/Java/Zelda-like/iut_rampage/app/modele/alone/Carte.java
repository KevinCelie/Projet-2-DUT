package app.modele.alone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import app.modele.statique.Porte;
import app.modele.tuile.Tuile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Carte {
	
	private Tuile[][] ram_map;
	private IntegerProperty largeurEnPixel, hauteurEnPixel;
	
	public Carte(Tuile[][] ram_map) {
		this.ram_map = ram_map;
		this.largeurEnPixel = new SimpleIntegerProperty(this.ram_map[0].length * Tuile.TAILLE);
		this.hauteurEnPixel = new SimpleIntegerProperty(this.ram_map.length * Tuile.TAILLE);
	}
	
	//GETTERS
	public Tuile[][] getRamMap() { //appele par carteVue
		return this.ram_map;
	}
	public IntegerProperty getLargeurPixelProperty() {
		return this.largeurEnPixel;
	}
	public IntegerProperty getHauteurPixelProperty() {
		return this.hauteurEnPixel;
	}
	public int getLargeurPixel() { //appele par carteVue
		return this.largeurEnPixel.get();
	}
	public int getHauteurPixel() { //appele par carteVue
		return this.hauteurEnPixel.get();
	}
	public Tuile getTuile(int colonne, int ligne) {
		return ram_map[ligne][colonne];
	}
	
	//UTILITAIRES
	public Tuile getTuile(Coordonnees cd) {
		return this.ram_map[cd.getTuileLigne()][cd.getTuileColonne()];
	}
	
	//CHARGEMENT
	public static Tuile[][] chargerMatrice(String url) {

		BufferedReader in;
		String line;
		int ligne, colonne;
		int id;
		ligne=-1;
		colonne=-1;
		int x=0;
		int y=0;
		try {
			in = new BufferedReader(new FileReader(url));
			try {
				while ((line = in.readLine()) != null) {
					y++;
					if( y == 1)
						for (String idTuile : line.split(" ")) {
							x++;

						}
				}
			} catch (IOException e) { e.printStackTrace(); }

		} catch (FileNotFoundException e2) { e2.printStackTrace(); }

		try {
			in = new BufferedReader(new FileReader(url));

			Tuile[][] tab = new Tuile[y][x];
			try {
				while ((line = in.readLine()) != null) {
					ligne++;
					colonne=-1;
					for (String idTuile : line.split(" ")) {
						colonne++;


						id = Integer.parseInt(idTuile);

						tab[ligne][colonne] = new Tuile(id);
						if (id == 21 || id == 15|| id == 20 || id == 19) {
							tab[ligne][colonne].setObjetStatique(new Porte(new Coordonnees(colonne * Tuile.TAILLE, ligne * Tuile.TAILLE), id));
						}
					}
				}
			} catch (IOException e) { e.printStackTrace(); }
			return tab;
		} catch (FileNotFoundException e2) { e2.printStackTrace(); }

		return null;
		
		
		

	}

}
