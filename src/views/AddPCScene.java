package views;

import java.sql.SQLException;

import controllers.PCController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;

public class AddPCScene implements IErrorMessage{
	public static void setScene(Stage primaryStage) {
		AddPCScene addPC = new AddPCScene();
		addPC._setScene(primaryStage);
	}
	
	public static void setScene(Stage primaryStage, PC pc) {
		setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	VBox container;
	
	Label pcIDLabel;
	Label pcConditionLabel;
	TextField pcIDInput;
	ComboBox<String> pcConditionInput;
	Label errorMsg;
	Button addButton;
	
	private AddPCScene() {
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		initializePCID();
		
		errorMsg = new Label();
		container.getChildren().add(errorMsg);
		
		initializeAddButton();
		container.getChildren().add(addButton);
		
		scene = new Scene(container);
		scene.setRoot(container);
	}
	
	
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID");
		pcIDInput = new TextField();
		pcIDDiv.getChildren().addAll(pcIDLabel, pcIDInput);
		container.getChildren().add(pcIDDiv);
	}
	
	private void initializeAddButton() {
		addButton = new Button("Save PC");
		addButton.setOnAction(event -> {
			String id = pcIDInput.getText();
			PCController controller = new PCController();
			
			try {
				if(!controller.addNewPC(this, id)) return;
				System.out.println(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			DisplayAllPCScene.setScene(primaryStage);
		});
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		errorMsg.setText(error);
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
	public MenuBar initiate(User user) {
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
			
		});
		viewCustTransMI.setOnAction((e)->{
			
		});
	}
}
