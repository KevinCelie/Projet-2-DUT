package app.controleur;
import app.modele.deplacable.GrilainMeldas;
import app.vue.DeplacableVue;
import javafx.collections.ListChangeListener;

public class DelmasListener implements ListChangeListener <GrilainMeldas>  {


	@Override
	public void onChanged(Change<? extends GrilainMeldas> c) {
		
		while (c.next()) {
			
			//Ajout de pnj
			for (GrilainMeldas e : c.getAddedSubList()) {

				DeplacableVue dV = ControleurJeu.paneDV.creerGrilainVue(e);
				
				ControleurJeu.hmDeplacables.put(e, dV);

				//listener pr deplacer la vue en meme temps
				
			}
			//Supression de pnj
			for (GrilainMeldas e : c.getRemoved()) {
				ControleurJeu.paneDV.getChildren().remove(ControleurJeu.hmDeplacables.get(e));
			}
		}

	}

}
