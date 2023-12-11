package views;

import java.sql.SQLException;

import controllers.PCController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;

public class EditPCScene implements IErrorMessage{
	public static void setScene(Stage primaryStage, PC pc) {
		EditPCScene editPC = new EditPCScene(pc);
		editPC._setScene(primaryStage);
	}
	
	public static void setScene(Stage primaryStage) {
		setScene(primaryStage);
	}
	
	PC pc;
	Stage primaryStage;
	Scene scene;
	VBox container;
	
	Label pcIDLabel;
	Label pcConditionLabel;
	ComboBox<String> pcConditionInput;
	Label errorMsg;
	Button saveButton;
	
	private EditPCScene(PC pc) {
		this.pc = pc;
		container = new VBox();
		initializePCID();
		initializePCCondition();
		
		errorMsg = new Label();
		container.getChildren().add(errorMsg);
		
		initializeSaveButton();
		container.getChildren().add(saveButton);
		
		scene = new Scene(container);
		scene.setRoot(container);
	}
	
	
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID: " + pc.getPcID());
		pcIDDiv.getChildren().add(pcIDLabel);
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
	
	private void initializeSaveButton() {
		saveButton = new Button("Save PC");
		saveButton.setOnAction(event -> {
			String condition = pcConditionInput.getValue();
			PCController controller = new PCController();
			
			try {
				if(!controller.updatePCCondition(this, pc.getPcID(), condition)) return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
