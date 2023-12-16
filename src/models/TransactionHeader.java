package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class TransactionHeader {
	private int transactionID;
	private int staffID;
	private String staffName;
	private String transactionDate;
	
	public int getTransactionID() {
		return transactionID;
	}
	public int getStaffID() {
		return staffID;
	}
	public String getStaffName() {
		return staffName;
	}
	public String getTransactionDate() {
		return transactionDate;
	}


	public TransactionHeader(int id, int staffId, String staffName, String transactionDate) {
		this.transactionID = id;
		this.staffID = staffId;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}
	
	public static ArrayList<TransactionHeader> getAllTransactionHeaderData() throws SQLException {
		ArrayList<TransactionHeader> tHeadList = new ArrayList<TransactionHeader>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT t.id, t.staffId, u.username, t.transactionDate FROM TransactionHeader t INNER JOIN Users u ON t.staffId = u.id ");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			tHeadList.add(new TransactionHeader(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
		}
		
		return tHeadList;
	}
	
	public void addNewTransactionHeader(String staffID, String transactionDate) {
		
	}
}
