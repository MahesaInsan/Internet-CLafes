package controllers;

import views.IErrorMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import models.PC;

public class PCController {
	public ArrayList<PC> getAllPCData(IErrorMessage error) {
		try {
			return PC.getAllPCData();
		} catch (SQLException e) {
			error.displayErrorMessage("Error fetching pc data");
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updatePCCondition(IErrorMessage error, String pcID, String condition) throws SQLException {
		if(condition == null) {
			error.displayErrorMessage("Condition must be selected");
			return false;
		}
		PC.updatePCCondition(pcID, condition);
		return true;
	}
	
	public boolean deletePC(String pcID) {
		//check if pc is used
		
		try {
			PC.deletePC(pcID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean addNewPC(IErrorMessage error, String pcID) throws SQLException {
		if(pcID.equals("")) {
			error.displayErrorMessage("All fields must be filled");
			return false;
		}
		
		if(checkPCID(pcID)) {
			error.displayErrorMessage("PC ID already exist");
			return false;
		}
		PC.addNewPC(pcID);
		return true;
	}
	
	public void getPCDetail(String pcID) {
		
	}
	
	private boolean checkPCID(String pcID) throws SQLException {
		if(PC.getPCDetail(pcID) == null) {
			return false;
		}else return true;
	}
}
