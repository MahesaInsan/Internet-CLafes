package views;

import java.sql.SQLException;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterScene implements IErrorMessage{
	//Inisiasi instance
	private static RegisterScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new RegisterScene();
		}
		instance._setScene(primaryStage);
	}

	//Inisiasi variable javafx
	Stage primaryStage;
	Scene scene;
	VBox container;
	Label usernameLabel;
	Label passwordLabel;
	Label confPassLabel;
	Label ageLabel;
	TextField usernameInput;
	PasswordField passwordInput;
	PasswordField confPassInput;
	Spinner<Integer> ageInput;
	Button registerButton;
	Hyperlink loginLink;
	Label errorLabel;

	//Constructor untuk memanggil variable dan bagian dari register
	private RegisterScene() {
		container = new VBox();
		initializeUsername();
		initializePassword();
		initializeConfirmPassword();
		initializeAge();
		errorLabel = new Label();
		container.getChildren().add(errorLabel);
		initializeRegister();
		initializeLogin();
		
		scene = new Scene(container);
		scene.setRoot(container);
	}

	//Menampilkan div untuk input username
	private void initializeUsername() {
		HBox usernameDiv = new HBox();
		usernameLabel = new Label("Username");
		usernameInput = new TextField();
		usernameDiv.getChildren().addAll(usernameLabel, usernameInput);
		container.getChildren().add(usernameDiv);
	}

	//Menampilkan div untuk input password
	private void initializePassword() {
		HBox passwordDiv = new HBox();
		passwordLabel = new Label("Password");
		passwordInput = new PasswordField();
		passwordDiv.getChildren().addAll(passwordLabel, passwordInput);
		container.getChildren().add(passwordDiv);
	}

	//Menampilkan div untuk input confirm password
	private void initializeConfirmPassword() {
		HBox confPassDiv = new HBox();
		confPassLabel = new Label("Confirm Password");
		confPassInput = new PasswordField();
		confPassDiv.getChildren().addAll(confPassLabel, confPassInput);
		container.getChildren().add(confPassDiv);
	}

	//Menampilkan div untuk input umur
	private void initializeAge() {
		HBox ageDiv = new HBox();
		ageLabel = new Label("Age");
		ageInput = new Spinner<Integer>(1, 100, 1);
		ageDiv.getChildren().addAll(ageLabel, ageInput);
		container.getChildren().add(ageDiv);
	}

	//Memberikan event handler untuk register button
	private void initializeRegister() {
		registerButton = new Button("Register");
		registerButton.setOnAction(event ->{
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			String confpass = confPassInput.getText();
			Integer age = ageInput.getValue();
			UserController controller = new UserController();
			try {
				if(!controller.addNewUser(this, username, password, confpass, age)) return;
				
				DisplayAllPCScene.setScene(primaryStage);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		container.getChildren().add(registerButton);
	}

	//Memberikan hyperlink untuk opsi login bagi user
	private void initializeLogin() {
		loginLink = new Hyperlink("Already have an account? login");
		loginLink.setOnAction(event ->{
			LoginScene.setScene(primaryStage);
		});
		container.getChildren().add(loginLink);
	}

	//Melakukan inisasi scene dan mengassign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	//Memberikan display berupa error message
	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		errorLabel.setText(error);
	}
	
}
