package org.presentation.layer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.business.layer.ServiceBusiness;
import org.model.layer.Film;
import org.model.layer.ServiceModel;
import org.persistent.layer.ServicePersistent;
import org.persistent.layer.XMLParser;

public class ServiceFrame {
	
	 public void removeNameFromDiscXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Odstrani filem z vsemi svojimi otroci");
			frame.setSize(320, 200);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 100, 25);
			panel.add(discLabel);
			
			final ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(120, 10, 160, 25);
		    
		    String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
		    final JComboBox<Object> nameFromDiscCombo = new JComboBox<Object>(nameFromDiscItems);
		    
		    combo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   nameFromDiscCombo.removeAllItems(); 
		        	   String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
		        	   
		        	   for(int i = 0; i < nameFromDiscItems.length; i++)
		        	   {
		        		   nameFromDiscCombo.addItem(nameFromDiscItems[i]);
		        	   }
		        	   
		           }   
		       }); 
		    panel.add(combo);
			
		    JLabel nameFromDiscLabel = new JLabel("Filmi:");
		    nameFromDiscLabel.setBounds(10, 40, 100, 25);
			panel.add(nameFromDiscLabel);
		    
		   
		    nameFromDiscCombo.setBounds(120, 40, 160, 25);
		    panel.add(nameFromDiscCombo);
		    
			JButton saveButton = new JButton("Briši");
			saveButton.setBounds(180, 100, 100, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frameOld.setVisible(false);

		        	   XMLParser p = new XMLParser();
		        	   p.removeNameFromDiscXML(xmlFile,combo.getSelectedItem().toString(),nameFromDiscCombo.getSelectedItem().toString()); 
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,100, 100, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	 }
	   
	   public void updateDiscXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Preimenovanje imena diska v XMLju");
			frame.setSize(320, 200);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Staro ime diska:");
			discLabel.setBounds(10, 10, 100, 25);
			panel.add(discLabel);
			
			ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(120, 10, 160, 25);
		    panel.add(combo);
		    
		    JLabel newDiscLabel = new JLabel("Novo ime diska:");
		    newDiscLabel.setBounds(10, 40, 100, 25);
			panel.add(newDiscLabel);

			final JTextField newDiscText = new JTextField(20);
			newDiscText.setBounds(120, 40, 160, 25);
			panel.add(newDiscText);
		    
			JButton saveButton = new JButton("Preimenuj");
			saveButton.setBounds(180, 100, 100, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frameOld.setVisible(false);

		        	   XMLParser p = new XMLParser();
		        	   p.updateDiscXML(xmlFile,combo.getSelectedItem().toString(),newDiscText.getText());
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,100, 100, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	 }
	   
	   public void removeDiscXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Brisanje diska v XMLju");
			frame.setSize(300, 150);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 80, 25);
			panel.add(discLabel);
			
			ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(100, 10, 160, 25);
		    panel.add(combo);
			
			JButton saveButton = new JButton("Briši");
			saveButton.setBounds(180, 70, 80, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frameOld.setVisible(false);

		        	   XMLParser p = new XMLParser();
		        	   p.removeDiscXML(xmlFile,combo.getSelectedItem().toString());
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,70, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	  }
	   
	   public void chooseForEditMovieXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Izberi filem");
			frame.setSize(320, 200);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 100, 25);
			panel.add(discLabel);
			
			final ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(120, 10, 160, 25);
		    
		    JLabel nameFromDiscLabel = new JLabel("Filmi:");
		    nameFromDiscLabel.setBounds(10, 40, 100, 25);
			panel.add(nameFromDiscLabel);
		    
		    String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
		    final JComboBox<Object> nameFromDiscCombo = new JComboBox<Object>(nameFromDiscItems);
		    nameFromDiscCombo.setBounds(120, 40, 160, 25);
		    panel.add(nameFromDiscCombo);
		    
		    combo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   nameFromDiscCombo.removeAllItems();
	        	       String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
	        	       for(int i = 0; i < nameFromDiscItems.length; i++)
		        	   {
		        		   nameFromDiscCombo.addItem(nameFromDiscItems[i]);
		        	   }
	 
		           }   
		       }); 
		    panel.add(combo);
		 
			JLabel nameLabel = new JLabel("Podfilem:");
			nameLabel.setBounds(10, 70, 100, 25);
			panel.add(nameLabel);
			
			String[] nameItems = sp.getNamesXML(xmlFile,combo.getSelectedItem().toString(),nameFromDiscCombo.getSelectedItem().toString());
		    final JComboBox<Object> nameCombo = new JComboBox<Object>(nameItems);
		    nameCombo.addItem("");
		    nameCombo.setBounds(120, 70, 160, 25);
		    panel.add(nameCombo);
		
		    nameFromDiscCombo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   nameCombo.removeAllItems();
		        	   String[] namesItems = null;
		        	  
		        	   if(nameFromDiscCombo.getSelectedItem() != null)
		        	   {
		        		   namesItems = sp.getNamesXML(xmlFile,combo.getSelectedItem().toString(),nameFromDiscCombo.getSelectedItem().toString());
		        	       for(int i = 0; i < namesItems.length; i++)
			        	   {
		        	    	   nameCombo.addItem(namesItems[i]);
			        	   }
		        	   }
		       
		           }   
		       }); 
		    
			JButton saveButton = new JButton("Naprej");
			saveButton.setBounds(180, 110, 100, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   ServiceFrame s = new ServiceFrame();
		        	   Film f = sp.getObjectName(xmlFile, nameFromDiscCombo.getSelectedItem().toString(), nameCombo.getSelectedItem().toString(), combo.getSelectedItem().toString());
		        	   
		        	   frameOld.setVisible(false);
		        	   frame.setVisible(false);
		        	   
		        	   s.editMovieXMLFrame(xmlFile,f);
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,110, 100, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	}
	   
	   
	   public void showMovieXMLFrame(final String xmlFile,final Film f){
		   
		    final JFrame frame = new JFrame("Prikaz filma");
			frame.setSize(1200, 950);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 15, 100, 25);
			discLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(discLabel);
			  
		    final JTextField comboText = new JTextField(20);
		    comboText.setText(f.getDisc());
		    comboText.setBounds(100, 10, 1060, 50);
		    comboText.setFont(new Font("Serif", Font.PLAIN, 30));
		    comboText.setFocusable(false);
			panel.add(comboText);
			
			JLabel nameFromDiscLabel = new JLabel("Ime filma na disku:");
			nameFromDiscLabel.setBounds(10, 80, 250, 25);
			nameFromDiscLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(nameFromDiscLabel);
			
			final JTextField nameFromDiscText = new JTextField(20);
			nameFromDiscText.setText(f.getNameFromDisc());
			nameFromDiscText.setBounds(250, 75, 910, 50);
			nameFromDiscText.setFont(new Font("Serif", Font.PLAIN, 30));
			nameFromDiscText.setFocusable(false);
			panel.add(nameFromDiscText);
			
			JLabel nameLabel = new JLabel("Originalni naslov filma:");
			nameLabel.setBounds(10, 145, 300, 25);
			nameLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(nameLabel);
			
			final JTextField nameText = new JTextField(20);
			nameText.setText(f.getName());
			nameText.setBounds(300, 140, 860, 50);
			nameText.setFont(new Font("Serif", Font.PLAIN, 30));
			nameText.setFocusable(false);
			panel.add(nameText);
			
			JLabel genreLabel = new JLabel("Zvrst:");
			genreLabel.setBounds(10, 210, 100, 25);
			genreLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(genreLabel);
			
			final JTextField genreText = new JTextField(20);
			genreText.setText(f.getGenre());
			genreText.setBounds(100, 205, 1060, 50);
			genreText.setFont(new Font("Serif", Font.PLAIN, 30));
			genreText.setFocusable(false);
			panel.add(genreText);
			
			JLabel ratingLabel = new JLabel("Ocena:");
			ratingLabel.setBounds(10, 280, 100, 25);
			ratingLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(ratingLabel);
			
			final JTextField ratingText = new JTextField(20);
			ratingText.setText(f.getRating());
			ratingText.setBounds(100, 270, 1060, 50);
			ratingText.setFont(new Font("Serif", Font.PLAIN, 30));
			ratingText.setFocusable(false);
			panel.add(ratingText);
			
			JLabel starsLabel = new JLabel("Igralci:");
			starsLabel.setBounds(10, 340, 100, 25);
			starsLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(starsLabel);
			
			final JTextField starsText = new JTextField(20);
			starsText.setText(f.getStars());
			starsText.setBounds(100, 330, 1060, 50);
			starsText.setFont(new Font("Serif", Font.PLAIN, 30));
			starsText.setFocusable(false);
			panel.add(starsText);
			
			JLabel directorLabel = new JLabel("Režiser:");
			directorLabel.setBounds(10, 400, 100, 25);
			directorLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(directorLabel);
		
			final JTextField directorText = new JTextField(20);
			directorText.setText(f.getDirector());
			directorText.setBounds(110, 390, 1050, 50);
			directorText.setFont(new Font("Serif", Font.PLAIN, 30));
			directorText.setFocusable(false);
			panel.add(directorText); 
			
		    JLabel durationLabel = new JLabel("Dolžina:");
		    durationLabel.setBounds(10, 460, 100, 25);
		    durationLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(durationLabel);

			final JTextField durationText = new JTextField(20);
			durationText.setText(f.getDuration());
			durationText.setBounds(115, 450, 1045, 50);
			durationText.setFont(new Font("Serif", Font.PLAIN, 30));
			durationText.setFocusable(false);
			panel.add(durationText);	
			
			JLabel releaseDateLabel = new JLabel("Leto izdelave:");
			releaseDateLabel.setBounds(10, 520, 250, 25);
			releaseDateLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(releaseDateLabel);
			
			final JTextField releaseText = new JTextField(20);
			releaseText.setText(f.getReleaseDate());
			releaseText.setBounds(180, 510, 980, 50);
			releaseText.setFont(new Font("Serif", Font.PLAIN, 30));
			releaseText.setFocusable(false);
			panel.add(releaseText);
			
			JLabel descriptionLabel = new JLabel("Opis:");
			descriptionLabel.setBounds(10, 580, 100, 25);
			descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 30));
			panel.add(descriptionLabel);
			
			String s = ServiceBusiness.getSizeableRowString(f.getDescription(),7);
			
			final JTextArea descriptionText = new JTextArea();
			descriptionText.setText(s);
			descriptionText.setBounds(80, 575, 600, 250);
			descriptionText.setFont(new Font("Serif", Font.PLAIN, 25));
			descriptionText.setFocusable(false);
			panel.add(descriptionText);
			 
			String path = f.getImageSrc();
			String http = "HTTP:";
			
			Pictures pic = new Pictures();
			
			 if(path != null && !path.equals("") && path.length() > http.length() && !path.contains(http) && !path.contains(http.toLowerCase())){}
			 else{
				   path = pic.getNotAvailablePic(); 
			 }
			
			byte[] im = ServicePersistent.decodeStringToByteArray(path);
				
	    	JLabel imageLabel = new JLabel(new ImageIcon(im));
			imageLabel.setBounds(700, 575, 460, 300);
			panel.add(imageLabel);
	        
		
			JButton cancelButton = new JButton("Zapri");
			cancelButton.setBounds(10, 850, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		        	   
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	 }
	   
	   public void editMovieXMLFrame(final String xmlFile,final Film f){
		   
		    final JFrame frame = new JFrame("Popravi filem");
			frame.setSize(300, 550);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 80, 25);
			panel.add(discLabel);
			  
		    final JTextField comboText = new JTextField(20);
		    comboText.setText(f.getDisc());
		    comboText.setBounds(100, 10, 160, 25);
		    comboText.setBackground(Color.PINK);
		    comboText.setFocusable(false);
			panel.add(comboText);
			
			JLabel nameFromDiscLabel = new JLabel("Ime filma na disku:");
			nameFromDiscLabel.setBounds(10, 40, 80, 25);
			panel.add(nameFromDiscLabel);

			final JTextField nameFromDiscText = new JTextField(20);
			nameFromDiscText.setText(f.getNameFromDisc());
			nameFromDiscText.setBounds(100, 40, 160, 25);
			panel.add(nameFromDiscText);
			
			JLabel nameLabel = new JLabel("Originalni naslov filma:");
			nameLabel.setBounds(10, 70, 80, 25);
			panel.add(nameLabel);

			final JTextField nameText = new JTextField(20);
			nameText.setText(f.getName());
			nameText.setBounds(100, 70, 160, 25);
			panel.add(nameText);
			
			JLabel genreLabel = new JLabel("Zvrst:");
			genreLabel.setBounds(10, 100, 80, 25);
			panel.add(genreLabel);

			final JTextField genreText = new JTextField(20);
			genreText.setText(f.getGenre());
			genreText.setBounds(100, 100, 160, 25);
			panel.add(genreText);
			
			JLabel ratingLabel = new JLabel("Ocena:");
			ratingLabel.setBounds(10, 130, 80, 25);
			panel.add(ratingLabel);

			final JTextField ratingText = new JTextField(20);
			ratingText.setText(f.getRating());
			ratingText.setBounds(100, 130, 160, 25);
			panel.add(ratingText);
			
			JLabel descriptionLabel = new JLabel("Opis:");
			descriptionLabel.setBounds(10, 160, 80, 25);
			panel.add(descriptionLabel);

			final JTextField descriptionText = new JTextField(20);
			descriptionText.setText(f.getDescription());
			descriptionText.setBounds(100, 160, 160, 25);
			panel.add(descriptionText);
			
			JLabel starsLabel = new JLabel("Igralci:");
			starsLabel.setBounds(10, 190, 80, 25);
			panel.add(starsLabel);

			final JTextField starsText = new JTextField(20);
			starsText.setText(f.getStars());
			starsText.setBounds(100, 190, 160, 25);
			panel.add(starsText);

			JLabel infobarLabel = new JLabel("Info:");
			infobarLabel.setBounds(10, 220, 80, 25);
			panel.add(infobarLabel);

			final JTextField infobarText = new JTextField(20);
			infobarText.setText(f.getInfobar());
			infobarText.setBounds(100, 220, 160, 25);
			panel.add(infobarText);
			
			JLabel directorLabel = new JLabel("Režiser:");
			directorLabel.setBounds(10, 250, 80, 25);
			panel.add(directorLabel);

			final JTextField directorText = new JTextField(20);
			directorText.setText(f.getDirector());
			directorText.setBounds(100, 250, 160, 25);
			panel.add(directorText);
			
			JLabel durationLabel = new JLabel("Dolžina:");
			durationLabel.setBounds(10, 280, 80, 25);
			panel.add(durationLabel);

			final JTextField durationText = new JTextField(20);
			durationText.setText(f.getDuration());
			durationText.setBounds(100, 280, 160, 25);
			panel.add(durationText);
			
			JLabel storylineLabel = new JLabel("Zgodba:");
			storylineLabel.setBounds(10, 310, 80, 25);
			panel.add(storylineLabel);

			final JTextField storylineText = new JTextField(20);
			storylineText.setText(f.getStoryline());
			storylineText.setBounds(100, 310, 160, 25);
			panel.add(storylineText);
			
			JLabel releaseDateLabel = new JLabel("Leto izdelave:");
			releaseDateLabel.setBounds(10, 340, 80, 25);
			panel.add(releaseDateLabel);

			final JTextField releaseText = new JTextField(20);
			releaseText.setText(f.getReleaseDate());
			releaseText.setBounds(100, 340, 160, 25);
			panel.add(releaseText);
			
			JLabel urlLabel = new JLabel("URL:");
			urlLabel.setBounds(10, 370, 80, 25);
			panel.add(urlLabel);

			final JTextField urlText = new JTextField(20);
			urlText.setText(f.getUrl());
			urlText.setBounds(100, 370, 160, 25);
			panel.add(urlText);
			
			JLabel imageSrcLabel = new JLabel("URL slike:");
			imageSrcLabel.setBounds(10, 400, 80, 25);
			panel.add(imageSrcLabel);

			final JTextField imageSrcText = new JTextField(20);
			imageSrcText.setText(f.getImageSrc());
			imageSrcText.setBounds(100, 400, 160, 25);
			panel.add(imageSrcText);
			
			JButton saveButton = new JButton("Shrani");
			saveButton.setBounds(180, 450, 80, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   
		        	   Film filem = new Film();
		        	   filem.setDisc(comboText.getText());
		        	   filem.setNameFromDisc(nameFromDiscText.getText());
		        	   filem.setName(nameText.getText());
		        	   filem.setGenre(genreText.getText());
		        	   filem.setRating(ratingText.getText());
		        	   filem.setDescription(descriptionText.getText());
		        	   filem.setStars(starsText.getText());
		        	   filem.setInfobar(infobarText.getText());
		        	   filem.setDirector(directorText.getText());
		        	   filem.setDuration(durationText.getText());
		        	   filem.setStoryline(storylineText.getText());
		        	   filem.setReleaseDate(releaseText.getText());
		        	   filem.setUrl(urlText.getText());
		        	   
		        	   final String http = "HTTP:";
		        	   final String imdb = "imdb_fb_logo";
		        	   String imageSrc = imageSrcText.getText();
		        	   
		        	   Pictures pic = new Pictures();
		        	   if(imageSrc != null && !imageSrc.equals("") && imageSrc.length() > http.length() && !imageSrc.contains(imdb) && (imageSrc.contains(http) || imageSrc.contains(http.toLowerCase()))){
		        		   imageSrc = ServicePersistent.getEncodedStringByteArray(imageSrc);
						   byte[] im = ServicePersistent.decodeStringToByteArray(imageSrc);
						   BufferedImage bi = pic.resizeImage(380, 330, im);
						   imageSrc = ServicePersistent.getEncodedStringBufferedImage(bi);
		        	   }
		        	   
		        	   filem.setImageSrc(imageSrc);
		        	   
		        	   ArrayList<Film> filemAL = new ArrayList<Film>();
		        	   filemAL.add(f);
		        	   filemAL.add(filem);
		        	   
		        	   ServicePersistent sp = new ServicePersistent();
		        	   sp.updateMovieToXML(xmlFile,filemAL);
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10, 450, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	  }
	   
	   public void addMovieXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Vnos novega filma");
			frame.setSize(300, 500);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 80, 25);
			panel.add(discLabel);
			
			ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(100, 10, 160, 25);
		    panel.add(combo);
			
			JLabel nameLabel = new JLabel("Ime filma:");
			nameLabel.setBounds(10, 40, 80, 25);
			panel.add(nameLabel);

			final JTextField nameText = new JTextField(20);
			nameText.setBounds(100, 40, 160, 25);
			panel.add(nameText);
			
			JLabel genreLabel = new JLabel("Zvrst:");
			genreLabel.setBounds(10, 70, 80, 25);
			panel.add(genreLabel);

			final JTextField genreText = new JTextField(20);
			genreText.setBounds(100, 70, 160, 25);
			panel.add(genreText);
			
			JLabel ratingLabel = new JLabel("Ocena:");
			ratingLabel.setBounds(10, 100, 80, 25);
			panel.add(ratingLabel);

			final JTextField ratingText = new JTextField(20);
			ratingText.setBounds(100, 100, 160, 25);
			panel.add(ratingText);
			
			JLabel descriptionLabel = new JLabel("Opis:");
			descriptionLabel.setBounds(10, 130, 80, 25);
			panel.add(descriptionLabel);

			final JTextField descriptionText = new JTextField(20);
			descriptionText.setBounds(100, 130, 160, 25);
			panel.add(descriptionText);
			
			JLabel starsLabel = new JLabel("Igralci:");
			starsLabel.setBounds(10, 160, 80, 25);
			panel.add(starsLabel);

			final JTextField starsText = new JTextField(20);
			starsText.setBounds(100, 160, 160, 25);
			panel.add(starsText);

			JLabel infobarLabel = new JLabel("Info:");
			infobarLabel.setBounds(10, 190, 80, 25);
			panel.add(infobarLabel);

			final JTextField infobarText = new JTextField(20);
			infobarText.setBounds(100, 190, 160, 25);
			panel.add(infobarText);
			
			JLabel directorLabel = new JLabel("Režiser:");
			directorLabel.setBounds(10, 220, 80, 25);
			panel.add(directorLabel);

			final JTextField directorText = new JTextField(20);
			directorText.setBounds(100, 220, 160, 25);
			panel.add(directorText);
			
			JLabel durationLabel = new JLabel("Dolžina:");
			durationLabel.setBounds(10, 250, 80, 25);
			panel.add(durationLabel);

			final JTextField durationText = new JTextField(20);
			durationText.setBounds(100, 250, 160, 25);
			panel.add(durationText);
			
			JLabel storylineLabel = new JLabel("Zgodba:");
			storylineLabel.setBounds(10, 280, 80, 25);
			panel.add(storylineLabel);

			final JTextField storylineText = new JTextField(20);
			storylineText.setBounds(100, 280, 160, 25);
			panel.add(storylineText);
			
			JLabel releaseDateLabel = new JLabel("Leto izdelave:");
			releaseDateLabel.setBounds(10, 310, 80, 25);
			panel.add(releaseDateLabel);

			final JTextField releaseText = new JTextField(20);
			releaseText.setBounds(100, 310, 160, 25);
			panel.add(releaseText);
			
			JLabel urlLabel = new JLabel("URL:");
			urlLabel.setBounds(10, 340, 80, 25);
			panel.add(urlLabel);

			final JTextField urlText = new JTextField(20);
			urlText.setBounds(100, 340, 160, 25);
			panel.add(urlText);
			
			JLabel imageSrcLabel = new JLabel("URL slike:");
			imageSrcLabel.setBounds(10, 370, 80, 25);
			panel.add(imageSrcLabel);

			final JTextField imageSrcText = new JTextField(20);
			imageSrcText.setBounds(100, 370, 160, 25);
			panel.add(imageSrcText);
			
			JButton saveButton = new JButton("Shrani");
			saveButton.setBounds(180, 420, 80, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frameOld.setVisible(false);
		        	   
		        	   Film filem = new Film();
		        	   filem.setDisc(combo.getSelectedItem().toString());
		        	   filem.setName(nameText.getText());
		        	   filem.setGenre(genreText.getText());
		        	   filem.setRating(ratingText.getText());
		        	   filem.setDescription(descriptionText.getText());
		        	   filem.setStars(starsText.getText());
		        	   filem.setInfobar(infobarText.getText());
		        	   filem.setDirector(directorText.getText());
		        	   filem.setDuration(durationText.getText());
		        	   filem.setStoryline(storylineText.getText());
		        	   filem.setReleaseDate(releaseText.getText());
		        	   filem.setNameFromDisc(nameText.getText());
		        	   filem.setUrl(urlText.getText());
		        	   filem.setImageSrc(imageSrcText.getText());
		        
		        	   ArrayList<Film> filmObject = new ArrayList<Film>();
		        	   filmObject.add(filem);
		        	 
		        	   final XMLParser p = new XMLParser();
		        	   p.createXML(filmObject,xmlFile,true);
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,420, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	   }
	   
	   public void reloadDiscXMLFrame(final String xmlFile,final Main frameOld){
		   
		    final JFrame frame = new JFrame("Prenaloži disk v XMLju");
			frame.setSize(300, 150);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 80, 25);
			panel.add(discLabel);
			
			ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.setBounds(100, 10, 160, 25);
		    panel.add(combo);
			
			JButton saveButton = new JButton("Reload");
			saveButton.setBounds(180, 70, 80, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   
		        	   boolean merge = true;
		        	   XMLParser p = new XMLParser();
		        	   ServicePersistent sp = new ServicePersistent();
		        	   
		        	   String disc = combo.getSelectedItem().toString();
		        	   String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,disc);
		        	   
		        	   if(disc != null && !disc.equals(""))
		        	   {
		        		   HashMap<String,HashMap<String,String>> filmi = new HashMap<String,HashMap<String,String>>();
		        		   HashMap<String,String> name = new HashMap<String,String>();
		        		   for(String nameFromDisc : nameFromDiscItems){
		        			   name.put(nameFromDisc, nameFromDisc);  
		        		   }
		        		   try {
			       			    
		        			    filmi.put(disc,name);
		        			   
		        			    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		        			    
			       		        // Pridobijo se podatki o filmih iz IMDBja. Seznam referenc, ker en filem jih ima lahko vec npr. Terminator 1, Terminator 2,...
								HashMap<String,HashMap<String,String>> movieRel = ServiceBusiness.doSearchRequestFromList(filmi);
								
								//Vsaki referenci potrebno dobiti dodatne podatke; opis, leto, ocena...
				        		ArrayList<Film> filmiObject = ServiceBusiness.doRequestDetailFromList(movieRel,filmi);
								
				        		//Sortiranje objektov.
				        		filmiObject = ServiceModel.sort(filmiObject);
				        		
				        		p.removeDiscXML(xmlFile,disc);
				        		
				        		//Vse podatke o filmih(referencah) shraniti v XML.
				        		p.createXML(filmiObject,xmlFile,merge);
				      		    
				        		frameOld.setVisible(false);
				        		
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Error: ServiceFrame.reloadDiscXMLFrame.");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Error: ServiceFrame.reloadDiscXMLFrame.");
							}
		        	   }
 
		        	   frame.setVisible(false);
		        	   
		        	   Main frameNew = new Main("Videoteka");
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,70, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	  }
	   
	   public void searchMovieXMLFrame(final String xmlFile,final Main frameOld,final ArrayList<Film> filmiO){
		   
		    final JFrame frame = new JFrame("Iskalnik filma");
			frame.setSize(300, 280);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);
			
			panel.setLayout(null);

			JLabel discLabel = new JLabel("Disk:");
			discLabel.setBounds(10, 10, 100, 25);
			panel.add(discLabel);
			
			final ServicePersistent sp = new ServicePersistent();
			
		    String[] items = sp.getDiscsXML(xmlFile);
		    final JComboBox<Object> combo = new JComboBox<Object>(items);
		    combo.addItem("");
		    combo.setBounds(100, 10, 160, 25);
		    
		    String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
		    final JComboBox<Object> nameFromDiscCombo = new JComboBox<Object>(nameFromDiscItems);
		    nameFromDiscCombo.setBounds(100, 40, 160, 25);
		    nameFromDiscCombo.addItem("");
		    panel.add(nameFromDiscCombo);
		    
		    combo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   nameFromDiscCombo.removeAllItems();
		        	   nameFromDiscCombo.addItem("");
		        	   if (!combo.getSelectedItem().toString().equals(""))
		        	   {	   
		        	       String[] nameFromDiscItems = sp.getNamesFromDiscXML(xmlFile,combo.getSelectedItem().toString());
		        	       for(int i = 0; i < nameFromDiscItems.length; i++)
			        	   {
			        		   nameFromDiscCombo.addItem(nameFromDiscItems[i]);
			        	   }
		        	   }
		        	   else
		        	   {
		        		   String[] nameFromDiscItems = sp.getNamesFromAllDiscXML(xmlFile);
		        		   for(int j = 0; j < nameFromDiscItems.length; ++j){nameFromDiscCombo.addItem(nameFromDiscItems[j]);}
		        	   }
		        	     
		           }   
		       }); 
		    panel.add(combo);
			
		    JLabel nameFromDiscLabel = new JLabel("Filmi:");
		    nameFromDiscLabel.setBounds(10, 40, 100, 25);
			panel.add(nameFromDiscLabel);
		     
			JLabel genreLabel = new JLabel("Zvrst:");
			genreLabel.setBounds(10, 70, 80, 25);
			panel.add(genreLabel);

			String[] genreItems = sp.getGenresXML(xmlFile);
		    final JComboBox<Object> genreCombo = new JComboBox<Object>(genreItems);
		    genreCombo.setBounds(100, 70, 160, 25);
		    genreCombo.addItem("");
		    panel.add(genreCombo);
		    
		    nameFromDiscCombo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   genreCombo.removeAllItems();
		        	   genreCombo.addItem("");
		        	  	   
	        	       String[] genresItems = sp.getGenresXML(xmlFile);
	        	       for(int i = 0; i < genresItems.length; i++)
		        	   {
	        	    	   genreCombo.addItem(genresItems[i]);
		        	   }
		        	       
		           }   
		       }); 
		    
			JLabel starsLabel = new JLabel("Igralci:");
			starsLabel.setBounds(10, 100, 80, 25);
			panel.add(starsLabel);
	        
			String[] starItems = sp.getStarsXML(xmlFile);
			final JComboBox<Object> starCombo = new JComboBox<Object>(starItems);
			starCombo.setBounds(100, 100, 160, 25);
			starCombo.addItem("");
			panel.add(starCombo);
			
			genreCombo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   starCombo.removeAllItems();
		        	   starCombo.addItem("");
		        	  	   
		      	       String[] starItems = sp.getStarsXML(xmlFile);
		      	       for(int i = 0; i < starItems.length; i++)
		        	   {
	      	    	       starCombo.addItem(starItems[i]);
		        	   }
		        	       
		           }   
		       }); 
			
			JLabel directorLabel = new JLabel("Režiser:");
			directorLabel.setBounds(10, 130, 80, 25);
			panel.add(directorLabel);

			String[] directorItems = sp.getDirectorsXML(xmlFile);
			final JComboBox<Object> directorCombo = new JComboBox<Object>(directorItems);
			directorCombo.setBounds(100, 130, 160, 25);
			directorCombo.addItem("");
			panel.add(directorCombo);
			
			starCombo.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   directorCombo.removeAllItems();
		        	   directorCombo.addItem("");
		        	  	   
		      	       String[] directorItems = sp.getDirectorsXML(xmlFile);
		      	       for(int i = 0; i < directorItems.length; i++)
		        	   {
		      	    	 directorCombo.addItem(directorItems[i]);
		        	   }
		        	       
		           }   
		       }); 
			
			JButton saveButton = new JButton("Poišèi");
			saveButton.setBounds(180, 180, 80, 25);
			saveButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   ArrayList<Film> filmi = filmiO;
		        	   
		        	   if(!combo.getSelectedItem().toString().equals(""))  {filmi = ServiceModel.searchMovieByDisc(filmi,combo.getSelectedItem().toString());}
		        	   if(!nameFromDiscCombo.getSelectedItem().toString().equals("")) {filmi = ServiceModel.searchMovieByNameFromDisc(filmi,nameFromDiscCombo.getSelectedItem().toString());}
		        	   if(!genreCombo.getSelectedItem().toString().equals("")) { filmi = ServiceModel.searchMovieByGenreReduced(filmi,genreCombo.getSelectedItem().toString());}
		        	   if(!starCombo.getSelectedItem().toString().equals("")) {filmi = ServiceModel.searchMovieByStarsReduced(filmi,starCombo.getSelectedItem().toString());}
		        	   if(!directorCombo.getSelectedItem().toString().equals("")) {filmi = ServiceModel.searchMovieByDirectorReduced(filmi,directorCombo.getSelectedItem().toString());}
		        	   
		        	   frame.setVisible(false);
		        	   
		        	   //Novi zaèasen GUI.
		        	   SearchMovieGUI frameNew = new SearchMovieGUI("Videoteka",filmi,xmlFile);
		        	   frameNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	   frameNew.turnOnWindow();  
		           }   
		       }); 
			panel.add(saveButton);
			JButton cancelButton = new JButton("Preklièi");
			cancelButton.setBounds(10,180, 80, 25);
			cancelButton.addActionListener(new ActionListener() {
		       	 
		           public void actionPerformed(ActionEvent e)
		           {
		        	   frame.setVisible(false);
		           }   
		       }); 
			panel.add(cancelButton);
			
			frame.setVisible(true);		
	  }
	   
}
