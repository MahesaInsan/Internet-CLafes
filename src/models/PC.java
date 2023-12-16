package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class PC {
	private String pcID;
	private String pcCondition;
	
	public PC(String id, String condition) {
		this.pcID = id;
		this.pcCondition = condition;
	}
	public String getPcID() {
		return pcID;
	}
	public String getPcCondition() {
		return pcCondition;
	}

	public static void updatePCCondition(String pcID, String pcCondition) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE pc SET pcCondition = ? WHERE pcID = ?");
		ps.setString(1, pcCondition);
		ps.setString(2, pcID);
		ps.executeUpdate();
	}
	
	public static void deletePC(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("DELETE FROM pc WHERE pcID = ?");
		ps.setString(1, pcID);
		ps.executeUpdate();
	}
	
	public static void addNewPC(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("INSERT INTO pc (pcID, pcCondition) VALUES (?, ?)");
		ps.setString(1, pcID);
		ps.setString(2, "Usable");
		ps.executeUpdate();
	}
	
	public static PC getPCDetail(String pcID) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM pc WHERE pcID = ?");
		ps.setString(1, pcID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return new PC(rs.getString("pcID"), rs.getString("pcCondition"));
		}
		return null;
	}
	
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
