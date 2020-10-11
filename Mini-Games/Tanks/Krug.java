package igraci;
import java.awt.*;
public class Krug {
	protected Vektor centar;
	protected double precnik;
	protected Color c;
	public Krug(Vektor centar, double precnik, Color c) {
		this.centar = centar;
		this.precnik = precnik;
		this.c = c;
	}
	public static boolean preklapaju(Krug A, Krug B) {
		return (A.precnik / 2 + B.precnik / 2 > Math.sqrt(Math.pow(A.centar.getX() - B.centar.getX(),2)
				+ Math.pow(A.centar.getY() - B.centar.getY(),2)));
	}
	public void crtaj(Scena s){
		double x = centar.getX() - precnik / 2;
		double y = centar.getY() - precnik / 2;
		Graphics g = s.getGraphics();
		g.setColor(c);
		g.fillOval((int)x, (int)y,(int) precnik,(int) precnik);
	}
	protected void zabeli(Scena s) {
		double x = centar.getX() - precnik / 2;
		double y = centar.getY() - precnik / 2;
		Graphics g = s.getGraphics();
		g.setColor(Color.white);
		g.fillOval((int)x, (int)y,(int) precnik,(int) precnik);
	}
}
