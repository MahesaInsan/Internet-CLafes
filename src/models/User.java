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
	
	public getUserData(String username, String password) {
		
	}
	
	public void addNewUser(String username, String password, int age) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("INSERT INTO `users` (username, password, age) VALUES (?, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setInt(3, age);
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
			this.userName = rs.getString("username");
			this.userPassword = rs.getString("password");
			this.userAge = rs.getInt("age");
			return this;
		}else return null;
	}
}
