package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@RestController
@RequestMapping("/songs")
@CrossOrigin("*")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // 查询类接口，公开访问
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Integer id) {
        return songService.getSongById(id);
    }

    @GetMapping("/artist/{artistId}")
    public List<Song> getSongsByArtistId(@PathVariable Integer artistId) {
        return songService.getSongsByArtistId(artistId);
    }

    @GetMapping("/{id}/coverUrl")
    public String getCoverUrlBySongId(@PathVariable Integer id){
        return songService.getCoverUrlBySongId(id);
    }

    @GetMapping("/{id}/album")
    public Album getAlbumBySongId(@PathVariable Integer id) {
        return songService.getAlbumBySongId(id);
    }

    @GetMapping("/album/{albumId}")
    public List<Song> getSongsByAlbumId(@PathVariable Integer albumId) {
        return songService.getSongsByAlbumId(albumId);
    }

    // 写操作，需要管理员权限
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songService.addSong(song);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/audioFile")
    public ResponseEntity<Void> uploadAudioFile(@PathVariable Integer id, @RequestParam("audioFile") MultipartFile audioFile) {
        return songService.uploadAudioFile(id, audioFile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/lrcFile")
    public ResponseEntity<Void> uploadLrcFile(@PathVariable Integer id, @RequestParam("lrcFile") MultipartFile lrcFile) {
        return songService.uploadLrcFile(id, lrcFile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Song updateSong(@RequestBody Song song) {
        return songService.updateSong(song);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSongById(@PathVariable Integer id) {
        songService.deleteSongById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/audioFile")
    public ResponseEntity<String> deleteAudioFileById(@PathVariable Integer id) {
        return songService.deleteAudioFileById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/lrcFile")
    public ResponseEntity<String> deleteLrcFileById(@PathVariable Integer id) {
        return songService.deleteLrcFileById(id);
    }
}

