import java.awt.*;
import java.awt.event.*;
public abstract class Polje extends Canvas {
	private Mreza mojaMreza;
	public Polje(Mreza mojaMreza) {
		this.mojaMreza = mojaMreza;
		setSize(20,20);
	}
	public int[] pozicija() {
		int[] arr = new int[2];
		Polje[][] matr = mojaMreza.matrica();
		for(int i = 0; i < matr.length; i++) {
			for(int j = 0; j < matr[i].length; j++) {
				if(matr[i][j] == this) {
					arr[0] = i;
					arr[1] = j;
					return arr;
				}
			}
		}
		return arr;
	}
	public Polje getPolje(int x,int y) {
		int[] poz = pozicija();
		int n = poz[0];
		int m = poz[1];
		Polje[][] matr = mojaMreza.matrica();
		if(n + x >= matr.length | n + x < 0) {
			return null;
		}
		if(m + y >= matr[0].length || m + y < 0) {
			return null;
		}
		return matr[n + x][m + y];
	}
	public abstract boolean dozvoljena(Figura f);

}
