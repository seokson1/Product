package com.yedam.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			psmt.setString(5, provo.getProMakdate());
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
				provo.setProMakdate(rs.getString("pro_makdate"));
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

	public ProductVO search(int no) {
		conn = Dao.getConnect();
		sql = "select * from tbl_product where pro_no = ?";
		sql1 = "insert into tbl_user (user-no,user_buyname, user_buyprice, user_seller) "
				+ "values(user_seq.nextval ,?,?,?)";
		PreparedStatement psmt1;
		try {
			String name;
			int price;
			String seller;
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
				
				
				
				name = provo.getProName();
				price = provo.getProPrice();
				seller = provo.getProSeller();
				
				psmt1 = conn.prepareStatement(sql1);
				psmt1.setString(1, name);
				psmt1.setInt(2, price);
				psmt1.setString(3, seller);
				int r = psmt1.executeUpdate();
				if(r > 0) {
					
				}
				
				return provo;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return null;
	}
	public void buy(int number) {
		conn = Dao.getConnect();
		sql = "insert into tbl_user (user_buyname, user_buyprice, user_seller, user_quantity) "
				+ "values(?,?,?,)";
		
		try {
			psmt = conn.prepareStatement(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	public boolean productin(int no, int no2) {
	// 입고 처리
		conn = Dao.getConnect();
		sql = "update tbl_product"
				+ " set pro_inventory = (select SUM(pro_inventory + ?)"
				+ " from tbl_product"
				+ " where pro_no = ?), pro_productin = ?"
				+ " where pro_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no2);
			psmt.setInt(2, no);
			psmt.setInt(3, no2);
			psmt.setInt(4, no);
			int r = psmt.executeUpdate();
		
			if(r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		} 
		
		return false;
	}

	public boolean productout(int no, int no2) {
		//출고 처리
		
		conn = Dao.getConnect();
		sql = "update tbl_product"
				+ " set pro_inventory = (select SUM(pro_inventory - ?)"
				+ " from tbl_product"
				+ " where pro_no = ?), pro_productout = ?"
				+ " where pro_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no2);
			psmt.setInt(2, no);
			psmt.setInt(3, no2);
			psmt.setInt(4, no);
			ProductVO prov = new ProductVO();
			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		} 
		
		return false;
	}
	public int rsInventory(int no) {
		conn = Dao.getConnect();
		sql = "select pro_inventory from tbl_product where pro_no = ? ";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
				int result =  rs.getInt("pro_inventory");
				return result; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return -1;
	}
	

	public ProductVO inventory(int no) {
		ProductVO prov = new ProductVO();
		conn = Dao.getConnect();
		sql = "select * from tbl_product where pro_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
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
	public boolean delete(int no) {
		conn = Dao.getConnect();
		sql = "delete from  tbl_product  where pro_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
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
	public boolean modify(ProductVO provo) {
		conn = Dao.getConnect();
		sql = " update tbl_product "
				+ " set pro_name = nvl(?,pro_name), pro_price = nvl(?, pro_price) , "
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
