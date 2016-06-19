package sg_battles.guns;
import java.awt.BasicStroke;
import java.awt.Color;



public class JumperDrons extends Guns {
	private final int DOSTREL = 500;
	private final int TYP = 2;
	private final int RYCHLOST = 55;
	private final int RYCHLOST_STRELBY = 80;
	private final int SILA = 40;
	private final boolean NAVADENA = true;
	private final Color BARVA = Color.YELLOW;
	private final BasicStroke STETEC = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	public JumperDrons(double[] souradnice, double smer) {
		setPosition(souradnice);
		setAngle(smer);
		setDamage(SILA);
		setFireSpeed(RYCHLOST_STRELBY);
		setSpeed(RYCHLOST);
		setType(TYP);
		setRange(DOSTREL);
		setGuided(NAVADENA);
		setColor(BARVA);
		setBrush(STETEC);
		
		nastavTvar();
		
	}
	
	public JumperDrons() {	
		setDamage(SILA);
		setFireSpeed(RYCHLOST_STRELBY);
		setSpeed(RYCHLOST);
		setType(TYP);
		setRange(DOSTREL);
		setGuided(NAVADENA);
		setColor(BARVA);
		setBrush(STETEC);
	}

	private void nastavTvar() {
		int tvar[] = new int[4];
		tvar[0]=0;
		tvar[1]=0;
		tvar[2]=2;
		tvar[3]=2;
		setShape(tvar);
	}

	public int getDOSTREL() {
		return DOSTREL;
	}

	public int getTYP() {
		return TYP;
	}

	public int getRYCHLOST() {
		return RYCHLOST;
	}

	public int getRYCHLOST_STRELBY() {
		return RYCHLOST_STRELBY;
	}

	public int getSILA() {
		return SILA;
	}

	public boolean isNAVADENA() {
		return NAVADENA;
	}

	public Color getBARVA() {
		return BARVA;
	}

	public BasicStroke getSTETEC() {
		return STETEC;
	}
}
