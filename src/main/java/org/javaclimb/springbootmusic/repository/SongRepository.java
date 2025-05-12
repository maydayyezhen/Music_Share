package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByArtistId(Integer id);

    Page<Song> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    List<Song> findByAlbumIdOrderByTrackNum(Integer albumId);

    @Modifying
    @Query("UPDATE Song a SET a.likeCount = a.likeCount + 1 WHERE a.id = :targetId")
    void incrementLikeCount(Integer targetId);

    @Modifying
    @Query("UPDATE Song a SET a.likeCount = a.likeCount - 1 WHERE a.id = :targetId")
    void decrementLikeCount(Integer targetId);
}

