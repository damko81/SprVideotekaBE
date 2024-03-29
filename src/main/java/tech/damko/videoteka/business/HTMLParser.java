package tech.damko.videoteka.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser {

    public HTMLParser(){}

    public String parseStars(String response){

        String stars = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;
        int endIndexT = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"actor\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"name\":");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndexT = mx.end();
            break;
        }
        if(endIndexT == 0){valid=false;}

        Pattern pxx = Pattern.compile("\"");
        Matcher mxx = pxx.matcher(response);
        while(mxx.find(endIndexT) && valid) {

            endIndex = mx.end();
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            stars = response.substring(endIndex+1,my.start());
            break;
        }

        return stars.replace("\n", "");
    }

    public String parseImageSrc(String response){

        String imageSrc = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"image\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            imageSrc = response.substring(endIndex+1,my.start());
            break;
        }

        return imageSrc.replace("\n", "");
    }

    public String parseDescription(String response){

        String description = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"description\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            description = response.substring(endIndex+1,my.start());
            break;
        }

        return description.replace("\n", "");
    }

    public String parseStoryline(String response){

        String storyline = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"description\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            storyline = response.substring(endIndex+1,my.start());
            break;
        }

        return storyline.replace("\n", "");
    }

    public String parseDirector(String response){

        String director = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;
        int endIndexT = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"director\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"name\":");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndexT = mx.end();
            break;
        }
        if(endIndexT == 0){valid=false;}

        Pattern pxx = Pattern.compile("\"");
        Matcher mxx = pxx.matcher(response);
        while(mxx.find(endIndexT) && valid) {

            endIndex = mx.end();
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            director = response.substring(endIndex+1,my.start());
            break;
        }

        return director.replace("\n", "");
    }

    public  String parseReleaseDate(String response){

        String releaseDate = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"datePublished\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            releaseDate = response.substring(endIndex+1,my.start());
            break;
        }

        return releaseDate.replace("\n", "");
    }

    public String parseDuration(String response){

        String duration = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;
        int endIndexT = 0;

        Pattern pt = Pattern.compile("property=\"og:description\""); //PROBLEM, ker ni del JSON_a.
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("content");
        Matcher m = p.matcher(response);
        while (m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\"");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\"");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            endIndexT = my.end();
            Pattern pyy = Pattern.compile(" \\|");
            Matcher myy = pyy.matcher(response);
            while(myy.find(endIndex+1) && myy.end() < endIndexT && valid)
            {
                duration = response.substring(endIndex+1,myy.start());
                break;
            }
            break;
        }

        duration = duration.replace("\n", "");
        duration = duration.replace(" ", "");
        return duration;
    }

    public String parseGenre(String response){

        String genre = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"genre\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("\\[");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("\\]");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            genre = response.substring(endIndex+1,my.start());
            break;
        }

        return genre.replace("\n", "");
    }

    public String parseRating(String response){

        String rating = "";
        boolean valid = true;
        int startIndex = 0;
        int startIndexT = 0;
        int endIndex = 0;

        Pattern pt = Pattern.compile("<script type=\"application/ld\\+json\">");
        Matcher mt = pt.matcher(response);
        while (mt.find() && valid) {

            startIndexT = mt.end();
            break;
        }
        if(startIndexT == 0){valid=false;}

        Pattern p = Pattern.compile("\"aggregateRating\":");
        Matcher m = p.matcher(response);
        while(m.find(startIndexT) && valid) {

            startIndex = m.end();
            break;
        }
        if(startIndex == 0){valid=false;}

        Pattern px = Pattern.compile("ratingValue\":");
        Matcher mx = px.matcher(response);
        while(mx.find(startIndex) && valid) {

            endIndex = mx.end() - 1;
            break;
        }
        if(endIndex == 0){valid=false;}

        Pattern py = Pattern.compile("}");
        Matcher my = py.matcher(response);
        while(my.find(endIndex+1) && valid)
        {
            rating = response.substring(endIndex+1,my.start());
            break;
        }

        return rating.replace("\n", "");
    }
}
