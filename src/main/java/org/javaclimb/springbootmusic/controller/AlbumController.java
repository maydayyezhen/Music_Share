package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.service.AlbumService;
import org.javaclimb.springbootmusic.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/albums")
@CrossOrigin("*")
public class AlbumController {
    private final AlbumService albumService;
    private final SongService songService;
    public AlbumController(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }
    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }
    @GetMapping("/artist/{artistId}")
    public List<Album> getAlbumsByArtistId(@PathVariable Integer artistId) {
        return albumService.getAlbumsByArtistId(artistId);
    }

    @GetMapping("/song/{songId}")
    public Album getAlbumBySongId(@PathVariable Integer songId) {
        Song song = songService.getSongById(songId);
        Album album = song.getAlbum();
        Integer albumId = album.getId();
        return albumService.getAlbumById(albumId);
    }

    @GetMapping("/{id}")
    public Album getAlbumsByAlbumId(@PathVariable Integer id) {
        return albumService.getAlbumById(id);
    }

    @PutMapping
    public Album updateAlbum(@RequestBody Album album) {
        return albumService.updateAlbum(album);
    }

    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.createAlbum(album);
    }
    @PostMapping("/{id}/coverFile")
    public ResponseEntity<Void> uploadCoverFile(@PathVariable Integer id, @RequestParam("coverFile") MultipartFile coverFile) {
        return albumService.uploadCoverFile(id,coverFile);
    }

    @DeleteMapping("/{id}")
    public void deleteAlbumById(@PathVariable Integer id) {
        List<Song> songs = songService.getSongsByAlbumId(id);
        for (Song song : songs) {
            Integer sid = song.getId();
            songService.deleteAudioFileById(sid);
            songService.deleteLrcFileById(sid);
            songService.deleteSongById(sid);
        }
        albumService.deleteAlbumById(id);
    }

    @DeleteMapping("/{id}/coverFile")
    public ResponseEntity<String> deleteCoverFileById(@PathVariable Integer id) {
        return albumService.deleteCoverFileById(id);
    }
}
