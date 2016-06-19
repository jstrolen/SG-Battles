package sg_battles.levels;

import sg_battles.ships.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Level_1 extends Levels {
	
	public Level_1() {
		setObjekty(new ArrayList<Ship>());
		setNep(new Relation());
		nepratelstvi();
		pridej();
		setJA(0);
		setSIRKA(2500);
		setVYSKA(1500);
		setPozice(new double[]{200,400});
		setSkupiny(2);
		try {
			setPozadi(ImageIO.read(new File("pics/space.jpg")));
		} catch (IOException e) {
			System.out.println("Nelze na��st pozad�");
		}
		//setMuj(new Atlantis(getPozice(), 0));
		setMuj(new Daedalus(getPozice(), 0));
		//setMuj(new PuddleJumper(getPozice(), 0));
	}

	private void nepratelstvi() {
		getNep().pridej(0, 1, 1);
		getNep().pridej(1, 0, 1);
	}

	private void pridej() {
		//getObjekty().add(new Hive(new double[]{200,200}, 0));
		getObjekty().add(new Atlantis(new double[]{1500,1500}, 1));
		//getObjekty().add(new Ori(new double[]{200,400}, 0));
		getObjekty().add(new Hive(new double[]{2000,250}, 1));
		getObjekty().add(new Hive(new double[]{2000,400}, 1));
		getObjekty().add(new Ori(new double[]{2000,550}, 1));
		getObjekty().add(new Dart(new double[]{2000,100}, 1));
		getObjekty().add(new PuddleJumper(new double[]{2000,0}, 1));
		//getObjekty().add(new X302(new double[]{2000,500}, 1));
	}
}
