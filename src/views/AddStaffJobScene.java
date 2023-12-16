package views;

import controllers.JobController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddStaffJobScene implements IErrorMessage{
	
	public static void setScene(Stage primaryStage) {
		AddStaffJobScene addJob = new AddStaffJobScene();
		addJob._setScene(primaryStage);
	}
	
	Stage primaryStage;
	Scene scene;
	VBox container;
	// Form
	HBox staffDiv;
	Label staffLabel;
	TextField staffInput;
	HBox pcDiv;
	Label pcLabel;
	TextField pcInput;
	Button addButton;
	Label errorMsg;
	
	private AddStaffJobScene() {
		initializeStaffForm();
		initializePcForm();
		initializeAddButton();
		
		errorMsg = new Label();
		container = new VBox();
		container.getChildren().addAll(staffDiv, errorMsg, pcDiv, addButton);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
		
	}
	
	private void initializeStaffForm() {
		staffDiv = new HBox();
		
		staffLabel = new Label("Staff ID");
		staffInput = new TextField();
		staffDiv.getChildren().addAll(staffLabel, staffInput);
	}
	
	private void initializePcForm() {
		pcDiv = new HBox();
		
		pcLabel = new Label("Pc ID");
		pcInput = new TextField();
		pcDiv.getChildren().addAll(pcLabel, pcInput);
	}
	
	private void initializeAddButton() {
		addButton = new Button("Add job");
		addButton.setOnAction(event -> {
			String staffId = staffInput.getText();
			String pcId = pcInput.getText();
			JobController controller = new JobController();
			
			try {
				if(!controller.addNewJob(this, staffId, pcId)) return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			ViewAllJobScene.setScene(primaryStage);
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
