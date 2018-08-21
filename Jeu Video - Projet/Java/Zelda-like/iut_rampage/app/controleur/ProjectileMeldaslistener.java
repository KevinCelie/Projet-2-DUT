package app.controleur;

import app.modele.deplacable.ProjectileMeldas;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class ProjectileMeldaslistener implements ListChangeListener<ProjectileMeldas>{
	@Override
	public void onChanged(Change<? extends ProjectileMeldas> pList) {
		while (pList.next()) {
			//Ajout de projectile
			for (ProjectileMeldas p : pList.getAddedSubList()) {
				
				DeplacableVue dV = ControleurJeu.paneDV.creerProjectileMVue(p);
				ControleurJeu.hmDeplacables.put(p, dV);
				
				//listener pr deplacer la vue en meme temps
				p.getCoord().getXProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(p).relocate(newV.doubleValue(), p.getCoord().getY());
				});
				p.getCoord().getYProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(p).relocate(p.getCoord().getX(), newV.doubleValue());
				});
				
			}
			//Supression de projectile
			for (ProjectileMeldas p : pList.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(p));
			}
		}
	}		

}
