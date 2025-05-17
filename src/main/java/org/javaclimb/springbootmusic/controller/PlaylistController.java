package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Playlist;
import org.javaclimb.springbootmusic.model.Song;
import org.javaclimb.springbootmusic.service.PlaylistService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin("*")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // 查询类接口，公开访问
    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/paged")
    public Page<Playlist> getPagedPlaylists(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        return playlistService.getPagedPlaylists(page, size, keyword, sortBy, sortOrder);
    }

    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Playlist> getPlaylistsByUserId(@PathVariable Integer userId) {
        return playlistService.getPlaylistsByUserId(userId);
    }

    @GetMapping("/{id}/songs")
    public List<Song> getSongsByPlaylistId(@PathVariable Integer id) {
        return playlistService.getSongsByPlaylistId(id);
    }

    // 写操作，需要管理员权限
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/cover")
    public ResponseEntity<Void> uploadCoverImage(@PathVariable Integer id, @RequestParam("coverImage") MultipartFile coverImage) {
        return playlistService.uploadCoverImage(id, coverImage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{playlistId}/songs/{songId}")
    public Playlist addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return playlistService.addSongToPlaylist(playlistId, songId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public Playlist removeSongFromPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        return playlistService.removeSongFromPlaylist(playlistId, songId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Playlist updatePlaylist(@RequestBody Playlist playlist) {
        return playlistService.updatePlaylist(playlist);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable Integer id) {
        playlistService.deletePlaylist(id);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePlaylist(@PathVariable Integer id) {
        return playlistService.likePlaylist(id);
    }
}
