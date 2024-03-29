package tech.damko.videoteka.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.damko.videoteka.exception.MovieNotFoundException;
import tech.damko.videoteka.model.Movie;
import tech.damko.videoteka.repo.MovieRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

            boolean movieExists = false;
            //Preveriti, da filem po ključu disk + ime filma na disku, še ne obstaja v bazi.
            Optional<List<Movie>> moviesByName = movieRepo.findMovieByName(movie.getName());
            Stream<Movie> stream = moviesByName.map(List::stream).orElse(Stream.empty());
            Iterator<Movie> it = stream.iterator();
            while (it.hasNext()) {
                Movie m = it.next();
                if(m.getDisc().equals(movie.getDisc())){
                    movieExists = true;
                    break;
                }
            }
            if(!movieExists){
                movieRepo.save(movie);
            }
        }
        return movieRepo.findAll();
    }
}
