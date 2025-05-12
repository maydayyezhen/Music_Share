package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    Page<Artist> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // 增加喜欢数
    @Modifying
    @Query("UPDATE Artist a SET a.likeCount = a.likeCount + 1 WHERE a.id = :targetId")
    void incrementLikeCount(Integer targetId);

    // 减少喜欢数
    @Modifying
    @Query("UPDATE Artist a SET a.likeCount = a.likeCount - 1 WHERE a.id = :targetId")
    void decrementLikeCount(Integer targetId);
}
