package sg_battles;

import sg_battles.ships.Relation;
import sg_battles.ships.Ship;
import sg_battles.ships.Pictures;
import sg_battles.levels.Levels;
import sg_battles.guns.Shot;
import sg_battles.guns.Guns;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;


public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<Ship> objekty;
	private ArrayList<Guns> strely;
	private final int SIRKA;
	private final int VYSKA;
	private Ship muj;
	private Pictures obr;
	private BufferedImage pozadi;
	private BufferedImage explozeMala;
	private BufferedImage explozeVelka;
	private BufferedImage explozeStit;
	private final int POMER_VELIKOST=12;
	private long cas0;
	private long cas1;
	private UI intel;
	private final int FPS = 35;
	private boolean palbaMysi=false;
	private double palbaMysiVektor;
	private boolean pohybMysi=false;
	private Shot vys;
	private boolean fps=false;
	private boolean hud=true;
	private boolean bar=true;
	private boolean pauza = false;
	private boolean zivy = true;
	private boolean vyhra = false;
	private Relation nep;
	private Window o;
	private double zoom=1;
	
	private int[] zbrane;
	
	private boolean vpred, vzad, vlevo, vpravo;
	private boolean[] palba;
	
	private boolean vypusteno = false;
	
	private Font hudFont;
	private Font podtrzenyFont;
	private Font konec;
	private BasicStroke stity;
	private BasicStroke okraje;
	private Color barvaHUD;

	
	public Panel(Dimension d, Levels lev, Window window) {
		this.setOpaque(true);
		try {
			explozeMala = ImageIO.read(new File("pics/exploze_mala.png"));
			explozeVelka = ImageIO.read(new File("pics/exploze_velka.png"));
			explozeStit = ImageIO.read(new File("pics/exploze_stit.png"));
		} catch (Exception e) {
			System.out.println("Nepodarilo se nacist obrazek.");
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					palbaVse(false);
					palbaMysi=false;
				}
				if (e.getButton()==MouseEvent.BUTTON3) {
					pohybMysi=false;
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					palbaVse(true);
					palbaMysi=true;
				}
				if (e.getButton()==MouseEvent.BUTTON3) {
					pohybMysi=true;
				}
			}
		});
		try {
			BufferedImage img = ImageIO.read(new File("pics/Zamerovac.png"));
			this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(16,16), "Kriz"));
		} catch (IOException e) {
			System.out.println("Nelze na��st kurzor.");
		}
		
		vys = new Shot();
		obr = new Pictures();
		intel = new UI(obr, (int) (3*(d.getWidth()+d.getHeight())/4));
		o= window;
		
		muj=lev.getMuj();
		palba=new boolean[muj.getZbrane().length];
		zbrane = new int[muj.getPocitadloVystrelu().length];
		for (int i=0; i<muj.getPocitadloVystrelu().length; i++) {
			zbrane[i]=vys.vratZbran(muj.getZbrane()[i]).getFireSpeed();
		}
		
		strely = new ArrayList<Guns>();
		objekty=lev.getObjekty();
		nep=lev.getNep();
		objekty.add(muj);
		muj.setSkupina(lev.getJA());
		muj.setSouradnice(lev.getPozice());
		SIRKA=lev.getSIRKA();
		VYSKA=lev.getVYSKA();
		pozadi=lev.getPozadi();
		
		nastav();
		
		cas1=System.currentTimeMillis();
		new javax.swing.Timer(1000/FPS, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!pauza) repaint();
			}
		}).start();
	}

	private void nastav() {
		konec = new Font("SansSerif", Font.BOLD, 24);
		hudFont = new Font("SansSerif", Font.BOLD, 16);
		Map<TextAttribute, Integer> podtrzeni = new HashMap<TextAttribute, Integer>();
		podtrzeni.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		podtrzenyFont = new Font("Arial", Font.BOLD, 16).deriveFont(podtrzeni);
		stity = new BasicStroke(2);
		okraje = new BasicStroke(5);
		barvaHUD=new Color(70,170,70);
	}
	
	private void spoctiVektorMysi(double x, double y) {			
		palbaMysiVektor=Math.atan2(y-this.getHeight()/2, x-this.getWidth()/2);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		
		AffineTransform af = g2.getTransform();
		g2.scale(zoom, zoom);
		g2.translate(-(this.getWidth()-this.getWidth()/zoom)/2, -(this.getHeight()-this.getHeight()/zoom)/2);
		
		cas0=System.currentTimeMillis();	
		if (palbaMysi) {
			try {
				Point2D p=Panel.this.getMousePosition();
				spoctiVektorMysi(p.getX(), p.getY());
			} catch (Exception e) {
				//My� mimo plochu
			}
		}

		if (zivy) zmenMe();
		zmenLode();
		
		tiskPozadi(g2);
		kresliOkraje(g2);

		pohybStrel(g2);
		if (zivy) vytvorStrely();
		kresliStrely(g2);

		kresliObjekty(g2);

		if (bar) kresliUdaje(g2);
		g2.setTransform(af);
		if (fps) kresliFPS(g2);
		if (hud) kresliPanel(g2);
		
		if (!objekty.contains(muj) && !vyhra) {
			zivy=false;
			g2.setColor(Color.CYAN);
			g2.setFont(konec);
			String s = "Game Over";
			int delka = (int)g2.getFontMetrics().getStringBounds(s, g2).getWidth();
			int vyska = (int)g2.getFontMetrics().getStringBounds(s, g2).getHeight();
			g2.drawString(s, this.getWidth()/2-delka/2, this.getHeight()/2-vyska/2);
		}
		
		if (zivy) {
			boolean pokracuj=false;
			for (int i=objekty.size()-1; i>=0; i--) {
				for (int j=objekty.size()-1; j>=0; j--) {
					if (nep.getSpojenectvi(objekty.get(i).getSkupina(), objekty.get(j).getSkupina())==1) {
						pokracuj=true;
						break;
					}
				}	
				if (pokracuj) break;
			}
			vyhra=!pokracuj;

			if (vyhra) {
				g2.setColor(Color.CYAN);
				g2.setFont(konec);
				String s = "Victory";
				int delka = (int)g2.getFontMetrics().getStringBounds(s, g2).getWidth();
				int vyska = (int)g2.getFontMetrics().getStringBounds(s, g2).getHeight();
				g2.drawString(s, this.getWidth()/2-delka/2, this.getHeight()/2-vyska/2);
			}
		}
		if (!zivy || vyhra) konec();
		this.removeAll();
	}

	private void konec() {
		TimerTask task = new TimerTask() {		
			@Override
			public void run() {
				o.dispose();
				System.exit(0); //TODO
			}
		};
		Timer t = new Timer();
		t.schedule(task, 200000l);		
	}

	private void zmenMe() {
		if (!pohybMysi) {
			stop();
			if (vpred) {
				intel.zrychli(muj);
			}
			if (vzad) {
				intel.vzad(muj);
			}
			if (vlevo && !vzad) {
				muj.setSmer(muj.getSmer()-muj.getMaxSpeed()/200);
			}
			if (vlevo && vzad) {
				muj.setSmer(muj.getSmer()+muj.getMaxSpeed()/200);
			}
			if (vpravo && !vzad) {
				muj.setSmer(muj.getSmer()+muj.getMaxSpeed()/200);
			}
			if (vpravo && vzad) {
				muj.setSmer(muj.getSmer()-muj.getMaxSpeed()/200);
			}
			if (vypusteno && muj.getOnBoard()>0) {
				intel.vypustLetku(muj, objekty);
			}
			if (!vypusteno && muj.getOnBoard()<muj.getMaxOnBorad()) 
				intel.letkaZpet(muj, objekty);
			if (vypusteno && muj.getOnBoard()<muj.getMaxOnBorad())
				for (Ship o:objekty) {
					if (o.getMaterskaLod()==muj) o.setNavrat(false);
				}
				
		}
		
		else {
			try {
				if (muj.getRychlost()<muj.getMaxSpeed()) muj.setRychlost(Math.min(muj.getRychlost()+muj.getMaxSpeed()/60,muj.getMaxSpeed()));
				Point2D p=Panel.this.getMousePosition();
				muj.setSmer(Math.atan2(p.getY()-this.getHeight()/2, p.getX()-this.getWidth()/2));
			} catch (Exception e) {
				//My� mimo plochu
			}
		}
	}
	
	public void stop() {
		if (!vpred && !vzad) {
			if (Math.abs(muj.getRychlost())<muj.getMaxSpeed()/20) muj.setRychlost(0);
			else if (muj.getRychlost()>0) muj.setRychlost(muj.getRychlost()-muj.getMaxSpeed()/50);
			else if (muj.getRychlost()<0) muj.setRychlost(muj.getRychlost()+muj.getMaxSpeed()/50);
		}
	}

	private void zmenLode() {
		for (int i=objekty.size()-1; i>=0; i--) {
			Ship o = objekty.get(i);
			if (o!=muj) intel.pohybLodi(o, objekty, strely, nep);
			o.setSmer(o.getSmer()%(2*Math.PI));
			double x = (o.getSouradnice()[0]+o.getRychlost()*Math.cos(o.getSmer()));
			double y = (o.getSouradnice()[1]+o.getRychlost()*Math.sin(o.getSmer()));
			if (x<0) x=0.0;
			else if (x>SIRKA) x=SIRKA;
			if (y<0) y=0.0;
			else if (y>VYSKA) y=VYSKA;
			o.setSouradnice(x, y);
			intel.setStity(o);
		}
	}
	
	private void tiskPozadi(Graphics2D g2) {
		int naSirku=SIRKA/(pozadi.getWidth())+1;
		int naVysku=VYSKA/(pozadi.getHeight())+1;
		int presahSirka=(this.getWidth())/(pozadi.getWidth())+1;
		int presahVyska=(this.getHeight())/(pozadi.getHeight())+1;
		for (int i=-presahSirka;i<=naSirku+presahSirka; i++) {
			for (int j=-presahVyska; j<=naVysku+presahVyska; j++) {
				g2.drawImage(pozadi, 
						(int)(-muj.getSouradnice()[0]+this.getWidth()/2+i*pozadi.getWidth()), 
						(int)(-muj.getSouradnice()[1]+this.getHeight()/2+j*pozadi.getHeight()), 
						pozadi.getWidth(), 
						pozadi.getHeight(), null);
			}
		}
	}
	
	private void kresliOkraje(Graphics2D g2) {
		g2.setStroke(okraje);
		g2.setColor(Color.RED);
		g2.drawRect((int)(-muj.getSouradnice()[0]+this.getWidth()/2), (int)(-muj.getSouradnice()[1]+this.getHeight()/2), SIRKA, VYSKA);	
	}

	private void pohybStrel(Graphics2D g2) {
		for (int i=strely.size()-1; i>=0; i--) {
			Guns z = strely.get(i);
			double sour[];
			double sourPuv[]=z.getPosition();
			if (!z.isGuided()) {
				sour = intel.pohyb(z);
			}
			else {
				sour = intel.pohybNavadena(z, objekty, nep);
			}
			z.setRange(z.getRange()-(int)(new Point2D.Double(sourPuv[0], sourPuv[1]).distance(sour[0], sour[1])));
			z.setPosition(sour);

			for (Ship o:objekty) {
				if (z.getSource()==o) continue;
				if (nep.getSpojenectvi(z.getSource().getSkupina(), o.getSkupina())!=1) continue;
				if(o.getKolize().contains(sour[0], sour[1])) {
					int damage = z.getDamage();
					int stity=o.getStity();
					int zdravi=o.getZdravi();
					if (stity-damage>0) {
						stity=stity-damage;
						AffineTransform puv = g2.getTransform();
						g2.translate(sour[0]-muj.getSouradnice()[0]+this.getWidth()/2, sour[1]-muj.getSouradnice()[1]+this.getHeight()/2);
						double delka=explozeStit.getWidth();
						double pomer=(double)explozeStit.getWidth()/explozeStit.getHeight();
						g2.drawImage(explozeStit, (int)(-delka/2), (int)(-delka/pomer/2), 
								(int)(delka), (int)(delka/pomer), null);
						g2.setTransform(puv);
					}
					else {
						stity=0;
						zdravi=zdravi-(damage-stity);					
						AffineTransform puv = g2.getTransform();
						g2.translate(sour[0]-muj.getSouradnice()[0]+this.getWidth()/2, sour[1]-muj.getSouradnice()[1]+this.getHeight()/2);
						double delka=explozeMala.getWidth();
						double pomer=(double)explozeMala.getWidth()/explozeMala.getHeight();
						g2.drawImage(explozeMala, (int)(-delka/2), (int)(-delka/pomer/2), 
								(int)(delka), (int)(delka/pomer), null);
						g2.setTransform(puv);
					}
					o.setStity(stity);
					o.setZdravi(zdravi);
					strely.remove(i);
					break;
				}
			}
		}
	}

	private void vytvorStrely() {
		for (int i=muj.getZbrane().length-1; i>=0; i--) {
			if (muj.getPocitadloVystrelu()[i]>0) muj.getPocitadloVystrelu()[i]-=1;
		}	
		for (int i=muj.getZbrane().length-1; i>=0; i--) {
			if (palba[i]) {
				if (muj.getPocitadloVystrelu()[i]==0) {
					double vektor=muj.getSmer();
					if (palbaMysi) vektor = palbaMysiVektor;
					Guns[] strela=vys.vystrel(muj.getZbrane()[i], muj.getSouradnice(), vektor);
					for (int j=strela.length-1; j>=0; j--) {
						strela[j].setSource(muj);
						if (muj.getRychlost()>0) {	
							double smer=muj.getSmer();
							double strelaSmer=strela[j].getAngle();
							smer = (Math.cos(smer)+1)/2;
							strelaSmer=(Math.cos(strelaSmer)+1)/2;							
							double pridanaRychlost=Math.abs(smer-strelaSmer);
							strela[j].setSpeed(strela[j].getSpeed()+(1-pridanaRychlost)*muj.getRychlost());
							strela[j].setRange((int) (strela[j].getRange()+(2-2*pridanaRychlost)*strela[j].getRange()));
						}
						strely.add(strela[j]);
					}
					muj.getPocitadloVystrelu()[i]=strela[0].getFireSpeed();
				}
			}
		}	
	}

	private void kresliStrely(Graphics2D g2) {	
		for (int i=strely.size()-1; i>=0; i--) {
			Guns z = strely.get(i);
			if (z.getRange()<=0) {
				strely.remove(i);
			}
			else {	
				AffineTransform puv = g2.getTransform();
				g2.translate(z.getPosition()[0]-muj.getSouradnice()[0]+this.getWidth()/2, z.getPosition()[1]-muj.getSouradnice()[1]+this.getHeight()/2);
				g2.rotate(z.getAngle()-Math.PI/4);
				g2.setColor(z.getColor());
				g2.setStroke(z.getBrush());
				g2.drawLine((int)(z.getShape()[0]), (int)(z.getShape()[1]),
						(int)(z.getShape()[2]), (int)(z.getShape()[3]));
				g2.setTransform(puv);
			}
		}
	}

	private void kresliObjekty(Graphics2D g2) {
		for (int i=objekty.size()-1; i>=0; i--) {
			Ship o = objekty.get(i);
			if (o.getZdravi()<=0) {
				objekty.remove(i);	
				AffineTransform puv = g2.getTransform();
				g2.translate(o.getSouradnice()[0]-muj.getSouradnice()[0]+this.getWidth()/2, o.getSouradnice()[1]-muj.getSouradnice()[1]+this.getHeight()/2);
				double delka=explozeVelka.getWidth();
				double pomer=(double)explozeVelka.getWidth()/explozeVelka.getHeight();
				g2.drawImage(explozeVelka, (int)(-delka/2), (int)(-delka/pomer/2), 
						(int)(delka), (int)(delka/pomer), null);
				g2.setTransform(puv);	
			}
			else {
				AffineTransform puv = g2.getTransform();
				g2.translate(o.getSouradnice()[0]-muj.getSouradnice()[0]+this.getWidth()/2, o.getSouradnice()[1]-muj.getSouradnice()[1]+this.getHeight()/2);
				g2.rotate(Math.PI+o.getSmer());
				BufferedImage ob=obr.getObrazek(o.getTvar());
				double delka=ob.getWidth()/POMER_VELIKOST;
				double pomer=(double)ob.getWidth()/ob.getHeight();
				g2.drawImage(ob, (int)(-delka/2), (int)(-delka/pomer/2), (int)(delka), (int)(delka/pomer), null);
				if (o.getStity()>0) {
					g2.setColor(Color.CYAN);
					g2.setStroke(stity);
					g2.setTransform(puv);
					g2.translate(this.getWidth()/2-muj.getSouradnice()[0]+o.getSouradnice()[0], 
							this.getHeight()/2-muj.getSouradnice()[1]+o.getSouradnice()[1]);	
					g2.rotate(Math.PI+o.getSmer());
					g2.drawOval((int)(-(obr.getObrazek(o.getTvar()).getWidth()/o.getPOMER_VELIKOST())/2), 
							(int)(-(obr.getObrazek(o.getTvar()).getHeight()/o.getPOMER_VELIKOST())/2), 
							obr.getObrazek(o.getTvar()).getWidth()/o.getPOMER_VELIKOST(), 
							obr.getObrazek(o.getTvar()).getHeight()/o.getPOMER_VELIKOST());
					//g2.draw(o.getKolize());
				}
				g2.setTransform(puv);
			}
		}
	}
	
	private void kresliUdaje(Graphics2D g2) {
		for (int i=objekty.size()-1; i>=0; i--) {
			Ship o = objekty.get(i);
			AffineTransform puv = g2.getTransform();
			g2.translate(o.getSouradnice()[0]-muj.getSouradnice()[0]+this.getWidth()/2, o.getSouradnice()[1]-muj.getSouradnice()[1]+this.getHeight()/2);
			BufferedImage ob=obr.getObrazek(o.getTvar());
			double delka=ob.getWidth()/POMER_VELIKOST;
			double pomer=(double)ob.getWidth()/ob.getHeight();
			g2.setColor(Color.WHITE);
			g2.fillRect((int)-(delka/2), (int)-(delka/pomer), (int)(delka), 3);
			g2.setColor(Color.RED);
			g2.fillRect((int)-(delka/2), (int)-(delka/pomer), (int)(delka*(o.getZdravi()/(double)o.getMaxZdravi())), 3);
			if (o.getStity()>0) {
				g2.setColor(Color.WHITE);
				g2.fillRect((int)-(delka/2), (int)-(delka/pomer)-3, (int)(delka), 3);
				g2.setColor(Color.CYAN);
				g2.fillRect((int)-(delka/2), (int)-(delka/pomer)-3, (int)(delka*(o.getStity()/(double)o.getMaxStity())), 3);
			}
			
			g2.setTransform(puv);
		}
	}
	
	private void kresliFPS(Graphics2D g2) {
		g2.setFont(hudFont);
		g2.setColor(Color.RED);
		String cas = String.valueOf(1000/Math.abs((double)(cas1-cas0)));
		g2.drawString(cas, 20, 30);
		g2.drawString(String.valueOf(zoom), 20, 50);
		cas1=System.currentTimeMillis();
	}
	
	private void kresliPanel(Graphics2D g2) {
		
		g2.setFont(podtrzenyFont);
		String trida = muj.getClass().getName();
		trida = trida.replaceFirst("Lode.", "");
		int delka = (int)g2.getFontMetrics().getStringBounds(trida, g2).getWidth();
		int vyska = (int)g2.getFontMetrics().getStringBounds(trida, g2).getHeight();
		
		Rectangle2D r = new Rectangle2D.Double(this.getWidth()/2-this.getWidth()/10, 
				this.getHeight()-8*vyska, 
				this.getWidth()/5, 
				8*vyska);
		
		g2.setColor(barvaHUD);
		g2.fill(r);
		
		g2.setColor(Color.BLACK);
		g2.drawString(trida, (int)(this.getWidth()/2-delka/2), this.getHeight()-6*vyska);
		
		g2.setFont(hudFont);
		g2.setColor(Color.RED);
		trida = muj.getZdravi()+"/"+muj.getMaxZdravi();
		delka = (int)g2.getFontMetrics().getStringBounds(trida, g2).getWidth();
		vyska = (int)g2.getFontMetrics().getStringBounds(trida, g2).getHeight();
		g2.drawString(trida, (int)(this.getWidth()/2-delka/2), this.getHeight()-4*vyska);
		
		g2.setColor(Color.BLUE);
		trida = muj.getStity()+"/"+muj.getMaxStity();
		delka = (int)g2.getFontMetrics().getStringBounds(trida, g2).getWidth();
		vyska = (int)g2.getFontMetrics().getStringBounds(trida, g2).getHeight();
		g2.drawString(trida, (int)(this.getWidth()/2-delka/2), this.getHeight()-3*vyska);

		for (int i=zbrane.length-1; i>=0; i--) {
			g2.setColor(vys.vratZbran(muj.getZbrane()[i]).getColor());
			g2.fillRect((int)(this.getWidth()/2-r.getWidth()/2+i*(r.getWidth()/zbrane.length)), this.getHeight()-2*vyska, 
					(int)((r.getWidth()/zbrane.length)-((r.getWidth()/zbrane.length)*(muj.getPocitadloVystrelu()[i])/zbrane[i])), vyska);
		}
		
		g2.setColor(Color.RED);
		g2.draw(r);
	}

	public void palba(int i, boolean b) {
		palba[i]=b;
	}
	
	public void palbaVse (boolean b) {
		for (int i=0; i<muj.getZbrane().length; i++) {
			palba[i]=b;
		}
	}
	
	public void vpred(boolean k) {
		vpred=k;	
	}
	
	public void vzad(boolean k) {
		vzad=k;
	}
	
	public void fps(boolean k) {
		fps=k;
	}
	
	public void hud(boolean k) {
		hud=k;
	}
	
	public void bar(boolean k) {
		bar=k;
	}
	
	public void vlevo(boolean k) {
		vlevo=k;
	}
	
	public void vpravo(boolean k) {
		vpravo=k;
	}
	
	public void pauza(boolean k) {
		pauza=k;
	}

	public boolean isFps() {
		return fps;
	}

	public boolean isHud() {
		return hud;
	}

	public boolean isBar() {
		return bar;
	}
	
	public boolean isPauza() {
		return pauza;
	}

	public boolean isVypusteno() {
		return vypusteno;
	}

	public void setVypusteno(boolean vypusteno) {
		this.vypusteno = vypusteno;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
}
