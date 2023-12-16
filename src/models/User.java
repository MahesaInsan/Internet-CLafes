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
	
	public void changeUserRole(String userID, String newRole) {
		
	}
	
	public getAllTechnician() {
		
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
