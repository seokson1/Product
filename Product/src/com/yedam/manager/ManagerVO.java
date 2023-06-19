package com.yedam.manager;

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

public class ManagerVO {
	
	private int managerNo; // 관리자 번호
	private String managerId; // 관리자 아이디
	private String managerPw; // 관리자 비밀번호
	private String managerName; // 관리자 이름
	private String managerBirth; // 관리자 생년월일
	private String managerPhone;  // 관리자 전화번호
	private String managerAddr; // 관리자 주소
	private String managerEmail; // 관리자 메일
	
	public String detailInfo() {
		return "관리자번호: " + managerNo + "| 아이디: " + managerId + "| 비밀번호: " + managerPw + "| 이름: " + 
				managerName + "| 생년월일: " + managerBirth + "| 전화번호: " + 
				managerPhone + " 주소: " + managerAddr + " 이메일: " + managerEmail;
	}
	public String info() {
		return "관리자번호: " + managerNo + "| 아이디: " + managerId + "| 이름: " + 
				managerName + "| 생년월일: " + managerBirth + "| 전화번호: " + 
				managerPhone + "| 주소: " + managerAddr + "| 이메일: " + managerEmail;
	}
}
