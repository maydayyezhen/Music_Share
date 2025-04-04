package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.repository.ArtistRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Integer id) {
        return artistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("歌手未找到: " + id));
    }

    public ResponseEntity<Resource> getAvatarFileById(Integer id){
        Artist artist = getArtistById(id);
        String fileName = artist.getAvatarFilename();
        Path storageDir = Paths.get("artists/avatar");
        return FileService.getFile(fileName,storageDir);
    }


    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public String uploadAvatarFile(MultipartFile avatarFile) {
        Path uploadDir = Paths.get("artists/avatar");
        return FileService.uploadFile(avatarFile, uploadDir);
    }


    public ResponseEntity<String> deleteAvatarFileById(Integer id) {
        Artist artist = getArtistById(id);
        String fileName = artist.getAvatarFilename();
        Path storageDir = Paths.get("artists/avatar");
        return FileService.deleteFile(fileName, storageDir);
    }

}
