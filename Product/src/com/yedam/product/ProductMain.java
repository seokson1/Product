package com.yedam.product;

import java.util.Scanner;

import com.yedam.user.UserVO;

public class ProductMain {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int selectNo = 0;
		boolean run = true;
		String loginCheck = "";
		UserVO user = null;
		ProductProc dao = new ProductProc();
		ProductDao pdao = new ProductDao();

		// 로그인 선택(관리재 or 사용자)
		while (run) {
			System.out.println("로그인 선택");
			System.out.println("1.관리자 | 2.사용자");
			selectNo = scn.nextInt();
			scn.nextLine();
			// 사용자로 할 경우 loginCheck = user , 관리자는 manager로 값을 받아 equals로 구분
			switch (selectNo) {
			case LoginSelect.MANAGER:
				dao.managerLoginCheck();
				loginCheck = "manager";
				break;

			case LoginSelect.USER:
				user = dao.userLoginCheck();
				loginCheck = "user";
				break;
			}
			run = false;
		}
		// 관리자 계정 화면
		if (loginCheck.equals("manager")) {
			while (true) {
				try {
					System.out.println("1.상품등록 | 2.상품목록 | 3. 상품 출고/입고 | 4. 재고관리 | 5. 상품삭제 | 6.상품수정 | 7. 종료");
					System.out.print("선택> ");
					// 관리자 메뉴 선택
					selectNo = scn.nextInt();
					scn.nextLine();
					switch (selectNo) {
					case SelectNo.REGISTRATION: // 상품등록
						dao.pRegistation();
						break;
					case SelectNo.LIST: // 상품목록
						dao.pList();
						break;
					case SelectNo.INOUT: // 출고, 입고
						dao.pInout(0);
						break;
					case SelectNo.INVENTORY: // 재고관리
						dao.pInventory();
						break;
					case SelectNo.DELETE: // 상품삭제
						dao.pDelete();
						break;
					case SelectNo.MODIFY: //상품 수정
						dao.pModify();
						break; 
					case SelectNo.EXIT: // 종료
						dao.exit();
					}

				} catch (ExitException e) {
					e.getMsg();
					break;
				}

			}
			System.out.println("end of prog.");
			scn.close();
			// 사용자 계정 화면
		} else if (loginCheck.equals("user")) {
			while (true) {
				try {
					System.out.println("1.상품목록 | 2.상품조회 및 구매 |3. 구매확인 | 4.구매목록 | 5. 종료");
					System.out.print("선택> ");
					// 관리자 메뉴 선택
					selectNo = scn.nextInt();
					scn.nextLine();
					switch (selectNo) {
					case UserSelectNo.LIST: // 상품목록
						dao.pList();
						break;
					case UserSelectNo.SEARCH_BUY: // 상품조회 및 구매
						ProductVO provo = dao.pSearch();
						if (provo != null) {
							System.out.print("상품을 구매하시겠습니까?");
							String choice = scn.nextLine();
							if (choice.equals("y")) {
								System.out.print("상품수량 선택>");
								int number = scn.nextInt();
								scn.nextLine();
								int result = dao.pInout(number);
								pdao.buy(provo, number, user);
							} else {
								return;
							}
						}
						break;
					case UserSelectNo.BUYRESULT: // 구매확인
						dao.buyResult();
						break;
					case UserSelectNo.BUYLIST: // 구매목록
						dao.buyList();
						break;
					case UserSelectNo.EXIT:
						dao.exit();
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

}
