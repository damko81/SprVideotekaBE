package tech.damko.videoteka.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String disc;
    private String name;
    private String nameFromDisc;
    private String genre;
    private String rating;
    private String description;
    private String stars;
    private String infobar;
    private String director;
    private String duration;
    private String storyline;
    private String releaseDate;
    private String url;
    private String imageSrc;

    public Movie(){}

    public Movie(String disc,String name,String genre, String rating, String description, String stars, String infobar, String director, String duration,String storyline,String releaseDate,String nameFromDisc,String url,String imageSrc){
        this.disc = disc;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.stars = stars;
        this.infobar = infobar;
        this.director = director;
        this.duration = duration;
        this.storyline = storyline;
        this.releaseDate = releaseDate;
        this.nameFromDisc = nameFromDisc;
        this.url = url;
        this.imageSrc = imageSrc;
    }

    public Long getId(){
        return id;
    }

    public void  setId(long id){
        this.id = id;
    }

    public void setImageSrc(String imageSrc){
        this.imageSrc = imageSrc;
    }

    public String getImageSrc(){
        return this.imageSrc;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setNameFromDisc(String nameFromDisc){
        this.nameFromDisc = nameFromDisc;
    }

    public String getNameFromDisc(){
        return this.nameFromDisc;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate(){
        return this.releaseDate;
    }

    public void setStoryline(String storyline){
        this.storyline = storyline;
    }

    public String getStoryline(){
        return this.storyline;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getDuration(){
        return this.duration;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector(){
        return this.director;
    }

    public void setInfobar(String infobar){
        this.infobar = infobar;
    }

    public String getInfobar(){
        return this.infobar;
    }

    public void setStars(String stars){
        this.stars = stars;
    }

    public String getStars(){
        return this.stars;
    }


    public void setDisc(String disc){
        this.disc = disc;
    }

    public String getDisc(){
        return this.disc;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return this.genre;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getRating(){
        return this.rating;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    @Override
    public String toString(){
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameFromDisc='" + nameFromDisc + '\'' +
                ", disc='" + disc + '\'' +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                ", stars='" + stars + '\'' +
                ", infobar='" + infobar + '\'' +
                ", director='" + director + '\'' +
                ", duration='" + duration + '\'' +
                ", storyline='" + storyline + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", url='" + url + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }

}

