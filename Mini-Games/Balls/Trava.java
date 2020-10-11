import java.awt.*;
import java.awt.Event.*;
public class Trava extends Polje {
	public Trava(Mreza mojaMreza) {
		super(mojaMreza);
	}
	public boolean dozvoljena(Figura f) {
		return true;
	}
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
