package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.javaclimb.springbootmusic.constants.FilePaths.ARTIST_AVATAR_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist getArtistById(Integer id) {
        return artistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("歌手未找到: " + id));
    }


    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public ResponseEntity<Void> uploadAvatarFile(Integer id, MultipartFile avatarFile) {
        Artist artist = getArtistById(id);
        String oldAvatarUrl = artist.getAvatarUrl();
        artist.setAvatarUrl(uploadFile(avatarFile, ARTIST_AVATAR_PATH));
        artistRepository.save(artist);
        // 删除旧的文件
        if (oldAvatarUrl != null&& !oldAvatarUrl.isEmpty()) {
            FileService.deleteFile(oldAvatarUrl);
        }
        return ResponseEntity.ok().build();
    }

    public void deleteArtistById(Integer id) {
        artistRepository.deleteById(id);
    }
    public ResponseEntity<String> deleteAvatarFileById(Integer id) {
        Artist artist = getArtistById(id);
        String fileUrl = artist.getAvatarUrl();
        return FileService.deleteFile(fileUrl);
    }

    public Page<Artist> getPagedArtists(int page, int size, String keyword, String sortBy, String sortOrder) {
        // 构造排序规则
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = Sort.by("desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (keyword == null || keyword.isEmpty()) {
            return artistRepository.findAll(pageRequest);
        } else {
            return artistRepository.findByNameContainingIgnoreCase(keyword, pageRequest);
        }
    }

}
