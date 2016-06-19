package sg_battles.ships;
import java.awt.geom.Area;
import java.util.ArrayList;

public class Ship {
	private double[] souradnice;
	private double rychlost;
	private double maxSpeed;
	private double smerPohyb;
	private double smer;
	private int zdravi;
	private int stity;
	private int maxZdravi;
	private int maxStity;
	private int armor;
	private int[] zbrane;
	private int tvar;
	private int[] pocitadloVystrelu;
	private Area kolize;
	private final int POMER_VELIKOST=10;
	private int skupina;
	private Ship nepritel;
	private ArrayList<Ship> letka;
	private Ship materskaLod=null;
	private int onBoard=0;
	private int maxOnBorad=0;
	private boolean navrat=false;
	

	public double[] getSouradnice() {
		return souradnice;
	}

	public void setSouradnice(double[] souradnice) {
		this.souradnice = souradnice;
	}
	
	public void setSouradnice(double x, double y) {
		this.souradnice[0]=x;
		this.souradnice[1]=y;
	}

	public double getRychlost() {
		return rychlost;
	}

	public void setRychlost(double rychlost) {
		this.rychlost = rychlost;
	}

	public double getSmerPohyb() {
		return smerPohyb;
	}

	public void setSmerPohyb(double smerPohyb) {
		this.smerPohyb = smerPohyb;
	}

	public double getSmer() {
		return smer;
	}

	public void setSmer(double smer) {
		this.smer = smer;
	}

	public int getZdravi() {
		return zdravi;
	}

	public void setZdravi(int zdravi) {
		this.zdravi = zdravi;
	}

	public int getStity() {
		return stity;
	}

	public void setStity(int stity) {
		this.stity = stity;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int[] getZbrane() {
		return zbrane;
	}

	public void setZbrane(int[] zbrane) {
		this.zbrane = zbrane;
	}

	public int getTvar() {
		return tvar;
	}

	public void setTvar(int tvar) {
		this.tvar = tvar;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int[] getPocitadloVystrelu() {
		return pocitadloVystrelu;
	}

	public void setPocitadloVystrelu(int[] pocitadloVystrelu) {
		this.pocitadloVystrelu = pocitadloVystrelu;
	}

	public Area getKolize() {
		return kolize;
	}

	public void setKolize(Area kolize) {
		this.kolize = kolize;
	}

	public int getPOMER_VELIKOST() {
		return POMER_VELIKOST;
	}

	public int getSkupina() {
		return skupina;
	}

	public void setSkupina(int skupina) {
		this.skupina = skupina;
	}

	public int getMaxStity() {
		return maxStity;
	}

	public void setMaxStity(int maxStity) {
		this.maxStity = maxStity;
	}

	public int getMaxZdravi() {
		return maxZdravi;
	}

	public void setMaxZdravi(int maxZdravi) {
		this.maxZdravi = maxZdravi;
	}

	public Ship getNepritel() {
		return nepritel;
	}

	public void setNepritel(Ship nepritel) {
		this.nepritel = nepritel;
	}

	public ArrayList<Ship> getLetka() {
		return letka;
	}

	public void setLetka(ArrayList<Ship> letka) {
		this.letka = letka;
	}

	public Ship getMaterskaLod() {
		return materskaLod;
	}

	public void setMaterskaLod(Ship materskaLod) {
		this.materskaLod = materskaLod;
	}

	public int getOnBoard() {
		return onBoard;
	}

	public void setOnBoard(int onBoard) {
		this.onBoard = onBoard;
	}

	public int getMaxOnBorad() {
		return maxOnBorad;
	}

	public void setMaxOnBorad(int maxOnBorad) {
		this.maxOnBorad = maxOnBorad;
	}

	public boolean isNavrat() {
		return navrat;
	}

	public void setNavrat(boolean navrat) {
		this.navrat = navrat;
	}
}
