package sg_battles.ships;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Pictures {
	private ArrayList<BufferedImage> obrazky;
	
	public Pictures() {
		obrazky = new ArrayList<BufferedImage>();
		pridej();
	}

	private void pridej() {
		try {
			BufferedImage obrazek = ImageIO.read(new File("pics/Daedalus.png"));
			obrazky.add(0, obrazek);
			obrazek = ImageIO.read(new File("pics/Hive.png"));
			obrazky.add(1, obrazek);
			obrazek = ImageIO.read(new File("pics/Ori.png"));
			obrazky.add(2, obrazek);
			obrazek = ImageIO.read(new File("pics/Atlantis.png"));
			obrazky.add(3, obrazek);
			obrazek = ImageIO.read(new File("pics/PuddleJumper.png"));
			obrazky.add(4, obrazek);
			obrazek = ImageIO.read(new File("pics/X302.png"));
			obrazky.add(5, obrazek);
			obrazek = ImageIO.read(new File("pics/Satellite.png"));
			obrazky.add(6, obrazek);
			obrazek = ImageIO.read(new File("pics/Dart.png"));
			obrazky.add(7, obrazek);
		} catch (IOException e) {
			System.out.println("Chyba pri nacitani obrazku");
		}
		
		
	}
	
	public BufferedImage getObrazek(int i) {
		return obrazky.get(i);
	}

}
