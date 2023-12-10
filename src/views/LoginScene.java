package views;

import java.sql.SQLException;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScene implements IErrorMessage{
	private static LoginScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new LoginScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	VBox container;
	Label usernameLabel;
	Label passwordLabel;
	TextField usernameInput;
	TextField passwordInput;
	Button loginButton;
	Label errorLabel;
	
	private LoginScene() {
		container = new VBox();
		initializeUsername();
		initializePassword();
		errorLabel = new Label();
		container.getChildren().add(errorLabel);
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
	
	private void initializeLogin() {
		loginButton = new Button("Login");
		loginButton.setOnAction(event ->{
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			UserController controller = new UserController();
			try {
				controller.loginUser(this, username, password);;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		container.getChildren().add(loginButton);
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
