package sg_battles;

import javax.swing.*;
import java.awt.*;


public class Loading extends JFrame {
	private static final long serialVersionUID = 1L;
	private Dimension d;
	private boolean b;
	
	public Loading(Dimension d, boolean b) {
		this.b=b;
		this.d=d;
		this.setSize(150, 50);
		this.setTitle("sg_battles.Loading");
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.add(panel());	
		proved();
		this.setVisible(true);
	}

	private void proved() {
		dispose();
		new Window(d, b);
	}

	private JPanel panel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.BLUE);
		JLabel lab = new JLabel("sg_battles.Loading...");
		lab.setForeground(Color.WHITE);
		lab.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
		panel.add(lab, new GridBagConstraints());
		return panel;
	}

}
