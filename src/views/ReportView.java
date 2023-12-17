package views;

import controllers.ReportController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Report;
import models.User;
import java.util.ArrayList;

public class ReportView {
    private Stage primaryStage;
    VBox container;
    TableView<Report> tableView;
    Scene scene;
    
	//inisiasi instance
	private static ReportView instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ReportView();
		}
		instance._setScene(primaryStage);
	}
  	
  	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}
  	
  	//Mengupdate data yang berada di dalam table jika database diubah
  	public void _repaint() {
		tableView.getItems().clear();
		ReportController controller = new ReportController();
		ArrayList<Report> reportList = controller.getAllReportData();
		for (Report report : reportList) {
			tableView.getItems().add(report);
		}
	}
  	
  	private ReportView() {
  		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().add(tableView);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}

	//Inisaliasi tabel yang berisikan informasi report
	private void initializeTable() {
		tableView = new TableView<Report>();
		
		TableColumn<Report, Integer> idColumn = new TableColumn<>("Report Id");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("ReportID")
				);
		
		TableColumn<Report, String> roleColumn = new TableColumn<>("User Role");
		roleColumn.setCellValueFactory(
				new PropertyValueFactory<>("UserRole")
				);
		
		TableColumn<Report, String> pcColumn = new TableColumn<>("PC Id");
		pcColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcID")
				);
		
		TableColumn<Report, String> noteColumn = new TableColumn<>("Report Note");
		noteColumn.setCellValueFactory(
				new PropertyValueFactory<>("ReportNote")
				);
		
		TableColumn<Report, String> dateColumn = new TableColumn<>("Report Date");
		dateColumn.setCellValueFactory(
				new PropertyValueFactory<>("ReportDate")
				);
		
	    tableView.getColumns().add(idColumn);
		tableView.getColumns().add(roleColumn);
		tableView.getColumns().add(pcColumn);
		tableView.getColumns().add(noteColumn);
		tableView.getColumns().add(dateColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
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
			
		});
		viewCustTransMI.setOnAction((e)->{
			
		});
	}
}
