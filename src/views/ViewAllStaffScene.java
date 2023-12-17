package views;

import java.util.ArrayList;

import controllers.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

public class ViewAllStaffScene implements IErrorMessage{
	//inisialisasi instance
	private static ViewAllStaffScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ViewAllStaffScene();
		}
		instance._setScene(primaryStage);
	}

	//JavaFX component
	Stage primaryStage;
	VBox container;
	HBox roleDropDownDiv;
	Scene scene;
	// Table
	TableView<User> tableView;
	Button updateButton;
	ComboBox<String> roleDropDown;

	//variable untuk menyimpan event listener
	int selectedUserID;
	
	
	//method constructor untuk class ini
	private ViewAllStaffScene() {
		initializeTable();
		initializeDropdown();
		initializeUpdateButton();
		addEventListener();
		// Add item to container
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().addAll(tableView, roleDropDownDiv, updateButton);
			
		scene = new Scene(container);
		
		scene.setRoot(container);
	}

	//method untuk inisialisasi tableView yang berisi data user
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

	//method untuk inisialisasi update button dimana jika di click akan menjalankan logika update
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

	//method untuk inisialisasi dropdown role untuk memilih role baru untuk di update
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
	
	//Event listener untuk mengambil data dari table yang sedang di click
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

	//Method untuk set scene
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}

	//method repaint untuk mengambil data dari database untuk ditampilkan di tableview
	public void _repaint() {
		tableView.getItems().clear();
		UserController controller = new UserController();
		ArrayList<User> userList = controller.getAllUserData(this);
		for (User user : userList) {
			tableView.getItems().add(user);
		}
	}

	//Method untuk menampilkan error message
	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		
	}
	
	//Inisiasi variable Navbar
	MenuBar menuBar;
	Menu adminMenu;
	Menu custMenu;
	Menu techMenu;
	Menu operatorMenu;
	MenuItem viewAdminPCMI;
	MenuItem viewCustPCMI;
	MenuItem viewReportMI;
	MenuItem viewStaffJobMI;
	MenuItem viewTransMI;
	MenuItem viewStaffMI;
	MenuItem viewTechJobMI;
	MenuItem viewPCBookedMI;
	MenuItem viewCustTransMI;
	
	//Membuat navbar dan memberikan label setiap navbar
	private MenuBar navbar(User user) {
		menuBar = new MenuBar();
		
		adminMenu = new Menu("Main Menu");
		custMenu = new Menu("Main Menu");
		techMenu = new Menu("Main Menu");
		operatorMenu = new Menu("Main Menu");
		
		viewAdminPCMI = new MenuItem("View All PC");
		viewCustPCMI = new MenuItem("View All PC");
		viewReportMI = new MenuItem("View All Report");
		viewStaffJobMI = new MenuItem("View All Staff Job");
		viewTransMI = new MenuItem("View All Transaction");
		viewStaffMI = new MenuItem("View All Staff");
		viewTechJobMI = new MenuItem("View All Technician Job");
		viewPCBookedMI = new MenuItem("View All Booked PC");
		viewCustTransMI = new MenuItem("View All TransactionHistory");
		buttonConfig();
		
		adminMenu.getItems().addAll(viewAdminPCMI, viewStaffJobMI, viewStaffMI, viewReportMI, viewTransMI);
		custMenu.getItems().addAll(viewCustPCMI, viewCustTransMI);
		techMenu.getItems().addAll(viewTechJobMI);
		operatorMenu.getItems().addAll(viewPCBookedMI);
		
		if(user.getRole().equals("Admin")) {
			menuBar.getMenus().add(adminMenu);
			return menuBar;
		}
		if(user.getRole().equals("Customer")) {
			menuBar.getMenus().add(custMenu);
			return menuBar;
		}
		if(user.getRole().equals("Computer Technician")) {
			menuBar.getMenus().add(techMenu);
			return menuBar;
		}
		if(user.getRole().equals("Operator")) {
			menuBar.getMenus().add(operatorMenu);
			return menuBar;
		}
		return menuBar;
	}
	
	//Menginisiasi functional dari masing-masing submenu untuk melakukan navigasi
	private void buttonConfig() {
		viewAdminPCMI.setOnAction((e)->{
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewCustPCMI.setOnAction((e)->{
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewReportMI.setOnAction((e)->{
//			ReportView.setScene(primaryStage);
		});
		viewStaffJobMI.setOnAction((e)->{
			ViewAllJobScene.setScene(primaryStage);
		});
		viewTransMI.setOnAction((e)->{
			ViewAllTransactionScene.setScene(primaryStage);
		});
		viewStaffMI.setOnAction((e)->{
			ViewAllStaffScene.setScene(primaryStage);
		});
		viewTechJobMI.setOnAction((e)->{
			TechnicianJobView.setScene(primaryStage);
		});
		viewPCBookedMI.setOnAction((e)->{
			
		});
		viewCustTransMI.setOnAction((e)->{
			
		});
	}
}
