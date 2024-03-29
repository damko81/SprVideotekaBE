package org.presentation.layer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.persistent.layer.ServicePersistent;

public class Author{

	private JPanel panel;
	
	public Author(){
		super();
		setupPanel();
	}
	
	private void setupPanel()
	{
		panel = new JPanel(new GridLayout(2,1));
		panel.setLayout(null);
		
		JLabel authorLabel = new JLabel("Avtor:");
		authorLabel.setBounds(10, 50, 70, 25);
		authorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		panel.add(authorLabel);
		
		JLabel authorValueLabel = new JLabel("Damjan Košæak");
		authorValueLabel.setBounds(80, 50, 200, 25);
		authorValueLabel.setFont(new Font("Italic", Font.ITALIC, 20));
		authorValueLabel.setForeground(Color.BLUE);
		panel.add(authorValueLabel);
		
		JLabel versionLabel = new JLabel("Verzija:");
		versionLabel.setBounds(10, 100, 70, 25);
		versionLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		panel.add(versionLabel);
		
		JLabel versionValueLabel = new JLabel("Iskalnik filmov 4.3");
		versionValueLabel.setBounds(80, 100, 200, 25);
		versionValueLabel.setFont(new Font("Serif", Font.BOLD, 20));
		panel.add(versionValueLabel);
		
		JLabel dateLabel = new JLabel("Datum:");
		dateLabel.setBounds(10, 150, 70, 25);
		dateLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		panel.add(dateLabel);
		
		JLabel dateValueLabel = new JLabel("2013");
		dateValueLabel.setBounds(80, 150, 100, 25);
		dateValueLabel.setFont(new Font("Roman", Font.ROMAN_BASELINE, 20));
		dateValueLabel.setForeground(Color.RED);
		panel.add(dateValueLabel);
		
		Pictures pic = new Pictures();
		byte[] im = ServicePersistent.decodeStringToByteArray(pic.getSearchMoviePic());
		   
    	JLabel imageLabel = new JLabel(new ImageIcon(im));
		imageLabel.setBounds(0, 80, 560, 600);
		panel.add(imageLabel);
		
	}
	
	public JPanel getPanel(){
		return this.panel;
	}
	
}
