package com.yedam.product;

import java.util.List;
import java.util.Scanner;

import com.yedam.manager.ManagerDao;
import com.yedam.user.UserDao;
import com.yedam.user.UserVO;

public class ProductProc {
	ProductDao pdao = new ProductDao();
	Scanner scn = new Scanner(System.in);
	int selectNumber = 0;
	ManagerDao mdao = new ManagerDao();
	UserDao udao = new UserDao();
	UserVO user = null;
	String user_id = null;
	
	
	// 관리자 로그인 하기 위한 아이디, 비밀번호 값 입력 -> ManagerDao 자료 넘김
	public void managerLoginCheck() {
		boolean run = true;
		while (run) {
			System.out.print("관리자 아이디> ");
			String manager_id = scn.nextLine();
			System.out.print("관리자 비밀번호 > ");
			String manager_pw = scn.nextLine();

			if (mdao.mgLoginCheck(manager_id, manager_pw)) {
				System.out.println("관리자 로그인 성공.");
				run = false;
			} else {
				System.out.println("관리자 로그인 실패 | 다시 입력해주세요.");
			}
		}
	}
	// 사용자 로그인 하기 위한 아이디, 비밀번호 값 입력 -> UserDao 자료 넘김
	public UserVO userLoginCheck() {
		boolean run = true;
		String result = "";
		while (run) {
			System.out.print("사용자 아이디> ");
			user_id = scn.nextLine();
			System.out.print("사용자 비밀번호> ");
			String user_pw = scn.nextLine();
			result = udao.userLoginCheck(user_id, user_pw);
			if (result != null) {
				System.out.println("회원 로그인 성공");
				run = false;
				return udao.uSearch(result);
			} else {
				System.out.println("회원 로그인 실패 | 다시 입력해주세요.");
			}

		}
		return null;
	}
	// 상품 등록을 위한 자료 입력
	public void pRegistation() {

		System.out.print("상품명> ");
		String proName = scn.nextLine();
		System.out.print("가격> ");
		int proPrice = scn.nextInt();
		scn.nextLine();
		System.out.print("판매자> ");
		String proSeller = scn.nextLine();
		System.out.print("제조일자> ");
		String proMakdate = scn.nextLine();
		System.out.print("제조회사> ");
		String makeId = scn.nextLine();
		System.out.print("재고> ");
		int proInventory = scn.nextInt();
		scn.nextLine();
		System.out.print("기타정보> ");
		String proInfo = scn.nextLine();

		ProductVO provo = new ProductVO();
		provo.setProName(proName);
		provo.setProPrice(proPrice);
		provo.setProSeller(proSeller);
		provo.setProMakdate(proMakdate);
		provo.setMakeId(makeId);
		provo.setProInventory(proInventory);
		provo.setProInfo(proInfo);

		if (pdao.registration(provo)) {
			System.out.println("정상적으로 상품등록 되었습니다.");
		} else {
			System.out.println("상품 등록에 실패했습니다.");
		}

	}
	// 등록된 상품 자료 조회 실행
	public void pList() {
		List<ProductVO> list = pdao.list();
		if (list.size() == 0) {
			System.out.println("상품 정보가 없습니다.");
			return;
		}
		for (ProductVO arr : list) {
			System.out.println(arr.detailInfo());
		}
	}
	// 특정 등록된 상품 자료 조회 실행
	public ProductVO pSearch() {
		System.out.print("상품번호> ");
		int no = scn.nextInt();
		scn.nextLine();
		ProductVO result = pdao.search(no);
		if (result != null) {
			System.out.println(result.detailInfo());
			return result;
		} else {
			System.out.println("조회된 결과가 없습니다.");
			return null;
		}
	}
	// 구매 후 구매확인 진행 -> 구매목록 메서드 동시에 진행됨.
	public UserVO buyResult() {
		user = pdao.pBuyResult(user_id);
		if (user != null) {
			System.out.println(user.buyInfo());
			return user;
		} else {
			System.out.println("구매내역이 없습니다.");
			return null;
		}
	}
	// 구매목록 출력 메소드 실행
	public void buyList() {
		
	List<UserVO> list= pdao.resultList();
	if(list.size() < 0) {
		System.out.println("구매목록이 없습니다.");
	} 
	for(UserVO user : list) {
		System.out.println(user.buyListInfo());
	}
		
	}
	// 입고, 출고 메소드 실행
	public int pInout(int number) {
		ProductVO provo = new ProductVO();
		System.out.println("1. 출고 | 2.입고");
		selectNumber = scn.nextInt();
		scn.nextLine();
		if (selectNumber == 1) {
			System.out.print("출고할 상품 번호>");
			int no = scn.nextInt();
			scn.nextLine();
			System.out.println("출고> ");
			number = scn.nextInt();
			scn.nextLine();
			if (pdao.rsInventory(no) - number < 0) {
				System.out.println("재고량이 부족합니다.");
				return -1;
			}
			if (pdao.productout(no, number)) {
				System.out.println("출고 되었습니다.");
			} else {
				System.out.println("출고 처리 실패 되었습니다.");
			}
		} else if (selectNumber == 2) {
			System.out.print("입고할 상품 번호>");
			int no3 = scn.nextInt();
			scn.nextLine();
			System.out.println("입고> ");
			int no4 = scn.nextInt();
			scn.nextLine();
			if (pdao.productin(no3, no4)) {
				System.out.println("입고 처리 되었습니다.");
			} else {
				System.out.println("입고 처리 실패 되었습니다.");
			}
		}
		return number;

	}
	// 재고관리 실행
	public void pInventory() {
		System.out.print("재고 확인할 상품번호> ");
		int no = scn.nextInt();
		scn.nextLine();
		if (pdao.inventory(no).inventoryInfo() != null) {
			System.out.println(pdao.inventory(no).inventoryInfo());
		} else {
			System.out.println("조회할 상품 재고가 없습니다.");
		}
	}
	// 상품 삭제 실행
	public void pDelete() {
		System.out.print("삭제할 상품번호> ");
		int no = scn.nextInt();
		scn.nextLine();

		if (pdao.delete(no)) {
			System.out.println("상품이 삭제 되었습니다.");
		} else {
			System.out.println("삭제할 상품이 없습니다.");
		}
	}
	// 상품 정보 수정
	public void pModify() {
		System.out.print("수정할 상품번호> ");
		int no = scn.nextInt();
		scn.nextLine();
		System.out.print("수정할 상품명> ");
		String proName = scn.nextLine();
		System.out.print("수정할 가격> ");
		int proPrice = scn.nextInt();
		scn.nextLine();
		System.out.print("수정할 판매자> ");
		String proSeller = scn.nextLine();
		System.out.print("수정할 제조회사> ");
		String makeId = scn.nextLine();
		System.out.print("수정할 재고> ");
		int proInventory = scn.nextInt();
		scn.nextLine();
		System.out.print("수정할 기타정보> ");
		String proInfo = scn.nextLine();

		ProductVO provo = new ProductVO();
		provo.setProNo(no);
		provo.setProName(proName);
		provo.setProPrice(proPrice);
		provo.setProSeller(proSeller);
		provo.setMakeId(makeId);
		provo.setProInventory(proInventory);
		provo.setProInfo(proInfo);

		if (pdao.modify(provo)) {
			System.out.println("상품 정보가 수정되었습니다.");
		} else {
			System.out.println("상품 정보 수정에 실패했습니다.");
		}
	}
	// 종료
	public void exit() throws ExitException {
		throw new ExitException("프로그램을 종료합니다.");
	}

}
