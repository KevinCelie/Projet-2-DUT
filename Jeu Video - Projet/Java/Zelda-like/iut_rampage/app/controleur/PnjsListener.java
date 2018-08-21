package app.controleur;

import app.modele.deplacable.Pnj;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class PnjsListener implements ListChangeListener<Pnj> {

	@Override
	public void onChanged(Change<? extends Pnj> pList) {
		while (pList.next()) {
			//Ajout de pnj
			for (Pnj p : pList.getAddedSubList()) {
				
				DeplacableVue dV = ControleurJeu.paneDV.creerPnjVue(p);
				ControleurJeu.hmDeplacables.put(p, dV);
				
				//listener pr deplacer la vue en meme temps
				p.getCoord().getXProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(p).relocate(newV.doubleValue(), p.getCoord().getY());
				});
				p.getCoord().getYProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(p).relocate(p.getCoord().getX(), newV.doubleValue());
				});
			}
			//Supression de pnj
			for (Pnj p : pList.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(p));
			}
		}
	}

}
