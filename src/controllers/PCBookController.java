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

	public void getPCBookedData(String pcID, String bookedDate) {
	}

	public boolean assignUserToPC(String bookID, String pcID) {
		return false;
	}

	public void getPCBookedDetail(String bookID) {
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

		// Create a new entry in the TransactionHeader table

		/*
		 * try { System.out.println("Creating transaction header....");
		 * TransactionHeader.addNewTransactionHeader(userId, timestamp); trHeader =
		 * TransactionHeader.getLastTransactionHeader(); System.out.println("tr id: " +
		 * trHeader.getTransactionID()); } catch (NumberFormatException e) {
		 * e.printStackTrace(); } catch (SQLException e) { e.printStackTrace(); return
		 * "Error: Sorry, we cannot procceced your request right now!"; }
		 *
		 * if (trHeader.getTransactionID() <= 0) { return
		 * "Error: Sorry, we cannot procceced your request right now!"; }
		 *
		 * // Create a new entry in the TransactionDetail table try {
		 * System.out.println("Creating transaction detail....");
		 * TransactionDetail.createTransactionDetail(userId, timestamp,
		 * trHeader.getTransactionID()); trDetail =
		 * TransactionDetail.getLastTransactionDetail(); System.out.println("tr id: " +
		 * trDetail.getTransactionID()); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); return
		 * "Error: Sorry, we cannot procceced your request right now!"; }
		 */

		/*
		 * if (trHeader.getTransactionID() <= 0) { return
		 * "Error: Sorry, we cannot procceced your request right now!"; }
		 */

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

	public void getBookedByDate(String date) {
	}

	public static boolean isValidDate(LocalDate selectedDate) {
		LocalDate currentDate = LocalDate.now();

		// Check if the selectedDate is in the future or until the current date
		return selectedDate.isAfter(currentDate) || selectedDate.isEqual(currentDate);
	}
}
