package com.moneybug.bug.myPage.vo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CouponVO {
	// 쿠폰 컬럼
	private int couponNo;
	private int memNo;
	private String couponCd;
	private String content;
	
	/**
	 *  @DateTimeFormat은 @RequestBody나 @ResponseBody에서는 동작하지 않는다. 
	 *  왜냐하면 @RequestBody와 @ResponseBody는 Jackson 라이브러리가 사용되기 때문 (*Jackson은 Java에서 제공하는 Json 파싱 라이브러리를 칭함)
	 *  @DateTimeFormat은 Spring 어노테이션이고, 
	 *  Spring은 Jackson에 의존하지만 Jackson은 Spring에 의존하지 않기 때문에
	 *  Jackson 라이브러리에서 사용하는 어노테이션에서는 포맷이 작동하지 않음
	 */
	
	@DateTimeFormat(pattern="yyyy-MM-dd") // @RequestParam, @ModelAttribute 사용 시 필요함 
	@JsonFormat(pattern="yyyy-MM-dd") // @RequestBody, @ResponseBody 사용 시 필요함 
	private Date regDate;
	private String expireDate;
	private boolean useYn;
	
	// 쿠폰 코드 컬럼
	private int period;
	
	// 쿼리에서 필요한 추가 컬럼
	private int couponCount;
	
}
