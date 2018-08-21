package app.controleur;

import app.modele.deplacable.Projectile;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class ProjectilesListener implements ListChangeListener<Projectile> {
	@Override
	public void onChanged(Change<? extends Projectile> pList) {
		while (pList.next()) {
			//Ajout de projectile
			for (Projectile p : pList.getAddedSubList()) {
				
				DeplacableVue dV = ControleurJeu.paneDV.creerProjectileVue(p);
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
			for (Projectile p : pList.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(p));
			}
		}
	}		
}
