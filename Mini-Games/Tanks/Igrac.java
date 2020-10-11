package igraci;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura{
	private boolean pomeraj = true;					//da ne pomera igraca kad se zavrsi igra
	public Igrac(Vektor centar, double precnik, Color c, Vektor brzina, Scena s) {
		super(centar, precnik, c, brzina, s);
	}
	public void pomeriVreme(double t) {

	}
	@Override
	public void sudarila() {
		pomeraj = false;
		scena.zaustavi();
	}
	
	public void pomeri(double x) {
		if(pomeraj) {
			if(x + centar.getX() - precnik / 2 < 0)
				x = precnik / 2 - centar.getX();
			if(x + centar.getX() + precnik / 2 > scena.getWidth())
				x = scena.getWidth() - precnik / 2 - centar.getX();
			centar.saberi(new Vektor(x,0));
		}
	}
	
	public void crtaj(Scena s) {
		super.crtaj(s);
		Graphics g = s.getGraphics();
		g.setColor(Color.BLUE);
		double x = centar.getX() - precnik / 4;
		double y = centar.getY() - precnik / 4;
		g.fillOval((int)x, (int)y, (int)precnik / 2,(int) precnik / 2);
	}
}
