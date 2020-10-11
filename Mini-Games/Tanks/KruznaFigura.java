package igraci;

import java.awt.Color;

public abstract class KruznaFigura extends Krug{
	protected Vektor brzina;
	protected Scena scena;
	public KruznaFigura(Vektor centar, double precnik, Color c,Vektor brzina,Scena s) {
		super(centar,precnik,c);
		this.brzina = brzina;
		this.scena = s;
	}
	public void pomeriVreme(double vreme) {
		Vektor tmp = brzina.clone();
		tmp.pomnozi(vreme);
		super.zabeli(scena);
		centar.saberi(tmp);
		crtaj(scena);
	}
	public void sudarila() {
		
	}
	public void obavestiProteklo(double vreme) {
		pomeriVreme(vreme);
		if(centar.getY() > scena.getHeight() || centar.getY() < 0 || 
				centar.getX() > scena.getWidth() || centar.getX() < 0) {
			zabeli(scena);
			scena.izbaci(this);
		}
	}
	public void obavestiSudarila() {
		sudarila();
	}
}
