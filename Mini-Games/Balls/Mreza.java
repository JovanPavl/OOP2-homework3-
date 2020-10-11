import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class Mreza extends Panel implements Runnable {
	static final int brojfigura = 2;
	private Polje[][] matr;
	private Polje aktivno = new Zid(this);
	private ArrayList<Kord> gen = new ArrayList<Kord>();
	private ArrayList<Kord> rasporedFigura = new ArrayList<Kord>();
	private int brojPoena = 0;
	private Thread nit;
	private Panel matrica;
	private int tipPolja = 0;				//0 zid 1 trava
	private static enum nazivi {
		IGRAC, TENK, NOVCIC
	};

	private ArrayList<ArrayList<Figura>> liste;
	Igrac mojIgrac;
	private int dim = 0;
	private Panel podl;
	private Checkbox trava, zid;
	private boolean igraj = false,pokrenuta = false;
	private Label poeni;

	public Mreza(int dim) {
		this.dim = dim;
		matr = new Polje[dim][dim];
		Ucitaj();
	}

	public Mreza() {
		this.dim = 17;
		matr = new Polje[dim][dim];
		Ucitaj();
	}
	private void Ucitaj() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				gen.add(new Kord(i, j));
			}
		}
		int zeleno = (dim * dim * 8) / 10;
		matrica = new Panel(new GridLayout(dim, dim));;
		Random r = new Random();
		while (zeleno-- > 0) {
			int ind = r.nextInt(gen.size());
			Kord tmp = gen.get(ind);
			gen.remove(tmp);
			matr[tmp.x][tmp.y] = new Trava(this);
			matr[tmp.x][tmp.y].addMouseListener(new SlusacMisa(tmp.x, tmp.y));
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matr[i][j] == null) {
					matr[i][j] = new Zid(this);
					matr[i][j].addMouseListener(new SlusacMisa(i, j));
				}
			}
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				matrica.add(matr[i][j]);
			}
		}
		setLayout(new BorderLayout());
		add(matrica,BorderLayout.CENTER);
		postaviIzgled();
		liste = new ArrayList<ArrayList<Figura>>();
		for(int i = 0; i <= brojfigura; i++) {
			liste.add(new ArrayList<Figura>());
		}
	}

	private void postaviIzgled() {
		//RADI ZA SAD
		podl = new Panel(new BorderLayout());
		Label podloga = new Label("Podloga:");
		podloga.setFont(new Font(null,0,15));
		podloga.setAlignment(Label.CENTER);
		podl.add(podloga,BorderLayout.CENTER);
		CheckboxGroup cg = new CheckboxGroup();
		trava = new Checkbox("Trava", cg, true);
		zid = new Checkbox("Zid", cg, true);
		Ochek tr = new Ochek(1);
		Ochek zi = new Ochek(0);
		zid.addItemListener(zi);
		trava.addItemListener(tr);
		Panel slika = new Panel(new GridLayout(2, 1));
		Panel tt = new Panel();
		tt.setBackground(Color.green);
		tt.add(trava);
		Panel zz = new Panel();
		zz.setBackground(Color.LIGHT_GRAY);
		zz.add(zid);
		slika.add(tt);
		slika.add(zz);
		Panel konacan = new Panel(new GridLayout(1,2));
		konacan.add(podl);
		konacan.add(slika);
		add(konacan,BorderLayout.EAST);
		poeni = new Label("Poena: " + 0);
		dodajTastaturu();
	}

	public void postaviSve(int broj) {
		brojPoena = 0;
		poeni.setText("Poena: " + brojPoena);
		for(int i = 0; i < brojfigura; i++) {
			for(int j = 0; j < liste.get(i).size(); j++) {
				liste.get(i).get(j).getPolje().repaint();
			}
		}
		if(mojIgrac != null) {
			mojIgrac.getPolje().repaint();
		}
		for (int i = 0; i < brojfigura; i++) {
			liste.get(i).removeAll(liste.get(i)); // sve figure unisti
		}
		rasporedFigura.removeAll(rasporedFigura);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (matr[i][j] instanceof Trava) { // OBAVEZNO DODAJ JOS INSTANCI ! ! !
					rasporedFigura.add(new Kord(i, j));
				}
			}
		}
		Random r = new Random();
		int novcici = broj, tenkovi = broj / 3;
		while (novcici-- > 0) {
			int ind = r.nextInt(rasporedFigura.size());
			int xx = rasporedFigura.get(ind).x;
			int yy = rasporedFigura.get(ind).y;
			rasporedFigura.remove(ind);
			Novcic nnn = new Novcic(matr[xx][yy]);
			nnn.crtaj();
			liste.get(1).add(nnn);
		}
		while(tenkovi-- > 0) {
			int ind = r.nextInt(rasporedFigura.size());
			int xx = rasporedFigura.get(ind).x;
			int yy = rasporedFigura.get(ind).y;
			rasporedFigura.remove(ind);
			Tenk ttt = new Tenk(matr[xx][yy]);
			ttt.crtaj();
			liste.get(0).add(ttt);
		}
		int ind = r.nextInt(rasporedFigura.size());
		int xx = rasporedFigura.get(ind).x;
		int yy = rasporedFigura.get(ind).y;
		rasporedFigura.remove(ind);
		mojIgrac= new Igrac(matr[xx][yy]);
		mojIgrac.crtaj();
	}

	public Polje[][] matrica() {
		return matr;
	}
	
	public void zaustavi() {
		for(int i = 0; i < liste.get(0).size(); i++) {
			((Tenk)liste.get(0).get(i)).zaustaviTenk();				//zaustavi sve tenkove
		}
		nit.interrupt();
	}
	
	public synchronized void pokreni() {
		nit = new Thread(this);
		nit.start();
		for(Figura f : liste.get(0)) {
			if(f instanceof Tenk) {				//jbg menjaj ako bude jos neka ista figura
				((Tenk) f).pokreni();
			}
		}
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				for(Figura f : liste.get(1)) {			//iscrtaj novcice
					if(mojIgrac.getPolje() == f.getPolje())
						f.getPolje().repaint();
					else
						f.crtaj();
				}
				mojIgrac.crtaj();
				for(Figura f : liste.get(0)) {
					if(mojIgrac.getPolje() == f.getPolje())
						mojIgrac.getPolje().repaint();
					else
						f.crtaj();						//iscrtaj tenkove
				}
				for(int i = 0; i < liste.get(1).size(); i++) {
					if(liste.get(1).get(i).getPolje() == mojIgrac.getPolje()) {
						brojPoena++;
						liste.get(1).remove(liste.get(1).get(i));	
						poeni.setText("Poena " + brojPoena);
						break;											//sakupi novcic
					}
				}
				if(liste.get(1).size() == 0) {							//trenutno nema novcica
					setPokrenuta(false);
					zaustavi();
				}
				for(int i = 0; i < liste.get(0).size(); i++) {
					if(liste.get(0).get(i).getPolje() == mojIgrac.getPolje()) {
						setPokrenuta(false);
						zaustavi();
						break;
					}
				}
				Thread.sleep(40);
			}
		}catch(Exception e) {
			
		}
	}
	
	public boolean getIgraj() {
		return igraj;
	}
	
	public void setIgraj(boolean igraj) {
		this.igraj = igraj;
	}
	
	public boolean getPokrenuta() {
		return pokrenuta;
	}
	
	public void setPokrenuta(boolean pokrenuta) {
		this.pokrenuta = pokrenuta;
	}
	
	public Label getLabel() {
		return poeni;
	}
	/*--------------------Privatne pomocne klase-----------------------------------------*/

	private class Kord {
		private int x, y;

		public Kord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class Ochek implements ItemListener {
		private int tip;

		public Ochek(int tip) {
			this.tip = tip;
		}

		public void itemStateChanged(ItemEvent e) {
			tipPolja = tip;
		}
	}

	private class SlusacMisa extends MouseAdapter {
		private int x, y;

		public SlusacMisa(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void mouseClicked(MouseEvent d) {
			if (!igraj) {
				if(tipPolja == 1)
					aktivno = new Trava(Mreza.this);
				else
					aktivno = new Zid(Mreza.this);
				aktivno.addMouseListener(this);
				int z = x * dim + y;
				matrica.remove(z);
				matrica.add(aktivno,z);
				matr[x][y] = aktivno;
				matrica.revalidate();
				matr[x][y].repaint();
				matr[dim - 1][dim - 1].repaint();
			}
		}
	}
	private void dodajTastaturu() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(pokrenuta) {								//mod u kome trenutno igra
					switch(e.getKeyCode()) {
						case KeyEvent.VK_LEFT: mojIgrac.pomeri(0, -1);
									  break;
						case KeyEvent.VK_RIGHT: mojIgrac.pomeri(0, 1);
						  		break;								
						case KeyEvent.VK_DOWN: mojIgrac.pomeri(1, 0);
				  				break;
						case KeyEvent.VK_UP: mojIgrac.pomeri(-1, 0);
								break;
					}
				}
			}
		});
	}
}
