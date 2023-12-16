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
}
