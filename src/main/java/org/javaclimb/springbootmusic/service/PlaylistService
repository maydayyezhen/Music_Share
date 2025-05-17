package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Playlist;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.repository.PlaylistRepository;
import org.javaclimb.springbootmusic.repository.SongRepository;
import org.javaclimb.springbootmusic.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import static org.javaclimb.springbootmusic.constants.FilePaths.PLAYLIST_COVER_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;
import static org.javaclimb.springbootmusic.service.FileService.deleteFile;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           UserRepository userRepository,
                           SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById(Integer id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("播放列表未找到: " + id));
    }

    public List<Playlist> getPlaylistsByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("用户未找到: " + userId);
        }
        return playlistRepository.findByCreatorId(userId);
    }

    public List<Song> getSongsByPlaylistId(Integer id) {
        Playlist playlist = getPlaylistById(id);
        return playlist.getSongs().stream().toList();
    }


    public Playlist createPlaylist(Playlist playlist) {
        if (playlist.getCreator() == null || playlist.getCreator().getId() == null) {
            throw new IllegalArgumentException("播放列表必须指定创建者");
        }

        User creator = userRepository.findById(playlist.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("用户未找到: " + playlist.getCreator().getId()));

        playlist.setCreator(creator);
        playlist.setLikeCount(0); // 初始化点赞数

        return playlistRepository.save(playlist);
    }


    public ResponseEntity<Void> uploadCoverImage(Integer id, MultipartFile coverImage) {
        Playlist playlist = getPlaylistById(id);

        if (coverImage.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String oldCoverUrl = playlist.getCoverUrl();
        playlist.setCoverUrl(uploadFile(coverImage, PLAYLIST_COVER_PATH));
        playlistRepository.save(playlist);

        // 删除旧的封面图片
        if (oldCoverUrl != null && !oldCoverUrl.isEmpty()) {
            deleteFile(oldCoverUrl);
        }

        return ResponseEntity.ok().build();
    }

    public Playlist addSongToPlaylist(Integer playlistId, Integer songId) {
        Playlist playlist = getPlaylistById(playlistId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + songId));

        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }

    public Playlist removeSongFromPlaylist(Integer playlistId, Integer songId) {
        Playlist playlist = getPlaylistById(playlistId);
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("歌曲未找到: " + songId));

        playlist.getSongs().remove(song);
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Playlist playlist) {
        Playlist existing = playlistRepository.findById(playlist.getId())
                .orElseThrow(() -> new EntityNotFoundException("播放列表未找到: " + playlist.getId()));

        if (playlist.getName() != null) existing.setName(playlist.getName());
        if (playlist.getDescription() != null) existing.setDescription(playlist.getDescription());
        if (playlist.getCoverUrl() != null) existing.setCoverUrl(playlist.getCoverUrl());
        if (playlist.getLikeCount() != null) existing.setLikeCount(playlist.getLikeCount());

        return playlistRepository.save(existing);
    }

    public void deletePlaylist(Integer id) {
        Playlist playlist = getPlaylistById(id);
        // 先删除封面图片
        if (playlist.getCoverUrl() != null && !playlist.getCoverUrl().isEmpty()) {
            deleteFile(playlist.getCoverUrl());
        }
        playlistRepository.delete(playlist);
    }


    public ResponseEntity<Void> likePlaylist(Integer id) {
        Playlist playlist = getPlaylistById(id);
        playlist.setLikeCount(playlist.getLikeCount() + 1);
        playlistRepository.save(playlist);
        return ResponseEntity.ok().build();
    }

    public Page<Playlist> getPagedPlaylists(int page, int size, String keyword, String sortBy, String sortOrder) {
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by("desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (keyword == null || keyword.isEmpty()) {
            return playlistRepository.findAll(pageRequest);
        } else {
            return playlistRepository.findByNameContainingIgnoreCase(keyword, pageRequest);
        }
    }
}
