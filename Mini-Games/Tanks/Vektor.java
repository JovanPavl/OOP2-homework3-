package igraci;

public class Vektor implements Cloneable{
	private double x,y;
	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void pomnozi(double v) {
		x *= v;
		y *= v;
	}
	public Vektor clone() {
		try {
			Vektor tmp = (Vektor)super.clone();
			tmp.x = this.x;
			tmp.y = this.y;
			return tmp;
		}
		catch(Exception e) {
			return null;
		}
	}
	public void saberi(Vektor v) {			//pretpostavljam da nije staticka 
		this.x += v.x;
		this.y += v.y;
	}
}
