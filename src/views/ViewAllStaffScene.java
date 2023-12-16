package views;

import java.util.ArrayList;

import controllers.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class ViewAllStaffScene implements IErrorMessage{
private static ViewAllStaffScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ViewAllStaffScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	VBox container;
	HBox roleDropDownDiv;
	Scene scene;
	// Table
	TableView<User> tableView;
	Button updateButton;
	ComboBox<String> roleDropDown;
	
	int selectedUserID;
	
	
	
	private ViewAllStaffScene() {
		initializeTable();
		initializeDropdown();
		initializeUpdateButton();
		addEventListener();
		// Add item to container
		container = new VBox();
		container.getChildren().addAll(tableView, roleDropDownDiv, updateButton);
			
		scene = new Scene(container);
		
		scene.setRoot(container);
	}
	
	private void initializeTable() {
		tableView = new TableView<User>();
		selectedUserID = 0;
		
		TableColumn<User, Integer> idColumn = new TableColumn<>("User ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("userID")
				);
		
		TableColumn<User, String> nameColumn = new TableColumn<>("User name");
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<>("userName")
				);
		
		TableColumn<User, Integer> ageColumn = new TableColumn<>("User age");
		ageColumn.setCellValueFactory(
				new PropertyValueFactory<>("userAge")
				);
		
		TableColumn<User, String> roleColumn = new TableColumn<>("User role");
		roleColumn.setCellValueFactory(
				new PropertyValueFactory<>("userRole")
				);
		
	    tableView.getColumns().add(idColumn);
	    tableView.getColumns().add(nameColumn);
	    tableView.getColumns().add(ageColumn);
	    tableView.getColumns().add(roleColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}
	
	private void initializeUpdateButton() {
		updateButton = new Button("Update User role");
		updateButton.setOnAction(event -> {
			if(selectedUserID == 0) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select a user !");
				
				alert.show();
				return;
			}
			String role = roleDropDown.getValue();
			if(role == null) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select a new role !");
				
				alert.show();
				return;
			}
			UserController controller2 = new UserController();
			
			try {
				if(!controller2.changeUserRole(this, selectedUserID, role)) return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Alert alert = new Alert
					(AlertType.INFORMATION, "User with id : " + selectedUserID + " role updated to " + role + " !");
			alert.show();
			ViewAllStaffScene.setScene(primaryStage);
		});
	}
	
	private void initializeDropdown() {
		String role [] = {
				"Admin",
				"Customer",
				"Operator",
				"Computer Technician"
		};
		
		roleDropDownDiv = new HBox();
		
		Label roleDropDownLabel = new Label("User role");
		roleDropDown = new ComboBox<String>(FXCollections.observableArrayList(role));
		roleDropDownDiv.getChildren().addAll(roleDropDownLabel, roleDropDown);
	}
	
	private void addEventListener() {
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if(newValue != null) {
					selectedUserID = newValue.getUserID();
				}
			}
		});
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}
	
	public void _repaint() {
		tableView.getItems().clear();
		UserController controller = new UserController();
		ArrayList<User> userList = controller.getAllUserData(this);
		for (User user : userList) {
			tableView.getItems().add(user);
		}
	}

	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		
	}
}
