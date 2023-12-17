package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.Connect;

public class User {
	//attribute user
	private int userID;
	private String userName;
	private String userPassword;
	private int userAge;
	private String userRole;

	//Constructor untuk set id, username, password, age dan role
	public User(int userID, String userName, String userPassword, int userAge, String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAge = userAge;
		this.userRole = userRole;
	}

	//Constructor jika tidak  mau melakukan set
	public User() {
	};
	//Getter id
	public int getUserID() {
		return userID;
	}
	//Setter id
	public void setUserID(int userID) {
		this.userID = userID;
	}
	//Getter username
	public String getUserName() {
		return userName;
	}
	//Setter username
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//Getter password
	public String getUserPassword() {
		return userPassword;
	}
	//Setter password
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	//Getter age
	public int getUserAge() {
		return userAge;
	}
	//Setter age
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	//Getter role
	public String getUserRole() {
		return userRole;
	}
	//Setter role
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	//Getter role
	public String getRole() {
		return userRole;
	}

	//Menambahkan data user ke dalam database
	public void addNewUser(String username, String password, int age) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("INSERT INTO `Users` (username, password, age, role) VALUES (?, ?, ?, ?)");
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setInt(3, age);
		ps.setString(4, "Customer");
		ps.executeUpdate();
	}

	//Method untuk mengganti role user dan mengganti nya juga di database menggunakan query
	public static void changeUserRole(int userID, String newRole) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE `Users` SET role = ? WHERE id = ?");
		ps.setString(1, newRole);
		ps.setInt(2, userID);
		ps.executeUpdate();
	}
	
	
	//Mengambil data" technician dari database 
	public List<User> getAllTechnician() throws SQLException{
	    List<User> technicians = new ArrayList<>();
	    Connect db = Connect.getConnection();
	    try (
	         PreparedStatement ps = db.prepareStatement("SELECT * FROM `Users` WHERE role = 'Technician'");
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

	//Method untuk mengambil data semua user di database
	public static ArrayList<User> getAllUser() throws SQLException{
		try {
			ArrayList<User> staffList = new ArrayList<User>();
			Connect db = Connect.getConnection();
			
			PreparedStatement ps = db.prepareStatement("SELECT * FROM `Users`");
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				return null;
			}
			
			while(rs.next()) {
				staffList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
			}
			
			return staffList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	//Mengecek username di database
	public boolean checkUsername(String username) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `Users` WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return true;
		}else return false;
	}

	//Method untuk mengambil satu user dan mereturnya kepada controller untuk memeriksa staff role
	public static User checkStaffRole(String staffId) throws SQLException{
		Connect db = Connect.getConnection();
		int idNum = Integer.parseInt(staffId);
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `Users` WHERE id = ?");
		ps.setInt(1, idNum);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
		}
		return null;
	}

	//Melakukan validasi user di dalam database
	public User validateUser(String username, String password) throws SQLException {
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("SELECT * FROM `Users` WHERE username = ? AND password = ?");
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
