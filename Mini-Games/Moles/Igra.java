package polje;
import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame{
	private Button b;
	private Basta bb;
	private static final int[] koraci = {10,8,6};
	private static final int[] vremena = {1000,750,500};
	private Igra() {
		super("Igra");
		setSize(500,400);
		setLayout(new BorderLayout());
		bb = new Basta(4,4);
		bb.setKoraci(10);
		bb.setCekanje(1000);
		add(bb,BorderLayout.CENTER);
		Panel zapad = new Panel(new GridLayout(2,1));
		zapad.add(dodajDugmad());
		zapad.add(bb.getLabelu());
		add(zapad,BorderLayout.EAST);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				bb.zaustaviNit();
				dispose();
			}
		});
		setVisible(true);
	}
	
	public Igra getIgra() {
		return this;
	}
	
	public static void main(String args[]) {
		new Igra();
	}
	
	
	private Panel dodajDugmad() {
		Panel p = new Panel(new GridLayout(5,1));
		Label pisi = new Label("Tezina :");
		pisi.setAlignment(Label.CENTER);
		pisi.setFont(new Font(null,Font.BOLD,15));
		CheckboxGroup ccc = new CheckboxGroup();
		Checkbox lako = new Checkbox("Lako",ccc,true);
		Checkbox srednje = new Checkbox("Srednje",ccc,false);
		Checkbox tesko = new Checkbox("Tesko",ccc,false);
		b = new Button("Kreni");
		p.add(pisi);
		p.add(lako);
		p.add(srednje);
		p.add(tesko);
		p.add(b);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String a = b.getLabel();
				if(a == "Kreni") {
					b.setLabel("Zustavi");
					bb.pokreniNit();
					lako.setEnabled(false);
					srednje.setEnabled(false);
					tesko.setEnabled(false);
				}
				else {
					b.setLabel("Pokreni");
					bb.zaustaviNit();
					lako.setEnabled(true);
					srednje.setEnabled(true);
					tesko.setEnabled(true);
				}
			}
			
		});
		Checkbox[] tmparr = new Checkbox[3];
		tmparr[0] = lako;
		tmparr[1] = srednje;
		tmparr[2] = tesko;
		for(int i = 0; i < 3; i++) {
			tmparr[i].addItemListener(new Ochek(i));
		}
		return p;
	}
	private class Ochek implements ItemListener{
		private int i;
		public Ochek(int i) {
			this.i = i;
		}
		public void itemStateChanged(ItemEvent e) {
			bb.setKoraci(koraci[i]);
			bb.setCekanje(vremena[i]);
		}
	}
}
/*----------------------------------------Neki listeneri---------------------------------------*/
/*addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
		}
});*/
/*MenuBar mb = new MenuBar();
		setMenuBar(mb);
		Menu rezim = new Menu("Rezim");
		MenuItem riz = new MenuItem("Rezim izmena");
		MenuItem rig = new MenuItem("Rezim igranja");
		rezim.add(riz);
		rezim.add(rig);
		mb.add(rezim);
*/
