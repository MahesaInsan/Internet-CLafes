package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import main.Connect;

public class TransactionHeader {
	//attribute transaction header
	private int transactionID;
	private int staffID;
	private String staffName;
	private String transactionDate;

	//constructor transaction header untuk set id, staff id, staff name dan transaction date
	public TransactionHeader(int id, int staffId, String staffName, String transactionDate) {
		this.transactionID = id;
		this.staffID = staffId;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}
	
	//getter id
	public int getTransactionID() {
		return transactionID;
	}
	
	//getter staff id
	public int getStaffID() {
		return staffID;
	}
	
	//getter staff name
	public String getStaffName() {
		return staffName;
	}
	
	//getter transaction date
	public String getTransactionDate() {
		return transactionDate;
	}
	
	public static TransactionHeader getTransactionHeader(int id) throws SQLException {
        TransactionHeader transactionHeader = null;
		Connect db = Connect.getConnection();
		String query = "SELECT t.id, t.staffId, u.username, t.transactionDate " +
                  "FROM TransactionHeader t " +
                  "INNER JOIN Users u ON t.staffId = u.id " +
                  "WHERE t.id = ?";
		
		PreparedStatement ps = db.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			transactionHeader = new TransactionHeader(
				rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)
			);
		}
		
		return transactionHeader;
	}

	
	public static TransactionHeader getLastTransactionHeader() throws SQLException {
        TransactionHeader transactionHeader = null;
        
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM `TransactionHeader` ORDER BY id DESC LIMIT 1;";
		
		PreparedStatement ps = db.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			transactionHeader = new TransactionHeader(rs.getInt(1), rs.getInt(2), "", rs.getString(3));
		}
		
		return transactionHeader;
	}
	
	//Mengambil semua data transaction header dari database
	public static ArrayList<TransactionHeader> getAllTransactionHeaderData() throws SQLException {
		ArrayList<TransactionHeader> tHeadList = new ArrayList<TransactionHeader>();
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT t.id, t.staffId, u.username, t.transactionDate FROM TransactionHeader t INNER JOIN Users u ON t.staffId = u.id ");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			tHeadList.add(new TransactionHeader(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
			System.out.println(rs.getInt(1));
			System.out.println(rs.getInt(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));
		}
		
		return tHeadList;
	}
	
	public static void addNewTransactionHeader(Integer staffId) throws SQLException {
		Connect db = Connect.getConnection();
		String query = "INSERT INTO `TransactionHeader` (staffId, transactionDate) VALUES (?, ?)";
		PreparedStatement ps = db.prepareStatement(query);
		
		ps.setInt(1, staffId);
		ps.setString(2, LocalDate.now().toString());
		ps.executeUpdate();
	}
}