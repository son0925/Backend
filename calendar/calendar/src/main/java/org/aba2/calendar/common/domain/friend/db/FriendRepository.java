package org.aba2.calendar.common.domain.friend.db;

import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity,String> {

    List<FriendEntity> findAllByFromUserIdAndStatus(String userId, FriendStatus status);

    List<FriendEntity> findAllByToUserIdAndStatus(String userId, FriendStatus status);

}
