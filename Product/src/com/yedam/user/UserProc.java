package com.yedam.user;

import java.util.Scanner;

import com.yedam.product.ExitException;

public class UserProc {
	
	UserDao dao = new UserDao();
	Scanner scn = new Scanner(System.in);
	
	// 회원가입을 위한 자료 입력
	public void userJoin( ) {
		
		System.out.print("아이디> ");
		String userId = scn.nextLine();
		System.out.print("비밀번호> ");
		String userPw = scn.nextLine();
		System.out.print("이름> ");
		String userName = scn.nextLine();
		System.out.print("생년월일> ");
		String userBirth= scn.nextLine();
		System.out.print("전화번호> ");
		String userPhone= scn.nextLine();
		System.out.print("주소> ");
		String userAddr= scn.nextLine();
		
		UserVO userVo = new UserVO();
		userVo.setUserId(userId);
		userVo.setUserPw(userPw);
		userVo.setBuyName(userName);
		userVo.setUserBirth(userBirth);
		userVo.setUserPhone(userPhone);
		userVo.setUserAddr(userAddr);
		
		if(dao.uJoin(userVo)) {
			System.out.println("회원가입 성공");
		} else {
			System.out.println("회원가입 실패");
		}
		
		
	}
	// 회원 정보 조회를 위해서 아이디 입력
	public void userSearch( ) {
		System.out.print("조회할 아이디> ");
		String userId = scn.nextLine();
		UserVO result = dao.uSearch(userId);
		if(result != null) {
			System.out.println(result.detailInfo());
			return;
		} else {
			System.out.println("조회한 아이디가 없습니다.");
			return;
		}
		
	}
	// 회원 탈퇴를 위해서 아이디 입력
	public void withdrawal() {
		System.out.print("탈퇴할 아이디> ");
		String userId = scn.nextLine();
		
		if(dao.uWithdrawal(userId)) {
			System.out.println("탈퇴 성공");
			return;
		} else {
			System.out.println("탈퇴  실패");
			return;
		}
	}
	// 시스템 종료
	public void exit() throws ExitException {
		throw new ExitException("프로그램을 종료합니다.");
	}
	
}
