package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Page<Album> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    List<Album> findByArtistIdOrderByReleaseDateDesc(Integer artistId);

    // 增加喜欢数
    @Modifying
    @Query("UPDATE Album a SET a.likeCount = a.likeCount + 1 WHERE a.id = :targetId")
    void incrementLikeCount(Integer targetId);

    // 减少喜欢数
    @Modifying
    @Query("UPDATE Album a SET a.likeCount = a.likeCount - 1 WHERE a.id = :targetId")
    void decrementLikeCount(Integer targetId);
}
