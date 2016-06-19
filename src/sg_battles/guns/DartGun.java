package sg_battles.guns;

import java.awt.BasicStroke;
import java.awt.Color;


public class DartGun extends Guns {
	private final int RANGE = 500;
	private final int TYPE = 2;
	private final int RYCHLOST = 100;
	private final int RYCHLOST_STRELBY = 20;
	private final int SILA = 5;
	private final boolean NAVADENA = false;
	private final Color BARVA = Color.BLUE;
	private final BasicStroke STETEC = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	public DartGun(double[] souradnice, double smer) {
		setPosition(souradnice);
		setAngle(smer);
		
		setDamage(SILA);
		setFireSpeed(RYCHLOST_STRELBY);
		setSpeed(RYCHLOST);
		setType(TYPE);
		setRange(RANGE);
		setGuided(NAVADENA);
		setColor(BARVA);
		setBrush(STETEC);
		
		nastavTvar();	
	}
	
	public DartGun() {
		setDamage(SILA);
		setFireSpeed(RYCHLOST_STRELBY);
		setSpeed(RYCHLOST);
		setType(TYPE);
		setRange(RANGE);
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

	public int getRANGE() {
		return RANGE;
	}

	public int getTYPE() {
		return TYPE;
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
