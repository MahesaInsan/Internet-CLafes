package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class TransactionDetail {
	private int transactionID;
	private String pcID;
	private String customerName;
	private String bookedTime;
	
	public TransactionDetail(int id, String pcId, String customerName, String bookedTime) {
		this.transactionID = id;
		this.pcID = pcId;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}
	
	public void getUserTransactionDetail(String userID) {
		
	}
	
	public int getTransactionID() {
		return transactionID;
	}

	public String getPcID() {
		return pcID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public static ArrayList<TransactionDetail> getAllTransaction(int transactionID) throws SQLException {
		ArrayList<TransactionDetail> tDetList = new ArrayList<TransactionDetail>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT t.id, b.pcId, u.username, t.bookedTime FROM TransactionDetail t INNER JOIN Users u ON t.customerId = u.id "
				+ "INNER JOIN PcBook b ON b.tDetId = t.id WHERE theadId = ?");
		ps.setInt(1, transactionID);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			tDetList.add(new TransactionDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		return tDetList;
	}
	public void addTransactionDetail(String transactionID, ArrayList<PCBook> pcBookList) {
		
	}
}
