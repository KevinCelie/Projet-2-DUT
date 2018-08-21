package app.controleur;

import app.modele.deplacable.Thunder;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class ThunderListener implements ListChangeListener <Thunder>  {


	@Override
	public void onChanged(Change<? extends Thunder> c) {

		while (c.next()) {

			//Ajout de pnj
			for (Thunder e : c.getAddedSubList()) {

				DeplacableVue dV = ControleurJeu.paneDV.creerThunderVue(e);

				ControleurJeu.hmDeplacables.put(e, dV);

				//listener pr deplacer la vue en meme temps
				e.getCoord().getXProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(e).relocate(newV.doubleValue(), e.getCoord().getY());
				});
				e.getCoord().getYProperty().addListener((obs, oldV, newV) -> {
					ControleurJeu.hmDeplacables.get(e).relocate(e.getCoord().getX(), newV.doubleValue());
				});
			}
			//Supression de pnj
			for (Thunder e : c.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(e));
			}
		}

	}

}
