package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import main.Connect;

public class PCBook {
  private int bookID;
  private String pcID;
  private int userID;
  private String bookedDate;
  private int tDetailId;
  private static Connect db = Connect.getConnection();

  public PCBook(int bookID, String pcID, int userID, String bookedDate) {
    super();
    this.bookID = bookID;
    this.pcID = pcID;
    this.userID = userID;
    this.bookedDate = bookedDate;
  }

  public static void deleteBookData(int bookID) throws SQLException {
	  String query = "DELETE FROM PcBook WHERE id = ?";
	  
	  try {
		  PreparedStatement ps = db.prepareStatement(query);
		  ps.setInt(1, bookID);
		  ps.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	}
	  
  }

  public void getPCBookedData(String bookID, String date) {}

  public void getPCBookedDetail(String bookID) {}

  public static void addNewBook(String pcId, int customerId, String bookedDate)
      throws SQLException {
    String query = "INSERT INTO PcBook (pcId, customerId, bookedDate) VALUES (?, ?, ?)";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setString(1, pcId);
      ps.setInt(2, customerId);
      ps.setString(3, bookedDate);

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void finishBook(ArrayList<PCBook> pcBook) {}

  public static ArrayList<PCBook> getAllPCBooked() throws SQLException {
    ArrayList<PCBook> pcBooks = new ArrayList<>();
    PreparedStatement ps = db.prepareStatement("SELECT * FROM PcBook");

    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      int id = rs.getInt(1);
      int customerId = rs.getInt(3);
      String pcId = rs.getString(2);
      String bookedDate = rs.getString(4);
      
      PCBook pcBook = new PCBook(id, pcId, customerId, bookedDate);
      
      pcBooks.add(pcBook);
    }

    return pcBooks;
  }
  
  public static ArrayList<PCBook> getAllPCBookedByDate(String date) throws SQLException {
	    ArrayList<PCBook> pcBooks = new ArrayList<>();
	    PreparedStatement ps = db.prepareStatement("SELECT * FROM PcBook WHERE bookedDate = ?");
        ps.setString(1, date);
        
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	      int id = rs.getInt(1);
	      int customerId = rs.getInt(3);
	      String pcId = rs.getString(2);
	      String bookedDate = rs.getString(4);
	      
	      PCBook pcBook = new PCBook(id, pcId, customerId, bookedDate);
	      
	      pcBooks.add(pcBook);
	    }

	    return pcBooks;
	  }

  public void getPCBookedByDate(String date) {}

  public void assignUsertoNewPC(String bookID, String newPCID) {}

  public int getBookID() {
    return bookID;
  }

  public void setBookID(int bookID) {
    this.bookID = bookID;
  }

  public String getPcID() {
    return pcID;
  }

  public void setPcID(String pcID) {
    this.pcID = pcID;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getBookedDate() {
    return bookedDate;
  }

  public void setBookedDate(String bookedDate) {
    this.bookedDate = bookedDate;
  }

  public int gettDetailId() {
    return tDetailId;
  }

  public void settDetailId(int tDetailId) {
    this.tDetailId = tDetailId;
  }
}
