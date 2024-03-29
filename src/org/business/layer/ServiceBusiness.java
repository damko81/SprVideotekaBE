package org.business.layer;

import org.model.layer.Film;
import org.persistent.layer.XMLParser;
import org.business.layer.HTMLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceBusiness {
	
@SuppressWarnings("rawtypes")
public static ArrayList<Film> doRequestDetailFromList(HashMap<String,HashMap<String,String>> list,HashMap<String,HashMap<String,String>> filmiDisc) throws IOException, Exception{
		
        
	    HashMap<String,HashMap<String,String>> filmDetail;
	    ArrayList<Film> filmi = new ArrayList<Film>();
	    
		for(String i : list.keySet()){
			
			HashMap<String,String> f = list.get(i);
			Iterator<?> it = f.entrySet().iterator();
	
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        filmDetail = doRequestMovieDetail((String)pairs.getKey(),(String)pairs.getValue());
		        filmi.add(createFilm(i,filmDetail,filmiDisc));
		    }
		
		}
		
		return filmi;
	}
	
    private static Film createFilm(String nameFromDisc,HashMap<String,HashMap<String,String>> filmDetail,HashMap<String,HashMap<String,String>> filmiDisc){
    	
    	Film film = null;
    	String disc;
    	String name;
    	String genre;
    	String rating;
    	String description;
    	String stars;
    	String infobar;
    	String director;
    	String duration;
    	String storyline;
    	String releaseDate;
    	String url;
    	String imageSrc;
    	
        for(String i : filmDetail.keySet()){
			
			HashMap<String,String> f = filmDetail.get(i);

			disc = getDisc(nameFromDisc,filmiDisc);
			name = f.get("name");
			genre = f.get("genre");
			rating = f.get("rating");
			description = f.get("description");
			stars = f.get("stars");
			infobar = f.get("infobar");
			director = f.get("director");
			duration = f.get("duration");
			storyline = f.get("storyline");
			releaseDate = f.get("releaseDate");
			url = f.get("url");
			imageSrc = f.get("imageSrc");
			
			film = new Film(disc,name,genre,rating,description,stars,infobar,director,duration,storyline,releaseDate,nameFromDisc,url,imageSrc);
			
			break;
		}
    	
    	
    	return film;
    	
    }
    
    private static String getDisc(String nameFromDisc,HashMap<String,HashMap<String,String>> filmiDisc){
    	
 
    	String disc="";
    	
    	
        for(String i : filmiDisc.keySet()){
			
			HashMap<String,String> f = filmiDisc.get(i);
            if(f.get(nameFromDisc) != null)	{
            	
            	disc = i;
            	break;
            	
            }
		}
    	
    	
    	return disc;
    	
    }
    
    private static HashMap<String,HashMap<String,String>> doRequestMovieDetail(String movieName,String urlString){
		
		
		 HttpURLConnection httpConn = null;
		 StringBuilder responseBuilder = new StringBuilder();
		 String line;
		
		 try
		 {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			httpConn = (HttpURLConnection)conn;
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			 while ((line = rd.readLine()) != null)
			 {
			    responseBuilder.append(line + '\n');
			 }
			
		 }catch(Exception ex){
			System.err.print(ex.getMessage());
		 }
		 finally{
			httpConn.disconnect();
		 }
	 
		 if (httpConn != null) {
			 httpConn.disconnect();
	     }
		 
		 String x = responseBuilder.toString();
		  
		 HashMap<String,HashMap<String,String>> filmDetail = new HashMap<String,HashMap<String,String>>();
		 HashMap<String,String> detail = new HashMap<String,String>();
		 
		 HTMLParser parser = new HTMLParser();
		 
		 String rating = parser.parseRating(x);
		 detail.put("rating", rating);
		 String genre = parser.parseGenre(x);
		 detail.put("genre", genre);
		 String duration = parser.parseDuration(x);
		 detail.put("duration", duration);
		 String releaseDate = parser.parseReleaseDate(x);
		 detail.put("releaseDate", releaseDate);
		 String infobar = parser.parseInfobar(x);
		 detail.put("infobar", infobar);
		 String director = parser.parseDirector(x);
		 detail.put("director", director);
		 String storyline = parser.parseStoryline(x);
		 detail.put("storyline", storyline);
		 String description = parser.parseDescription(x);
		 detail.put("description", description);
		 String stars = parser.parseStars(x);
		 detail.put("stars", stars);
		 detail.put("name", movieName);
		 detail.put("url", urlString);
		 String imageSrc = parser.parseImageSrc(x);	
		 detail.put("imageSrc", imageSrc);
		 
		 filmDetail.put(movieName, detail);
		 return filmDetail;
	}
    
       
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap<String,String>> doSearchRequestFromList(HashMap<String,HashMap<String,String>> list) throws IOException, Exception{
		
		HashMap<String,HashMap<String,String>> movieRef = new HashMap<String,HashMap<String,String>>();
	    
		for(String disc : list.keySet()){
			
			HashMap<String,String> f = list.get(disc);

			Iterator<?> it = f.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        
		        movieRef = doSearchRequestParser((String)pairs.getKey(),"http://www.imdb.com/find?q=" + pairs.getKey().toString().replace(" ", "+") + "&s=all","http://www.imdb.com/title/",movieRef);
		      	
		    }
		    		
		}
	
		return movieRef;	
	}
	
	private static HashMap<String,HashMap<String,String>> doSearchRequestParser(String movieName,String urlString,String prefix,HashMap<String,HashMap<String,String>> movieRel){
		
		
		 HttpURLConnection httpConn = null;
		 StringBuilder responseBuilder = new StringBuilder();
		 String line;
		
		 try
		 {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			httpConn = (HttpURLConnection)conn;
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			 while ((line = rd.readLine()) != null)
			 {
			    responseBuilder.append(line + '\n');
			 }
			
		 }catch(Exception ex){
			System.err.print(ex.getMessage());
		 }
		 finally{
			httpConn.disconnect();
		 }

		 String x = responseBuilder.toString();
		  
		 String href = "";
		 int startIndex = 0;
		 int endIndex = 0;
		 int count = 1;
		 String title = "";
		 
		 HashMap<String,String> t = new HashMap<String,String>();
		 
		 Pattern p = Pattern.compile("href=\"/title/");
	     Matcher m = p.matcher(x);
	     String ch = " ";
		 while (m.find()) {
			 
			    startIndex = m.end();   
			 
			    Pattern px = Pattern.compile(">");
			    Matcher mx = px.matcher(x);
			    
			    while(mx.find(startIndex))
			    {
			    	endIndex = mx.end() - 1;
			    	break;
			    	
			    }
			    
			    href = (prefix + x.substring(startIndex, endIndex-1)).replace("\"", "");
			    
			    if(count == 2){
			    	
			       Pattern py = Pattern.compile("<");
				   Matcher my = py.matcher(x);
				   
				   while(my.find(endIndex+1))
				    {
					    title = x.substring(endIndex+1,my.start());
				    	break;    	
				    }
				
				   if(title.toLowerCase().contains(movieName.toLowerCase())){
					  
					   //HashMap lahko vsebuje unikaten key - lahko imamo vec enakih naslovov vendar drugi filmi. Dodamo skrite presledke. Cudna resitev, vendar deluje lepo :)
					   if(t.containsKey(title)){
						   title += ch;  
						   ch += " ";
					   }
					   else{
						   ch = " ";
					   }
					   t.put(title, href);  
				   }
				       
			       count = 1;
			    }   
			    
			    ++count;
			}
		 
		 if (httpConn != null) {
			 httpConn.disconnect();
	     }
		 
		 // Klic rekurzije !!!
		 if(t.isEmpty()) t = parseTitleRecursive(prefix,movieName,t);
			    
		 movieRel.put(movieName,t);
		 return movieRel;
	}
	
	//Rekurzivna metoda !
	private static HashMap<String,String> parseTitleRecursive(String prefix,String movieName,HashMap<String,String> t){
		
	   int size = t.size();
	  
	   if( movieName.lastIndexOf(" ") == -1){return t;}
	   if( movieName.lastIndexOf(" ") != -1){	 
		   		   
	    	 
		    movieName = movieName.substring(0,movieName.lastIndexOf(" "));
			String urlString = "http://www.imdb.com/find?q=" + movieName.replace(" ", "+") + "&s=all";
			HttpURLConnection httpConn = null;
			StringBuilder responseBuilder = new StringBuilder();
			String line;
			
			try
			{
				URL url = new URL(urlString);
				URLConnection conn = url.openConnection();
				httpConn = (HttpURLConnection)conn;
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				 while ((line = rd.readLine()) != null)
				 {
				  responseBuilder.append(line + '\n');
				 }
				
			}catch(Exception ex){
				System.err.print(ex.getMessage());
			}
			finally{
				httpConn.disconnect();
			}
		
			 String x = responseBuilder.toString();
			 
			 
			 String href = "";
			 int startIndex = 0;
			 int endIndex = 0;
			 int count = 1;
			 String title = "";
			 		 
			 Pattern p = Pattern.compile("href=\"/title/");
		     Matcher m = p.matcher(x);
			 while (m.find()) {
				 
				    startIndex = m.end();   
				 
				    Pattern px = Pattern.compile(">");
				    Matcher mx = px.matcher(x);
				    
				    while(mx.find(startIndex))
				    {
				    	endIndex = mx.end() - 1;
				    	break;
				    	
				    }
				    
				    href = (prefix + x.substring(startIndex, endIndex-1)).replace("\"", "");
				    
				    if(count == 2){
				    	
				       Pattern py = Pattern.compile("<");
					   Matcher my = py.matcher(x);
					   
					   while(my.find(endIndex+1))
					    {
						    title = x.substring(endIndex+1,my.start());
					    	break;
					    	
					    }
					
					   if(title.toLowerCase().contains(movieName.toLowerCase())){
						   t.put(title, href);
					   }
					      
				       count = 1;
				    }   
				    
				    ++count;
				}
			 
		   if (httpConn != null) {
				 httpConn.disconnect();
		   } 
	   }
	   if(t.size() == size){
		   t = parseTitleRecursive(prefix,movieName,t);
		   return t;
	   }
	   
	   return t;
	}
	
	public static HashMap<String,HashMap<String,String>> listOfFile(ArrayList<String> directories,HashMap<String,HashMap<String,String>> files,boolean merge,String xmlFile){
		
		String movieKey = "";
		
		for(String dir : directories){
		
			File folder = new File(dir);
			File[] listOfFiles = folder.listFiles();
			HashMap<String,String> dirfile = new HashMap<String,String>(); 
			
			for (File file : listOfFiles) {
				
				movieKey = removeSpecialChar(file.getName());
				
				if(!merge){
		            
					dirfile.put(movieKey, file.getName());
		        }
		        else{
		        	 XMLParser p = new XMLParser();
		        	 if( !p.existsDiscXML(xmlFile, dir) ){
		        		  
		        		 dirfile.put(movieKey, file.getName());
		        		 
		        	 }
		        	 else{
		        		 
		        		 if(!p.existsNameFromDiscXML(xmlFile,movieKey,dir)){
		        			 
		        			  dirfile.put(movieKey, file.getName());
		        			 
		        		 }
		        		 
		        	 }
		        }
				
				
			}
			files.put(dir, dirfile);
	    }	
		
		return files;
	}
	
	private static String removeSpecialChar(String niz){
		
		String x = niz.replace("."," ");
		x = x.replace("("," ");
		x = x.replace(")"," ");
		x = x.replace("["," ");
		x = x.replace("]"," ");
		x = x.replace("<"," ");
		x = x.replace(">"," ");
		x = x.replace("{"," ");
		x = x.replace("}"," ");
		
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(x);
		while (m.find()) {
			
		  if(m.group().length() > 2)
		  {
			  x = x.substring(0, m.start()-1);
			  break;
		  }
		  
		}
		
		return x;
	}
	
	public static String getSizeableRowString(String s, int rowSize){
		   
		   String[] st = s.split(" ");
		   String str = "";
		   
		   int j = 0;
		   for(int i=0; i < st.length; ++i){
			   
			   if(j >= rowSize){
				   str += "\n";
				   j = 0;
			   }
			   
			   str += st[i] + " "; 
			   
			   ++j;
		   }
		   
		   return str;
	}
	
	@SuppressWarnings("rawtypes")
	public static void izpisiSeznam(HashMap<String,HashMap<String,String>> list){
		
		for(String i : list.keySet()){
					
			HashMap<String,String> f = list.get(i);

			Iterator<?> it = f.entrySet().iterator();
			System.out.println("Disk: " + i);
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println("Key: " + pairs.getKey() + " Value: " + pairs.getValue());
		    }
					
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void izpisiSeznamFilemRef(HashMap<String,HashMap<String,String>> movieRel){
		
		for(String i : movieRel.keySet()){
			
			HashMap<String,String> f = movieRel.get(i);

			Iterator<?> it = f.entrySet().iterator();
			System.out.println("Ime filma iz diska: " + i);
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println("Title: " + pairs.getKey() + " URL: " + pairs.getValue());
		    }
		    
		    System.out.println("==============================================================================================");
					
		}
	}
	
}
