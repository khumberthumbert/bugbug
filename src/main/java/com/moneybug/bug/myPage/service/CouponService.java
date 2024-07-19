package com.moneybug.bug.myPage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneybug.bug.myPage.mapper.CouponMapper;
import com.moneybug.bug.myPage.vo.CouponVO;

@Service
public class CouponService {

    private final CouponMapper couponMapper;

    @Autowired
    public CouponService(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    // 보유쿠폰조회
    public List<CouponVO> selectCouponList(int memNo, Boolean expireYn, String period, int periodNum) {
        return couponMapper.selectCouponList(memNo, expireYn, period, periodNum);
    }

    // 유저별 사용 가능한 쿠폰의 갯수
    public int selectCountAvailableCoupon(int memNo) {
    	Integer count = couponMapper.selectCountAvailableCoupon(memNo); // 조회결과가 null 일 경우 체크하기 위함 = Integer
    	return (count != null)? count : 0; // null 체크 후 기본값 반환
    }

    // 쿠폰코드조회
    public CouponVO selectCouponCode(CouponVO couponVO) {
    	return couponMapper.selectCouponCode(couponVO);
    }
    
    // 쿠폰등록
    public int insertCouponCode(CouponVO couponVO) {
        return couponMapper.insertCouponCode(couponVO);
    }
    
    // 쿠폰사용
    public int updateCouponNo(CouponVO couponVO) {
    	return couponMapper.updateCouponNo(couponVO);
    }
    
}
