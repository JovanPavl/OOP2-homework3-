import java.awt.*;
import java.awt.event.*;
public class Igrac extends Figura{

	public Igrac(Polje mojePolje) {
		super(mojePolje);
	}

	@Override
	public void crtaj() {
		Graphics g = mojePolje.getGraphics();
		g.setColor(Color.red);
		g.drawLine(mojePolje.getWidth() / 2, 0, mojePolje.getWidth() / 2, mojePolje.getHeight());
		g.drawLine(0,mojePolje.getHeight() / 2,mojePolje.getWidth(), mojePolje.getHeight() / 2);
	}
	
	public void pomeri(int x, int y) {
		if(mojePolje.getPolje(x,y) != null && mojePolje.getPolje(x, y).dozvoljena(this)) {
			mojePolje.repaint();
			mojePolje = mojePolje.getPolje(x,y);
	//		crtaj();									dacu ipak mrezi da uradi ovaj deo
		}
	}

}
