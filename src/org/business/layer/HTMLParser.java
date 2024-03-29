package org.business.layer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser {
	
	 public HTMLParser(){}
	
	 public String parseStars(String response){
			
		  
			String stars = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("Stars:");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("class=\"ghost\"");
		    Matcher my = py.matcher(response);
		    int ghostStartIndex = 0;
		    while(my.find(endIndex+1))
		    {
		    	ghostStartIndex = my.start();
		    	break;
		    }
		    
		    Pattern pz = Pattern.compile("itemprop=\"name\"");
		    Matcher mz = pz.matcher(response);
		    while(mz.find(endIndex+1))
		    {   
		    	if(mz.start() > ghostStartIndex) break;
		    
		    	Pattern pl = Pattern.compile("<");
			    Matcher ml = pl.matcher(response);
			    while(ml.find(mz.start()))
			    {
			    	stars = stars + response.substring(mz.end()+1,ml.start())+", ";
			    	break;
			    }
			    
		       endIndex = mz.end();
		    }
		   
			return stars.replace("\n", "");
			
		}
	 
	    public String parseImageSrc(String response){
			
		  
			String imageSrc = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("image_src");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile("href=\"");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("\"");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	imageSrc = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return imageSrc.replace("\n", "");
			
		}
	    
	    public String parseDescription(String response){
			
			  
			String description = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("itemprop=\"description\"");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	description = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return description.replace("\n", "");
			
		}
	    
	    public String parseStoryline(String response){
			
			  
			String storyline = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("infobar");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	storyline = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return storyline.replace("\n", "");
			
		}
	    
	    public String parseDirector(String response){
			
			  
			String director = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("itemprop=\"director\"");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile("itemprop=\"name\"");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end();
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	director = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return director.replace("\n", "");
			
		}
	    
	    
	    public String parseInfobar(String response){
			
			  
			String infobar = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("infobar");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	infobar = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
		    infobar = infobar.replace("\n", "");
		    infobar = infobar.replace("&nbsp;", "");
		    infobar = infobar.replace("-", "");
		    infobar = infobar.replace(" ", "");
			return infobar;
			
		}
	    
	    public  String parseReleaseDate(String response){
			
			  
			String releaseDate = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("Release Date:");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	releaseDate = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return releaseDate.replace("\n", "");
			
		}
	    
	    public String parseDuration(String response){
			
			  
			String duration = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("<time");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
		    	duration = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
		    duration = duration.replace("\n", "");
		    duration = duration.replace(" ", "");
			return duration;
			
		}
	    
	    public String parseGenre(String response){
			
			  
			String genre = "";
	        int startIndex = 0;
	        int endIndex = 0;
			 
			 Pattern p = Pattern.compile("itemprop=\"genre\"");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
			    genre = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		   
			return genre.replace("\n", "");
			
		}
	    
	    public String parseRating(String response){
			
			  
			 String rating = "";
	         int startIndex = 0;
	         int endIndex = 0;
			 
			 Pattern p = Pattern.compile("class=\"rating\"");
		     Matcher m = p.matcher(response);
			 while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			 }
			 
			 Pattern px = Pattern.compile(">");
			 Matcher mx = px.matcher(response);
			 while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			 }
			    	    	   	    	
	        Pattern py = Pattern.compile("<");
		    Matcher my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
			    rating = response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
		    
		    
		    p = Pattern.compile("class=\"ofTen\"");
		    m = p.matcher(response);
			while (m.find()) {
				 
				  startIndex = m.end();   
				  break;
			}
			 
			px = Pattern.compile(">");
			mx = px.matcher(response);
			while(mx.find(startIndex)) {
				 
			      endIndex = mx.end() - 1;
			      break;
			    	
			}
			    	    	   	    	
	        py = Pattern.compile("<");
		    my = py.matcher(response);
		    while(my.find(endIndex+1))
		    {
			    rating += response.substring(endIndex+1,my.start());
		    	break;
		    	
		    }
			 
			return rating.replace("\n", "");
			
		}


}
