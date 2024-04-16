package tech.damko.videoteka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.damko.videoteka.model.Movie;
import tech.damko.videoteka.response.ResponseMessage;
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
        String discTmp = disc.replace("!", "\\");
        movieService.deleteMovieByDisc(discTmp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<ResponseMessage> loadMovies(@RequestBody String disc){
        String discTmp = disc.replace("!", "\\");
        String message = "";
        try {
            boolean isLoaded = movieService.loadMovies(discTmp);

            if (isLoaded) {
                message = "Movies loaded from directori successfully: " + disc;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            message = "No movies has been necessary loaded";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not load movies from the directori: " + disc + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }

    }

}
