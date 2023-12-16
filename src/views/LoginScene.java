package views;

import java.sql.SQLException;

import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScene implements IErrorMessage{
	//Melakukan inisialisasi instance
	private static LoginScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new LoginScene();
		}
		instance._setScene(primaryStage);
	}

	//Inisasi variable javafx
	Stage primaryStage;
	Scene scene;
	VBox container;
	Label usernameLabel;
	Label passwordLabel;
	TextField usernameInput;
	PasswordField passwordInput;
	Button loginButton;
	Hyperlink registerLink;
	Label errorLabel;

	//Constructor login untuk memanggil function yang ada
	private LoginScene() {
		container = new VBox();
		initializeUsername();
		initializePassword();
		errorLabel = new Label();
		container.getChildren().add(errorLabel);
		initializeLogin();
		initializeRegisterLink();
		
		scene = new Scene(container);
		scene.setRoot(container);
	}

	//Inisialisasi untuk meminta input username
	private void initializeUsername() {
		HBox usernameDiv = new HBox();
		usernameLabel = new Label("Username");
		usernameInput = new TextField();
		usernameDiv.getChildren().addAll(usernameLabel, usernameInput);
		container.getChildren().add(usernameDiv);
	}

	//Inisialisasi untuk meminta input password
	private void initializePassword() {
		HBox passwordDiv = new HBox();
		passwordLabel = new Label("Password");
		passwordInput = new PasswordField();
		passwordDiv.getChildren().addAll(passwordLabel, passwordInput);
		container.getChildren().add(passwordDiv);
	}

	//Memberikan event handler untuk button login
	private void initializeLogin() {
		loginButton = new Button("Login");
		loginButton.setOnAction(event ->{
			String username = usernameInput.getText();
			String password = passwordInput.getText();
			UserController controller = new UserController();
			try {
				if(!controller.loginUser(this, username, password)) return;
				
				DisplayAllPCScene.setScene(primaryStage);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		container.getChildren().add(loginButton);
	}

	//Memberikan hyperlink untuk memberikan opsi register
	private void initializeRegisterLink() {
		registerLink = new Hyperlink("Don't have an account? Register");
		registerLink.setOnAction(event ->{
			RegisterScene.setScene(primaryStage);
		});
		container.getChildren().add(registerLink);
	}

	//Melakukan inisiasi scene dan mengassign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	//Memberikan display error
	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		errorLabel.setText(error);
	}
}
