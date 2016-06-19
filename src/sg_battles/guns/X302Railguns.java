package sg_battles.guns;
import java.awt.BasicStroke;
import java.awt.Color;

public class X302Railguns extends Guns {
	private final int DOSTREL = 500;
	private final int TYP = 2;
	private final int RYCHLOST = 50;
	private final int RYCHLOST_STRELBY = 8;
	private final int SILA = 2;
	private final boolean NAVADENA = false;
	private final Color BARVA = Color.ORANGE;
	private final BasicStroke STETEC = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	public X302Railguns(double[] souradnice, double smer) {
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
	
	private void nastavTvar() {
		int tvar[] = new int[4];
		tvar[0]=0;
		tvar[1]=0;
		tvar[2]=3;
		tvar[3]=3;
		setShape(tvar);
	}
	
	public X302Railguns() {	
		setDamage(SILA);
		setFireSpeed(RYCHLOST_STRELBY);
		setSpeed(RYCHLOST);
		setType(TYP);
		setRange(DOSTREL);
		setGuided(NAVADENA);
		setColor(BARVA);
		setBrush(STETEC);
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
