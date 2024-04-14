package tech.damko.videoteka.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();
    public void save(MultipartFile file);
    public Resource loadFromDownload(String filename);
    public Resource load(String filename);
    public boolean delete(String filename);
    public void deleteAll();
    public Stream<Path> loadDownload();
    public Stream<Path> loadAll();
    public Stream<Path> loadForLogin(String username);
    boolean loadMoviesFromXml(String filename);
    boolean export(String filename);
}
