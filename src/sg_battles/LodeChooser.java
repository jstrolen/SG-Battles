package sg_battles;

import sg_battles.ships.*;

import java.util.ArrayList;

public class LodeChooser {
	private int[] lodeCisla;
	private String[] lodeNazvy;
	private ArrayList<Ship> obj;

	public LodeChooser() {
		lodeCisla=new int[]{1,2,3,4,5,6};
		lodeNazvy=new String[]{"Atlantis", "Daedalus", "Hive", "Ori", "PuddleJumper", "X302"};
		incObj();
	}
	
	private void incObj() {
		obj = new ArrayList<Ship>();
		obj.add(new Atlantis(new double[]{0,0}, 0));
		obj.add(new Daedalus(new double[]{0,0}, 0));
		obj.add(new Hive(new double[]{0,0}, 0));
		obj.add(new Ori(new double[]{0,0}, 0));
		obj.add(new PuddleJumper(new double[]{0,0}, 0));
		obj.add(new X302(new double[]{0,0}, 0));
	}

	public int[] getLodeCisla() {
		return lodeCisla;
	}
	
	public void setLodeCisla(int[] lodeCisla) {
		this.lodeCisla = lodeCisla;
	}
	
	public String[] getLodeNazvy() {
		return lodeNazvy;
	}
	
	public void setLodeNazvy(String[] lodeNazvy) {
		this.lodeNazvy = lodeNazvy;
	}
	
	public ArrayList<Ship> getObj() {
		return obj;
	}

	public void setObj(ArrayList<Ship> obj) {
		this.obj = obj;
	}

}
