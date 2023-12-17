package views;

import java.sql.SQLException;
import java.util.ArrayList;

import controllers.PCBookController;
import controllers.PCController;
import controllers.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.PC;
import models.PCBook;
import models.TransactionDetail;
import models.TransactionHeader;
import models.User;

public class DisplayAllPCScene implements IErrorMessage {
	// inisiasi instance
	private static DisplayAllPCScene instance;

	public static void setScene(Stage primaryStage) {
		if (instance == null) {
			instance = new DisplayAllPCScene();
		}
		instance._setScene(primaryStage);
	}

	// inisasi variable javafx
	Stage primaryStage;
	Scene scene;
	// Table
	TableView<PC> tableView;
	Button addButton, bookButton;
	VBox container;
	User currentUser = null;

	// Constructor untuk memanggil setiap bagian yang ada di function
	private DisplayAllPCScene() {
		UserController userController = new UserController();
		this.currentUser = userController.getCurrentUser();
		
		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().add(tableView);
		
		if (UserController.getAuthorization().equals("Admin"))
			initializeAddButton();

		scene = new Scene(container);
		scene.setRoot(container);
	}

	// Inisaliasi tabel yang berisikan informasi pc
	private void initializeTable() {
		tableView = new TableView<PC>();

		TableColumn<PC, String> idColumn = new TableColumn<>("PC ID");

		TableColumn<PC, String> conditionColumn = new TableColumn<>("PC Condition");

		TableColumn<PC, Void> actionColumn = new TableColumn<>("Action");

		TableColumn<PC, Void> reportColumn = new TableColumn<>("Report");
		
		TableColumn<PC, String> statusColumn = new TableColumn<>("Status");

		TableColumn<PC, Void> bookPCColumn = new TableColumn<>("Book");
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("pcID"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		conditionColumn.setCellValueFactory(new PropertyValueFactory<>("pcCondition"));

		reportColumn.setCellFactory(param -> new TableCell<>() {
			private final Button reportButton = new Button("Report");
			
			{
				reportButton.setOnAction(event -> {
					
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				
				if (empty) {
					setGraphic(null);
				} else {
					HBox buttons = new HBox(reportButton);
					setGraphic(buttons);
				}
			}

		});
		
		actionColumn.setCellFactory(param -> new TableCell<>() {
			private final Button detailButton = new Button("Detail");
			{
				detailButton.setOnAction(event -> {
					PC pc = getTableView().getItems().get(getIndex());
					DetailPCScene.setScene(primaryStage, pc);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				
				if (empty) {
					setGraphic(null);
				} else {
					HBox buttons = new HBox(detailButton);
					setGraphic(buttons);
				}
			}
		});

		bookPCColumn.setCellFactory(param -> new TableCell<PC, Void>() {
			private Button bookButton = new Button("Booking now");

			{ 
				bookButton.setOnAction(event -> {
					BookingFormScene.setScene(primaryStage);
				});
			}
			
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
				} else {
					
					PC pc = getTableView().getItems().get(getIndex());

		            if (pc != null && "Available".equals(pc.getStatus())) {
		                bookButton.setDisable(false);
		            } else {
		                bookButton.setDisable(true);
		            }
		            
					setGraphic(bookButton);
				}
			}
		});

		// Add to column table headers
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(conditionColumn);
		
		String role = currentUser.getRole();
		
		if (role.equals("Admin")) {
			tableView.getColumns().add(actionColumn);
		}
		
		tableView.getColumns().add(statusColumn);

		if (role.equals("Customer")) {
			tableView.getColumns().add(bookPCColumn);
		}
		
		if (role.equals("Operator") || role.equals("Customer")) {
			tableView.getColumns().add(reportColumn);
		}
		
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}

	// Melakukan inisialisasi add button untuk memindahkan user ke view add pc
	private void initializeAddButton() {
		addButton = new Button("Add PC");
		addButton.setOnAction(event -> {
			AddPCScene.setScene(primaryStage);
		});
		container.getChildren().add(addButton);
	}

	
	// Melakukan inisiasi set scene dan juga meng assign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Home");
		primaryStage.setScene(scene);
		_repaint();
	}

	// Merubah table jika terdapat perubahan data atau inisiasi pertama
	public void _repaint() {
		tableView.getItems().clear();
		
		PCController controller = new PCController();
		PCBookController pcBookController = new PCBookController();
		
		ArrayList<PC> pcList = controller.getAllPCData(this);
		ArrayList<PCBook> pcBooks = pcBookController.getAllPCBookedData();

		for (PC pc : pcList) {
			boolean isBooked = false;

			for (PCBook pcBook : pcBooks) {
				if (pc.getPcID().equals(pcBook.getPcID())) {
					isBooked = true;
					break;
				}
			}

			pc.setStatus(isBooked ? "Booked" : "Available");
			tableView.getItems().add(pc);
		}
	}

	// Memberikan error jika ada
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
