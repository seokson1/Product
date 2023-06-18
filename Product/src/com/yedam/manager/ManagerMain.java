package com.yedam.manager;

import java.util.Scanner;

import com.yedam.product.ExitException;

public class ManagerMain {
	public static void main(String[] args) {
		ManagerProc pdao = new ManagerProc();
		Scanner scn = new Scanner(System.in);
		
		while (true) {
			try {
				int selectNo = 0;
				System.out.println("1.관리자 가입 | 2.관리자 조회 | 3.관리자 수정 | 4.관리자 삭제 | 5.관리자 목록  |6.종료");
				System.out.print("선택> ");
				selectNo = scn.nextInt(); scn.nextLine();

				switch (selectNo) {
				case SelectNo.MEMBER: // 회원가입
					pdao.managerJoin();
					break;
				case SelectNo.SEARCH: // 회원조회
					pdao.managerSearch();
					break;
				case SelectNo.MODIFY: // 회원수정
					pdao.managerModify();
					break;
				case SelectNo.DELETE: // 회원삭제
					pdao.managerDelete();
					break;
				case SelectNo.LIST: // 회원목록
					pdao.managerList();
					break;
				case SelectNo.EXIT: // 종료
					pdao.exit();
				}
			} catch (ExitException e) {
				e.getMsg();
				break;
			}
		}
		System.out.println("end of prog.");
		scn.close();
	}
}
