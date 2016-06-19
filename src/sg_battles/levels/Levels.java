package sg_battles.levels;

import sg_battles.ships.Relation;
import sg_battles.ships.Ship;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Levels {
	private ArrayList<Ship> objekty;
	private int SIRKA;
	private int VYSKA;
	private Relation nep;
	private int JA;
	private double[] pozice;
	private int skupiny;
	private BufferedImage pozadi;
	private Ship muj;
	
	public ArrayList<Ship> getObjekty() {
		return objekty;
	}
	public void setObjekty(ArrayList<Ship> objekty) {
		this.objekty = objekty;
	}
	public int getSIRKA() {
		return SIRKA;
	}
	public void setSIRKA(int sIRKA) {
		SIRKA = sIRKA;
	}
	public int getVYSKA() {
		return VYSKA;
	}
	public void setVYSKA(int vYSKA) {
		VYSKA = vYSKA;
	}
	public Relation getNep() {
		return nep;
	}
	public void setNep(Relation nep) {
		this.nep = nep;
	}
	public int getJA() {
		return JA;
	}
	public void setJA(int jA) {
		JA = jA;
	}
	public double[] getPozice() {
		return pozice;
	}
	public void setPozice(double[] pozice) {
		this.pozice = pozice;
	}
	public int getSkupiny() {
		return skupiny;
	}
	public void setSkupiny(int skupiny) {
		this.skupiny = skupiny;
	}
	public BufferedImage getPozadi() {
		return pozadi;
	}
	public void setPozadi(BufferedImage pozadi) {
		this.pozadi = pozadi;
	}
	public Ship getMuj() {
		return muj;
	}
	public void setMuj(Ship muj) {
		this.muj = muj;
	}
}
