package polje;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Basta extends Panel implements Runnable {
	private Rupa[][] rupe;
	private int brojPovrca = 100;
	private int brojKoraka;
	private int n, m;
	private Thread nit = new Thread(this);
	private int cekanje;
	private ArrayList<Rupa> lista = new ArrayList<Rupa>();
	private Label povrce = new Label("Povrce: " + brojPovrca);

	public Basta(int n, int m) {
		this.setBackground(new Color(0, 170, 0));
		povrce.setFont(new Font(null,Font.BOLD,20));
		povrce.setAlignment(Label.CENTER);
		this.n = n;
		this.m = m;
		setLayout(new GridLayout(n, m, 20, 20));
		rupe = new Rupa[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				rupe[i][j] = new Rupa(this);
				add(rupe[i][j]);
				lista.add(rupe[i][j]);
				rupe[i][j].addMouseListener(new SlusacMisa(i, j));
			}
		}
	}

	private class SlusacMisa extends MouseAdapter {
		private int x, y;

		public SlusacMisa(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void mouseClicked(MouseEvent d) {
			Basta.this.rupe[x][y].ispoljiUdarena();
		}
	}

	public synchronized void pokreniNit() {
		nit.start();
	}

	public  void zaustaviNit() {
		nit.interrupt();
		for(int i = 0; i < n; i++) {					//obavezno zaustavi sve niti rupa
			for(int j = 0; j < m; j++) {
				rupe[i][j].zaustaviNit();
			}
		}
	}

	public synchronized void obavesti(Rupa r) {
		lista.add(r);
		notify();
	}

	public void setKoraci(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (rupe[i][j] != null) {
					rupe[i][j].setKoraci(brojKoraka);
				}
			}
		}
	}

	public int getKoraci() {
		return this.brojKoraka;
	}

	public void setCekanje(int cekanje) {
		this.cekanje = cekanje;
	}

	public synchronized void smanjiPovrce() {
		brojPovrca--;
		povrce.setText("Povrce :" + brojPovrca);
		if(brojPovrca == 0)
			zaustaviNit();
	}

	public Label getLabelu() {
		return povrce;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (lista.size() == 0) {
						wait();
					}
				}
				Random r = new Random();
				Thread.sleep(cekanje);
				int ind = r.nextInt(lista.size());
				cekanje = (cekanje * 99) / 100;
				lista.get(ind).stvoriNit();
				lista.get(ind).setZivotinja(new Krtica(lista.get(ind)));
				lista.get(ind).pokreniNit();
				lista.remove(ind);
			}
		} catch (InterruptedException e) {
		}
	}
}
