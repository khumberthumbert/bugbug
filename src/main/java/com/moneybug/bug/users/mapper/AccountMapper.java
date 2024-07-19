package com.moneybug.bug.users.mapper;

import com.moneybug.bug.users.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * useGeneratedKeys : true 설정시 MyBatis가 데이터베이스 자동으로 생성 키 값을 사용할 수 있도록 한다. 'AUTO_INCREMENT' 키 사용할 떄 유용
 * keyProperty : MyBatis가 자동 생성된 키 값을 Java 객체의 특징 필드에 매핑할 수 있도록 한다.
 */

@Mapper
public interface AccountMapper {

    //회원 등록
    @Insert("INSERT INTO tb_member (mem_no, name, username, password, email, contact, roles) " +
            "VALUES (#{memNo}, #{name}, #{username}, #{password}, #{email}, #{contact} ,#{roles})")
    @Options(useGeneratedKeys = true, keyProperty = "memNo")
    void accountSave(Account account);

    //회원 아이디 가져오기
    @Select("SELECT COUNT(*) > 0 FROM tb_member WHERE username = #{username}")
    boolean existByUsernames(String username);

    //회원 탈퇴
    @Delete("DELETE FROM tb_member WHERE mem_no = #{memNo}")
    void deleteAccount(Long memNo);

    //회원 조회
    Account findByUsername(String username);

}
