package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import models.PCBook;
import models.TransactionDetail;
import models.TransactionHeader;

public class TransactionController {
	public void addTransaction(String transactionID, ArrayList<PCBook> pcBookList, String staffID) {
		
	}
	public ArrayList<TransactionHeader> getAllTransactionHeaderData() {
		try {
			return TransactionHeader.getAllTransactionHeaderData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<TransactionDetail> getAllTransactionDetail(int id) {
		try {
			return TransactionDetail.getAllTransaction(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getUserTransactionDetail(String userID) {
		
	}
}
