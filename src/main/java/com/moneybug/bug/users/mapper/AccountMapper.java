package com.moneybug.bug.users.mapper;

import com.moneybug.bug.users.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AccountMapper {

    /**
     *
     * @Options
     * useGeneratedKeys : true 설정시 MyBatis가 데이터베이스 자동으로 생성 키 값을 사용할 수 있도록 한다. 'AUTO_INCREMENT' 키 사용할 떄 유용
     * keyProperty : MyBatis가 자동 생성된 키 값을 Java 객체의 특징 필드에 매핑할 수 있도록 한다.
     */
    @Insert("INSERT INTO tb_member(mem_no, name, password, roles) VALUES(#{memNo},#{username}, #{password}, #{roles})")
    @Options(useGeneratedKeys = true, keyProperty = "memNo")
    void insert(Account account);
}
