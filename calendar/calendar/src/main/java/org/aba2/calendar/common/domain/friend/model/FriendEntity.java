package org.aba2.calendar.common.domain.friend.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.friend.model.enums.FriendStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendEntity {

    private String toUserId;

    private String fromUserId;

//    @Enumerated(EnumType.STRING)
    private FriendStatus status;

}
