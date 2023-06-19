package com.yedam.product;

public class ExitException  extends Exception{
	private String msg;
	// 예외처리
	public  ExitException(String msg) {
		this.msg = msg;
	}
	public void getMsg() {
		System.out.println(msg);
	}
}
