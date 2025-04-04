package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.repository.SongRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public  List<Song> getAllSongs() {return songRepository.findAll();}

    public List<Song> getSongsByArtistId(Integer id) {
        return songRepository.findByArtistId(id);
    }

    public List<Song> getSongsByAlbumId(Integer albumId) {
        return songRepository.findByAlbumId(albumId);
    }

    public ResponseEntity<Resource> getAudioFileById(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + id));
        String fileName = song.getAudioFilename();
        Path storageDir = Paths.get("songs/audio");
        return FileService.getFile(fileName, storageDir);
    }

    public ResponseEntity<Resource> getCoverBySongId(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + id));

        Album album = song.getAlbum();
        if (album == null || album.getCoverFilename() == null) {
            throw new EntityNotFoundException("专辑封面不存在");
        }
        String fileName = album.getCoverFilename();
        Path storageDir = Paths.get("albums/cover");
        return FileService.getFile(fileName, storageDir);
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public String uploadAudioFile(MultipartFile audioFile) {
        Path uploadDir = Paths.get("songs/audio");
        return FileService.uploadFile(audioFile, uploadDir);
    }

    public Song updateSong(Song song) {
        return songRepository.save(song);
    }

    public void deleteSongById(Integer id) {
        songRepository.deleteById(id);
    }

    public ResponseEntity<String> deleteAudioFileById(Integer id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + id));
        String fileName = song.getAudioFilename();
        Path storageDir = Paths.get("songs/audio");
        return  FileService.deleteFile(fileName, storageDir);
    }
}
