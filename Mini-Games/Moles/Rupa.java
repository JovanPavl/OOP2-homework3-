package polje;

import java.awt.*;

public class Rupa extends Canvas implements Runnable {
	private Basta b; // mozda se nesto bude izvodilo iz rupe pa stavi protected
	private Thread nit;
	private Zivotinja mojaZivotinja;
	private boolean pokrenuta = false;
	private int brojacKoraka = 0;
	int brojKoraka;
	private static int idcnt = 0;
	public int id = idcnt++;

	public Rupa(Basta b) {
		this.b = b;
		setSize(70, 70);
	}
	public void setZivotinja(Zivotinja z) {
		mojaZivotinja = z;
	}

	public Zivotinja getZivotinja() {
		return mojaZivotinja;
	}

	public Basta getBasta() {
		return b;
	}
	public void setKoraci(int brojKoraka) {
		this.brojKoraka = brojKoraka;
	}
	
	public int brojKoraka() {
		return brojKoraka;
	}

	public synchronized void stvoriNit() {
		nit = new Thread(this);
	}

	public synchronized void pokreniNit() {
		nit.start();
		pokrenuta = true;
	}

	public void zaustaviNit() {
		if(pokrenuta == true) {
			brojacKoraka = 0;
			repaint();
			pokrenuta = false;
			nit.interrupt();
			b.obavesti(this);
		}
	}

	public boolean pokrenutaNit() {
		return pokrenuta;
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(102, 51, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		if(mojaZivotinja != null) {
			mojaZivotinja.crtaj(brojacKoraka);
		}
	}
	public void run() {
		try {
		while(!Thread.interrupted()) {
				for(int i = 1; i <= brojKoraka; i++) {
					brojacKoraka = i;
					mojaZivotinja.crtaj(i);
					Thread.sleep(100);
				}
				Thread.sleep(2000);
				zaustaviNit();
				mojaZivotinja.pobeglaZivotinja();
				mojaZivotinja = null;
			}
		}catch(InterruptedException e) {
			return;
		}
	}
	
	public void ispoljiUdarena() {
		if(mojaZivotinja != null) {
			mojaZivotinja.udarenaZivotinja();
		}
	}
	
}
