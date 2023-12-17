package views;

import java.sql.SQLException;
import java.time.LocalDate;

import controllers.PCBookController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import models.PCBook;
import models.User;

public class BookingFormScene implements IErrorMessage {
	
	Stage primaryStage;
	Scene scene;
	PCBook pcBook;
	VBox container;
	
	Label errorLabel, pcIdLabel, bookDateLabel, bookingFormLabel;
	Button bookButton, backButton;
	TextField pcIdField;
	DatePicker bookDatePicker;
	User currentUser = null;
	
	private BookingFormScene() {
		container = new VBox();
		
		UserController userController = new UserController();
		this.currentUser = userController.getCurrentUser();
		
		errorLabel = new Label();
		bookingFormLabel = new Label("Booking Form");
		
		container.getChildren().add(navbar());
		container.getChildren().add(bookingFormLabel);
		
		Text text = new Text("\n");
        container.getChildren().add(text);
        
		intializeBookDatePicker();
		intializePcIdField();
		intializeBookButton();
		initializeBackButton();
		container.getChildren().add(errorLabel);
		
		scene = new Scene(container, 500, 500);
		scene.setRoot(container);
	}
	
	public static void setScene(Stage primaryStage) {
		BookingFormScene bookingFormScene = new BookingFormScene();		
		bookingFormScene._setScene(primaryStage);
	}

	//insialisasi set scene
	public static void setScene(Stage primaryStage, PCBook pcBook) {
		setScene(primaryStage);
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		primaryStage.setTitle("Booking PC Form");
	}
	
	private void intializeBookDatePicker() {
		VBox bookDateDiv = new VBox();
		bookDateLabel = new Label("Choose booking date");
		bookDatePicker = new DatePicker();
		bookDatePicker.setValue(LocalDate.now().plusDays(1));
		
		bookDateDiv.getChildren().addAll(bookDateLabel, bookDatePicker);
		container.getChildren().add(bookDateDiv);
	}
	
	private void intializePcIdField() {
		VBox pcIdDiv = new VBox();
		pcIdLabel = new Label("Choose PC ID: ");
		pcIdField = new TextField();
		
		pcIdDiv.getChildren().addAll(pcIdLabel, pcIdField);
		container.getChildren().add(pcIdDiv);
	}

	private void intializeBookButton() {
		bookButton = new Button("Book now");
		
		bookButton.setOnAction(event -> {
			String pcId = pcIdField.getText();
			System.out.println("PCID: " + pcId);
			
			LocalDate selectedDate = bookDatePicker.getValue();
			
			PCBookController pcBookController = new PCBookController();
			int userId = currentUser.getUserID();
			
			try {
				String result = pcBookController.addNewBook(pcId, userId,selectedDate);
				
				if (result.length() <= 0) {
					DisplayAllPCScene.setScene(primaryStage);
					return;
				}
				
				errorLabel.setText(result);
			} catch (SQLException e1) {
				e1.printStackTrace();
				errorLabel.setText(e1.getLocalizedMessage());
			}
		});
		
		container.getChildren().add(bookButton);
	}
	
	private void initializeBackButton() {
		backButton = new Button("Back Home");
		
		backButton.setOnAction(event -> {
			DisplayAllPCScene.setScene(primaryStage);
		});
		
		container.getChildren().add(backButton);
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
		MenuItem viewReportMI;
		MenuItem viewStaffJobMI;
		MenuItem viewTransMI;
		MenuItem viewStaffMI;
		MenuItem viewTechJobMI;
		MenuItem viewPCBookedMI;
		MenuItem viewCustTransMI;
		
		//Membuat navbar dan memberikan label setiap navbar
		private MenuBar navbar() {
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
			
			if(currentUser.getRole().equals("Admin")) {
				menuBar.getMenus().add(adminMenu);
				return menuBar;
			}
			if(currentUser.getRole().equals("Customer")) {
				menuBar.getMenus().add(custMenu);
				return menuBar;
			}
			if(currentUser.getRole().equals("Computer Technician")) {
				menuBar.getMenus().add(techMenu);
				return menuBar;
			}
			if(currentUser.getRole().equals("Operator")) {
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
			//	ReportView.setScene(primaryStage);
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
