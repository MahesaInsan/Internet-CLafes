package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.Connect;

public class User {
	private int userID;
	private String userName;
	private String userPassword;
	private int userAge;
	private String userRole;
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void getAllUserData() {
		System.out.println(this.userName + " " + this.userPassword + " " + this.userAge);
	}
	
	public String getRole() {
		return userRole;
	}
	
	public void getUserData(String username, String password) {
		
	}
	
	public void addNewUser(String username, String password, int age) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("INSERT INTO `users` (username, password, age, role) VALUES (?, ?, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setInt(3, age);
		ps.setString(4, "Customer");
		ps.executeUpdate();
	}
	
	public void changeUserRole(String userID, String newRole) {
		
	}
	// Data" technician 
	
	public List<User> getAllTechnician() throws SQLException{
	    List<User> technicians = new ArrayList<>();
	    Connect db = Connect.getConnection();
	    try (
	         PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE role = 'Technician'");
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int technicianID = rs.getInt("user_id");
	            String technicianName = rs.getString("username");
	            String technicianPassword = rs.getString("password");
	            int technicianAge = rs.getInt("age");
	            String technicianRole = rs.getString("role");

	            User technician = new User();
	            technician.setUserID(technicianID);
	            technician.setUserName(technicianName);
	            technician.setUserPassword(technicianPassword);
	            technician.setUserAge(technicianAge);
	            technician.setUserRole(technicianRole);

	            technicians.add(technician);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle or log the exception as needed
	    }

	    return technicians;
	}
	
	public boolean checkUsername(String username) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `users` WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return true;
		}else return false;
	}
	
	public User validateUser(String username, String password) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `users` WHERE username = ? AND password = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			this.userID = rs.getInt(1);
			this.userName = rs.getString(2);
			this.userPassword = rs.getString(3);
			this.userAge = rs.getInt(4);
			this.userRole = rs.getString(5);
			return this;
		}else return null;
	}
}
