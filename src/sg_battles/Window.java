package sg_battles;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sg_battles.levels.Level_1;


public class Window extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private Panel pan;
	
	public Window(Dimension velikost, boolean fullscreen) {
		addKeyListener(this);
		this.setFocusable(true);
		this.setSize(velikost);
		this.setTitle("SG - Alpha");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(komp());
		this.addMouseWheelListener(new MouseWheelListener() {		
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				pan.setZoom(Math.min(Math.max(pan.getZoom()-e.getWheelRotation()*0.05,0.4),5));
			}
		});
		if (fullscreen) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setUndecorated(true);
		}
		this.setVisible(true);
	}

	private Component komp() {
		pan = new Panel(new Dimension(this.getWidth(), this.getHeight()), new Level_1(), this);
		JPanel panel = pan;
		return panel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar()=='w' || e.getKeyChar()=='W' /*|| e.getKeyCode()==KeyEvent.VK_UP*/) pan.vpred(false);
		if (e.getKeyChar()=='s' || e.getKeyChar()=='S' /*|| e.getKeyCode()==KeyEvent.VK_DOWN*/) pan.vzad(false);
		if (e.getKeyChar()=='a' || e.getKeyChar()=='A' /*|| e.getKeyCode()==KeyEvent.VK_LEFT*/) pan.vlevo(false);
		if (e.getKeyChar()=='d' || e.getKeyChar()=='D' /*|| e.getKeyCode()==KeyEvent.VK_RIGHT*/) pan.vpravo(false);
		if (e.getKeyChar()=='f' || e.getKeyChar()=='F') pan.palba(0,false);
		if (e.getKeyChar()=='q' || e.getKeyChar()=='Q') pan.palba(1,false);
		if (e.getKeyChar()=='e' || e.getKeyChar()=='E') pan.palba(2,false);
		if (e.getKeyChar()==' ') pan.palbaVse(false);
		if (e.getKeyChar()=='v' || e.getKeyChar()=='V') pan.hud(!pan.isHud());
		if (e.getKeyChar()=='b' || e.getKeyChar()=='B') pan.fps(!pan.isFps());
		if (e.getKeyChar()=='c' || e.getKeyChar()=='C') pan.bar(!pan.isBar());
		if (e.getKeyChar()=='p' || e.getKeyChar()=='P') pan.pauza(!pan.isPauza());
		if (e.getKeyChar()=='r' || e.getKeyChar()=='R') pan.setVypusteno(!pan.isVypusteno());
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar()=='w' || e.getKeyChar()=='W' /*|| e.getKeyCode()==KeyEvent.VK_UP*/) pan.vpred(true);
		if (e.getKeyChar()=='s' || e.getKeyChar()=='S' /*|| e.getKeyCode()==KeyEvent.VK_DOWN*/) pan.vzad(true);
		if (e.getKeyChar()=='a' || e.getKeyChar()=='A' /*|| e.getKeyCode()==KeyEvent.VK_LEFT*/) pan.vlevo(true);
		if (e.getKeyChar()=='d' || e.getKeyChar()=='D' /*|| e.getKeyCode()==KeyEvent.VK_RIGHT*/) pan.vpravo(true);
		if (e.getKeyChar()=='f' || e.getKeyChar()=='F') pan.palba(0,true);
		if (e.getKeyChar()=='q' || e.getKeyChar()=='Q') pan.palba(1,true);
		if (e.getKeyChar()=='e' || e.getKeyChar()=='E') pan.palba(2,true);
		if (e.getKeyChar()==' ') pan.palbaVse(true);
	}
}
