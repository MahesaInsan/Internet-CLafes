package views;

import controllers.PCController;
import controllers.UserController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.PC;

public class DetailPCScene implements IErrorMessage{
	public static void setScene(Stage primaryStage, PC pc) {
		DetailPCScene detailPC = new DetailPCScene(pc);
		detailPC._setScene(primaryStage);
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
	Label errorMsg;
	Button editButton;
	Button deleteButton;
	
	private DetailPCScene(PC pc) {
		this.pc = pc;
		container = new VBox();
		initializePCID();
		initializePCCondition();
		
		errorMsg = new Label();
		container.getChildren().add(errorMsg);
		
		if(UserController.getAuthorization().equals("Admin")) initializeButton();
		
		scene = new Scene(container);
		scene.setRoot(container);
	}
	
	
	private void initializePCID() {
		HBox pcIDDiv = new HBox();
		
		pcIDLabel = new Label("PC ID: " + pc.getPcID());
		pcIDDiv.getChildren().addAll(pcIDLabel);
		container.getChildren().add(pcIDDiv);
	}
	
	private void initializePCCondition() {
		HBox pcConditionDiv = new HBox();
		
		Label pcConditionLabel = new Label("PC Condition: " + pc.getPcCondition());
		pcConditionDiv.getChildren().add(pcConditionLabel);
		container.getChildren().add(pcConditionDiv);
	}
	
	private void initializeButton() {
		editButton = new Button("Edit");
		editButton.setOnAction(event -> {
			EditPCScene.setScene(primaryStage, pc);
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> {
			PCController controller = new PCController();
			controller.deletePC(pc.getPcID());
			this.pc = null;
			
			DisplayAllPCScene.setScene(primaryStage);
		});
		container.getChildren().addAll(editButton, deleteButton);
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
