package views;

import java.sql.SQLException;

import controllers.PCController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;

public class EditPCScene implements IErrorMessage{
	//Inisiasi view editPc
	public static void setScene(Stage primaryStage, PC pc) {
		EditPCScene editPC = new EditPCScene(pc);
		editPC._setScene(primaryStage);
	}

	//Melakukan inisiasi set scene
	public static void setScene(Stage primaryStage) {
		setScene(primaryStage);
	}

	//Inisiasi variable javafx
	PC pc;
	Stage primaryStage;
	Scene scene;
	VBox container;
	
	Label pcIDLabel;
	Label pcConditionLabel;
	ComboBox<String> pcConditionInput;
	Label errorMsg;
	Button saveButton;

	//Constructor untuk memanggil bagian dari view
	private EditPCScene(PC pc) {
		this.pc = pc;
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		initializePCID();
		initializePCCondition();
		
		errorMsg = new Label();
		container.getChildren().add(errorMsg);
		
		initializeSaveButton();
		container.getChildren().add(saveButton);
		
		scene = new Scene(container);
		scene.setRoot(container);
	}
	
	//Memberikan display pc yang akan diubah
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID: " + pc.getPcID());
		pcIDDiv.getChildren().add(pcIDLabel);
		container.getChildren().add(pcIDDiv);
	}

	//Memberikan menu drop down untuk mengubah kondisi pc
	private void initializePCCondition() {
		String conditions [] = {
				"Usable",
				"Maintenance",
				"Broken"
		};
		
		HBox pcConditionDiv = new HBox();
		
		Label pcConditionLabel = new Label("PC Condition");
		pcConditionInput = new ComboBox<String>(FXCollections.observableArrayList(conditions));
		pcConditionDiv.getChildren().addAll(pcConditionLabel, pcConditionInput);
		container.getChildren().add(pcConditionDiv);
	}

	//Melakukan inisialisasi save button yang akan diklik untuk mengubah data pc
	private void initializeSaveButton() {
		saveButton = new Button("Save PC");
		saveButton.setOnAction(event -> {
			String condition = pcConditionInput.getValue();
			PCController controller = new PCController();
			
			try {
				if(!controller.updatePCCondition(this, pc.getPcID(), condition)) return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DisplayAllPCScene.setScene(primaryStage);
		});
	}

	//Melakukan inisiasi set scene dan assign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	//Memberikan display error
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
	private MenuBar initiate(User user) {
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
