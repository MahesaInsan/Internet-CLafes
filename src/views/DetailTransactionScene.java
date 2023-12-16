package views;

import java.util.ArrayList;

import controllers.TransactionController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.TransactionDetail;
import models.TransactionHeader;

public class DetailTransactionScene {
	private static DetailTransactionScene instance;
	
	public static void setScene(Stage primaryStage, TransactionHeader tHead) {
		if(instance == null) {
			instance = new DetailTransactionScene(tHead);
		}
		instance._setScene(primaryStage);
	}
	
	TransactionHeader tHead;
	Stage primaryStage;
	Scene scene;
	// Table
	TableView<TransactionDetail> tableView;
	Button addButton;
	VBox container;
	
	private DetailTransactionScene(TransactionHeader tHead) {
		this.tHead = tHead;
		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().add(tableView);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}
	
	private void initializeTable() {
		tableView = new TableView<TransactionDetail>();
		
		TableColumn<TransactionDetail, Integer> idColumn = new TableColumn<>("Transaction ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("transactionID")
				);
		
		TableColumn<TransactionDetail, String> customerColumn = new TableColumn<>("Customer Name");
		customerColumn.setCellValueFactory(
				new PropertyValueFactory<>("customerName")
				);
		
		TableColumn<TransactionDetail, String> pcColumn = new TableColumn<>("PC ID");
		pcColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcID")
				);
		
		TableColumn<TransactionDetail, String> dateColumn = new TableColumn<>("Transaction Date");
		dateColumn.setCellValueFactory(
				new PropertyValueFactory<>("bookedTime")
				);
		
	    tableView.getColumns().add(idColumn);
		tableView.getColumns().add(customerColumn);
		tableView.getColumns().add(pcColumn);
		tableView.getColumns().add(dateColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}
	
	public void _repaint() {
		tableView.getItems().clear();
		TransactionController controller = new TransactionController();
		ArrayList<TransactionDetail> tDetList = controller.getAllTransactionDetail(tHead.getTransactionID());
		for (TransactionDetail tDet : tDetList) {
			tableView.getItems().add(tDet);
		}
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
