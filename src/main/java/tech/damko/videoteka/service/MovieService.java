package tech.damko.videoteka.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.damko.videoteka.exception.MovieNotFoundException;
import tech.damko.videoteka.model.Movie;
import tech.damko.videoteka.repo.MovieRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepo movieRepo;

    @Autowired
    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public Movie addMovie(Movie film){
        return movieRepo.save(film);
    }

    public List<Movie> findAllMovies(){
        return movieRepo.findAll();
    }

    public Movie updateMovie(Movie movie){
        return movieRepo.save(movie);
    }

    public Movie findMovieById(Long id){
        return movieRepo.findMovieById(id).orElseThrow(() -> new MovieNotFoundException("Movie by id " + id + " was not found."));
    }

    @Transactional
    public void deleteMovie(Long id){
        movieRepo.deleteMovieById(id);
    }

    public List<Movie> loadMovies(String disc){

        ArrayList<Movie> movies = BusinessService.loadMovies(disc);
        for (Movie movie : movies){
            movieRepo.save(movie);
        }
        return movieRepo.findAll();
    }
}
