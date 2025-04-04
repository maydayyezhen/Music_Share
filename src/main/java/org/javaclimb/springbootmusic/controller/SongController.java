package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.service.SongService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }
    //根据歌手ID获取歌曲
    @GetMapping("/artist/{artistId}")
    public List<Song> getSongsByArtistId(@PathVariable Integer artistId) {
        return songService.getSongsByArtistId(artistId);
    }
    @GetMapping("/{id}/audioFile")
    public ResponseEntity<Resource> getAudioFileById(@PathVariable Integer id){
        return songService.getAudioFileById(id);
    }
    @GetMapping("/{id}/coverFile")
    public ResponseEntity<Resource> getCoverBySongId(@PathVariable Integer id){
        return songService.getCoverBySongId(id);
    }

    @GetMapping("/album/{albumId}")
    public List<Song> getSongsByAlbumId(@PathVariable Integer albumId) {
        return songService.getSongsByAlbumId(albumId);
    }

    @PostMapping
    public Song addSong(@RequestBody Song song) {return songService.addSong(song);}
    //歌曲上传
    @PostMapping("/audioFile")
    public String uploadAudioFile(@RequestParam("audioFile") MultipartFile audioFile) {
        return songService.uploadAudioFile(audioFile);
    }

    @PutMapping
    public Song updateSong(@RequestBody Song song) {
        return songService.updateSong(song);
    }


    @DeleteMapping("/{id}")
    public void deleteSongById(@PathVariable Integer id) {
        songService.deleteSongById(id);
    }
    @DeleteMapping("/{id}/audioFile")
    public ResponseEntity<String> deleteAudioFileById(@PathVariable("id") Integer id) {
        return songService.deleteAudioFileById(id);
    }


}
