package com.moneybug.bug.myPage.service;

import java.util.List;
import com.moneybug.bug.myPage.vo.CouponVO;

public interface CouponService {
	
	// 보유쿠폰조회
	public List<CouponVO> selectCouponList(int memNo, Boolean expireYn, String period, int periodNum);
	// 유저별 사용 가능한 쿠폰의 갯수
	public int selectCountAvailableCoupon(int memNo);
	// 쿠폰등록
	public int insertCouponCode(CouponVO couponVO);

}
