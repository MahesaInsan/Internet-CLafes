package views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controllers.PCBookController;
import controllers.PCController;
import controllers.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import models.PCBook;
import models.User;

public class ViewAllBookedDataScene implements IErrorMessage {
	Stage primaryStage;
	Scene scene;
	PCBook pcBook;
	VBox container;

	Label errorLabel, bookedDatePickerLabel, currentSetTimeBookingLabel, pcIDDropDownLabel;
	Button datePickerButton, finishBookedPCButton, assignButton;
	DatePicker bookedDatePicker;
	TableView<PCBook> pcBookTV;
	ComboBox<String> pcListDropDown;
	String selectedPcID;
	List<String> pcListId = null;
	ArrayList<PCBook> pcBookedList = new ArrayList<PCBook>();

	public static void setScene(Stage primaryStage) {
		ViewAllBookedDataScene viewAllBookedDataScene = new ViewAllBookedDataScene();
		viewAllBookedDataScene._setScene(primaryStage);
	}

	// insialisasi set scene
	public static void setScene(Stage primaryStage, PCBook pcBook) {
		setScene(primaryStage);
	}

	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("View All Booked PC Data");
		primaryStage.setScene(scene);
		_repaint(LocalDate.now());
	}

	private ViewAllBookedDataScene() {
		initializePcBookTable();

		container = new VBox();

		errorLabel = new Label();

		container.getChildren().add(navbar(UserController.currentUser));
		initializeBookedDatePicker();
		container.getChildren().add(pcBookTV);
		intializeFinishButtonBookedPC();
		addEventListener();
		initializeDropdown();
		initializeAssignToNewPCButton();
		container.getChildren().add(assignButton);
		container.getChildren().add(errorLabel);

		scene = new Scene(container, 500, 500);
		scene.setRoot(container);
	}
	
	private void initializeAssignToNewPCButton() {
		assignButton = new Button("Assign to New PC");
		assignButton.setOnAction(event -> {
			if (selectedPcID.isBlank()) {
				errorLabel.setText("Error: Please select the Row or PC you want to assign!");
			}
		});
	}
	
	private void addEventListener() {
		pcBookTV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PCBook>() {
			@Override
			public void changed(ObservableValue<? extends PCBook> observable, PCBook oldValue, PCBook newValue) {
				if(newValue != null) {
					selectedPcID = newValue.getPcID();
				}
			}
		});
	}
	
	private void initializeDropdown() {
		
		PCController pcController = new PCController();
		ArrayList<PC> pcList = pcController.getAllPCData(this);
		
		Label pcIDDropDownLabel = new Label("Select PC ID: ");
		pcListDropDown = new ComboBox<String>(FXCollections.observableArrayList(pcListId));
		container.getChildren().addAll(pcIDDropDownLabel, pcListDropDown);
	}

	private void initializePcBookTable() {
		pcBookTV = new TableView<PCBook>();

		TableColumn<PCBook, String> bookIDCol = new TableColumn<>("Book ID");
		TableColumn<PCBook, String> pcIDCol = new TableColumn<>("PC ID");
		TableColumn<PCBook, String> userIDCol = new TableColumn<>("User ID");
		TableColumn<PCBook, String> bookedDateCol = new TableColumn<>("Booked Date");
		TableColumn<PCBook, Void> cancelBookCol = new TableColumn<>("Cancel Book");

		bookIDCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
		pcIDCol.setCellValueFactory(new PropertyValueFactory<>("pcID"));
		userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
		bookedDateCol.setCellValueFactory(new PropertyValueFactory<>("bookedDate"));

		pcBookTV.setOnMouseClicked(event -> {
			// Check if the click was a primary (left) click and if a row was selected
			if (event.getClickCount() == 1 && !pcBookTV.getSelectionModel().isEmpty()) {
				PCBook selectePC = pcBookTV.getSelectionModel().getSelectedItem();
				// System.out.println("Selected Book PC: " + selectePC.getPcID());
			}
		});

		cancelBookCol.setCellFactory(param -> new TableCell<>() {
			private final Button cancelBookButton = new Button("Cancel book");

			{
				cancelBookButton.setOnAction(event -> {
					PCBook pcBook = getTableView().getItems().get(getIndex());
					PCBookController pcBookController = new PCBookController();
					Integer bookID = pcBook.getBookID();
					if (bookID > 0) {
						String result = pcBookController.deleteBookData(bookID);
						if (result.isBlank()) {
							_repaint(LocalDate.now().plusDays(1));
							errorLabel.setText("Booking PC With ID: " + bookID + " is successfuly canceled!");
							return;
						}

						errorLabel.setText(result);
					}
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
				} else {
					HBox buttons = new HBox(cancelBookButton);

					setGraphic(buttons);
				}
			}

		});

		pcBookTV.getColumns().add(bookIDCol);
		pcBookTV.getColumns().add(pcIDCol);
		pcBookTV.getColumns().add(userIDCol);
		pcBookTV.getColumns().add(bookedDateCol);
		pcBookTV.getColumns().add(cancelBookCol);

		pcBookTV.setPlaceholder(new Label("No Data Booked Records!"));
	}

	private void initializeBookedDatePicker() {
		HBox bookedDatePickerDiv = new HBox();

		datePickerButton = new Button("Set Date");
		bookedDatePicker = new DatePicker();

		bookedDatePicker.setValue(LocalDate.now());
		bookedDatePickerLabel = new Label("Set Booked Date: ");

		datePickerButton.setOnAction(event -> {
			LocalDate selectedDate = bookedDatePicker.getValue();
			_repaint(selectedDate);

		});

		bookedDatePickerDiv.getChildren().addAll(bookedDatePickerLabel, bookedDatePicker, datePickerButton);
		container.getChildren().add(bookedDatePickerDiv);
	}

	private void intializeFinishButtonBookedPC() {
		VBox div = new VBox();

		LocalDate selectedDate = bookedDatePicker.getValue();
		currentSetTimeBookingLabel = new Label(selectedDate.toString());

		finishBookedPCButton = new Button("Finish Current Booked PC");

		finishBookedPCButton.setOnAction(event -> {
			ObservableList<PCBook> pcBookedList = pcBookTV.getItems();
			ArrayList<PCBook> pcBookArrayList = new ArrayList<>(pcBookedList);

			PCBookController pcBookController = new PCBookController();
			String result = pcBookController.finishBook(pcBookArrayList);
			errorLabel.setText(result);
			_repaint(selectedDate);
		});

		div.getChildren().addAll(currentSetTimeBookingLabel, finishBookedPCButton);
		container.getChildren().add(div);
	}

	public void _repaint(LocalDate date) {
		pcBookTV.getItems().clear();
		PCBookController pcBookController = new PCBookController();
		pcBookedList = pcBookController.getAllPCBookedDataByDate(date);

		for (PCBook pcBooked : pcBookedList) {
			System.out.println("Booked ID: " + pcBooked.getBookID());
			System.out.println("PC ID: " + pcBooked.getPcID());
			System.out.println("User ID: " + pcBooked.getUserID());
			System.out.println("Booked Date: " + pcBooked.getBookedDate());
			pcListId.add(pcBooked.getPcID());
			pcBookTV.getItems().add(pcBooked);
		}

	}

	@Override
	public void displayErrorMessage(String error) {
		errorLabel.setText(error);
	}

	// Inisiasi variable Navbar
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

	// Membuat navbar dan memberikan label setiap navbar
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

		if (user.getRole().equals("Admin")) {
			menuBar.getMenus().add(adminMenu);
			return menuBar;
		}
		if (user.getRole().equals("Customer")) {
			menuBar.getMenus().add(custMenu);
			return menuBar;
		}
		if (user.getRole().equals("Computer Technician")) {
			menuBar.getMenus().add(techMenu);
			return menuBar;
		}
		if (user.getRole().equals("Operator")) {
			menuBar.getMenus().add(operatorMenu);
			return menuBar;
		}
		return menuBar;
	}

	// Menginisiasi functional dari masing-masing submenu untuk melakukan navigasi
	private void buttonConfig() {
		viewAdminPCMI.setOnAction((e) -> {
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewCustPCMI.setOnAction((e) -> {
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewReportMI.setOnAction((e) -> {
			// ReportView.setScene(primaryStage);
		});
		viewStaffJobMI.setOnAction((e) -> {
			ViewAllJobScene.setScene(primaryStage);
		});
		viewTransMI.setOnAction((e) -> {
			ViewAllTransactionScene.setScene(primaryStage);
		});
		viewStaffMI.setOnAction((e) -> {
			ViewAllStaffScene.setScene(primaryStage);
		});
		viewTechJobMI.setOnAction((e) -> {
			TechnicianJobView.setScene(primaryStage);
		});
		viewPCBookedMI.setOnAction((e) -> {

		});
		viewCustTransMI.setOnAction((e) -> {

		});
	}
}
