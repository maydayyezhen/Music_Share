package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.repository.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@CrossOrigin("*")
public class SongController {
    private final SongRepository songRepository;

    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
    @PutMapping
    public Song updateSong(@RequestBody Song song) {
        return songRepository.save(song);
    }
    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Integer id) {
        songRepository.deleteById(id);
    }
    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

}
