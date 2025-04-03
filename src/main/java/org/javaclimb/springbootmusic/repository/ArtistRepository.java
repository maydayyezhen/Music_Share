package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
}
