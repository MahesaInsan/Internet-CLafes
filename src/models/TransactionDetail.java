package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

	public static List<TransactionHistory> getUserTransactionDetail(int userID) {
		String query = "SELECT th.id AS header_id, th.staffId, th.transactionDate, " +
                  "td.id AS detail_id, td.customerId, td.bookedTime, td.theadId, td.pcId " +
                  "FROM TransactionHeader th " +
                  "JOIN TransactionDetail td ON th.id = td.theadId " +
                  "WHERE td.customerId = ?";
		
		PreparedStatement ps = db.prepareStatement(query);
		List<TransactionHistory> transactionHistoryList = new ArrayList<>();
		
		try {
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
		    
			while (rs.next()) {
                int headerId = rs.getInt("header_id");
                int staffId = rs.getInt("staffId");
                String transactionDate = rs.getString("transactionDate");

                int detailId = rs.getInt("detail_id");
                int bookedCustomerId = rs.getInt("customerId");
                String bookedTime = rs.getString("bookedTime");
                int theadId = rs.getInt("theadId");
                String pcId = rs.getString("pcId");

                // Create TransactionHeader and TransactionDetail objects and add them to the list
                TransactionHeader transactionHeader = new TransactionHeader(headerId, staffId, "", transactionDate);
                TransactionDetail transactionDetail = new TransactionDetail(detailId, pcId, "", bookedTime);
                TransactionHistory transactionHistory = new TransactionHistory(transactionHeader, transactionDetail);
                
                transactionHistoryList.add(transactionHistory);
            }
			
            rs.close();
            return transactionHistoryList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	//Mengambil semua transaksi detail dari database yang memiliki transaction head yang sama
	public static ArrayList<TransactionDetail> getAllTransaction(int transactionID) throws SQLException {
		ArrayList<TransactionDetail> tDetList = new ArrayList<TransactionDetail>();
		
		PreparedStatement ps = db.prepareStatement("SELECT t.id, t.pcId, u.username, t.bookedTime FROM TransactionDetail t INNER JOIN Users u ON t.customerId = u.id "
				+ "WHERE theadId = ?");
		
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
