package sg_battles.ships;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;



public class PuddleJumper extends Ship {

	public PuddleJumper(double[] souradnice, int skupina) {
		setSkupina(skupina);
		this.setZdravi(50);
		this.setMaxZdravi(50);
		this.setStity(0);
		this.setMaxStity(0);
		this.setTvar(4);
		this.setArmor(2);
		this.setMaxSpeed(12);
		int[] zbrane = new int[1];
		nastavZbrane(zbrane);
		this.setZbrane(zbrane);
		setSouradnice(souradnice);
		
		nastavArea();
		
		int[] pocitadlo = new int[getZbrane().length];
		this.setPocitadloVystrelu(pocitadlo);
		
	}

	private void nastavArea() {
		Pictures obr = new Pictures();
		double[] souradnice = this.getSouradnice();
		Ellipse2D el = 
				new Ellipse2D.Double(souradnice[0]-(obr.getObrazek(getTvar()).getWidth()/getPOMER_VELIKOST())/2, 
						souradnice[1]-(obr.getObrazek(getTvar()).getHeight()/getPOMER_VELIKOST())/2, 
				obr.getObrazek(getTvar()).getWidth()/getPOMER_VELIKOST(), 
				obr.getObrazek(getTvar()).getHeight()/getPOMER_VELIKOST());
		Area oblast = new Area(el);
		this.setKolize(oblast);
	}

	public void setSouradnice(double[] souradnice) {
		super.setSouradnice(souradnice);
	}
	
	private void nastavZbrane(int[] zbrane) {
		zbrane[0]=6;
	}
}
