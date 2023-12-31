package views;

import controllers.PCController;
import controllers.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;
import models.User;

public class DetailPCScene implements IErrorMessage{
	//Inisialisasi instance view
	public static void setScene(Stage primaryStage, PC pc) {
		DetailPCScene detailPC = new DetailPCScene(pc);
		detailPC._setScene(primaryStage);
	}

	//Inisiasis set scene
	public static void setScene(Stage primaryStage) {
		setScene(primaryStage);
	}

	//Inisiasi variable javafx
	PC pc;
	Stage primaryStage;
	Scene scene;
	VBox container;
	VBox mainDiv;
	
	Label pcIDLabel;
	Label pcConditionLabel;
	Label errorMsg;
	Button editButton;
	Button deleteButton;

	//Constructor untuk memanggil masing-masing function bagian
	private DetailPCScene(PC pc) {
		this.pc = pc;
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		mainDiv = new VBox(10);
		mainDiv.setPadding(new Insets(10, 10, 10, 10));
		mainDiv.getChildren().add(new Label("PC DETAIL"));
		initializePCID();
		initializePCCondition();
		
		errorMsg = new Label();
		mainDiv.getChildren().add(errorMsg);
		container.getChildren().add(mainDiv);
		
		if(UserController.getAuthorization().equals("Admin")) initializeButton();
		
		scene = new Scene(container);
		scene.setRoot(container);
		mainDiv.setAlignment(Pos.CENTER);
	}
	
	//Inisiasi untuk display pc id
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID: " + pc.getPcID());
		pcIDDiv.getChildren().addAll(pcIDLabel);
		mainDiv.getChildren().add(pcIDDiv);
	}

	//inisiasi untuk display pc condition
	private void initializePCCondition() {
		HBox pcConditionDiv = new HBox();
		
		Label pcConditionLabel = new Label("PC Condition: " + pc.getPcCondition());
		pcConditionDiv.getChildren().add(pcConditionLabel);
		mainDiv.getChildren().add(pcConditionDiv);
	}

	//inisiasi button untuk edit dan delete
	private void initializeButton() {
		HBox buttonDiv = new HBox(30);
		editButton = new Button("Edit");
		editButton.setOnAction(event -> {
			EditPCScene.setScene(primaryStage, pc);
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> {
			PCController controller = new PCController();
			if(!controller.deletePC(this, pc.getPcID())) return;
			this.pc = null;
			
			DisplayAllPCScene.setScene(primaryStage);
		});
		buttonDiv.getChildren().addAll(editButton, deleteButton);
		mainDiv.getChildren().addAll(buttonDiv);
	}

	//inisasi set scene dan melakukan assign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	//display error message
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
