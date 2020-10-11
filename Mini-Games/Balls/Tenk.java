import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Tenk extends Figura implements Runnable{
	private Thread nit = new Thread(this);
	public Tenk(Polje mojePolje) {
		super(mojePolje);
	}
	public synchronized void pokreni() {
		nit.start();
	}
	public void zaustaviTenk() {
		nit.interrupt();
	}
	public synchronized void crtaj() {
		Graphics g = mojePolje.getGraphics();
		g.setColor(Color.black);
		g.drawLine(0, 0, mojePolje.getWidth(),mojePolje.getHeight());
		g.drawLine(mojePolje.getWidth(), 0, 0, mojePolje.getHeight());
	}
	public void run() {								//mozda posebno da obradis slucaj kad je okruzen sa 4 bloka, videcemo
		try {
			while(!Thread.interrupted()) {
					Random r = new Random();
					int x = 0, y = 0;
					int dodaj = r.nextInt(2) - 1;
					if(dodaj == 0)
						dodaj = 1;
					int smer = r.nextInt(2);
					if(smer == 0)
						x += dodaj;
					else
						y += dodaj;
					while(mojePolje.getPolje(x, y) == null ||  !mojePolje.getPolje(x, y).dozvoljena(this)) {
						x = 0;
						y = 0;
						dodaj = r.nextInt(2) - 1;
						if(dodaj == 0)
							dodaj = 1;
						smer = r.nextInt(2);
						if(smer == 0)
							x += dodaj;
						else
							y += dodaj;
	 				}
					mojePolje.repaint();
					mojePolje = (mojePolje.getPolje(x, y));
					Thread.sleep(500);
				}
		}catch(InterruptedException e) {
			
		}
	}
}
