package views;

import java.time.LocalDate;
import java.util.ArrayList;

import controllers.PCBookController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TableView;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import models.PCBook;
import models.User;

public class ViewAllTransactionHistory implements IErrorMessage {
	Stage primaryStage;
	Scene scene;
	PCBook pcBook;
	VBox container;

	Label errorLabel, bookedDatePickerLabel;
	Button datePickerButton, backButton;
	DatePicker bookedDatePicker;
	TableView<PCBook> pcBookTV;

	public static void setScene(Stage primaryStage) {
		ViewAllTransactionHistory viewAllTransactionHistory = new ViewAllTransactionHistory();
		viewAllTransactionHistory._setScene(primaryStage);
	}

	// insialisasi set scene
	public static void setScene(Stage primaryStage, PCBook pcBook) {
		setScene(primaryStage);
	}

	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("View All Transaction History");
		primaryStage.setScene(scene);
		_repaint();
	}

	private ViewAllTransactionHistory() {
		container = new VBox();
		
		initializeBackButton();
		errorLabel = new Label();
		
		container.getChildren().add(errorLabel);
		scene = new Scene(container, 500, 500);
		scene.setRoot(container);
	}

	

	private void initializeBackButton() {
		backButton = new Button("Back Home");

		backButton.setOnAction(event -> {
			DisplayAllPCScene.setScene(primaryStage);
		});

		container.getChildren().add(backButton);
	}

	public void _repaint() {
		pcBookTV.getItems().clear();
		PCBookController pcBookController = new PCBookController();
		ArrayList<PCBook> pcBookedList = pcBookController.getAllPCBookedData();
		
		for (PCBook pcBooked: pcBookedList) {
			pcBookTV.getItems().add(pcBooked);
		}

	}

	@Override
	public void displayErrorMessage(String error) {
		errorLabel.setText(error);
	}

//Inisiasi variable Navbar
	MenuBar menuBar;
	Menu adminMenu;
	Menu custMenu;
	Menu techMenu;
	Menu operatorMenu;
	MenuItem viewAdminPCMI;
	MenuItem viewCustPCMI;
	MenuItem viewOpPCMI;
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
		viewOpPCMI = new MenuItem("View All PC");
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
		operatorMenu.getItems().addAll(viewOpPCMI, viewPCBookedMI);
		
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
		viewOpPCMI.setOnAction((e)->{
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewReportMI.setOnAction((e)->{
			ReportView.setScene(primaryStage);
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
			ViewAllBookedDataScene.setScene(primaryStage);
		});
		viewCustTransMI.setOnAction((e)->{
			ViewAllTransactionHistory.setScene(primaryStage);
		});
	}
}
