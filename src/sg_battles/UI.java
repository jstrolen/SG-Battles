package sg_battles;

import sg_battles.guns.Shot;
import sg_battles.guns.Guns;
import sg_battles.ships.Relation;
import sg_battles.ships.Ship;
import sg_battles.ships.Pictures;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class UI {
	private Pictures obr;
	private final int MEZNI_VZDALENOST;
	private Shot vys;
	
	public UI(Pictures obr, int vzd) {
		this.obr=obr;
		MEZNI_VZDALENOST=vzd;
		vys = new Shot();
	}
	
	public void pohybLodi(Ship o, ArrayList<Ship> objekty, ArrayList<Guns> strely, Relation nep) {
		if (o.isNavrat()) zpet(o, objekty);
		else {
			if (o.getNepritel()!=null && o.getNepritel().getZdravi()<=0) o.setNepritel(null);
			if (o.getNepritel()==null) najdiNepritele(o, objekty, nep);
			if (o.getNepritel()!=null) {
				lodLetKCili(o, strely, objekty);
			}
			if (o.getNepritel()==null) {
				zastav(o);
				if (o.getMaxOnBorad()>o.getOnBoard()) letkaZpet(o, objekty);
			}
		}
	}

	private void zpet(Ship o, ArrayList<Ship> objekty) {
		lodZatocKMaterskeLodi(o);	
		zrychli(o);	
		if (new Point2D.Double(o.getSouradnice()[0], 
				o.getSouradnice()[1]).distance(o.getMaterskaLod().getSouradnice()[0], o.getMaterskaLod().getSouradnice()[1])<10) {
			o.setRychlost(0);
			o.getMaterskaLod().getLetka().add(o);
			o.getMaterskaLod().setOnBoard(o.getMaterskaLod().getOnBoard()+1);
			objekty.remove(o);
		}
	}
	
	private void lodZatocKMaterskeLodi(Ship o) {
		double sourX=o.getSouradnice()[0];
		double sourY=o.getSouradnice()[1];
		
		double vzdalenostX=o.getMaterskaLod().getSouradnice()[0]-sourX;
		double vzdalenostY=o.getMaterskaLod().getSouradnice()[1]-sourY;
		double uhelPozadovany=Math.atan2(vzdalenostY, vzdalenostX);
		
		o.setSmer(uhelPozadovany);
	}
	
	public void letkaZpet(Ship o, ArrayList<Ship> objekty) {
		for (Ship obj:objekty) {
			if (obj.getMaterskaLod()==o) obj.setNavrat(true);
		}
	}
	
	public void vypustLetku(Ship o, ArrayList<Ship> objekty) {
		Ship obj = o.getLetka().remove(o.getOnBoard()-1);
		obj.setSouradnice(o.getSouradnice()[0], o.getSouradnice()[1]);
		obj.setNavrat(false);
		objekty.add(obj); 
		o.setOnBoard(o.getOnBoard()-1);	
	}

	private void lodLetKCili(Ship o, ArrayList<Guns> strely, ArrayList<Ship> objekty) {
		lodZatocKCili(o);
		lodNastavRychlost(o);
		strilej(o, strely);
		if (o.getOnBoard()>0) vypustLetku(o, objekty);
	}

	private void lodZatocKCili(Ship o) {
		double sourX=o.getSouradnice()[0];
		double sourY=o.getSouradnice()[1];
		
		double vzdalenostX=o.getNepritel().getSouradnice()[0]-sourX;
		double vzdalenostY=o.getNepritel().getSouradnice()[1]-sourY;
		double uhelPozadovany=Math.atan2(vzdalenostY, vzdalenostX);
		
		o.setSmer(uhelPozadovany);
	}

	private void lodNastavRychlost(Ship o) {
		double vzdalenost=new Point2D.Double(o.getSouradnice()[0], 
				o.getSouradnice()[1]).distance(o.getNepritel().getSouradnice()[0], o.getNepritel().getSouradnice()[1]);
		if (vzdalenost>MEZNI_VZDALENOST/2) zrychli(o);
		else zastav(o);	
	}
	
	private void strilej(Ship o, ArrayList<Guns> strely) {
		for (int i=o.getZbrane().length-1; i>=0; i--) {
			if (o.getPocitadloVystrelu()[i]>0) o.getPocitadloVystrelu()[i]-=1;
		}	
		for (int i=o.getZbrane().length-1; i>=0; i--) {
			if (o.getPocitadloVystrelu()[i]==0) {			
				double vektor=o.getSmer();
				Guns[] strela=vys.vystrel(o.getZbrane()[i], o.getSouradnice(), vektor);
				for (int j=strela.length-1; j>=0; j--) {
					strela[j].setSource(o);
					if (o.getRychlost()>0) {	
						double smer=o.getSmer();
						double strelaSmer=strela[j].getAngle();
						smer = (Math.cos(smer)+1)/2;
						strelaSmer=(Math.cos(strelaSmer)+1)/2;							
						double pridanaRychlost=Math.abs(smer-strelaSmer);
						strela[j].setSpeed(strela[j].getSpeed()+(1-pridanaRychlost)*o.getRychlost());
						strela[j].setRange((int) (strela[j].getRange()+(2-2*pridanaRychlost)*strela[j].getRange()));
					}
					strely.add(strela[j]);
				}
				o.getPocitadloVystrelu()[i]=strela[0].getFireSpeed();
			}
		}	
	}

	public void vzad(Ship o) {
		if (o.getRychlost()>-o.getMaxSpeed()/2) o.setRychlost(Math.max(o.getRychlost()-o.getMaxSpeed()/60,-o.getMaxSpeed()/2));
	}

	public void zastav(Ship o) {
		if (o.getRychlost()>0) o.setRychlost(Math.max(o.getRychlost()-o.getMaxSpeed()/60, 0));
	}

	public void zrychli(Ship o) {
		if (o.getRychlost()<o.getMaxSpeed()) o.setRychlost(Math.min(o.getRychlost()+o.getMaxSpeed()/60,o.getMaxSpeed()));
	}

	private void najdiNepritele(Ship o, ArrayList<Ship> objekty, Relation nep) {
		double sourX=o.getSouradnice()[0];
		double sourY=o.getSouradnice()[1];
		
		double vzdalenost=2*MEZNI_VZDALENOST;
		boolean nalezen = false;
		int index=0;
		for (int i=objekty.size()-1; i>=0; i--) {
			Ship obj=objekty.get(i);
			if (obj==o) continue;
			if (nep.getSpojenectvi(o.getSkupina(), obj.getSkupina())!=1) continue;
			double x=obj.getSouradnice()[0]-sourX;
			double y=obj.getSouradnice()[1]-sourY;	
			double vzd=Math.sqrt(x*x+y*y);
			if (vzd<vzdalenost) {
				vzdalenost=vzd;
				index=i;
				nalezen = true;
			}
		}
		if (nalezen) o.setNepritel(objekty.get(index));
		else o.setNepritel(null);
	}

	public void setStity(Ship o) {
		double[] souradnice = o.getSouradnice();
		Ellipse2D el = 
				new Ellipse2D.Double(souradnice[0]-(obr.getObrazek(o.getTvar()).getWidth()/o.getPOMER_VELIKOST())/2, 
				souradnice[1]-(obr.getObrazek(o.getTvar()).getHeight()/o.getPOMER_VELIKOST())/2, 
				obr.getObrazek(o.getTvar()).getWidth()/o.getPOMER_VELIKOST(), 
				obr.getObrazek(o.getTvar()).getHeight()/o.getPOMER_VELIKOST());
		Area oblast = new Area(el);
		o.setKolize(oblast);	
	}
	
	public double[] pohyb(Guns z) {
		double x=z.getPosition()[0];
		double y=z.getPosition()[1];
		x = (x+(z.getSpeed()/5)*Math.cos(z.getAngle()));
		y = (y+(z.getSpeed()/5)*Math.sin(z.getAngle()));
		double[] souradnice = new double[]{x,y};
		return souradnice;
	}
	
	public double[] pohybNavadena(Guns z, ArrayList<Ship> objekty, Relation nep) {
	
		if (z.getTarget()!=null && z.getTarget().getZdravi()<=0) z.setTarget(null);
		
		if (z.getTarget()==null) {
			najdiNejblizsiObjektProStrelu(z, objekty, nep);
		}
		
		if (z.getTarget()!=null) {
			strelaZatocKCili(z);
		}
		
		return pohyb(z);
	}
	
	private void najdiNejblizsiObjektProStrelu(Guns z, ArrayList<Ship> objekty, Relation nep) {
		double zbranX=z.getPosition()[0];
		double zbranY=z.getPosition()[1];
		
		double vzdalenost=MEZNI_VZDALENOST;
		int index=0;
		boolean nalezen = false;
		for (int i=objekty.size()-1; i>=0; i--) {
			Ship o=objekty.get(i);
			if (z.getSource()==o) continue;
			if (nep.getSpojenectvi(z.getSource().getSkupina(), o.getSkupina())!=1) continue;
			double x=o.getSouradnice()[0]-zbranX;
			double y=o.getSouradnice()[1]-zbranY;	
			double vzd=Math.sqrt(x*x+y*y);
			if (vzd<vzdalenost) {
				vzdalenost=vzd;
				index=i;
				nalezen=true;
			}
		}
		if (nalezen) z.setTarget(objekty.get(index));
		else z.setTarget(null);
	}
	
	private void strelaZatocKCili(Guns z) {
		double zbranX=z.getPosition()[0];
		double zbranY=z.getPosition()[1];
		
		double vzdalenostX=z.getTarget().getSouradnice()[0]-zbranX;
		double vzdalenostY=z.getTarget().getSouradnice()[1]-zbranY;
		double uhelPozadovany=Math.atan2(vzdalenostY, vzdalenostX);	
		z.setAngle(uhelPozadovany);
	}

}
