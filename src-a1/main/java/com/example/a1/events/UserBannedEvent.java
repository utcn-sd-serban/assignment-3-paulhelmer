package com.example.a1.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBannedEvent extends BaseEvent {
    private int userId;

    public UserBannedEvent(int userId) {
        super(EventType.USER_BANNED);
        this.userId = userId;
    }
}