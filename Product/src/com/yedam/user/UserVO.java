package com.yedam.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserVO {
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private String userBirth;
	private String userPhone;
	private String userAddr;
	private String basket;
	private int basketIn;
	private int basketOut;
	private String buyList;
	private String buyName;
	private String buyPrice;
	private String seller;
	private int buyin;
	private int totalPrice;
	
	public String detailInfo() {
		return " 회원번호:" + userNo + " 아이디: " + userId + " 생년월일: " + userBirth
				 + " 연락처: " + userPhone + " 주소: " + userAddr;
	}
	
}
