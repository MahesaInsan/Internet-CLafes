package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class TransactionHeader {
	//attribute transaction header
	private int transactionID;
	private int staffID;
	private String staffName;
	private String transactionDate;

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

	//constructor transaction header untuk set id, staff id, staff name dan transaction date
	public TransactionHeader(int id, int staffId, String staffName, String transactionDate) {
		this.transactionID = id;
		this.staffID = staffId;
		this.staffName = staffName;
		this.transactionDate = transactionDate;
	}

	//Mengambil semua data transaction header dari database
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
