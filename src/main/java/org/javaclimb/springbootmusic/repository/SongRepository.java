package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByArtistId(Integer id);
}

