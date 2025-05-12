package org.javaclimb.springbootmusic.service;
import org.javaclimb.springbootmusic.model.UserLike;
import org.javaclimb.springbootmusic.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserLikeService {
    public final UserLikeRepository userLikeRepository;
    public final SongRepository songRepository;
    public final AlbumRepository albumRepository;
    public final ArtistRepository artistRepository;
    public final CommentRepository commentRepository;

    public UserLikeService(UserLikeRepository userLikeRepository, SongRepository songRepository, AlbumRepository albumRepository, ArtistRepository artistRepository, CommentRepository commentRepository) {
        this.userLikeRepository = userLikeRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.commentRepository = commentRepository;

    }

    @Transactional
    public UserLike createUserLike(UserLike userLikes) {
        UserLike savedLike = userLikeRepository.save(userLikes);
        switch (userLikes.getTargetType()) {
            case "song" -> songRepository.incrementLikeCount(userLikes.getTargetId());
            case "album"-> albumRepository.incrementLikeCount(userLikes.getTargetId());
            case "artist"-> artistRepository.incrementLikeCount(userLikes.getTargetId());
        }
        return savedLike;
    }

    @Transactional
    public void deleteByUserAndTarget(Integer userId, String targetType, Integer targetId) {
        userLikeRepository.deleteByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);

        switch (targetType) {
            case "song" -> songRepository.decrementLikeCount(targetId);
            case "album" -> albumRepository.decrementLikeCount(targetId);
            case "artist" -> artistRepository.decrementLikeCount(targetId);
        }
    }

    public List<UserLike> getUserLikesByUserId(Integer userId) {
        return userLikeRepository.findByUserId(userId);
    }
}
