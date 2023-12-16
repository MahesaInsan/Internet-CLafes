package controllers;

import views.IErrorMessage;

import java.sql.SQLException;
import java.util.ArrayList;
import models.PC;

public class PCController {
	//Mengambil semua data dari model PC dan mendisplay error jika tidak dapat melakukan fetching
	public ArrayList<PC> getAllPCData(IErrorMessage error) {
		try {
			return PC.getAllPCData();
		} catch (SQLException e) {
			error.displayErrorMessage("Error fetching pc data");
			e.printStackTrace();
			return null;
		}
	}

	//Melakukan update pada pc yang dipilih dan menampilkan error jika status tidak dipilih
	public boolean updatePCCondition(IErrorMessage error, String pcID, String condition) throws SQLException {
		if(condition == null) {
			error.displayErrorMessage("Condition must be selected");
			return false;
		}
		PC.updatePCCondition(pcID, condition);
		return true;
	}

	//Melakukan delete pada pc yang dipilih dan menampilkan error jika pc di book oleh user
	public boolean deletePC(IErrorMessage error, String pcID) {
		//check if pc is used
		try {
			if(checkPCID(pcID)) {
				error.displayErrorMessage("This PC is booked by user!");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			PC.deletePC(pcID);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	//Menambahkan pc ke dalam model/database dan menampilkan error sesuai validasi
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

	//Mengecek apa pc id sudah digunakan atau belum
	private boolean checkPCID(String pcID) throws SQLException {
		if(PC.getPCDetail(pcID) == null) {
			return false;
		}else return true;
	}
}
