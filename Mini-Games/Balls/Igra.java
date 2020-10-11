import java.awt.*;
import java.awt.event.*;
public class Igra extends Frame{
	private Mreza mojaMreza;
	private TextField novac = new TextField(1);
	public Igra() {
		super("Igra");
		setSize(600,500);
		mojaMreza = new Mreza();
		setLayout(new BorderLayout());
		add(mojaMreza,BorderLayout.CENTER);
		dodajPodnozje();
		postaviMeni();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(mojaMreza.getPokrenuta()) {
					mojaMreza.zaustavi();
				}
				dispose();
			}
		});
		setVisible(true);
	}
	
	private void postaviMeni() {
		MenuBar mb = new MenuBar();
		setMenuBar(mb);
		Menu rezim = new Menu("Rezim");
		MenuItem riz = new MenuItem("Rezim izmena");
		MenuItem rig = new MenuItem("Rezim igranja");
		rig.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!mojaMreza.getIgraj()) {					//igra nije u toku
					mojaMreza.setIgraj(true);
				}
			}
			
		});
		
		riz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mojaMreza.getPokrenuta()) {
					mojaMreza.zaustavi();
				}
				mojaMreza.setIgraj(false);
				mojaMreza.setPokrenuta(false);
			}
		});
		rezim.add(riz);
		rezim.add(rig);
		mb.add(rezim);
	}
	private void dodajPodnozje() {
		Panel podnozje = new Panel(new FlowLayout());
		Label l = new Label("Novcica: ");
		l.setAlignment(Label.CENTER);
		podnozje.add(l);
		podnozje.add(novac);
		podnozje.add(mojaMreza.getLabel());
		Button b = new Button("Pocni");
		b.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent arg0) {
				if(mojaMreza.getIgraj()) {							//trenutno smo u rezimu igre
					if(mojaMreza.getPokrenuta()) {
						mojaMreza.zaustavi();
					}
					mojaMreza.postaviSve(Integer.parseInt(novac.getText()));
					mojaMreza.setPokrenuta(true);                                		//igra je trenutno pokrenuta
					mojaMreza.pokreni();
					mojaMreza.requestFocus();
				}
			}
			
		});
		podnozje.add(b);
		add(podnozje,BorderLayout.SOUTH);
	}
	public static void main(String args[]) {
		new Igra();
	}
}
