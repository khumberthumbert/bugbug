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
 * @apiNote 1. 보유 쿠폰 select (+ 기간별) 2. 쿠폰 코드 insert
 * @throws Exception
 */

@RestController
public class CouponController {
	
	@Autowired
	CouponService couponService;

	// 보유쿠폰조회
	@GetMapping("/couponList")
	public Map<String, Object> selectCouponList(@RequestParam int memNo, @RequestParam(required = false) Boolean expireYn
				, @RequestParam(required = false) String period, @RequestParam(required = false, defaultValue = "0") Integer periodNum) {
		
		Map<String, Object> map = new HashMap<>();
		
		if(period != null || !period.equals("")) {
			if(period.equals("aDay") || period.equals("aDay")) {
				periodNum = 1;
			} else if(period.equals("aWeek")) {
				periodNum = 7;
			} else {
				periodNum = 3;
			}
		}
		
		List<CouponVO> list = couponService.selectCouponList(memNo, expireYn, period, periodNum);

		// 세션에서 memNo 받아올 수 있을 때 memNo을 사용가능한 보유쿠폰 갯수 서비스 실행해서 map에 넣을 예정
		
		map.put("couponList", list);
		
		return map;
	}
	
	// 쿠폰등록
	@PostMapping("/insertCouponCode")
	public int insertCouponCode(CouponVO couponVO) {
		return couponService.insertCouponCode(couponVO);
	}
}
