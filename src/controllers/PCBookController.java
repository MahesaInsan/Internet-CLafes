package controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.PCBook;
import models.TransactionDetail;
import models.TransactionHeader;

public class PCBookController {
	private PCController pcController = new PCController();
	private UserController userController = new UserController();
	private TransactionController transactionController = new TransactionController();

	public PCBook getPCBookedData(int bookId) {
		try {
			return PCBook.getPCBookedData(bookId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String assignUserToPC(PCBook pcBooked, String newPCId) {
		if (pcBooked.getPcID() == null) {
			return "Error: Please select the Row or PC you want to assign!";
		} 
		
		if (newPCId == null || newPCId.isEmpty()) {
			return "Error: Please select PC ID you want to assign!";
		}
		
		
		PCBook result = getPCBookedData(pcBooked.getBookID());
		
		if (result == null) {
			return "Cannot find bookedPC with ID " +  pcBooked.getBookID();
		}
		
		
		PCBook.assignUsertoNewPC(pcBooked.getBookID(), newPCId);
		
		return "Succeced assigning to new PC ID: " + newPCId;
	}

	public String addNewBook(String pcId, int userId, LocalDate bookedDate) throws SQLException {
		// check PC with pcID is exists
		Boolean validatePCId = pcId != "" && pcId.length() > 0;
		Boolean validateUserId = userId > 0;
		Boolean validateDatePicker = bookedDate != null && isValidDate(bookedDate);

		if (!validatePCId) {
			return "Error: PC ID field cannot be empty!";
		}

		if (!validateDatePicker) {
			return "Error: Date Picker should be in feauture or current time!";
		}

		if (!validateUserId) {
			return "Error: Unauthorized!";
		}

		try {
			if (!pcController.checkPCID(pcId)) {
				return "Error: The PC with ID: " + pcId + " is not available or not ready to book!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return "Error: Sorry, we cannot procceced your request right now!";
		}

		// Create a new entry in the PcBook table
		System.out.println("Creating PCBook....");
		PCBook.addNewBook(pcId, userId, bookedDate.toString());

		return "";
	}

	public String deleteBookData(Integer bookID) {
		Boolean validBookID = bookID >= 0;

		if (!validBookID) {
			return "Error: Book ID cannot be empty or blank!";
		}

		try {
			PCBook.deleteBookData(bookID);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: Sorry, we cannot procceced your request right now!";
		}
	}

	public String finishBook(ArrayList<PCBook> pcBookedList) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		if (pcBookedList.size() <= 0) {
			return "No Records of Booking to be Finished";
		}

		LocalDate bookedTime = LocalDate.parse(pcBookedList.get(0).getBookedDate(), formatter);

		if (now.isBefore(bookedTime)) {
			return "Complete PC Reservation must pass today's date!";
		}

		Integer userID = userController.getCurrentUser().getUserID();
		// create new Transaction Header
		transactionController.addTransactionHeader(userID);
		TransactionHeader trHeader = transactionController.getLastTransactionHeader();

		int trHeaderID = trHeader.getTransactionID();
		Boolean result = false;

		for (PCBook pcBooked : pcBookedList) {
			if (transactionController.addTransactionDetail(trHeaderID, pcBooked)) {
				deleteBookData(pcBooked.getBookID());
				result = true;
			};
		}

		if (!result) {
			return "Error: Sorry, we cannot procceced your request right now!";
		}
		
		return "Success finished the booking!";
	}

	public ArrayList<PCBook> getAllPCBookedData() {
		try {
			ArrayList<PCBook> pcBooks = PCBook.getAllPCBooked();
			return pcBooks;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<PCBook> getAllPCBookedDataByDate(LocalDate date) {
		try {
			ArrayList<PCBook> pcBooks = PCBook.getAllPCBookedByDate(date.toString());
			return pcBooks;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isValidDate(LocalDate selectedDate) {
		LocalDate currentDate = LocalDate.now();

		// Check if the selectedDate is in the future or until the current date
		return selectedDate.isAfter(currentDate) || selectedDate.isEqual(currentDate);
	}
}
