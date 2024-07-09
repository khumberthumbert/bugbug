package com.moneybug.bug.users.entity;

import lombok.Data;

@Data
public class Account {

    private int memNo;
    private String id;
    private String email;
    private String emailValid;
    private String contact;
    private String addr;
    private String post;
    private String addrDetail;
    private boolean alert_yn;
    private String name;
    private String password;
    private String roles;
}
