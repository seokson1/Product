package com.yedam.product;

import java.util.Scanner;

public class ProductMain {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int selectNo = 0;
		boolean run = true;
		String loginCheck = "";

		ProductProc dao = new ProductProc();
		ProductDao pdao = new ProductDao();
		
		while (run) {
			System.out.println("로그인 선택");
			System.out.println("1.관리자 | 2.사용자");
			selectNo = scn.nextInt();
			scn.nextLine();
			switch (selectNo) {
			case LoginSelect.MANAGER:
				dao.managerLoginCheck();
				loginCheck = "manager";
				break;

			case LoginSelect.USER:
				dao.UserLoginCheck();
				loginCheck = "user";
				break;
			}
			run = false;
		}
		if (loginCheck.equals("manager")) {
			while (true) {
				try {
					System.out.println("1.상품등록 | 2.상품목록 | 3. 상품 출고/입고 | 4. 재고관리 | 5. 상품삭제 | 6.상품수정 | 7. 종료");
					System.out.print("선택> ");
					// 관리자 메뉴 선택
					selectNo = scn.nextInt();
					scn.nextLine();
					switch (selectNo) {
					case SelectNo.REGISTRATION:
						dao.pRegistation();
						break;
					case SelectNo.LIST:
						dao.pList();
						break;
					case SelectNo.INOUT:
						dao.pInout();
						break;
					case SelectNo.INVENTORY:
						dao.pInventory();
						break;
					case SelectNo.DELETE:
						dao.pDelete();
						break;
					case SelectNo.MODIFY:
						dao.pModify();
						break;
					case SelectNo.EXIT:
						dao.exit();
					}

				} catch (ExitException e) {
					e.getMsg();
					break;
				}

			}
			System.out.println("end of prog.");
			scn.close();

		} else if (loginCheck.equals("user")) {
			while (true) {
				try {
					System.out
							.println("1.상품목록 | 2.상품조회 및 구매 | 3. 종료");
					System.out.print("선택> ");
					// 관리자 메뉴 선택
					selectNo = scn.nextInt();
					scn.nextLine();
					switch (selectNo) {
					case UserSelectNo.LIST:
						dao.pList();
						break;
					case UserSelectNo.SEARCH:
						ProductVO prod = dao.pSearch();
						if(prod!=null) {
							System.out.print("상품을 구매하시겠습니까?");
							String choice = scn.nextLine();
							if(choice.equals("y")) {
								System.out.print("상품수량 선택>");
								int number = scn.nextInt(); scn.nextLine();
								pdao.buy(number);
							} else {
								return;
							}
						}
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
