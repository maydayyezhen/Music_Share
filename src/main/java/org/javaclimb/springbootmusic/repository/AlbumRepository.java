package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByArtistId(Integer artistId);
}
