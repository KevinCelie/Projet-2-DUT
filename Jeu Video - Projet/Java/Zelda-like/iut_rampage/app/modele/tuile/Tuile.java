package app.modele.tuile;

import app.modele.deplacable.Deplacable;
import app.modele.statique.ObjetStatique;
import app.modele.statique.SacAToutou;

public class Tuile {
	
	public final static int TAILLE = 50;
	
	private Deplacable deplacable;
	private ObjetStatique objStatique;
	private int id;
	
	public Tuile(int id) {
		this.id = id;
	}
	public Tuile(int id, ObjetStatique objStatique) {
		this.id = id;
		this.objStatique = objStatique;
	}
	
	
	//GETTERS
	public int getID() {
		return this.id;
	}
	public Deplacable getEnnemi() {
		return this.deplacable;
	}
	public ObjetStatique getObjStatique() {
		return this.objStatique;
	}
	
	//SETTERS
	public void setObjetStatique(ObjetStatique objStatique) {
		this.objStatique = objStatique;
		if (objStatique == null)
		{
			switch(id)
			{
			case 0:
				this.id = 12;
				break;
			}
		}
	}
	
	public void setADeplacable (Deplacable d) {
		this.deplacable = d;
	}
}
