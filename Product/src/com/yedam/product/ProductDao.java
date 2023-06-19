package com.yedam.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.user.UserDao;
import com.yedam.user.UserVO;

public class ProductDao {

	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;
	String sql;
	String sql1;

	private void close() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// DB에 상품 정보 등록 저장
	public boolean registration(ProductVO provo) {
		sql = "insert into tbl_product (pro_no ,pro_name, pro_price, pro_info,  pro_seller,"
				+ "pro_makdate, pro_makefactureId, pro_inventory) " + "values(pro_seq.nextval, ?,?,?,?,?,?,?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, provo.getProName());
			psmt.setInt(2, provo.getProPrice());
			psmt.setString(3, provo.getProInfo());
			psmt.setString(4, provo.getProSeller());
			psmt.setString(5, provo.getProMakdate().substring(0, 10));
			psmt.setString(6, provo.getMakeId());
			psmt.setInt(7, provo.getProInventory());

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
	// DB에 저장된 상품 목록 출력
	public List<ProductVO> list() {
		List<ProductVO> list = new ArrayList<>();
		sql = "select * from tbl_product";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				ProductVO provo = new ProductVO();
				provo.setProNo(rs.getInt("pro_no"));
				provo.setProName(rs.getString("pro_name"));
				provo.setProPrice(rs.getInt("pro_price"));
				provo.setProInfo(rs.getString("pro_info"));
				provo.setProSeller(rs.getString("pro_seller"));
				provo.setProMakdate(rs.getString("pro_makdate").substring(0, 10));
				provo.setMakeId(rs.getString("pro_makefactureId"));
				provo.setProInventory(rs.getInt("pro_inventory"));
				list.add(provo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}
	// DB에 저장된 특성 상품 자료 조회
	public ProductVO search(int no) {
		conn = Dao.getConnect();
		sql = "select * from tbl_product where pro_no = ?";

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);

			rs = psmt.executeQuery();
			if (rs.next()) {
				ProductVO provo = new ProductVO();
				provo.setProNo(rs.getInt("pro_no"));
				provo.setProName(rs.getString("pro_name"));
				provo.setProPrice(rs.getInt("pro_price"));
				provo.setProInfo(rs.getString("pro_info"));
				provo.setProSeller(rs.getString("pro_seller"));
				provo.setProMakdate(rs.getString("pro_makdate"));
				provo.setMakeId(rs.getString("pro_makefactureId"));

				return provo;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return null;
	}
	// User 테이블에 구매 관련 데이터 저장
	public int buy(ProductVO provo, int number, UserVO uservo) {
		conn = Dao.getConnect();
		sql = "update tbl_user" + " set user_pw = nvl(?, user_pw), "
				+ " user_name = nvl(?, user_name), user_birth = nvl(?,user_birth), user_phone = nvl(?, user_phone),"
				+ " user_addr = nvl(?, user_addr), user_buyname = ?, user_buyprice = ?, "
				+ " user_seller = ?, user_quantity = ?, user_totalprice = ? where user_id = ?";

		try {
			int total = provo.getProPrice() * number; // 상품 총액 계산

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, uservo.getUserPw());
			psmt.setString(2, uservo.getUserName());
			psmt.setString(3, uservo.getUserBirth().substring(0, 10)); // 1993-03-03
			psmt.setString(4, uservo.getUserPhone());
			psmt.setString(5, uservo.getUserAddr());
			psmt.setString(6, provo.getProName());
			psmt.setInt(7, provo.getProPrice());
			psmt.setString(8, provo.getProSeller());
			psmt.setInt(9, number);
			psmt.setInt(10, total);
			psmt.setString(11, uservo.getUserId());
			int r = psmt.executeUpdate();
			if (r > 0) {

				return total;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -1;

	}
	// DB에 저장된 최근 구매 값 콘솔에 출력
	public UserVO pBuyResult(String id) {
		conn = Dao.getConnect();
		sql = "select * from tbl_user where  user_id = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setUserName(rs.getString("user_name"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserAddr(rs.getString("user_addr"));
				user.setBuyName(rs.getString("user_buyname"));
				user.setBuyPrice(rs.getInt("user_buyprice"));
				user.setQuantity(rs.getInt("user_quantity"));
				user.setTotalPrice(rs.getInt("user_totalprice"));
				user.setUserId(rs.getString("user_id"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserBirth(rs.getString("user_birth").substring(0, 10));
				user.setSeller(rs.getString("user_seller"));
				pBuyList(user); // 구매확인을 통해서 구매목록에 값이 동시에 진행 될 수 있도록 함.
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	// pBuyResult 메소드 실행시 동시에 진행 구매리스트 저장을 위한 메소드
	public boolean pBuyList(UserVO user) {
		sql = "insert into tbl_list (list_no, list_userid, list_username, list_userphone, "
				+ "list_useraddr, list_buyname, list_buyprice, list_buyquantity,  list_buytotalprice) "
				+ "values(list_seq.nextval, ?,?,?,?,?,?,?,?)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getUserName());
			psmt.setString(3, user.getUserPhone());
			psmt.setString(4, user.getUserAddr());
			psmt.setString(5, user.getBuyName());
			psmt.setInt(6, user.getBuyPrice());
			psmt.setInt(7, user.getQuantity());
			psmt.setInt(8, user.getTotalPrice());

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
	// 저장된 구매리스트 값 콘솔 출력
	public List<UserVO> resultList() {
		List<UserVO> list = new ArrayList<>();
		conn = Dao.getConnect();
		sql = "select * from tbl_list";
		

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				UserVO user = new UserVO();
				user.setListNo(rs.getInt("list_no"));
				user.setUserName(rs.getString("list_username"));
				user.setUserPhone(rs.getString("list_userphone"));
				user.setUserAddr(rs.getString("list_useraddr"));
				user.setBuyName(rs.getString("list_buyname"));
				user.setBuyPrice(rs.getInt("list_buyprice"));
				user.setQuantity(rs.getInt("list_buyquantity"));
				user.setTotalPrice(rs.getInt("list_buytotalprice"));

				list.add(user);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}
	// 상품 입고
	public boolean productin(int no, int no2) {
		// 입고 처리
		conn = Dao.getConnect();
		sql = "update tbl_product" + " set pro_inventory = (select SUM(pro_inventory + ?)" + " from tbl_product"
				+ " where pro_no = ?), pro_productin = ?" + " where pro_no = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no2);
			psmt.setInt(2, no);
			psmt.setInt(3, no2);
			psmt.setInt(4, no);
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
	// 상품 출고
	public boolean productout(int no, int no2) {
		// 출고 처리

		conn = Dao.getConnect();
		sql = "update tbl_product" + " set pro_inventory = (select SUM(pro_inventory - ?)" + " from tbl_product"
				+ " where pro_no = ?), pro_productout = ?" + " where pro_no = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no2);
			psmt.setInt(2, no);
			psmt.setInt(3, no2);
			psmt.setInt(4, no);
			ProductVO prov = new ProductVO();

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
	//  재고 결과 값을 받아 재고 부족할 경우 물량부족으로 어려운 부분 위해서 리턴 값 받아서 계산 하기 위한 메소드
	public int rsInventory(int no) {
		conn = Dao.getConnect();
		sql = "select pro_inventory from tbl_product where pro_no = ? ";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if (rs.next()) {
				int result = rs.getInt("pro_inventory");
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
	// 현재 물품에 관한 재고관리 자료 콘솔에 출력
	public ProductVO inventory(int no) {
		ProductVO prov = new ProductVO();
		conn = Dao.getConnect();
		sql = "select * from tbl_product where pro_no = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if (rs.next()) {
				prov.setProNo(rs.getInt("pro_no"));
				prov.setProName(rs.getString("pro_name"));
				prov.setProPrice(rs.getInt("pro_price"));
				prov.setProInventory(rs.getInt("pro_inventory"));
				return prov;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return null;
	}
	// DB에 저장된 등록된 상품 정보 삭제
	public boolean delete(int no) {
		conn = Dao.getConnect();
		sql = "delete from  tbl_product  where pro_no = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
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
	//  DB에 저장된 등록된 상품 정보 수정
	public boolean modify(ProductVO provo) {
		conn = Dao.getConnect();
		sql = " update tbl_product " + " set pro_name = nvl(?,pro_name), pro_price = nvl(?, pro_price) , "
				+ " pro_info = nvl(?,pro_info) , pro_seller = nvl(?,pro_seller) , "
				+ " pro_makefactureid = nvl(?,pro_makefactureid) , pro_inventory = nvl(?,pro_inventory) "
				+ " where pro_no = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, provo.getProName());
			psmt.setInt(2, provo.getProPrice());
			psmt.setString(3, provo.getProInfo());
			psmt.setString(4, provo.getProSeller());
			psmt.setString(5, provo.getMakeId());
			psmt.setInt(6, provo.getProInventory());
			psmt.setInt(7, provo.getProNo());
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

}
