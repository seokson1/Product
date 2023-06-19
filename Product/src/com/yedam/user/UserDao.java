package com.yedam.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yedam.product.Dao;

public class UserDao {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs!= null) {
				rs.close();
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 상품 테이블에서 로그인 하기 위해서 만듬 메소드
	// id, pw 정보 받아 쿼리문으로 성공, 실패 여부 판단
	public String userLoginCheck(String id, String pw) {
		conn = Dao.getConnect();
		sql = "select * from tbl_user where user_id = ? and user_pw = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return null;
	}
	// 회원 가입 자료 DB에 저장
	public boolean uJoin(UserVO userVo) {
		conn = Dao.getConnect();
		sql = "insert into tbl_user ( user_id, user_pw, user_name,"
				+ " user_birth, user_phone, user_addr)"
				+ " values( ?,?,?,?,?,?) ";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userVo.getUserId());
			psmt.setString(2, userVo.getUserPw());
			psmt.setString(3, userVo.getBuyName());
			psmt.setString(4, userVo.getUserBirth());
			psmt.setString(5, userVo.getUserPhone());
			psmt.setString(6, userVo.getUserAddr());
			
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	// DB에서 자료 받아 콘솔 출력
	public UserVO uSearch(String id) {
		UserVO userVo = new UserVO();
		conn = Dao.getConnect();
		sql = "select * from tbl_user where user_id = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				userVo.setUserId(rs.getString("user_id"));
				userVo.setUserBirth(rs.getString("user_birth"));
				userVo.setUserPhone(rs.getString("user_phone"));
				userVo.setUserAddr(rs.getString("user_addr"));
				return userVo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return null; 
	}
	// DB 회원 데이터 삭제
	public boolean uWithdrawal(String id) {
		
		conn = Dao.getConnect();
		sql = "delete from tbl_user where user_id = ? ";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return false;
	}


}
