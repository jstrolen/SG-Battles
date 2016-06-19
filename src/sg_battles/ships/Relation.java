package sg_battles.ships;


public class Relation {
	private int[][] skupiny;
	private final int MAX=10;
	
	public Relation() {
		skupiny=new int[MAX][MAX];
	}
	
	public void pridej(int skupinaKdo, int skupinaKomu, int stav) {
		skupiny[skupinaKdo][skupinaKomu]=stav;
		//	STAV
		//	 	0 - alli
		//		1 - enemy
		//		2 - neutral
	}
	
	public int getSpojenectvi(int kdo, int komu) {
		return skupiny[kdo][komu];
	}

}
