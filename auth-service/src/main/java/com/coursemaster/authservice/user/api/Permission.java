package com.coursemaster.authservice.user.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADD_VIDEO("ADD_VIDEO"),
    DELETE_VIDEO("DELETE_VIDEO"),
    READ_VIDEO_LIST("READ_VIDEO_LIST"),
    GUEST("GUEST");

    @Getter
    private final String permission;
}
