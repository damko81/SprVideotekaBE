package org.presentation.layer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.business.layer.ServiceBusiness;
import org.model.layer.Film;
import org.model.layer.ServiceModel;
import org.persistent.layer.XMLParser;

@SuppressWarnings("serial")
public class ServiceButton extends JPanel {
	
	
	public JButton createXMLButton(final String xmlFile,final Main frameOld){
		   
		   JButton btn=new JButton("Naloži filme");
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Naloži filme iz izbranega diska v XML datoteko.");
		   
	       btn.addActionListener(new ActionListener() {
	    	 
	           public void actionPerformed(ActionEvent e)
	           {
	           	    // Ali hocemo dopolniti xml ? => true Gre za zastarelo zastavico in je vedno true!
	            	boolean merge = true;
	           	    
	             	// Preberemo filme iz podanega imena diska. Vrne disk in ime filma.
	               	HashMap<String,HashMap<String,String>> filmi = new HashMap<String,HashMap<String,String>>();
	        		ServicePresentation sp = new ServicePresentation();
	        		ArrayList<String> diski = sp.getDiscs();
	        		
	        		if(diski == null){
	        			
	        			return;
	        		}
	        		
	        		filmi = ServiceBusiness.listOfFile(diski,filmi,merge,xmlFile);
	        		 
	       		try {
	       			    
	       		        frameOld.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	       		        
	       		        // Pridobijo se podatki o filmih iz IMDBja. Seznam referenc, ker en filem jih ima lahko vec npr. Terminator 1, Terminator 2,...
						HashMap<String,HashMap<String,String>> movieRel = ServiceBusiness.doSearchRequestFromList(filmi);
						
						//Vsaki referenci potrebno dobiti dodatne podatke; opis, leto, ocena...
		        		ArrayList<Film> filmiObject = ServiceBusiness.doRequestDetailFromList(movieRel,filmi);
						
		        		//Sortiranje objektov.
		        		filmiObject = ServiceModel.sort(filmiObject);
		        		
		        		//Vse podatke o filmih(referencah) shraniti v XML.
		        		XMLParser p = new XMLParser();
		        		p.createXML(filmiObject,xmlFile,merge);
		        			
		        		frameOld.setVisible(false);
		        		
		        	    Main frame = new Main("Tvoja videoteka");
		        	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		      		    frame.turnOnWindow();
		      		      		
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: ServiceButton.createXMLButton.");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: ServiceButton.createXMLButton.");
					}
	           }
	       });
	        	   
	       return btn;
	   }
	   
	   public JButton searchMovieXMLButton(final String xmlFile,final Main frameOld,final ArrayList<Film> filmiO){
		     
		   JButton btn=new JButton("Poišèi filem"); 
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Poišèe filem/filme po raznovrstnih kriterijih iz XML datoteke.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	  //Funkcija, ki poišèe filme glede na vnešeni kriterij (genre,rating,...).
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.searchMovieXMLFrame(xmlFile,frameOld,filmiO);
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	   public JButton removeDiscXMLButton(final String xmlFile,final Main frameOld){
		     
		   JButton btn=new JButton("Briši disk");
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   btn.setForeground(Color.RED);
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Zbriše izbrani disk vkljuèno z filmi iz XML datoteke.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	   //Funkcija, ki odstrani disk z vsemi svojimi otroci v XMLju.
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.removeDiscXMLFrame(xmlFile,frameOld);
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	   public JButton updateDiscXMLButton(final String xmlFile,final Main frameOld){
		   
		   JButton btn=new JButton("Preimenuj ime diska");
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Preimenuje ime diska v XML datoteki.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	 //Naredi update na xml datoteki za novo ime diska.
	        	   ServiceFrame sp = new ServiceFrame();
	        	 sp.updateDiscXMLFrame(xmlFile,frameOld);
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	   public JButton removeNameFromDiscXMLButton(final String xmlFile,final Main frameOld){
		   
		   JButton btn=new JButton("Odstrani filem z podfilmi");
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   btn.setForeground(Color.RED);
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Odstrani filem z njegovimi podfilmi iz XML datoteke.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	  //Funkcija, ki odstrani filem z vsemi svojimi otroci iz željenega diska v XMLju.
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.removeNameFromDiscXMLFrame(xmlFile,frameOld); 
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	  public JButton editMovieXMLButton(final String xmlFile,final Main frameOld){
		   
		   JButton btn=new JButton("Popravi filem"); 
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Popravi lastnosti filma v XML datoteki.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	  //Funkcija, ki popravi filem v XMLju.
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.chooseForEditMovieXMLFrame(xmlFile,frameOld); 
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	   public JButton addMovieXMLButton(final String xmlFile,final Main frameOld){
		  
		   JButton btn=new JButton("Dodaj filem");  
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Doda filem z željenimi lastnostmi v XML datoteko.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.addMovieXMLFrame(xmlFile,frameOld);
	           }   
	       }); 
		   
		   return btn;
	   }
	   
	   public JButton reloadDiscXMLButton(final String xmlFile,final Main frameOld){
			  
		   JButton btn=new JButton("Prenaloži disk"); 
		   btn.setFont(new Font("Italic", Font.BOLD, 18));
		   btn.setForeground(Color.RED);
		   Border border = new LineBorder(Color.WHITE, 35);
		   btn.setBorder(border);
		   btn.setToolTipText("Prenaloži vse filme izbranega diska v XML datoteko.");
		   btn.addActionListener(new ActionListener() {
		       	 
	           public void actionPerformed(ActionEvent e)
	           {
	        	  //Funkcija, ki prenaloži obstojeèe filme na izbranem disku v XMLju.
	        	   ServiceFrame sp = new ServiceFrame();
	        	   sp.reloadDiscXMLFrame(xmlFile,frameOld);
	           }   
	       }); 
		  
		   return btn;
	   }
	   
	   public JButton addBlankXMLButton(){
			  
		   JButton btn=new JButton(); 
		   btn.setBackground(Color.WHITE);
		   Border border = new LineBorder(Color.WHITE, 5);
		   btn.setBorder(border);
		  
		   return btn;
	   }
	
}
