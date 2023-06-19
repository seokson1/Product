package com.yedam.user;

import java.util.Scanner;

import com.yedam.product.ExitException;

public class UserMain {
	public static void main(String[] args) {
		UserProc pdao = new UserProc();
		Scanner scn = new Scanner(System.in);
		int selectNo = 0;

		while (true) {
			try {
				System.out.println("1.회원가입 | 2.회원조회 | 3.탈퇴  | 4.종료");
				System.out.print("선택> ");
				selectNo = scn.nextInt();
				scn.nextLine();
				switch (selectNo) {
				case SelectNo.USERJOIN: // 회원가입
					pdao.userJoin();
					break;
				case SelectNo.USERSEARCH: // 회원조회
					pdao.userSearch();
					break;
				case SelectNo.WITHDRAWAL: // 탈퇴
					pdao.withdrawal();
					break;
				case SelectNo.EXIT: // 종료
					pdao.exit();
					break;
				}
			} catch (ExitException e) {
				e.getMsg();
				break;
			}
		}
	}
}
