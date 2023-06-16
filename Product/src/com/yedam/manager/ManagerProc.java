package com.yedam.manager;

import java.util.List;
import java.util.Scanner;

import com.yedam.product.ExitException;

public class ManagerProc {

	ManagerDao dao = new ManagerDao();
	Scanner scn = new Scanner(System.in);

	public void managerJoin() {
		System.out.print("아이디> ");
		String managerId = scn.nextLine();
		System.out.print("비밀번호> ");
		String managerPw = scn.nextLine();
		System.out.print("이름> ");
		String managerName = scn.nextLine();
		System.out.print("생년원일> ");
		String managerBirth = scn.nextLine();
		System.out.print("전화번호> ");
		String managerPhone = scn.nextLine();
		System.out.print("주소> ");
		String managerAddr = scn.nextLine();
		System.out.print("이메일> ");
		String managerEmail = scn.nextLine();

		ManagerVO mgvo = new ManagerVO();
		mgvo.setManagerId(managerId);
		mgvo.setManagerPw(managerPw);
		mgvo.setManagerName(managerName);
		mgvo.setManagerBirth(managerBirth);
		mgvo.setManagerPhone(managerPhone);
		mgvo.setManagerAddr(managerAddr);
		mgvo.setManagerEmail(managerEmail);

		if (dao.mgJoin(mgvo)) {
			System.out.println("회원 가입 되었습니다.");
		} else {
			System.out.println("회원 가입 실패 되었습니다.");
		}

	}

	public void managerSearch() {
		System.out.print("조회할 아이디> ");
		String managerId = scn.nextLine();

		if (dao.mgSearch(managerId) != null) {
			System.out.println(dao.mgSearch(managerId).detailInfo());
		} else {
			System.out.println("조회할 아이디 없습니다.");
		}
	}

	public void managerModify() {
		System.out.print("수정할 아이디 검색> ");
		String managerId = scn.nextLine();
		System.out.print("수정할 비밀번호> ");
		String managerPw = scn.nextLine();
		System.out.print("수정할 이름> ");
		String managerName = scn.nextLine();
		System.out.print("수정할 전화번호> ");
		String managerPhone = scn.nextLine();
		System.out.print("수정할 주소> ");
		String managerAddr = scn.nextLine();
		System.out.print("수정할 이메일> ");
		String managerEmail = scn.nextLine();

		ManagerVO mavo = new ManagerVO();
		mavo.setManagerId(managerId);
		mavo.setManagerPw(managerPw);
		mavo.setManagerName(managerName);
		mavo.setManagerPhone(managerPhone);
		mavo.setManagerAddr(managerAddr);
		mavo.setManagerEmail(managerEmail);

		if (dao.mgModify(mavo)) {
			System.out.println("수정되었습니다.");
		} else {
			System.out.println("수정에 실패했습니다.");
		}

	}

	public void managerDelete() {
		System.out.print("삭제할 아이디 검색>");
		String managerId = scn.nextLine();

		if (dao.mgDelete(managerId)) {
			System.out.println("삭제가 완료 되었습니다.");
		} else {
			System.out.println("삭제 실패했습니다.");
		}
	}

	public void managerList() {
		List<ManagerVO> list = dao.mgList();

		if (list.size() == 0) {
			System.out.println("조회할 목록이 없습니다.");
		}
		for (ManagerVO mavo : list) {
			System.out.println(mavo.info());
		}
	}

	public void exit() throws ExitException {
		throw new ExitException("프로그램을 종료합니다.");
	}
}
