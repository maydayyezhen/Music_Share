package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Playlist;
import org.javaclimb.springbootmusic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {


    List<Playlist> findByCreator(User creator);

    List<Playlist> findByCreatorId(Integer creatorId);

    Page<Playlist> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Playlist> findByOrderByLikeCountDesc(Pageable pageable);


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Playlist p JOIN p.songs s " +
            "WHERE p.id = :playlistId AND s.id = :songId")
    boolean existsSongInPlaylist(@Param("playlistId") Integer playlistId,
                                 @Param("songId") Integer songId);

    @Query("SELECT p FROM Playlist p JOIN p.songs s WHERE s.id = :songId")
    List<Playlist> findBySongId(@Param("songId") Integer songId);
}
