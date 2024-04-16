package tech.damko.videoteka.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void init();
    void save(MultipartFile file);
    Resource loadFromDownload(String filename);
    Resource load(String filename);
    boolean delete(String filename);
    void deleteAll();
    Stream<Path> loadDownload();
    Stream<Path> loadAll();
    Stream<Path> loadForLogin(String username);
    boolean loadMoviesFromXml(String filename);
    boolean export(String filename);
}
