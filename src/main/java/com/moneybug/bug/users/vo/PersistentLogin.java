package com.moneybug.bug.users.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistentLogin {

    private String username;
    private String series;
    private String token;
    private java.sql.Timestamp lastUsed;

}
