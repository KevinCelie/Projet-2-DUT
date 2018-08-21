package app.modele.alone;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import app.modele.deplacable.Joueur;
import app.modele.tuile.Tuile;

class EstMarchableTest {
	Tuile[][] ram_map=Carte.chargerMatrice("iut_rampage/matrice/batailleGrilain.json");
	Carte x=new Carte(ram_map);
	Joueur j=new Joueur("eric fait du junit bordel");
	Niveau n=new Niveau(ram_map, j);
	@Test
	void test() {
		//ont retranche a la coordonnee 40/40 les directions pour connaitre la prochaine case
		assertTrue(n.estMarchable(new Coordonnees(41*Tuile.TAILLE,40*Tuile.TAILLE), Direction.EST));
		assertTrue(n.estMarchable(new Coordonnees(40*Tuile.TAILLE,39*Tuile.TAILLE), Direction.NORD));
		assertTrue(n.estMarchable(new Coordonnees(39*Tuile.TAILLE,40*Tuile.TAILLE), Direction.OUEST));
		assertTrue(n.estMarchable(new Coordonnees(40*Tuile.TAILLE,41*Tuile.TAILLE), Direction.SUD));
		
		//ont retranche a la coordonnee 3/3 les directions pour connaitre la prochaine case
		assertFalse(n.estMarchable(new Coordonnees(4*Tuile.TAILLE,3*Tuile.TAILLE), Direction.EST));
		assertFalse(n.estMarchable(new Coordonnees(3*Tuile.TAILLE,2*Tuile.TAILLE), Direction.OUEST));
		assertFalse(n.estMarchable(new Coordonnees(2*Tuile.TAILLE,3*Tuile.TAILLE), Direction.NORD));
		assertFalse(n.estMarchable(new Coordonnees(3*Tuile.TAILLE,4*Tuile.TAILLE), Direction.SUD));
		
		//ont retranche a la coordonnee 36/34 les directions pour connaitre la prochaine case
		assertTrue(n.estMarchable(new Coordonnees(38*Tuile.TAILLE,34*Tuile.TAILLE), Direction.EST));
		assertFalse(n.estMarchable(new Coordonnees(37*Tuile.TAILLE,33*Tuile.TAILLE), Direction.NORD));
		assertFalse(n.estMarchable(new Coordonnees(36*Tuile.TAILLE,34*Tuile.TAILLE), Direction.OUEST));
		assertTrue(n.estMarchable(new Coordonnees(37*Tuile.TAILLE,35*Tuile.TAILLE), Direction.SUD));
		
	}

}
