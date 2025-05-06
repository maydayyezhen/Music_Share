package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.service.AlbumService;
import org.javaclimb.springbootmusic.service.ArtistService;
import org.javaclimb.springbootmusic.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/artists")
@CrossOrigin("*")
public class ArtistController {
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, AlbumService albumService, SongService songService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/paged")
    public Page<Artist> getPagedArtists(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        return artistService.getPagedArtists(page, size, keyword, sortBy, sortOrder);
    }


    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Integer id) {
        return artistService.getArtistById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/avatarFile")
    public ResponseEntity<Void> uploadAvatarFile(@PathVariable Integer id, @RequestParam("avatarFile") MultipartFile avatarFile) {
        return artistService.uploadAvatarFile(id, avatarFile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Artist updateArtist(@RequestBody Artist artist) {
        return artistService.updateArtist(artist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteArtistById(@PathVariable Integer id) {
        List<Album> albums = albumService.getAlbumsByArtistId(id);
        for (Album album : albums) {
            Integer albumId = album.getId();
            List<Song> songs = songService.getSongsByAlbumId(albumId);
            for (Song song : songs) {
                Integer sid = song.getId();
                songService.deleteAudioFileById(sid);
                songService.deleteLrcFileById(sid);
                songService.deleteSongById(sid);
            }
            albumService.deleteCoverFileById(albumId);
            albumService.deleteAlbumById(albumId);
        }
        artistService.deleteArtistById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable Integer id) {
        return artistService.deleteAvatarFileById(id);
    }
}

