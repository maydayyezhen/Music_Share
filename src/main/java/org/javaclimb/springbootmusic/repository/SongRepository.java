package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer> {
}

