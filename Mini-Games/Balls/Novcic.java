import java.awt.*;
import java.awt.event.*;
public class Novcic extends Figura{

	public Novcic(Polje mojePolje) {
		super(mojePolje);
	}
	
	public void crtaj() {
		Graphics g = mojePolje.getGraphics();
		g.setColor(Color.YELLOW);
		g.fillOval(mojePolje.getWidth() / 4, mojePolje.getHeight() / 4, mojePolje.getWidth() / 2, mojePolje.getHeight() / 2);
	}
	
	
}
