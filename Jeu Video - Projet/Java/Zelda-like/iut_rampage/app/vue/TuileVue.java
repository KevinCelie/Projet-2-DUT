package app.vue;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TuileVue extends ImageView {

	public TuileVue(Image i) {
		super(i);
	}
	
	public void setViewport(int id) {
		this.setViewport(new Rectangle2D(50*((id-1)%10), 50*(int)((id-1)/10), 50, 50));
	}

}
