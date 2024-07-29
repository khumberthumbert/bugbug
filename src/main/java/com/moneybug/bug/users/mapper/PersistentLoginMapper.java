package com.moneybug.bug.users.mapper;

import com.moneybug.bug.users.vo.PersistentLogin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PersistentLoginMapper {

    @Insert("INSERT INTO persistent_logins (username, series, token, last_used) VALUES (#{username}, #{series}, #{token}, #{lastUsed})")
    void createNewToken(@Param("username") String username, @Param("series") String series, @Param("token") String token, @Param("lastUsed") java.sql.Timestamp lastUsed);

    @Update("UPDATE persistent_logins SET token = #{token}, last_used = #{lastUsed} WHERE series = #{series}")
    void updateToken(@Param("series") String series, @Param("token") String token, @Param("lastUsed") java.sql.Timestamp lastUsed);

    @Select("SELECT username, series, token, last_used FROM persistent_logins WHERE series = #{series}")
    PersistentLogin getTokenForSeries(@Param("series") String series);

    @Delete("DELETE FROM persistent_logins WHERE username = #{username}")
    void removeUserTokens(@Param("username") String username);

}
