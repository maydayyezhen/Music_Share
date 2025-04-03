package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.repository.ArtistRepository;
import org.javaclimb.springbootmusic.service.ArtistService;
import org.javaclimb.springbootmusic.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/artists")
@CrossOrigin("*")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }
    @GetMapping("/{id}/avatarFile")
    public ResponseEntity<Resource> getAvatarFileById(@PathVariable Integer id){
        return artistService.getAvatarFileById(id);
    }


    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }
    @PostMapping("/avatarFile")
    public String uploadAvatarFile(@RequestParam("avatarFile") MultipartFile avatarFile) {
        return artistService.uploadAvatarFile(avatarFile);
    }


    @DeleteMapping("/{id}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable("id") Integer id) {
        return artistService.deleteAvatarFileById(id);
    }
}
