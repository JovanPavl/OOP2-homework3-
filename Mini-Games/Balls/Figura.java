
public abstract class Figura {
	protected Polje mojePolje;
	public Figura(Polje mojePolje) {
		this.mojePolje = mojePolje;
	}
	public Polje getPolje() {
		return mojePolje;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Figura other = (Figura) obj;
		return (other.mojePolje == this.mojePolje);
	}
	public abstract void crtaj();
}
