package polje;

import java.awt.Color;

public class Krtica extends Zivotinja{

	public Krtica(Rupa mojaRupa) {
		super(mojaRupa);	
	}

	@Override
	public void udarenaZivotinja() {
		mojaRupa.zaustaviNit();
		
	}

	@Override
	public void pobeglaZivotinja() {
		mojaRupa.getBasta().smanjiPovrce();
	}

	@Override
	public void crtaj(int x) {
		int curw = (mojaRupa.getWidth() / mojaRupa.brojKoraka()) * x;		//vidi mozda u double da stavis zbog preciznosti
		int curh = (mojaRupa.getHeight() / mojaRupa.brojKoraka()) * x;
		if(curw % 2 == 0 && curh % 2 == 0) {
			int tmp1 = curw / 2;
			int tmp2 = curh / 2;;
			mojaRupa.getGraphics().fillOval(mojaRupa.getWidth() / 2 - tmp1 , mojaRupa.getHeight() / 2 - tmp2, curw, curh);
		}
	}
	

}
