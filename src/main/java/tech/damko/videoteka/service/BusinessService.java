package tech.damko.videoteka.service;

import tech.damko.videoteka.model.Movie;
import tech.damko.videoteka.business.HTMLParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;

public class BusinessService {

    public static ArrayList<Movie> loadMovies(String disc){

        ArrayList<Movie> movies = null;
        HashMap<String,String> files = listOfFiles(disc); //Map: {movieKey(for imdb),MovieFileName(on disc)}
        HashMap<String,HashMap<String,String>> movieRel=null;
        try {
            movieRel = doSearchRequestFromList(files); //Pridobijo se podatki o filmih iz IMDBja. Seznam referenc, ker en filem jih ima lahko vec npr. Terminator, Terminator and Predator,...
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Vsaki referenci potrebno dobiti dodatne podatke; opis, leto, ocena...
        try {
             movies = doRequestDetailFromList(movieRel,disc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static ArrayList<Movie> doRequestDetailFromList(HashMap<String,HashMap<String,String>> movieRel,String disc) throws IOException, Exception{

        HashMap<String,HashMap<String,String>> movieDetails;
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for(String i : movieRel.keySet()){

            HashMap<String,String> f = movieRel.get(i);
            Iterator<?> it = f.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                movieDetails = doRequestMovieDetail((String)pairs.getKey(),(String)pairs.getValue());
                movies.add(createMovie(i,movieDetails,disc));
            }
        }

        return movies;
    }

    private static Movie createMovie(String nameFromDisc,HashMap<String,HashMap<String,String>> movieDetails,String disc){

        Movie movie = null;
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

        for(String i : movieDetails.keySet()){

            HashMap<String,String> f = movieDetails.get(i);

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

            movie = new Movie(disc,name,genre,rating,description,stars,infobar,director,duration,storyline,releaseDate,nameFromDisc,url,imageSrc);

            break;
        }

        return movie;
    }

    private static HashMap<String,HashMap<String,String>> doRequestMovieDetail(String movieName,String urlString) throws IOException{

        //urlString = "https://www.imdb.com/title/tt0078748/"; //TEST
        HttpURLConnection httpConn = null;
        StringBuilder responseBuilder = new StringBuilder();
        String line;

        try
        {
            URL url = new URL(urlString);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.addRequestProperty("User-Agent", "Chrome");
            httpConn.addRequestProperty("Referer", "google.si");

            boolean redirect = false;

            int status = httpConn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = httpConn.getHeaderField("Location");
                // open the new connnection again
                httpConn = (HttpURLConnection) new URL(newUrl).openConnection();
                httpConn.addRequestProperty("User-Agent", "Chrome");
                httpConn.addRequestProperty("Referer", "google.si");
            }

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

        HashMap<String,HashMap<String,String>> movieDetail = new HashMap<String,HashMap<String,String>>();
        HashMap<String,String> detail = new HashMap<String,String>();

        HTMLParser parser = new HTMLParser();

        String rating = parser.parseRating(x);
        detail.put("rating", rating);
        String genre = parser.parseGenre(x).replace("\"","");
        detail.put("genre", genre);
        String duration = parser.parseDuration(x);
        detail.put("duration", duration);
        String releaseDate = parser.parseReleaseDate(x);
        detail.put("releaseDate", releaseDate);
        String infobar = parser.parseStoryline(x);
        detail.put("infobar", infobar);
        String director = parser.parseDirector(x);
        detail.put("director", director);
        String storyline = infobar;
        detail.put("storyline", storyline);
        String description = parser.parseDescription(x);
        detail.put("description", description);
        String stars = parser.parseStars(x);
        detail.put("stars", stars);
        detail.put("name", movieName);
        detail.put("url", urlString);
        String imageSrc = parser.parseImageSrc(x);
        detail.put("imageSrc", imageSrc);

        movieDetail.put(movieName, detail);
        return movieDetail;
    }

    private static HashMap<String,HashMap<String,String>> movieRelDistinct(HashMap<String,HashMap<String,String>> movieRel){

        HashMap<String,HashMap<String,String>> movieRelDist = new HashMap<String,HashMap<String,String>>();

        for(String i : movieRel.keySet()){

            HashMap<String,String> movieRelDistHS = new HashMap<String,String>();
            HashMap<String,String> movieRelNoDistHS = new HashMap<String,String>();
            HashMap<String,String> f = movieRel.get(i);
            Iterator<?> it = f.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                // Napolnemo filme z točnim imenom, če obstaja.
                if(((String)pairs.getKey()).equals(i)){
                    movieRelDistHS.put((String)pairs.getKey(),(String)pairs.getValue());
                }// Sicer napolnemo vse z podobnim imenom.
                else{
                    movieRelNoDistHS.put((String)pairs.getKey(),(String)pairs.getValue());
                }
            }
            // Napolnemo vse z enakim imenom iz movieRelDist.
            if (!movieRelDistHS.isEmpty()){
                movieRelDist.put(i,movieRelDistHS);
            }// Sicer napolnemo vse z podobnim imenom.
            else if(!movieRelNoDistHS.isEmpty()){
                movieRelDist.put(i,movieRelNoDistHS);
            }
        }
        return movieRelDist;
    }

    private static HashMap<String,HashMap<String,String>> doSearchRequestFromList(HashMap<String,String> list) throws IOException, Exception{

        HashMap<String,HashMap<String,String>> movieRef = new HashMap<String,HashMap<String,String>>();

        Iterator<?> it = list.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            movieRef = doSearchRequestParser((String)pairs.getKey(),"http://www.imdb.com/find/?q=" + pairs.getKey().toString().replace(" ", "+") + "&s=all","http://www.imdb.com/title/",movieRef);
        }

        movieRef = movieRelDistinct(movieRef); // Pobriše odvečne filme.
        return movieRef;
    }

    private static HashMap<String,HashMap<String,String>> doSearchRequestParser(String movieName,String urlString,String prefix,HashMap<String,HashMap<String,String>> movieRel){

        HttpURLConnection httpConn = null;
        StringBuilder responseBuilder = new StringBuilder();
        String line;

        try
        {

            URL url = new URL(urlString);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.addRequestProperty("User-Agent", "Chrome");
            httpConn.addRequestProperty("Referer", "google.si");

            boolean redirect = false;

            int status = httpConn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = httpConn.getHeaderField("Location");
                // open the new connnection again
                httpConn = (HttpURLConnection) new URL(newUrl).openConnection();
                httpConn.addRequestProperty("User-Agent", "Chrome");
                httpConn.addRequestProperty("Referer", "google.si");
            }

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
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.addRequestProperty("User-Agent", "Chrome");
                httpConn.addRequestProperty("Referer", "google.si");

                boolean redirect = false;

                int status = httpConn.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }

                if (redirect) {

                    // get redirect url from "location" header field
                    String newUrl = httpConn.getHeaderField("Location");
                    // open the new connnection again
                    httpConn = (HttpURLConnection) new URL(newUrl).openConnection();
                    httpConn.addRequestProperty("User-Agent", "Chrome");
                    httpConn.addRequestProperty("Referer", "google.si");
                }

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

    private static HashMap<String,String> listOfFiles(String dir){

        String movieKey = "";
        HashMap<String,String> files = new HashMap<String,String>();

        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            movieKey = removeSpecialChar(file.getName());
            files.put(movieKey, file.getName());
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

}
