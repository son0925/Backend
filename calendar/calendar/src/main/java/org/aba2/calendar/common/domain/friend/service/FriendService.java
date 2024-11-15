package org.aba2.calendar.common.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.domain.friend.db.FriendRepository;
import org.aba2.calendar.common.domain.friend.model.FriendEntity;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    // 친구 요청 보내기
    public FriendEntity send(String fromId, String toId) {
        var send = FriendEntity.builder()
                .fromUserId(fromId)
                .toUserId(toId)
                .status(FriendStatus.WAIT)
                .build()
                ;

        return friendRepository.save(send);
    }


    // 친구 요청 상태 변화 (승락, 거절, 삭제, 차단)
    public FriendEntity stateChange(String fromId, String toId, FriendStatus status) {
        var send = FriendEntity.builder()
                .fromUserId(fromId)
                .toUserId(toId)
                .status(status)
                .build()
                ;

        return friendRepository.save(send);
    }

    // 친구 목록 불러오기
    public List<FriendEntity> getFriendList(String userId) {
        return null;
    }

    // 친구 초대 요청 목록 불러오기

}
