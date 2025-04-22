package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.repository.AlbumRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.javaclimb.springbootmusic.constants.FilePaths.ALBUM_COVER_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }


    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Integer id) {
        return albumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("专辑未找到: " + id));
    }

    public List<Album> getAlbumsByArtistId(Integer artistId) {
        return albumRepository.findByArtistId(artistId);
    }


    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }
    public ResponseEntity<Void> uploadCoverFile(Integer id,MultipartFile coverFile) {
        Album album = getAlbumById(id);
        String oldCoverUrl = album.getCoverUrl();//获取旧封面URL
        album.setCoverUrl(uploadFile(coverFile,ALBUM_COVER_PATH));
        albumRepository.save(album);
        if (oldCoverUrl != null && !oldCoverUrl.isEmpty()) {//

            FileService.deleteFile(oldCoverUrl);           //删除旧文件
        }                                                  //
        return ResponseEntity.ok().build();
    }


    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
    }

    public void deleteAlbumById(Integer id) {
        albumRepository.deleteById(id);
    }

    public ResponseEntity<String> deleteCoverFileById(Integer id) {
        Album album = getAlbumById(id);
        String fileUrl = album.getCoverUrl();
        return FileService.deleteFile(fileUrl);
    }
}
