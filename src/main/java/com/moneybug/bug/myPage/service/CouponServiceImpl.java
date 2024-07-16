package com.moneybug.bug.myPage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneybug.bug.myPage.mapper.CouponMapper;
import com.moneybug.bug.myPage.vo.CouponVO;

@Service
public class CouponServiceImpl implements CouponService{

	@Autowired
	CouponMapper couponMapper;
	
	@Override
	public List<CouponVO> selectCouponList(int memNo, Boolean expireYn, String period, int periodNum) {
		return couponMapper.selectCouponList(memNo, expireYn, period, periodNum);
	}

	@Override
	public int selectCountAvailableCoupon(int memNo) {
		return couponMapper.selectCountAvailableCoupon(memNo);
	}

	@Override
	public int insertCouponCode(CouponVO couponVO) {
		return couponMapper.insertCouponCode(couponVO);
	}

}
