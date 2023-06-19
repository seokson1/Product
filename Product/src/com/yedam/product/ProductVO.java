package com.yedam.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ProductVO {

	private int proNo; // 상품번호
	private String proName; // 상품명
	private int proPrice; // 상품가격
	private String proInfo; // 상품 기타정보
	private String proSeller; // 판매자
	private String proMakdate; // 제조일자
	private String makeId; // 제조회사
	private int proInventory; // 재고관리
	private int proIn; // 입고
	private int proOut; // 출고

	public String detailInfo() {

		String msg = " 상품번호: " + proNo + "| 상품명: " + proName + "| 상품가격: " + proPrice + "| 기타정보: " + proInfo + "| 판매자: "
				+ proSeller + "| 제조일자: " + proMakdate + "| 제조회사: " + makeId;
		return msg;
	}

	public String inventoryInfo() {
		String msg = "상품번호: " + proNo + " 상품명: " + proName +  "\n"  +"상품가격: "+  proPrice + " 재고관리: " + proInventory;
		
		return msg;
	}

}
