package org.javaclimb.springbootmusic.repository;
import org.javaclimb.springbootmusic.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike,Integer> {
    void deleteByUserIdAndTargetTypeAndTargetId(Integer userId, String targetType, Integer targetId);
    List<UserLike> findByUserId(Integer userId);
}
