package views;

import java.util.ArrayList;

import controllers.JobController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Job;

public class ViewAllJobScene implements IErrorMessage{
	private static ViewAllJobScene instance;
	
	public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new ViewAllJobScene();
		}
		instance._setScene(primaryStage);
	}
	
	Stage primaryStage;
	VBox container;
	HBox updateJobDiv;
	Scene scene;
	// Table
	TableView<Job> tableView;
	Button addButton;
	Button updateButton;
	ComboBox<String> jobDropDown;
	Label jobDropDownLabel;
	
	int selectedJobID = 0;
	String selectedJobPC = "";
	
	private ViewAllJobScene() {
		initializeTable();
		initializeAddButton();
		initializeDropDown();
		initializeUpdateButton();
		addEventListener();
		
		container = new VBox();
		container.getChildren().addAll(tableView, addButton, updateJobDiv, updateButton);
		
		scene = new Scene(container);
		
		scene.setRoot(container);
	}
	
	private void initializeTable() {
		tableView = new TableView<Job>();
		
		TableColumn<Job, String> idColumn = new TableColumn<>("Job ID");
		idColumn.setCellValueFactory(
				new PropertyValueFactory<>("jobID")
				);
		
		TableColumn<Job, String> staffColumn = new TableColumn<>("Staff ID");
		staffColumn.setCellValueFactory(
				new PropertyValueFactory<>("userID")
				);
		
		TableColumn<Job, String> pcColumn = new TableColumn<>("Pc ID");
		pcColumn.setCellValueFactory(
				new PropertyValueFactory<>("pcID")
				);
		
		TableColumn<Job, String> statusColumn = new TableColumn<>("Job status");
		statusColumn.setCellValueFactory(
				new PropertyValueFactory<>("jobStatus")
				);
		
		tableView.getColumns().add(idColumn);
		tableView.getColumns().add(staffColumn);
		tableView.getColumns().add(pcColumn);
		tableView.getColumns().add(statusColumn);
		tableView.setPlaceholder(new Label("No Rows to Display"));
	}
	
	private void initializeAddButton() {
		addButton = new Button("Add Job");
		addButton.setOnAction(event -> {
			AddStaffJobScene.setScene(primaryStage);
		});
	}
	
	private void initializeDropDown() {
		String status[] = {
				"Complete",
				"UnComplete"
		};
		
		updateJobDiv = new HBox();
		
		jobDropDownLabel = new Label("Job status");
		jobDropDown = new ComboBox<String>(FXCollections.observableArrayList(status));
		updateJobDiv.getChildren().addAll(jobDropDownLabel, jobDropDown);
	}
	
	private void initializeUpdateButton() {
		updateButton = new Button("Update job");
		updateButton.setOnAction(event -> {
			if(selectedJobID == 0) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select a Job !");
				
				alert.show();
				return;
			}
			String status = jobDropDown.getValue();
			if(status == null) {
				Alert alert = new Alert
						(AlertType.ERROR, "Please select the job status !");
				
				alert.show();
				return;
			}
			JobController controller2 = new JobController();
			
			try {
				if(!controller2.updateJobStatus(selectedJobID, status, selectedJobPC)) return;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Alert alert = new Alert
					(AlertType.INFORMATION, "Job with id : " + selectedJobID + " status updated to " + status + " !");
			alert.show();
			ViewAllJobScene.setScene(primaryStage);
		});
	}
	
	private void addEventListener() {
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Job>() {
			public void changed(ObservableValue<? extends Job> observable, Job oldValue, Job newValue) {
				if(newValue != null) {
					selectedJobID = newValue.getJobID();
					selectedJobPC = newValue.getPcID();
				}
			}
		});
	}
	
	private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		_repaint();
	}
	
	public void _repaint() {
		tableView.getItems().clear();
		JobController controller = new JobController();
		ArrayList<Job> jobList = controller.getAllJobData(this);
		for(Job job : jobList) {
			tableView.getItems().add(job);
		}
	}
	
	@Override
	public void displayErrorMessage(String error) {
		// TODO Auto-generated method stub
		
	}

}
