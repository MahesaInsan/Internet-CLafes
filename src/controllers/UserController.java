package controllers;

import java.sql.SQLException;

import models.User;
import views.IErrorMessage;

public class UserController {
	
	public static User currentUser;
	
	public void getUserData(String username, String password) {
		
	}
	
	public static String getAuthorization() {
		System.out.println(currentUser.getRole());
		return currentUser.getRole();
	}
	
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
		return true;
	}
	
	
	public void changeUserRole(String userID, String newRole) {
		
	}
	public void getAllTechnician() {
		
	}
	public void getAllUserData() {
		
	}
	
	private boolean checkUsername(String username) throws SQLException {
		User user = new User();
		return user.checkUsername(username);
	}
	
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
			user.getAllUserData();
			currentUser = user;
			return true;
		}
	}
	
	private boolean checkAlphaNumeric(String password) {
		for (char ch : password.toCharArray()) {
			if(!Character.isLetterOrDigit(ch)) {
				return false;
			}
		}
		return true;
	}
}
