package com.website.authentication.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AttemptLoginVo {

    private Boolean isLocked;
    private Timestamp lockedUntil;
    private Integer attemptCount;
}
