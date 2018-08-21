package app.controleur;
import app.modele.deplacable.ObjetPoussable;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class ObjetPousListener  implements ListChangeListener<ObjetPoussable>{


	@Override
	public void onChanged(Change<? extends ObjetPoussable> eList) {
		while (eList.next()) {
			//Ajout de pnj
			for (ObjetPoussable e : eList.getAddedSubList()) {
				
				DeplacableVue dV = ControleurJeu.paneDV.creerObjetPousVue(e);
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
			for (ObjetPoussable e : eList.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(e));
			}
		}
	}

}
