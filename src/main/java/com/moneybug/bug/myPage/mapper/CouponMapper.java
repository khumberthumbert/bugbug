package com.moneybug.bug.myPage.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.moneybug.bug.myPage.vo.CouponVO;

@Mapper
public interface CouponMapper {

	// 보유쿠폰조회
	public List<CouponVO> selectCouponList(int memNo, Boolean expireYn, String period, int periodNum);
	// 유저별 사용 가능한 쿠폰의 갯수
	public Integer selectCountAvailableCoupon(int memNo);
	// 쿠폰코드조회
	public CouponVO selectCouponCode(CouponVO couponVO);
	// 쿠폰등록
	public int insertCouponCode(CouponVO couponVO);
	// 쿠폰사용
	public int updateCouponNo(CouponVO couponVO);
}
