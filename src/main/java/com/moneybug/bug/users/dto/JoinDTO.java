package com.moneybug.bug.users.dto;

import lombok.*;
import org.springframework.lang.NonNull;

/**
 * 이 클래스는 DTO 이자 entity 역할을 함.
 */
@Getter
@Setter
public class JoinDTO {

    private Long memNo;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private String contact;

    @NonNull
    private String roles;

}
