package app.controleur;
import app.modele.deplacable.Ennemi;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class LaptopListener implements ListChangeListener<Ennemi> {

	@Override
	public void onChanged(Change<? extends Ennemi> eList) {
		while (eList.next()) {
			//Ajout de pnj
			for (Ennemi e : eList.getAddedSubList()) {
				DeplacableVue dV = ControleurJeu.paneDV.creerLaptopVue(e);
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
			for (Ennemi e : eList.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(e));
			}
		}
	}

}
