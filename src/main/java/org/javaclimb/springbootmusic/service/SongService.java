package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import static org.javaclimb.springbootmusic.constants.FilePaths.SONG_AUDIO_PATH;
import static org.javaclimb.springbootmusic.constants.FilePaths.SONG_LYRIC_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;

@Service
public class SongService {
    private final SongRepository songRepository;
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public  List<Song> getAllSongs() {return songRepository.findAll();}

    public Song getSongById(Integer id) {
        return songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + id));
    }

    public List<Song> getSongsByArtistId(Integer id) {
        return songRepository.findByArtistId(id);
    }

    public List<Song> getSongsByAlbumId(Integer albumId) {
        return songRepository.findByAlbumIdOrderByTrackNum(albumId);
    }

    public String getCoverUrlBySongId(Integer id) {
        Song song = getSongById(id);
        Album album = song.getAlbum();
        if (album == null || album.getCoverUrl() == null) {
            throw new EntityNotFoundException("专辑封面不存在");
        }
        return album.getCoverUrl();
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public ResponseEntity<Void> uploadAudioFile(Integer id ,MultipartFile audioFile) {
        Song song = getSongById(id);
        String oldAudioUrl = song.getAudioUrl();
        song.setAudioUrl(uploadFile(audioFile, SONG_AUDIO_PATH));
        songRepository.save(song);
        // 删除旧的音频文件
        if (oldAudioUrl != null&&!oldAudioUrl.isEmpty()) {
            FileService.deleteFile(oldAudioUrl);
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> uploadLrcFile(Integer id, MultipartFile lrcFile) {
        Song song = getSongById(id);
        String oldLrcUrl = song.getLyricUrl();
        song.setLyricUrl(uploadFile(lrcFile, SONG_LYRIC_PATH));
        songRepository.save(song);
        // 删除旧的歌词文件
        if (oldLrcUrl != null&&!oldLrcUrl.isEmpty()) {
            FileService.deleteFile(oldLrcUrl);
        }
        return ResponseEntity.ok().build();
    }

    public Song updateSong(Song song) {
        return songRepository.save(song);
    }

    public void deleteSongById(Integer id) {
        songRepository.deleteById(id);
    }

    public ResponseEntity<String> deleteAudioFileById(Integer id) {
        Song song = getSongById(id);
        String fileUrl = song.getAudioUrl();
        return  FileService.deleteFile(fileUrl);
    }
    public ResponseEntity<String> deleteLrcFileById(Integer id) {
        Song song = getSongById(id);
        String fileUrl = song.getLyricUrl();
        return  FileService.deleteFile(fileUrl);
    }

    public Album getAlbumBySongId(Integer id) {
        Song song = getSongById(id);
        return song.getAlbum();
    }

    public Page<Song> getPagedSongs(int page, int size, String keyword, String sortBy, String sortOrder) {
        // 构造排序规则
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by("desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (keyword == null || keyword.isEmpty()) {
            return songRepository.findAll(pageRequest);
        } else {
            return songRepository.findByTitleContainingIgnoreCase(keyword, pageRequest);
        }
    }
}
