package com.moneybug.bug.users.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 이 클래스는 DTO 이자 entity 역할을 함.
 */
@Data
@Builder
public class AccountDto {

    private Long memNo;
    private String username;
    private String email;
    private String name;
    private String password;
    private String roles;

}
