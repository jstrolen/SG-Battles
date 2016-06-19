package sg_battles;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class Chooser extends JFrame {
	private static final long serialVersionUID = 1L;
	private JCheckBox fullscreen;
	private JTextField sirka;
	private JTextField vyska;
	
	public Chooser() {
		this.setSize(new Dimension(640,480));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("V�b�r lodi");
		this.add(komp());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	private JPanel komp() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill=GridBagConstraints.BOTH;
		panel.setBackground(Color.WHITE); 

		JButton bt;	
		JPanel tlacitka = new JPanel();
		tlacitka.setBackground(Color.WHITE);
		bt=new JButton("Start");
		
		bt.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Dimension d=null;
				try {
					d = new Dimension(Integer.parseInt(sirka.getText()), Integer.parseInt(vyska.getText()));
					if (d.getWidth()<640) d.setSize(640, d.getHeight());
					if (d.getHeight()<480) d.setSize(d.getWidth(), 480);
				} catch (NumberFormatException e0) {
					d=new Dimension(640, 480);
				}
				new Window(d, fullscreen.isSelected());
				dispose();
				//new sg_battles.Loading(i, d, fullscreen.isSelected()); //TODO
			}
		});
		
		tlacitka.add(bt);
		cs.gridx=0;
		cs.gridy=0;
		panel.add(tlacitka, cs);

		JPanel nastaveni = new JPanel();
		nastaveni.setBackground(Color.WHITE);
		fullscreen = new JCheckBox("Fullscreen", true);
		fullscreen.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (fullscreen.isSelected()) {
					sirka.setEnabled(false);
					vyska.setEnabled(false);
				}
				else {
					sirka.setEnabled(true);
					vyska.setEnabled(true);
				}
			}
		});
		fullscreen.setBackground(Color.WHITE);
		nastaveni.add(fullscreen);
		sirka = new JTextField(new JTextFieldLimit(4), String.valueOf(this.getWidth()), 5);
		sirka.setEnabled(!fullscreen.isSelected());
		nastaveni.add(sirka);
		vyska= new JTextField(new JTextFieldLimit(4), String.valueOf(this.getHeight()), 5);
		vyska.setEnabled(!fullscreen.isSelected());
		nastaveni.add(vyska);

		cs.gridx=0;
		cs.gridy=1;
		panel.add(nastaveni, cs);
		
		return panel;
	}
	
	class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			str = proved(str);
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}

		private String proved(String str) {
			for (int i=str.length()-1; i>=0; i--) {
				if (!Character.isDigit(str.charAt(i))) {
					String a = "";
					if (i>0) a = str.substring(0, i-1);
					String b = "";
					if (i<str.length()-1) b = str.substring(i+1);
					str=a+b;
				}
			}
			return str;
		}
	}

}
