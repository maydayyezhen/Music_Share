package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/albums")
@CrossOrigin("*")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }
    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }
    @GetMapping("/artist/{artistId}")
    public List<Album> getAlbumsByArtistId(@PathVariable Integer artistId) {
        return albumService.getAlbumsByArtistId(artistId);
    }

    @GetMapping("/{id}")
    public Album getAlbumsByAlbumId(@PathVariable Integer id) {
        return albumService.getAlbumById(id);
    }


    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.createAlbum(album);
    }
    @PostMapping("/{id}/coverFile")
    public ResponseEntity<Void> uploadCoverFile(@PathVariable Integer id, @RequestParam("coverFile") MultipartFile coverFile) {
        return albumService.uploadCoverFile(id,coverFile);
    }

    @DeleteMapping("/{id}/coverFile")
    public ResponseEntity<String> deleteCoverFileById(@PathVariable Integer id) {
        return albumService.deleteCoverFileById(id);
    }
}
