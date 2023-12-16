package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class User {
	private int userID;
	private String userName;
	private String userPassword;
	private int userAge;
	private String userRole;
	
	public User(int userID, String userName, String userPassword, int userAge, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}
	
	public User() {
	};

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
	
	public getUserData(String username, String password) {
		
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
	
	public static void changeUserRole(int userID, String newRole) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE users SET role = ? WHERE id = ?");
		ps.setString(1, newRole);
		ps.setInt(2, userID);
		ps.executeUpdate();
	}
	
	public getAllTechnician() {
		
	}
	
	public static ArrayList<User> getAllUser() throws SQLException{
		ArrayList<User> staffList = new ArrayList<User>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("SELECT * FROM users");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			staffList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
		}
		
		return staffList;
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
	
	public static User checkStaffRole(String staffId) throws SQLException{
		Connect db = Connect.getConnection();
		int idNum = Integer.parseInt(staffId);
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `users` WHERE id = ?");
		ps.setInt(1, idNum);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
		}
		return null;
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
