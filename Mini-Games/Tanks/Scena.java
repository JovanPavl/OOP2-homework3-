package igraci;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Scena extends Canvas implements Runnable{
	private ArrayList<KruznaFigura> lista = new ArrayList<KruznaFigura>();
	private Thread nit = new Thread(this);
	private Igrac glavniIgrac;
	
	public Scena() {
		addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(glavniIgrac != null) {
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						glavniIgrac.zabeli(Scena.this);				//mogao bi da sve to enkapsuliras u pomeri, vrv i hoces posle
						glavniIgrac.pomeri(-8);
						glavniIgrac.crtaj(Scena.this);
					}
					else {
						if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
							glavniIgrac.zabeli(Scena.this);
							glavniIgrac.pomeri(8);
							glavniIgrac.crtaj(Scena.this);
						}
					}
				}
				}
			});
	}
	public void izbaci(KruznaFigura k) {
		lista.remove(k);
	}
	public void dodajFigura(KruznaFigura k) {
		lista.add(k);
	}
	public void zaustavi() {
		nit.interrupt();
	}
	public void pokreni() {
		glavniIgrac = new Igrac(new Vektor(getWidth() / 2, getHeight() - 40),30,Color.green,new Vektor(0,0),this);
		lista.add(glavniIgrac);
		nit.start();
	}
	private void generisiRandom() {
		Random r = new Random();
		if(r.nextDouble() <= 0.1) {
			int w = r.nextInt(getWidth());
			lista.add(new Balon(new Vektor(w,0),20,Color.red,new Vektor(-2,4),this));		//mozes ovaj vektor i random zadavati
		}
	}
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 400, 500);
		for(KruznaFigura kf : lista) {
			kf.crtaj(this);
		}
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				generisiRandom();
				Thread.sleep(60);
				for(int i = 0; i < lista.size(); i++) {
					lista.get(i).obavestiProteklo(0.6);
				}
		/*		for(KruznaFigura kf : lista) {
					kf.crtaj(this);
				}*/
				for(int i = 0; i < lista.size(); i++) {
					for(int j = i + 1; j < lista.size(); j++) {
						if(Krug.preklapaju(lista.get(i), lista.get(j))) {
							lista.get(i).obavestiSudarila();
							lista.get(j).obavestiSudarila();
						}
					}
				}
			}
		}catch(InterruptedException e) {
			
		}
	}
}
