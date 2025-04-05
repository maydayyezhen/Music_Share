package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.repository.AlbumRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.javaclimb.springbootmusic.service.FileService.uploadFile;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }


    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Integer id) {
        return albumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("专辑未找到: " + id));
    }

    public List<Album> getAlbumsByArtistId(Integer artistId) {
        return albumRepository.findByArtistId(artistId);
    }
    public ResponseEntity<Resource> getCoverFileById(Integer id){
        Album album = getAlbumById(id);
        String fileName = album.getCoverFilename();
        Path storageDir = Paths.get("albums/cover");
        return FileService.getFile(fileName,storageDir);
    }



    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }
    public String uploadCoverFile(MultipartFile coverFile) {
        Path uploadDir = Paths.get("albums/cover");
        return uploadFile(coverFile, uploadDir);
    }


    public ResponseEntity<String> deleteCoverFileById(Integer id) {
        Album album = getAlbumById(id);
        String fileName = album.getCoverFilename();
        Path storageDir = Paths.get("albums/cover");
        return FileService.deleteFile(fileName, storageDir);
    }
}
