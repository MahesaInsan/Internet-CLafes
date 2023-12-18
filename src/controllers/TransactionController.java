package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.PCBook;
import models.TransactionDetail;
import models.TransactionHeader;
import models.TransactionHistory;

public class TransactionController {
	
	public Boolean addTransactionDetail(Integer trHeadID, PCBook pcBooked) {
		
		try {
			TransactionDetail.createTransactionDetail(pcBooked.getUserID(), pcBooked.getBookedDate(), trHeadID);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void addTransactionHeader(Integer staffID) {
		try {
			TransactionHeader.addNewTransactionHeader(staffID);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	public TransactionHeader getLastTransactionHeader() {
		try {
			return TransactionHeader.getLastTransactionHeader();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//Mengambil data transaction header dari model
	public ArrayList<TransactionHeader> getAllTransactionHeaderData() {
		try {
			return TransactionHeader.getAllTransactionHeaderData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	//Mengambil data transaction detail dari model
	public ArrayList<TransactionDetail> getAllTransactionDetail(int id) {
		try {
			return TransactionDetail.getAllTransaction(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<TransactionHistory> getUserTransactionDetail(int userID) {
		if (userID == 0) {
			return null;
		}
		
		return TransactionDetail.getUserTransactionDetail(userID);
	}
}
