package polje;

public abstract class Zivotinja {
	protected Rupa mojaRupa;
	public Zivotinja(Rupa mojaRupa) {
		this.mojaRupa = mojaRupa;
	}
	
	public abstract void udarenaZivotinja();
	public abstract void pobeglaZivotinja();
	public abstract void crtaj(int x);				//trenutni korak 
}
