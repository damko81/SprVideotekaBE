package tech.damko.videoteka.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import tech.damko.videoteka.business.XMLParser;
import tech.damko.videoteka.model.Movie;


@Service
public class FilesStorageServiceIMPL implements FilesStorageService {

    private final Path root = Paths.get("uploads");
    private final Path download = Paths.get("download");

    @Autowired
    private MovieService movieService;

    @Override
    public void init() {
        try {
              Files.createDirectories(root);
              Files.createDirectories(download);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload or download!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
              Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
              Path file = root.resolve(filename);
              Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
              Path file = root.resolve(filename);
              return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
              return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public boolean loadMoviesFromXml(String filename) {
        boolean isLoaded = false;
        XMLParser ps = new XMLParser();
        ArrayList<Movie> movies = ps.readXML(filename);
        for (Movie movie : movies){
             Movie m = movieService.findMovieByDiscAndName(movie.getDisc(),movie.getName());
             if(m == null){
                 movieService.addMovie(movie);
                 isLoaded = true;
             }
        }

        return isLoaded;
    }

    @Override
    public boolean export(String filename) {
        boolean isExported = false;
        XMLParser ps = new XMLParser();
        List<Movie> movies = movieService.findAllMovies();
        if(!movies.isEmpty()){
            ps.createXML(movies,filename,false);
            isExported = true;
        }

        return isExported;
    }
}
