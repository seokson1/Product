package com.yedam.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.product.Dao;

public class ManagerDao {

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
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean mgLoginCheck(String id, String pw) {
		conn = Dao.getConnect();
		sql = "select * from tbl_manager where manager_id =? and manager_pw =?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	public boolean mgJoin(ManagerVO mavo) {
		conn = Dao.getConnect();
		sql = "insert into tbl_manager (manager_no ,manager_id, manager_pw,"
				+ " manager_name, manager_birth, manager_phone," + " manager_addr, manager_email) "
				+ "values(manager_seq.nextval,?,?,?,?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, mavo.getManagerId());
			psmt.setString(2, mavo.getManagerPw());
			psmt.setString(3, mavo.getManagerName());
			psmt.setString(4, mavo.getManagerBirth());
			psmt.setString(5, mavo.getManagerPhone());
			psmt.setString(6, mavo.getManagerAddr());
			psmt.setString(7, mavo.getManagerEmail());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;

	}

	public ManagerVO mgSearch(String id) {
		conn = Dao.getConnect();
		sql = "select * from tbl_manager where manager_id = ?";
		ManagerVO mavo = new ManagerVO();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				mavo.setManagerNo(rs.getInt("manager_no"));
				mavo.setManagerId(rs.getString("manager_id"));
				mavo.setManagerPw(rs.getString("manager_pw"));
				mavo.setManagerName(rs.getString("manager_name"));
				mavo.setManagerBirth(rs.getString("manager_birth"));
				mavo.setManagerPhone(rs.getString("manager_phone"));
				mavo.setManagerAddr(rs.getString("manager_addr"));
				mavo.setManagerEmail(rs.getString("manager_email"));

				return mavo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return null;
	}

	public boolean mgModify(ManagerVO mavo) {
		conn = Dao.getConnect();
		sql = "update tbl_manager" + " set manager_pw = nvl(?,manager_pw),"
				+ " manager_name = nvl(?,manager_name), manager_phone = nvl(?,manager_phone),"
				+ " manager_addr = nvl(?,manager_addr), manager_email = nvl(?,manager_email) " + "where manager_id = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, mavo.getManagerPw());
			psmt.setString(2, mavo.getManagerName());
			psmt.setString(3, mavo.getManagerPhone());
			psmt.setString(4, mavo.getManagerAddr());
			psmt.setString(5, mavo.getManagerEmail());
			psmt.setString(6, mavo.getManagerId());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	public boolean mgDelete(String id) {
		conn = Dao.getConnect();
		sql = "delete from tbl_manager where manager_id = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return false;
	}

	public List<ManagerVO> mgList() {
		conn = Dao.getConnect();
		sql = "select * from tbl_manager";
		List<ManagerVO> list = new ArrayList<>();
		try {
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();
			while (rs.next()) {
				ManagerVO mavo = new ManagerVO();
				mavo.setManagerNo(rs.getInt("manager_no"));
				mavo.setManagerId(rs.getString("manager_id"));
				mavo.setManagerName(rs.getString("manager_name"));
				mavo.setManagerBirth(rs.getString("manager_birth"));
				mavo.setManagerPhone(rs.getString("manager_phone"));
				mavo.setManagerAddr(rs.getString("manager_addr"));
				mavo.setManagerEmail(rs.getString("manager_email"));
				list.add(mavo);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}
}
