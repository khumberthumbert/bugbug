package com.moneybug.bug.users.entity;

import lombok.Data;

@Data
public class Account {

    private String memNo;
    private String username;
    private String password;
    private String roles;
}
