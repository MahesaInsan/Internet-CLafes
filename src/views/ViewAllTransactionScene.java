package views;

import java.util.ArrayList;

import controllers.TransactionController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.TransactionHeader;

public class ViewAllTransactionScene{
	private static ViewAllTransactionScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ViewAllTransactionScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	// Table
	TableView<TransactionHeader> tableView;
	Button addButton;
	VBox container;
	
	private ViewAllTransactionScene() {
		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(tableView);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}
	
	private void initializeTable() {
		tableView = new TableView<TransactionHeader>();
		
		TableColumn<TransactionHeader, Integer> idColumn = new TableColumn<>("Transaction ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("transactionID")
				);
		
		TableColumn<TransactionHeader, Integer> staffIdColumn = new TableColumn<>("Staff ID");
		staffIdColumn.setCellValueFactory(
				new PropertyValueFactory<>("staffID")
				);
		
		TableColumn<TransactionHeader, String> staffNameColumn = new TableColumn<>("Staff Name");
		staffNameColumn.setCellValueFactory(
				new PropertyValueFactory<>("staffName")
				);
		
		TableColumn<TransactionHeader, String> dateColumn = new TableColumn<>("Transaction Date");
		dateColumn.setCellValueFactory(
				new PropertyValueFactory<>("transactionDate")
				);
		
		TableColumn<TransactionHeader, Void> actionColumn = new TableColumn<>("Action");
	    actionColumn.setCellFactory(param -> new TableCell<>() {
	        private final Button detailButton = new Button("Detail");
	        {
	            detailButton.setOnAction(event -> {
	                TransactionHeader tHead = getTableView().getItems().get(getIndex());
	                DetailTransactionScene.setScene(primaryStage, tHead);
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
		tableView.getColumns().add(staffIdColumn);
		tableView.getColumns().add(staffNameColumn);
		tableView.getColumns().add(dateColumn);
		tableView.getColumns().add(actionColumn);
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
		ArrayList<TransactionHeader> tHeadList = controller.getAllTransactionHeaderData();
		for (TransactionHeader tHead : tHeadList) {
			tableView.getItems().add(tHead);
		}
	}
}
