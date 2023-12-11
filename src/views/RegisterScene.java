package views;

import java.sql.SQLException;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterScene implements IErrorMessage{
	private static RegisterScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new RegisterScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	VBox container;
	Label usernameLabel;
	Label passwordLabel;
	Label confPassLabel;
	Label ageLabel;
	TextField usernameInput;
	TextField passwordInput;
	TextField confPassInput;
	Spinner<Integer> ageInput;
	Button registerButton;
	Hyperlink loginLink;
	Label errorLabel;
	
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
	
	private void initializeUsername() {
		HBox usernameDiv = new HBox();
		usernameLabel = new Label("Username");
		usernameInput = new TextField();
		usernameDiv.getChildren().addAll(usernameLabel, usernameInput);
		container.getChildren().add(usernameDiv);
	}
	
	private void initializePassword() {
		HBox passwordDiv = new HBox();
		passwordLabel = new Label("Password");
		passwordInput = new TextField();
		passwordDiv.getChildren().addAll(passwordLabel, passwordInput);
		container.getChildren().add(passwordDiv);
	}
	
	private void initializeConfirmPassword() {
		HBox confPassDiv = new HBox();
		confPassLabel = new Label("Confirm Password");
		confPassInput = new TextField();
		confPassDiv.getChildren().addAll(confPassLabel, confPassInput);
		container.getChildren().add(confPassDiv);
	}
	
	private void initializeAge() {
		HBox ageDiv = new HBox();
		ageLabel = new Label("Age");
		ageInput = new Spinner<Integer>(1, 100, 1);
		ageDiv.getChildren().addAll(ageLabel, ageInput);
		container.getChildren().add(ageDiv);
	}
	
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
	
	private void initializeLogin() {
		loginLink = new Hyperlink("Already have an account? login");
		loginLink.setOnAction(event ->{
			LoginScene.setScene(primaryStage);
		});
		container.getChildren().add(loginLink);
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		errorLabel.setText(error);
	}
	
}
