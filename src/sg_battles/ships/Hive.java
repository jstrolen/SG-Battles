package sg_battles.ships;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;


public class Hive extends Ship {

	public Hive(double[] souradnice, int skupina) {
		setSkupina(skupina);
		this.setZdravi(3500);
		this.setMaxZdravi(3500);
		this.setStity(0);
		this.setMaxStity(0);
		this.setTvar(1);
		this.setArmor(10);
		this.setMaxSpeed(6);
		int[] zbrane = new int[1];
		nastavZbrane(zbrane);
		this.setZbrane(zbrane);
		setSouradnice(souradnice);
		nastavLetka();
		
		nastavArea();
		
		int[] pocitadlo = new int[getZbrane().length];
		this.setPocitadloVystrelu(pocitadlo);
		
	}
	
	private void nastavLetka() {
		setMaxOnBorad(5);
		setLetka(new ArrayList<Ship>());
		for (int i=0; i<getMaxOnBorad(); i++) {
			Ship o =new Dart(Arrays.copyOf(getSouradnice(), getSouradnice().length), getSkupina());
			o.setMaterskaLod(this);
			getLetka().add(o);
		}
		setOnBoard(getMaxOnBorad());
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
		zbrane[0]=4;		//Wraith gun

	}
}

