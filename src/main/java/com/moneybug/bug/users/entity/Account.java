package com.moneybug.bug.users.entity;

import lombok.Data;

@Data
public class Account {

    private Long memNo;
    private String username;
    private String name;
    private String password;
    private String email;
    private String emailValid;
    private String contact;
    private String addr;
    private String post;
    private String addrDetail;
    private boolean alertYn;
    private String attachNo;

    private boolean userAgreeYn;
    private boolean infoAgreeYn;
    private boolean snsAgreeYn;
    private boolean emailAgreeYn;
    private boolean workAlertYn;
    private boolean workConfAlertYn;
    private boolean hireAlertYn;
    private boolean applyAlertYn;
    private boolean userYn;

    private String roles;

}
