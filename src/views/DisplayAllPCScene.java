package views;

import java.util.ArrayList;

import controllers.PCController;
import controllers.UserController;
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
import models.PC;

public class DisplayAllPCScene  implements IErrorMessage{
	private static DisplayAllPCScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new DisplayAllPCScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	// Table
	TableView<PC> tableView;
	Button addButton;
	VBox container;
	
	private DisplayAllPCScene() {
		initializeTable();
		// Add item to container
		container = new VBox();
		container.getChildren().add(tableView);
		if(UserController.getAuthorization().equals("Admin")) initializeAddButton();
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}
	
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
	
	private void initializeAddButton() {
		addButton = new Button("Add PC");
		addButton.setOnAction(event -> {
			AddPCScene.setScene(primaryStage);
		});
		container.getChildren().add(addButton);
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}
	
	public void _repaint() {
		tableView.getItems().clear();
		PCController controller = new PCController();
		ArrayList<PC> pcList = controller.getAllPCData(this);
		for (PC pc : pcList) {
			tableView.getItems().add(pc);
		}
	}

	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		
	}
}
