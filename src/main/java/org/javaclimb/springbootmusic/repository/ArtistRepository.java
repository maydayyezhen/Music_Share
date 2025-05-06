package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    Page<Artist> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
