package org.persistent.layer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.model.layer.Film;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServicePersistent {

	public String[] getDiscsXML(String xmlFile){
		 
		String[] discs = null;
		ArrayList<String> discsAL = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	discsAL.add(listOfDiscs.item(i).getAttributes().getNamedItem("disc").getNodeValue());
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDiscsXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDiscsXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDiscsXML.");
		}
		 
		 Collections.sort(discsAL);
		 discs = new String[discsAL.size()];
		 for(int i=0; i < discs.length; ++i){discs[i] = discsAL.get(i);}
		 
		 return discs;
	 }
	
	public String cleanString(String s){
		
		String niz = s;
		
		if(niz.startsWith(" ")) {niz = niz.replaceFirst(" ", "");}
		if(niz.length() > 0 && niz.substring(niz.length() - 1).equals(" ")) {niz = niz.substring(0, niz.length()-1);}
		
		if(niz.startsWith(" ") || (niz.length() > 0 && niz.substring(niz.length() - 1).equals(" ")))
		{
			niz = cleanString(niz);
			return niz;
		}
		
		return niz;
	}
	
	public void updateMovieToXML(String xmlFile, ArrayList<Film> filemAL){
		
		XMLParser p = new XMLParser();
		
		Film oldMov = filemAL.get(0);
		Film newMov = filemAL.get(1);
		
		String oldDisc = this.cleanString(oldMov.getDisc());
		String newDisc = this.cleanString(newMov.getDisc());
		String oldNameFromDisc = this.cleanString(oldMov.getNameFromDisc());
		String newNameFromDisc = this.cleanString(newMov.getNameFromDisc());	
		String oldName = this.cleanString(oldMov.getName());
		String newName = this.cleanString(newMov.getName());
		String oldGenre = this.cleanString(oldMov.getGenre());
		String newGenre = this.cleanString(newMov.getGenre());
		String oldRating = this.cleanString(oldMov.getRating());
		String newRating = this.cleanString(newMov.getRating());
		String oldDescription = this.cleanString(oldMov.getDescription());
		String newDescription = this.cleanString(newMov.getDescription());
		String oldStars = this.cleanString(oldMov.getStars());
		String newStars = this.cleanString(newMov.getStars());
		String oldInfobar = this.cleanString(oldMov.getInfobar());
		String newInfobar = this.cleanString(newMov.getInfobar());
		String oldDirector = this.cleanString(oldMov.getDirector());
		String newDirector = this.cleanString(newMov.getDirector());
		String oldDuration = this.cleanString(oldMov.getDuration());
		String newDuration = this.cleanString(newMov.getDuration());
		String oldStoryline = this.cleanString(oldMov.getStoryline());
		String newStoryline = this.cleanString(newMov.getStoryline());
		String oldReleaseDate = this.cleanString(oldMov.getReleaseDate());
		String newReleaseDate = this.cleanString(newMov.getReleaseDate());
		String oldUrl = this.cleanString(oldMov.getUrl());
		String newUrl = this.cleanString(newMov.getUrl());
		String oldImageSrc = this.cleanString(oldMov.getImageSrc());
		String newImageSrc = this.cleanString(newMov.getImageSrc());
		
		
		if(!oldDisc.equals(newDisc)){
			//TODO: Trenutno ta spremeba je onemogoèena.
		}
		if(!oldNameFromDisc.equals(newNameFromDisc)){
			//Popravi nameFromDisc v XML.
			p.updateNameFromDiscXML(xmlFile,newDisc,oldNameFromDisc,newNameFromDisc);
		}
		if(!oldName.equals(newName)){
			//TODO: Potrebno popraviti name v XML.
			p.updateNameXML(xmlFile,newDisc,newNameFromDisc,oldName,newName);
		}
		if(!oldGenre.equals(newGenre)){
			
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"genre",newGenre);
		}
		if(!oldRating.equals(newRating)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"rating",newRating);
		}
		if(!oldDescription.equals(newDescription)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"description",newDescription);
		}
		if(!oldStars.equals(newStars)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"stars",newStars);
		}
		if(!oldInfobar.equals(newInfobar)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"infobar",newInfobar);
		}
		if(!oldDirector.equals(newDirector)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"director",newDirector);
		}
		if(!oldDuration.equals(newDuration)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"duration",newDuration);
		}
		if(!oldStoryline.equals(newStoryline)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"storyline",newStoryline);
		}
		if(!oldReleaseDate.equals(newReleaseDate)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"storyline",newStoryline);
		}
		if(!oldUrl.equals(newUrl)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"url",newUrl);
		}
		if(!oldImageSrc.equals(newImageSrc)){
			p.updateNameChildAttribXML(xmlFile,newDisc,newNameFromDisc,newName,"imageSrc",newImageSrc);
		}

	}
	
	public String[] getNamesXML(String xmlFile,String disc,String nameFromDisc){
		 
		String[] names = null;
		ArrayList<String> namesAL = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	Node discNode = listOfDiscs.item(i);
		    	
		    	if(discNode.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase())){
		    	   
		    		NodeList nameFromDiscNL = discNode.getChildNodes();
		    		
		    		int j = 0;
			    	while(j < nameFromDiscNL.getLength()){
			    		
			    		Node nameFromDiscNode = nameFromDiscNL.item(j);
			    		
			    		if(nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase().equals(nameFromDisc.toLowerCase())){
			    			
			    			NodeList nameNL = nameFromDiscNode.getChildNodes();
			    			
			    			int k = 0;
			    			while(k < nameNL.getLength()){
	
			    				namesAL.add(nameNL.item(k).getAttributes().getNamedItem("name").getNodeValue());
			    				
			    				++k;
			    			}
			    		}	
			    		++j;
			    	}
		    	
		    	}
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesXML.");
		}
		 
		 Collections.sort(namesAL);
		 names = new String[namesAL.size()];
		 for(int i=0; i < names.length; ++i){names[i] = namesAL.get(i);}
		
		 return names;
	 }
	
	public Film getObjectName(String xmlFile,String nameFromDisc,String nameMov,String disc){
		 
		 Film f = null;
			
		 try
		 {
		    
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		    	
		    	Node discNode = listOfDiscs.item(i);
		    	
		    	if(discNode.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase())){
		    		
		    		NodeList nameFromDiscNL = discNode.getChildNodes();
		    		
			    	int j = 0;
			    	while(j < nameFromDiscNL.getLength()){
			    		
			    		Node nameFromDiscNode = nameFromDiscNL.item(j);
			    		
			    		if(nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().equals(nameFromDisc)) {
			    		    
				    		NodeList nameNL = nameFromDiscNode.getChildNodes();
				    		
				    		int z = 0;
				    		while(z < nameNL.getLength()){
				    			
				    			Node nameNode = nameNL.item(z);
				    			
				    			if(nameNode.getAttributes().getNamedItem("name").getNodeValue().equals(nameMov)) {
				    			
					    			NodeList nameChildNL = nameNode.getChildNodes();
					    			
					    			String name = nameNode.getAttributes().getNamedItem("name").getNodeValue();
					    			String genre = "";
					    			String rating = "";
					    			String description = "";
					    			String stars = "";
					    			String director = "";
					    			String duration = "";
					    			String releaseDate = "";
					    			String url = "";
					    			String imageSrc = "";
					    			
					    			int u = 0;
					    			while(u < nameChildNL.getLength())
					    			{
					    				Node nameChild = nameChildNL.item(u);
					    				String childAttrib = nameChild.getNodeName();
					    				
					    				if(childAttrib.equals("genre")){
					    					genre = nameChild.getAttributes().getNamedItem("genre").getNodeValue();
					    				}
					    				if(childAttrib.equals("rating")){
					    					rating = nameChild.getAttributes().getNamedItem("rating").getNodeValue();
					    				}
					    				if(childAttrib.equals("description")){
					    					description = nameChild.getAttributes().getNamedItem("description").getNodeValue();
					    				}
					    				if(childAttrib.equals("stars")){
					    					stars = nameChild.getAttributes().getNamedItem("stars").getNodeValue();
					    				}
					    				if(childAttrib.equals("director")){
					    					director = nameChild.getAttributes().getNamedItem("director").getNodeValue();
					    				}
					    				if(childAttrib.equals("duration")){
					    					duration = nameChild.getAttributes().getNamedItem("duration").getNodeValue();
					    				}
					    				if(childAttrib.equals("releaseDate")){
					    					releaseDate = nameChild.getAttributes().getNamedItem("releaseDate").getNodeValue();
					    				}
					    				if(childAttrib.equals("url")){
					    					url = nameChild.getAttributes().getNamedItem("url").getNodeValue();
					    				}
					    				if(childAttrib.equals("imageSrc")){
					    					imageSrc = nameChild.getAttributes().getNamedItem("imageSrc").getNodeValue();
					    				}
					    				
					    				++u;
					    		   }
				    			
				    			  f = new Film(disc,name,genre,rating,description,stars,"",director,duration,"",releaseDate,nameFromDisc,url,imageSrc);
				    			} 
				    			
				    			++z;
				    		 }

			    	     }		
			    		
			    	   ++j;
			    	}
		    		
		    	}
		    	
		    	++i;
		    }
		    
		 }catch(ParserConfigurationException pce){
			 pce.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getObjectName.");
	     } catch (SAXException e) {
			// TODO Auto-generated catch block
		     e.printStackTrace();
		     JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getObjectName.");
	     } catch (IOException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getObjectName.");
	     }  
				
		 return f;
	 }
	
	public String[] getNamesFromDiscXML(String xmlFile,String disc){
		 
		String[] namesFromDisc = null;
		ArrayList<String> namesFromDiscAL = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	Node discNode = listOfDiscs.item(i);
		    	
		    	if(discNode.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase())){
		    	   
		    		NodeList nameFromDiscNL = discNode.getChildNodes();
		    		
		    		int j = 0;
			    	while(j < nameFromDiscNL.getLength()){

			    		namesFromDiscAL.add(nameFromDiscNL.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue());	
			    		++j;
			    	}
		    	
		    	}
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromDiscXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromDiscXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromDiscXML.");
		}
		 
		 Collections.sort(namesFromDiscAL);
		 namesFromDisc = new String[namesFromDiscAL.size()];
		 for(int i=0; i < namesFromDisc.length; ++i){namesFromDisc[i] = namesFromDiscAL.get(i);}
		
		 return namesFromDisc;
	 }
	
	public String[] getNamesFromAllDiscXML(String xmlFile){
		 
		String[] namesFromDisc = null;
		ArrayList<String> namesFromDiscAL = new ArrayList<String>();
		
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	    Node discNode = listOfDiscs.item(i);
		    		NodeList nameFromDiscNL = discNode.getChildNodes();
		    		
		    		int j = 0;
			    	while(j < nameFromDiscNL.getLength()){

			    		if(!namesFromDiscAL.contains(nameFromDiscNL.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue())){
			    			namesFromDiscAL.add(nameFromDiscNL.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue());
			    		}
			    		++j;
			    	}

		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromAllDiscXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromAllDiscXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getNamesFromAllDiscXML.");
		}
		 
		 Collections.sort(namesFromDiscAL);
		 namesFromDisc = new String[namesFromDiscAL.size()];
		 
		 for(int i=0; i < namesFromDisc.length; ++i){namesFromDisc[i] = namesFromDiscAL.get(i);}
		 
		 return namesFromDisc;
	 }
	
	public String[] getStarsXML(String xmlFile){
		 
		 ArrayList<String> starsList = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	Node discNode = listOfDiscs.item(i);
	    		NodeList nameFromDiscNL = discNode.getChildNodes();
	    		
	    		int j = 0;
		    	while(j < nameFromDiscNL.getLength()){
 
		    		Node nameFromDiscNode = nameFromDiscNL.item(j);
		    		NodeList nameNL = nameFromDiscNode.getChildNodes();
		    		
		    		int k = 0;
		    		while(k < nameNL.getLength()){
		    			
		    			Node nameNode = nameNL.item(k);
		    			NodeList nameChildNL = nameNode.getChildNodes();
		    			
		    			int l = 0;
		    			while(l < nameChildNL.getLength())
		    			{
		    				Node nameChild = nameChildNL.item(l);
		    				if(nameChild.getNodeName().equals("stars")){
		    					
		    					String[] stars = nameChild.getAttributes().getNamedItem("stars").getNodeValue().split(","); 
		    					
		    					for(int m = 0; m < stars.length; ++m)
		    					{
		    						String star = stars[m];
		    						
		    						if(star.startsWith(" ")) {star = star.replaceFirst(" ", "");}
		    						if(star.length() > 0 && star.substring(star.length() - 1).equals(" ")) {star = star.substring(0, star.length()-1);}
		    						if(!starsList.contains(star) && !star.equals("")){ starsList.add(star);}
		
		    					}
		    						
		    				}
		    				
		    				++l;
		    			}
		    				
		    			++k;
		    		}
		    	   
		    		++j;
		    	}
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getStarsXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getStarsXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getStarsXML.");
		}
		
		 Collections.sort(starsList);
		 String[] stars = new String[starsList.size()];
		 for(int i=0; i < stars.length; ++i) stars[i] = starsList.get(i);
		 
		 return stars;
	 }
	
	public String[] getDirectorsXML(String xmlFile){
		 
		 ArrayList<String> directorList = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	Node discNode = listOfDiscs.item(i);
	    		NodeList nameFromDiscNL = discNode.getChildNodes();
	    		
	    		int j = 0;
		    	while(j < nameFromDiscNL.getLength()){

		    		Node nameFromDiscNode = nameFromDiscNL.item(j);
		    		NodeList nameNL = nameFromDiscNode.getChildNodes();
		    		
		    		int k = 0;
		    		while(k < nameNL.getLength()){
		    			
		    			Node nameNode = nameNL.item(k);
		    			NodeList nameChildNL = nameNode.getChildNodes();
		    			
		    			int l = 0;
		    			while(l < nameChildNL.getLength())
		    			{
		    				Node nameChild = nameChildNL.item(l);
		    				if(nameChild.getNodeName().equals("director")){
		    					
		    					String[] directors = nameChild.getAttributes().getNamedItem("director").getNodeValue().split(","); 
		    					
		    					for(int m = 0; m < directors.length; ++m)
		    					{
		    						String director = directors[m];
		    						
		    						if(director.startsWith(" ")) {director = director.replaceFirst(" ", "");}
		    						if(director.length() > 0 && director.substring(director.length() - 1).equals(" ")) {director = director.substring(0, director.length()-1);}
		    						if(!directorList.contains(director) && !director.equals("")){ directorList.add(director);}
		
		    					}
		    						
		    				}
		    				
		    				++l;
		    			}
		    				
		    			++k;
		    		}
		    	   
		    		++j;
		    	}
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDirectorsXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDirectorsXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getDirectorsXML.");
		}
		
		 Collections.sort(directorList);
		 String[] directors = new String[directorList.size()];
		 for(int i=0; i < directors.length; ++i) directors[i] = directorList.get(i);
		 
		 return directors;
	 }
	
	public static String encodeToStringByteArray(byte[] imageBytes){

		return Base64.getEncoder().encodeToString(imageBytes);
	}
	
	public static byte[] decodeStringToByteArray(String imageBytesString){

		return Base64.getDecoder().decode(imageBytesString);
	}
	
	public static String getEncodedStringByteArray(String imageSrc){
		
		String stringByteArray = null;
		
		BufferedImage image = null;
		URL url = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
		try {
				url = new URL(imageSrc);
				image = ImageIO.read(url);
				ImageIO.write(image, "jpg", baos);
				baos.flush();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getEncodedStringByteArray.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getEncodedStringByteArray.");
		}

		 byte[] imageBytes = baos.toByteArray();
		 
		 stringByteArray = ServicePersistent.encodeToStringByteArray(imageBytes);
		
		 return stringByteArray;
	}
	
	public static String getEncodedStringBufferedImage(BufferedImage imageSrc){
		
		BufferedImage image = imageSrc;
		String stringByteArray = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
		try {
				ImageIO.write(image, "jpg", baos);
				baos.flush();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getEncodedStringBufferedImage.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getEncodedStringBufferedImage.");
		}

		 byte[] imageBytes = baos.toByteArray();
		 
		 stringByteArray = ServicePersistent.encodeToStringByteArray(imageBytes);
		
		 return stringByteArray;
	}
	
	public String[] getGenresXML(String xmlFile){
		 
		 ArrayList<String> genresList = new ArrayList<String>();
		 try
		 {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(xmlFile);
		    document.getDocumentElement().normalize ();
		    
		    NodeList listOfDiscs = document.getElementsByTagName("disc");
		    
		    int i = 0;
		    while( i < listOfDiscs.getLength()){
		 
		    	Node discNode = listOfDiscs.item(i);
	    		NodeList nameFromDiscNL = discNode.getChildNodes();
	    		
	    		int j = 0;
		    	while(j < nameFromDiscNL.getLength()){
  
		    		Node nameFromDiscNode = nameFromDiscNL.item(j);
		    		NodeList nameNL = nameFromDiscNode.getChildNodes();
		    		
		    		int k = 0;
		    		while(k < nameNL.getLength()){
		    			
		    			Node nameNode = nameNL.item(k);
		    			NodeList nameChildNL = nameNode.getChildNodes();
		    			
		    			int l = 0;
		    			while(l < nameChildNL.getLength())
		    			{
		    				Node nameChild = nameChildNL.item(l);
		    				if(nameChild.getNodeName().equals("genre")){
		    					
		    					if(!genresList.contains(nameChild.getAttributes().getNamedItem("genre").getNodeValue()) && !nameChild.getAttributes().getNamedItem("genre").getNodeValue().equals("")){
				    				
				    				genresList.add(nameChild.getAttributes().getNamedItem("genre").getNodeValue());
				    			}
		    					
		    				}
		    				
		    				++l;
		    			}
		    				
		    			++k;
		    		}
		    	   
		    		++j;
		    	}
		    	
		    	++i;
		    }
		   
		 }catch(ParserConfigurationException pce){
		   pce.printStackTrace();
		   JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getGenresXML.");
		 } catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getGenresXML.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error: ServicePersistent.getGenresXML.");
		}
		
		 Collections.sort(genresList);
		 String[] genres = new String[genresList.size()];
		 for(int i=0; i < genres.length; ++i) genres[i] = genresList.get(i);
		 
		 return genres;
	 }
	
}
