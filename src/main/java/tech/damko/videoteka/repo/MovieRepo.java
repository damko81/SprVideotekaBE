package tech.damko.videoteka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.damko.videoteka.model.Movie;

import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie,Long> {

    void deleteMovieById(Long id);

    Optional<Movie> findMovieById(Long id);
}
