package org.model.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.persistent.layer.ServicePersistent;

public class ServiceModel {
		  
	  enum FilmComparator implements Comparator<Film> {
		    DISC_SORT {
		        public int compare(Film f1, Film f2) {
		            return f1.getDisc().compareTo(f2.getDisc());
		              }},
		    NAME_FROM_DISC_SORT {
		        public int compare(Film f1, Film f2) {
		            return f1.getNameFromDisc().compareTo(f2.getNameFromDisc());
		        }},
		    NAME_SORT {
			        public int compare(Film f1, Film f2) {
			            return f1.getName().compareTo(f2.getName());
			        }};    

		    public static Comparator<Film> decending(final Comparator<Film> other) {
		        return new Comparator<Film>() {
		            public int compare(Film f1, Film f2) {
		                return -1 * other.compare(f1, f2);
		            }
		        };
		    }
		    
		    public static Comparator<Film> acending(final Comparator<Film> other) {
		        return new Comparator<Film>() {
		            public int compare(Film f1, Film f2) {
		                return other.compare(f1, f2);
		            }
		        };
		    }

		    public static Comparator<Film> getComparator(final FilmComparator... multipleOptions) {
		        return new Comparator<Film>() {
		            public int compare(Film o1, Film o2) {
		                for (FilmComparator option : multipleOptions) {
		                    int result = option.compare(o1, o2);
		                    if (result != 0) {
		                        return result;
		                    }
		                }
		                return 0;
		            }
		        };
		    }
		}  
	  
	public static ArrayList<Film> sort(ArrayList<Film> filmiObject){
		
		
		Collections.sort(filmiObject, FilmComparator.acending(FilmComparator.getComparator(FilmComparator.DISC_SORT, FilmComparator.NAME_FROM_DISC_SORT,FilmComparator.NAME_SORT)));
		
		return filmiObject;
	}

  
	public static ArrayList<Film> searchMovieByNameFromDisc(ArrayList<Film> filmiObject,String nameFromDisc){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getNameFromDisc().toLowerCase().equals(nameFromDisc.toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByDisc(ArrayList<Film> filmiObject,String disc){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getDisc().toLowerCase().equals(disc.toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByGenre(ArrayList<Film> filmiObject,String genre){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getGenre().toLowerCase().equals(genre.toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
  public static ArrayList<Film> searchMovieByGenreReduced(ArrayList<Film> filmiObject,String genre){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getGenre().toLowerCase().equals(genre.toLowerCase()) && f.getNameFromDisc().toLowerCase().equals(f.getName().toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByStars(ArrayList<Film> filmiObject,String stars){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getStars().toLowerCase().contains(stars.toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByStarsReduced(ArrayList<Film> filmiObject,String stars){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getStars().toLowerCase().contains(stars.toLowerCase()) && f.getNameFromDisc().toLowerCase().equals(f.getName().toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByDirector(ArrayList<Film> filmiObject,String director){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getDirector().toLowerCase().contains(director.toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static ArrayList<Film> searchMovieByDirectorReduced(ArrayList<Film> filmiObject,String director){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		for(Film f : filmiObject)
		{	
			if(f.getDirector().toLowerCase().contains(director.toLowerCase()) && f.getNameFromDisc().toLowerCase().equals(f.getName().toLowerCase())) filmiO.add(f);
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static boolean existsNameFromDisc(ArrayList<Film> filmiO,String nameFromDisc,String disc){
		
		boolean existNameFromDisc = false;
		
		String nameFromDiscT = null;
		
		ServicePersistent sp = new ServicePersistent();
		
		for(Film f : filmiO){
			
			nameFromDiscT = f.getNameFromDisc().toLowerCase();
			nameFromDiscT = sp.cleanString(nameFromDiscT);
			
			if(nameFromDiscT.equals(nameFromDisc.toLowerCase()) && f.getDisc().toLowerCase().equals(disc.toLowerCase())){
				existNameFromDisc = true;
				break;
			}
			
		}
		
		return existNameFromDisc;
	}
	
	public static ArrayList<Film> ReduceMovieList(ArrayList<Film> filmiObject){
		
		ArrayList<Film> filmiO = new ArrayList<Film>();
		
		String nameFromDisc = null;
		String name = null;
		
		ServicePersistent sp = new ServicePersistent();
		
		for(Film f : filmiObject)
		{
			nameFromDisc = f.getNameFromDisc().toLowerCase();
			name = f.getName().toLowerCase();
			
			nameFromDisc = sp.cleanString(nameFromDisc);
			name = sp.cleanString(name);

			if(nameFromDisc.equals(name)){filmiO.add(f);}
		       
		}
		for(Film f : filmiObject)
		{
			nameFromDisc = f.getNameFromDisc().toLowerCase();
			name = f.getName().toLowerCase();
			
			nameFromDisc = sp.cleanString(nameFromDisc);
			name = sp.cleanString(name);
			
			if(!nameFromDisc.equals(name) && !existsNameFromDisc(filmiO,nameFromDisc,f.getDisc())){filmiO.add(f);}
		       
		}
		
		filmiO = ServiceModel.sort(filmiO);
		return filmiO;
  }
	
	public static void izpisiFilmiObject(ArrayList<Film> filmiObject){
			
			for(Film f : filmiObject)
			{	
				
				System.out.println("Disk: " + f.getDisc());
				System.out.println("Naslov filma na disku: " + f.getNameFromDisc());
				System.out.println("Originalen naslov filma: " + f.getName());
				System.out.println("Tip: " + f.getInfobar());
				System.out.println("Zvrst: " + f.getGenre());
				System.out.println("Ocena: " + f.getRating());
				System.out.println("Reziser: " + f.getDirector());
				System.out.println("Igralci: " + f.getStars());
				System.out.println("Dolzina: " + f.getDuration());
				System.out.println("Datum izdelave: " + f.getReleaseDate());
				System.out.println("Opis: " + f.getDescription());
				System.out.println("URL: " + f.getUrl());
				System.out.println("Image src: " + f.getImageSrc());
			
			    System.out.println("==============================================================================================");
			}			

	}

}
