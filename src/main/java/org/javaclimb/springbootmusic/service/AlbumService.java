package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Album;
import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.repository.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return albumRepository.findByArtistIdOrderByReleaseDateDesc(artistId);
    }



    public Album createAlbum(Album album) {
        if (album.getLikeCount() == null) {
            album.setLikeCount(0);
        }
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
        Album original = albumRepository.findById(album.getId())
                .orElseThrow(() -> new RuntimeException("Album not found"));

        if (album.getTitle() != null) original.setTitle(album.getTitle());
        if (album.getDescription() != null) original.setDescription(album.getDescription());
        if (album.getCoverUrl() != null) original.setCoverUrl(album.getCoverUrl());
        if (album.getReleaseDate() != null) original.setReleaseDate(album.getReleaseDate());
        if (album.getArtist() != null) original.setArtist(album.getArtist());

        return albumRepository.save(original);
    }


    public void deleteAlbumById(Integer id) {
        albumRepository.deleteById(id);
    }

    public ResponseEntity<String> deleteCoverFileById(Integer id) {
        Album album = getAlbumById(id);
        String fileUrl = album.getCoverUrl();
        return FileService.deleteFile(fileUrl);
    }

    public Page<Album> getPagedAlbums(int page, int size, String keyword, String sortBy, String sortOrder) {
        // 构造排序规则
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by("desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (keyword == null || keyword.isEmpty()) {
            return albumRepository.findAll(pageRequest);
        } else {
            return albumRepository.findByTitleContainingIgnoreCase(keyword, pageRequest);
        }
    }
}
