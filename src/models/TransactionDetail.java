package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import main.Connect;

public class TransactionDetail {
	//transaction detail attribute
	private int transactionID;
	private String pcID;
	private String customerName;
	private String bookedTime;
	private static Connect db = Connect.getConnection();


	//Constructor transaction detail untuk set id, pc id, customer name dan booked detail
	public TransactionDetail(int id, String pcId, String customerName, String bookedTime) {
		this.transactionID = id;
		this.pcID = pcId;
		this.customerName = customerName;
		this.bookedTime = bookedTime;
	}
	
	//getter id
	public int getTransactionID() {
		return transactionID;
	}

	//getter pc id
	public String getPcID() {
		return pcID;
	}
	
	//getter customer name
	public String getCustomerName() {
		return customerName;
	}
	
	//getter booked time
	public String getBookedTime() {
		return bookedTime;
	}

	public void getUserTransactionDetail(String userID) {
		
	}
	
	
	//Mengambil semua transaksi detail dari database yang memiliki transaction head yang sama
	public static ArrayList<TransactionDetail> getAllTransaction(int transactionID) throws SQLException {
		ArrayList<TransactionDetail> tDetList = new ArrayList<TransactionDetail>();
		
		PreparedStatement ps = db.prepareStatement("SELECT t.id, b.pcId, u.username, t.bookedTime FROM TransactionDetail t INNER JOIN Users u ON t.customerId = u.id "
				+ "INNER JOIN PcBook b ON b.tDetId = t.id WHERE theadId = ?");
		
		ps.setInt(1, transactionID);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			tDetList.add(new TransactionDetail(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		return tDetList;
	}
	
	public static void createTransactionDetail(int customerId, String bookedTime, int theadId) throws SQLException {
		System.out.println("CID: " + customerId);
		System.out.println("time: " + bookedTime);
		System.out.println("theadid: " + theadId);
		
		String query = "INSERT INTO TransactionDetail (customerId, bookedTime, theadId) VALUES (?, ?, ?)";
		PreparedStatement ps = db.prepareStatement(query);
		
		ps.setInt(1, customerId);
		ps.setString(2, bookedTime);
		ps.setInt(3, theadId);

		ps.executeUpdate();
	}
	

	
	public static TransactionDetail getLastTransactionDetail() throws SQLException {
        TransactionDetail transactionDetail = null;
        
		Connect db = Connect.getConnection();
		
		String query = "SELECT * FROM `TransactionDetail` ORDER BY id DESC LIMIT 1;";
		
		PreparedStatement ps = db.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		/*
		 * id customerId bookedTime theadId 
		 * int id, String pcId, String customerName, Timestamp bookedTime) {
		 */
		if (rs.next()) {
			transactionDetail = new TransactionDetail(rs.getInt(1), "", "", rs.getString(3));
		}
		
		return transactionDetail;
	}
	
	/*
	 * public void addTransactionDetail(String transactionId, ArrayList<PCBook>
	 * pcBookList) {
	 * 
	 * }
	 */
}