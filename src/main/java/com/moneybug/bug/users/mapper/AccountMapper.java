package com.moneybug.bug.users.mapper;

import com.moneybug.bug.users.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * useGeneratedKeys : true 설정시 MyBatis가 데이터베이스 자동으로 생성 키 값을 사용할 수 있도록 한다. 'AUTO_INCREMENT' 키 사용할 떄 유용
 * keyProperty : MyBatis가 자동 생성된 키 값을 Java 객체의 특징 필드에 매핑할 수 있도록 한다.
 */

@Mapper
public interface AccountMapper {

    //회원 등록
    void insertAccount(Account account);

    //회원 정보 가져오기 (이메일로 가져올지 username으로 가져올지 고민 중)
    //Account getAccountByEmail(String email);

    //회원 아이디 가져오기
    Account getAccountUsername(String username);

    //memNo 조회
    Account getAccountByMemNo(Long memNo);

    //회원 탈퇴
    void deleteAccount(Long memNo);

    // Account 테이블 가져오기
    List<Account> getAcoountList();
}
