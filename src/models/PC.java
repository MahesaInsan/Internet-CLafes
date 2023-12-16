package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class PC {
	//Attribute pc
	private String pcID;
	private String pcCondition;

	//Constructor pc untuk set id dan condition
	public PC(String id, String condition) {
		this.pcID = id;
		this.pcCondition = condition;
	}
	//Getter id
	public String getPcID() {
		return pcID;
	}
	//Getter pc condition
	public String getPcCondition() {
		return pcCondition;
	}

	//Melakukan edit dan update pc condition pada database
	public static void updatePCCondition(String pcID, String pcCondition) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE pc SET pcCondition = ? WHERE id = ?");
		ps.setString(1, pcCondition);
		ps.setString(2, pcID);
		ps.executeUpdate();
	}

	//Menghapus pc dari database
	public static void deletePC(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("DELETE FROM pc WHERE pcID = ?");
		ps.setString(1, pcID);
		ps.executeUpdate();
	}

	//Menambahkan pc baru ke dalam database
	public static void addNewPC(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("INSERT INTO pc (id, pcCondition) VALUES (?, ?)");
		ps.setString(1, pcID);
		ps.setString(2, "Usable");
		ps.executeUpdate();
	}

	//Mengecek apakah terdapat pc id tersebut di dalam database
	public static boolean checkPC(String pcID) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM pcbook WHERE pcId = ?");
		ps.setString(1, pcID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return true;
		}
		return false;
	}

	//Mengambil pc detail dari database
	public static PC getPCDetail(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM pc WHERE id = ?");
		ps.setString(1, pcID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return new PC(rs.getString("id"), rs.getString("pcCondition"));
		}
		return null;
	}

	//Mengambil semua data pc dari database
	public static ArrayList<PC> getAllPCData() throws SQLException{
		ArrayList<PC> pcList = new ArrayList<PC>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM pc");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			pcList.add(new PC(rs.getString(1), rs.getString(2)));
		}
		
		return pcList;
	}
}
