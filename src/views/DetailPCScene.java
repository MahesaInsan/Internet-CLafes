package views;

import java.sql.SQLException;

import controllers.PCController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;

public class DetailPCScene {
	public static void setScene(Stage primaryStage) {
		DetailPCScene detailPC = new DetailPCScene();
		detailPC._setScene(primaryStage);
	}
	
	public static void setScene(Stage primaryStage, PC pc) {
		setScene(primaryStage);
	}
	
	PC pc;
	Stage primaryStage;
	Scene scene;
	VBox container;
	
	Label pcIDLabel;
	Label pcConditionLabel;
	TextField pcIDInput;
	ComboBox<String> pcConditionInput;
	Label errorMsg;
	Button addButton;
	
	private DetailPCScene() {
		container = new VBox();
		initializePCID();
		initializePCCondition();
		
		errorMsg = new Label();
		container.getChildren().add(errorMsg);
		
		initializeAddButton();
		container.getChildren().add(addButton);
		
		scene = new Scene(container);
		scene.setRoot(container);
	}
	
	
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID");
		pcIDInput = new TextField();
		pcIDDiv.getChildren().addAll(pcIDLabel, pcIDInput);
		container.getChildren().add(pcIDDiv);
	}
	
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
	
	private void initializeAddButton() {
		addButton = new Button("Save PC");
		addButton.setOnAction(event -> {
			String id = pcIDInput.getText();
			String condition = pcConditionInput.getValue();
			PCController controller = new PCController();
			
			try {
				if(!controller.addNewPC(this, id)) return;
				System.out.println(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			DisplayAllPCScene.setScene(primaryStage);
		});
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
	}

	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		errorMsg.setText(error);
	}
}
