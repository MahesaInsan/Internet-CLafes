package views;

import java.util.ArrayList;

import controllers.PCController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;
import models.User;

public class DisplayAllPCScene  implements IErrorMessage{
	//inisiasi instance
	private static DisplayAllPCScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new DisplayAllPCScene();
		}
		instance._setScene(primaryStage);
	}

	//inisasi variable javafx
	Stage primaryStage;
	Scene scene;
	// Table
	TableView<PC> tableView;
	Button addButton;
	VBox container;

	//Constructor untuk memanggil setiap bagian yang ada di function
	private DisplayAllPCScene() {
		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(navbar(UserController.currentUser));
		container.getChildren().add(tableView);
		if(UserController.getAuthorization().equals("Admin")) initializeAddButton();
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}

	//Inisaliasi tabel yang berisikan informasi pc
	private void initializeTable() {
		tableView = new TableView<PC>();
		
		TableColumn<PC, String> idColumn = new TableColumn<>("PC ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcID")
				);
		
		TableColumn<PC, String> conditionColumn = new TableColumn<>("PC Condition");
		conditionColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcCondition")
				);
		
		TableColumn<PC, Void> actionColumn = new TableColumn<>("Action");
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
		
	    tableView.getColumns().add(idColumn);
		tableView.getColumns().add(conditionColumn);
		tableView.getColumns().add(actionColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}

	//Melakukan inisialisasi add button untuk memindahkan user ke view add pc
	private void initializeAddButton() {
		addButton = new Button("Add PC");
		addButton.setOnAction(event -> {
			AddPCScene.setScene(primaryStage);
		});
		container.getChildren().add(addButton);
	}

	//Melakukan inisiasi set scene dan juga meng assign primary stage
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}

	//Merubah table jika terdapat perubahan data atau inisiasi pertama
	public void _repaint() {
		tableView.getItems().clear();
		PCController controller = new PCController();
		ArrayList<PC> pcList = controller.getAllPCData(this);
		for (PC pc : pcList) {
			tableView.getItems().add(pc);
		}
	}

	//Memberikan error jika ada
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
