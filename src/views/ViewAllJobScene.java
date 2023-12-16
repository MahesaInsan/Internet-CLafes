package views;

import java.util.ArrayList;

import controllers.JobController;
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
import models.Job;
import models.User;

public class ViewAllJobScene implements IErrorMessage{
	//initialize instance
	private static ViewAllJobScene instance;
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ViewAllJobScene();
		}
		instance._setScene(primaryStage);
	}

	//javafx component
	Stage primaryStage;
	VBox container;
	HBox updateJobDiv;
	Scene scene;
	// Table
	TableView<Job> tableView;
	Button addButton;
	Button updateButton;
	ComboBox<String> jobDropDown;
	Label jobDropDownLabel;

	//Variable untuk event listener
	int selectedJobID = 0;
	String selectedJobPC = "";

	//Constructor untuk class ini
	private ViewAllJobScene() {
		initializeTable();
		initializeAddButton();
		initializeDropDown();
		initializeUpdateButton();
		addEventListener();
		
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().addAll(tableView, addButton, updateJobDiv, updateButton);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}

	//Inisialisasi table, dipisah kedalam method ini supaya lebih rapih
	private void initializeTable() {
		tableView = new TableView<Job>();
		
		TableColumn<Job, String> idColumn = new TableColumn<>("Job ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("jobID")
				);
		
		TableColumn<Job, String> staffColumn = new TableColumn<>("Staff ID");
		staffColumn.setCellValueFactory(
				new PropertyValueFactory<>("userID")
				);
		
		TableColumn<Job, String> pcColumn = new TableColumn<>("Pc ID");
		pcColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcID")
				);
		
		TableColumn<Job, String> statusColumn = new TableColumn<>("Job status");
		statusColumn.setCellValueFactory(
				new PropertyValueFactory<>("jobStatus")
				);
		
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(staffColumn);
		tableView.getColumns().add(pcColumn);
		tableView.getColumns().add(statusColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}

	//Inisialisasi add button dimana jika di click akan redirect ke scene baru yaitu AddStaffJobScene
	private void initializeAddButton() {
		addButton = new Button("Add Job");
		addButton.setOnAction(event -> {
			AddStaffJobScene.setScene(primaryStage);
		});
	}

	//Inisialisasi drop down untuk memilih status dari job
	private void initializeDropDown() {
		String status[] = {
				"Complete",
				"UnComplete"
		};
		
		updateJobDiv = new HBox();
		
		jobDropDownLabel = new Label("Job status");
		jobDropDown = new ComboBox<String>(FXCollections.observableArrayList(status));
		updateJobDiv.getChildren().addAll(jobDropDownLabel, jobDropDown);
	}

	//Inisialisasi untuk button update dimana jika di click akan menjalankan logika update
	private void initializeUpdateButton() {
		updateButton = new Button("Update job");
		updateButton.setOnAction(event -> {
			if(selectedJobID == 0) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select a Job !");
				
				alert.show();
				return;
			}
			String status = jobDropDown.getValue();
			if(status == null) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select the job status !");
				
				alert.show();
				return;
			}
			JobController controller2 = new JobController();
			
			try {
				if(!controller2.updateJobStatus(selectedJobID, status, selectedJobPC)) return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Alert alert = new Alert
					(AlertType.INFORMATION, "Job with id : " + selectedJobID + " status updated to " + status + " !");
			alert.show();
			ViewAllJobScene.setScene(primaryStage);
		});
	}

	//Event listener untuk mengambil data dari table yang sedang di click
	private void addEventListener() {
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Job>() {
			public void changed(ObservableValue<? extends Job> observable, Job oldValue, Job newValue) {
				if(newValue != null) {
					selectedJobID = newValue.getJobID();
					selectedJobPC = newValue.getPcID();
				}
			}
		});
	}

	//method untuk set scene
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}

	//method repaint untuk mengambil data dari database untuk ditampilkan di tableview
	public void _repaint() {
		tableView.getItems().clear();
		JobController controller = new JobController();
		ArrayList<Job> jobList = controller.getAllJobData(this);
		for(Job job : jobList) {
			tableView.getItems().add(job);
		}
	}
	
	//method untuk display error message
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
