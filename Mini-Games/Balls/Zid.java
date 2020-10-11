import java.awt.*;
import java.awt.event.*;
public class Zid extends Polje{
	public Zid(Mreza mojaMreza) {
		super(mojaMreza);
	}
	public boolean dozvoljena(Figura f) {
		return false;
	}
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
