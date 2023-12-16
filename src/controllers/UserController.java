package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import models.User;
import views.IErrorMessage;

public class UserController {
	//Menyimpan user yang sudah di authorisasi
	public static User currentUser;

	//Mengecek role dari user
	public static String getAuthorization() {
		System.out.println(currentUser.getRole());
		return currentUser.getRole();
	}

	//Menambahkan user ke dalam model dan melakukan validasi pada input yang dilakukan oleh user
	public boolean addNewUser(IErrorMessage error, String username, String password, String confpass, int age) throws SQLException{
		final boolean CHECKFIELD = (username.equals("") || password.equals("") || confpass.equals(""));
		
		System.out.println(username + " " + password + " " + age);
		if(CHECKFIELD) {
			error.displayErrorMessage("All fields must be filled");
			return false;
		}
		
		if(checkUsername(username)) {
			System.out.println("Username is already in used");
			error.displayErrorMessage("Username is already in used");
			return false;
		}else if(username.length() < 7) {
			error.displayErrorMessage("Username must be longer than 7 letters");
			return false;
		}
		
		if(password.length() < 6) {
			error.displayErrorMessage("Password must be longer than 6 letters");
			return false;
		}else if(!checkAlphaNumeric(password)) {
			error.displayErrorMessage("Password must be alphanumeric");
			return false;
		}
		
		if(!confpass.equals(password)) {
			error.displayErrorMessage("Confirm password and password must be the same");
			return false;
		}
				
		if(age <= 13 || age >= 65) {
			error.displayErrorMessage("You must be older than 13 and younger than 65");
			return false;
		}
		
		User user = new User();
		user.addNewUser(username, password, age);
		loginUser(error, username, password);
		return true;
	}
	
	//Mengubah role user
	public boolean changeUserRole(IErrorMessage error, int userID, String newRole) throws SQLException{
		if(newRole == null) {
			error.displayErrorMessage("Role must be selected!");
			return false;
		}
		
		User.changeUserRole(userID, newRole);
		return true;
		
	}

	//Mengambil semua data technician
	public void getAllTechnician() {
		
	}

	//Mengambil semua data user dari database dan memberikan error jika fetch tidak berhasil
	public ArrayList<User> getAllUserData(IErrorMessage error) {
		try {
			return User.getAllUser();
		} catch (SQLException e) {
			error.displayErrorMessage("Error fetching user data");
			e.printStackTrace();
			return null;
		}
	}

	//Mengecek apakah username sudah diambil
	private boolean checkUsername(String username) throws SQLException {
		User user = new User();
		return user.checkUsername(username);
	}

	//Melakukan login user dan juga validasi pada input yang dilakukan user
	public boolean loginUser(IErrorMessage error, String username, String password) throws SQLException {
		final boolean CHECKFIELD = (username.equals("") || password.equals(""));
		
		if(CHECKFIELD) {
			error.displayErrorMessage("All fields must be filled");
			return false;
		}
		
		if(!checkUsername(username)) {
			System.out.println("Username is not found");
			error.displayErrorMessage("Username is not found");
			return false;
		}
		
		User user = new User();
		if(user.validateUser(username, password) == null){
			System.out.println("Password is incorrect");
			error.displayErrorMessage("Password is incorrect");
			return false;
		}else {
			currentUser = user;
			return true;
		}
	}

	//Melakukan check apakah password yang diinput alphanumeric
	private boolean checkAlphaNumeric(String password) {
		for (char ch : password.toCharArray()) {
			if(!Character.isLetterOrDigit(ch)) {
				return false;
			}
		}
		return true;
	}
}
