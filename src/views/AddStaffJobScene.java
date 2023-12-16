package views;

import controllers.JobController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddStaffJobScene implements IErrorMessage{
	public static void setScene(Stage primaryStage) {
		AddStaffJobScene addJob = new AddStaffJobScene();
		addJob._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	VBox container;
	// Form
	HBox staffDiv;
	Label staffLabel;
	TextField staffInput;
	HBox pcDiv;
	Label pcLabel;
	TextField pcInput;
	Button addButton;
	Label errorMsg;
	
	private AddStaffJobScene() {
		initializeStaffForm();
		initializePcForm();
		initializeAddButton();
		
		errorMsg = new Label();
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().addAll(staffDiv, errorMsg, pcDiv, addButton);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
		
	}
	
	private void initializeStaffForm() {
		staffDiv = new HBox();
		
		staffLabel = new Label("Staff ID");
		staffInput = new TextField();
		staffDiv.getChildren().addAll(staffLabel, staffInput);
	}
	
	private void initializePcForm() {
		pcDiv = new HBox();
		
		pcLabel = new Label("Pc ID");
		pcInput = new TextField();
		pcDiv.getChildren().addAll(pcLabel, pcInput);
	}
	
	private void initializeAddButton() {
		addButton = new Button("Add job");
		addButton.setOnAction(event -> {
			String staffId = staffInput.getText();
			String pcId = pcInput.getText();
			JobController controller = new JobController();
			
			try {
				if(!controller.addNewJob(this, staffId, pcId)) return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			ViewAllJobScene.setScene(primaryStage);
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
