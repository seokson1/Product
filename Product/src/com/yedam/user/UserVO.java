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
	private int listNo; // 구매 내역 번호
	private String userId; // 사용자 계정
	private String userPw; // 사용자 비밀번호
	private String userName; // 사용자 이름
	private String userBirth; // 사용자 생년월일
	private String userPhone; // 사용자 전화번호
	private String userAddr; // 사용자 주소
	private String buyName; // 상품명
	private int buyPrice; // 상품가격
	private String seller; // 판매자
	private int quantity; // 주문수량
	private int totalPrice; // 총 주문 금액
	
	public String detailInfo() {
		return "아이디: " + userId + "| 생년월일: " + userBirth
				 + "| 연락처: " + userPhone + "| 주소: " + userAddr;
	}
	public String buyInfo() {
		return  "수령인 " + userName  + "| 연락처: " + userPhone + "| 배송지: " + 
				userAddr + "\n" + "상품명: " + buyName + "| 상품가격: " + buyPrice  + 
				"| 주문수량: " + quantity + "| 총금액: "  + totalPrice;
	}
	public String buyListInfo() {
		return "번호: " + listNo + "| 구매자: " + userName + "| 연락처: " + userPhone + "| 주소:" + userAddr +
			"| 상품명:" + buyName	+ "| 상품가격: "  + buyPrice + "| 주문수량:" + quantity + "| 총금액:" + totalPrice;
				}
	
}
