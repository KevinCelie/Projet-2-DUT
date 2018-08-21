package app.modele.alone;

import java.util.ArrayList;

import app.modele.deplacable.Deplacable;
import app.modele.deplacable.ObjetPoussable;
import app.modele.tuile.Tuile;
	
public class Bfs {

	private static boolean verifDejaTraite(Coordonnees c, ArrayList<Coordonnees> traite) {
		for (Coordonnees i : traite) {
			if(c.getX() == i.getX() && c.getY() == i.getY())
				return true;
		}
		return false;
	}
	
	//renvoie un tableau d'entiers (chaque entier est la longueur du chemin vers le joueur, -1 si inaccessible)
	public static int[][] lancerBfs(Coordonnees cdJoueur, Tuile[][]mapT,Niveau n) {
		
		Coordonnees cdJoueurMilieu = cdJoueur.getCoordonneesMilieu();

		//creation et remplissage du tableau d'ids
		int map[][] = new int[mapT.length][mapT[0].length];
		for(int i = 0; i < map.length; i++) {
			for(int k = 0; k < map[0].length; k++){
				map[i][k] = mapT[i][k].getID();
			}
		}
		
		//creation et remplissage par défault de -1 (innaccessible) du bfs
		int mapBfsFinale[][] =  new int[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			for(int k = 0; k < map[0].length; k++){
				mapBfsFinale[i][k] = -1;
			}
		}
		
		ArrayList<Coordonnees>listNonTraite = new ArrayList<Coordonnees>();
		ArrayList<Coordonnees>listTraite = new ArrayList<Coordonnees>();
		
		listNonTraite.add(new Coordonnees(cdJoueurMilieu.getTuileLigne(),cdJoueurMilieu.getTuileColonne()));
		
		int iterationSimultaneOld  =  1;
		int iterationSimultaneNew  =  0;
		int nbrEloignement = 0;

		boolean coord1=false;
		boolean coord2=false;
		boolean coord3=false;
		boolean coord4=false;
		
		while(!listNonTraite.isEmpty()) {
			coord1=false;
			coord2=false;
			coord3=false;
			coord4=false;
			//si le premier de la liste des nonTraite n'est pas deja traite alors ...
			if(!verifDejaTraite(new Coordonnees(listNonTraite.get(0).getX(), listNonTraite.get(0).getY()), listTraite)) {
				
				//pr chq if : si la case a cote n'est pas dejatraite alors je l'ajoute au nonTraite
				//System.out.println(n.getObjetPoussable().get(0).getCoord().getX()+"***"+(listNonTraite.get(0).getX()+1*Tuile.TAILLE));
				for(ObjetPoussable d :n.getObjetPoussable()) {
					if(d.getCoord().getTuileLigne() == listNonTraite.get(0).getX() + 1 && d.getCoord().getTuileColonne() == listNonTraite.get(0).getY()) {
						coord1 = true;
					}  
					if(d.getCoord().getTuileLigne() == listNonTraite.get(0).getX() - 1 && d.getCoord().getTuileColonne() == listNonTraite.get(0).getY()) {
						coord2 = true;
					}
					if(d.getCoord().getTuileLigne() == listNonTraite.get(0).getX() && d.getCoord().getTuileColonne() == listNonTraite.get(0).getY() + 1) {
						coord3 = true;
					}
					if(d.getCoord().getTuileLigne() == listNonTraite.get(0).getX() && d.getCoord().getTuileColonne() == listNonTraite.get(0).getY() - 1) {
						coord4 = true;
					}
				}


				if(coord1 == false && (!verifDejaTraite(new Coordonnees(listNonTraite.get(0).getX() + 1,listNonTraite.get(0).getY()),listTraite)) && ( map[(int) (listNonTraite.get(0).getX() + 1)][(int) listNonTraite.get(0).getY()] <= 12)){
					listNonTraite.add(new Coordonnees(listNonTraite.get(0).getX() + 1,listNonTraite.get(0).getY()));
					iterationSimultaneNew++;
				}
				if(coord2 == false && (!verifDejaTraite(new Coordonnees(listNonTraite.get(0).getX() - 1,listNonTraite.get(0).getY()),listTraite)) && (map[(int) (listNonTraite.get(0).getX() - 1)][(int) listNonTraite.get(0).getY()] <= 12)){
					listNonTraite.add(new Coordonnees(listNonTraite.get(0).getX() - 1,listNonTraite.get(0).getY()));
					iterationSimultaneNew++;
				}
				if(coord3 == false && (!verifDejaTraite(new Coordonnees(listNonTraite.get(0).getX(),listNonTraite.get(0).getY() + 1),listTraite)) && (map[(int) listNonTraite.get(0).getX()][(int) (listNonTraite.get(0).getY() + 1)] <= 12)){
					listNonTraite.add(new Coordonnees(listNonTraite.get(0).getX(),listNonTraite.get(0).getY() + 1));
					iterationSimultaneNew++;
				}
				if(coord4 == false && (!verifDejaTraite(new Coordonnees(listNonTraite.get(0).getX(),listNonTraite.get(0).getY() - 1),listTraite)) && (map[(int) listNonTraite.get(0).getX()][(int) (listNonTraite.get(0).getY() - 1)] <= 12)){
					listNonTraite.add(new Coordonnees(listNonTraite.get(0).getX(),listNonTraite.get(0).getY() - 1));
					iterationSimultaneNew++;
				}
				
			}
			
			//...je l'ajoute aux traite, l'enleve des nontraite et je stocke sa valeur du chemin
			listTraite.add(new Coordonnees(listNonTraite.get(0).getX(),listNonTraite.get(0).getY()));
			mapBfsFinale[(int) listNonTraite.get(0).getX()][(int) listNonTraite.get(0).getY()] = nbrEloignement;
			listNonTraite.remove(0);

			iterationSimultaneOld--;
			if(iterationSimultaneOld == 0) {
				iterationSimultaneOld = iterationSimultaneNew;
				iterationSimultaneNew = 0;
				nbrEloignement++;
			}
			
		}
		
		return mapBfsFinale;
	}
}