package com.yedam.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

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
	private int quantity;
	private int totalPrice;
	
	public String detailInfo() {
		return " 회원번호:" + userNo + " 아이디: " + userId + " 생년월일: " + userBirth
				 + " 연락처: " + userPhone + " 주소: " + userAddr;
	}
	
}
