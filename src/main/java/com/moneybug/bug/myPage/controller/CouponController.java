package com.moneybug.bug.myPage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneybug.bug.myPage.service.CouponService;
import com.moneybug.bug.myPage.vo.CouponVO;

/**
 * @author hyson
 * @since 2024.07.15
 * @apiNote 1. 보유 쿠폰 select (+ 기간별, 보유가능쿠폰갯수) 2. 쿠폰 코드 insert 3. 쿠폰사용 update
 * @throws Exception
 */

@RestController
public class CouponController {
	
	@Autowired
	CouponService couponService;

	// 보유쿠폰조회
	@GetMapping("/couponList")
	public Map<String, Object> selectCouponList(@RequestParam int memNo
											  , @RequestParam(required = false) Boolean expireYn
											  , @RequestParam(required = false) String period
											  , @RequestParam(required = false, defaultValue = "0") Integer periodNum) {

		// 세션에서 memNo 받아올 수 있을 때 memNo 받아오는 코드 필요
		
		Map<String, Object> map = new HashMap<>();
		
		if(period != null) {
			if(period.equals("aDay") || period.equals("aDay")) {
				periodNum = 1;
			} else if(period.equals("aWeek")) {
				periodNum = 7;
			} else {
				periodNum = 3;
			}
		}
		
		List<CouponVO> list = couponService.selectCouponList(memNo, expireYn, period, periodNum);
		int count = couponService.selectCountAvailableCoupon(memNo);

		map.put("couponList", (list != null) ? list : List.of());
		map.put("numOfCoupon", count);
		
		return map;
	}
	
	// 쿠폰등록
	@PostMapping("/insertCouponCode")
	public int insertCouponCode(CouponVO couponVO) {
		
		// 결과값
		int result = 0;
		
		CouponVO couponCdInfo = couponService.selectCouponCode(couponVO);
		
		if(couponCdInfo != null) {
			// memNo을 추가로 넣어준다. 세션 완성되면 세션에서 memNo가지고 와서 넣기
			int memNo = couponVO.getMemNo();
			couponCdInfo.setMemNo(memNo);
			result = couponService.insertCouponCode(couponCdInfo);
		}
		
		return result;
	}
	
	// 쿠폰 사용할 경우 (use_yn 업데이트)
	@PostMapping("/useCoupon")
	public int useCouponNo(CouponVO couponVo) {
		
		// 세션에서 memNo가지고 와서 넣기
		return couponService.updateCouponNo(couponVo);
	}
}
