package igraci;
import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame{
	Scena s;
	public Igra() {
		super("Baloni");
		setSize(400,500);
		s = new Scena();
		add(s);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				s.zaustavi();
				dispose();
			}
		});
		setVisible(true);
		s.pokreni();
	}
	public static void main(String args[]) {
		new Igra();
	}
}
/*-----------------Neki listneri koji postoje----------------------------------------*/
/*private class Ochek implements ItemListener{			//za checkbox
	
	public void itemStateChanged(ItemEvent e) {
	}
}*/
/*private class SlusacMisa extends MouseAdapter {		//dodas na kanvas

	public SlusacMisa(int x, int y) {
	
	}

	public void mouseClicked(MouseEvent d) {
	
	}
}*/