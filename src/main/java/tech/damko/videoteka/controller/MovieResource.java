package tech.damko.videoteka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.damko.videoteka.model.Movie;
import tech.damko.videoteka.service.MovieService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/movie")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = movieService.findAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id){
        Movie movie = movieService.findMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie newMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(newMovie,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie){
        Movie updateMovie = movieService.updateMovie(movie);
        return new ResponseEntity<>(updateMovie,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteMovieByDisc/{disc}")
    public ResponseEntity<?> deleteMovieByDisc(@PathVariable("disc") String disc){
        movieService.deleteMovieByDisc(disc);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<List<Movie>> loadMovies(@RequestBody String disc){
        List<Movie> movies = movieService.loadMovies(disc);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
