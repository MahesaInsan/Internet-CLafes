package views;

import controllers.JobController;
import controllers.UserController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Job;

import java.sql.SQLException;
import java.util.ArrayList;

public class TechnicianJobView implements IErrorMessage {
    private JobController jobController;
    private Stage primaryStage;
    private TableView<Job> jobTableView;
    private static TechnicianJobView instance;
    Scene scene;
    Button completeButton;
    
    public static void setScene(Stage primaryStage) {
		if(instance == null) {
			instance = new TechnicianJobView();
		}
		instance._setScene(primaryStage);
	}

    public TechnicianJobView() {
        jobController = new JobController();

        initializeTable();
        initializeCompleteButton();
        addEventListener(); // Add the event listener

        VBox container = new VBox();
        container.getChildren().addAll(jobTableView, completeButton);
        scene = new Scene(container);
        scene.setRoot(container);
    }

    private void initializeTable() {
        jobTableView = new TableView<>();

        TableColumn<Job, String> jobIdCol = new TableColumn<>("Job ID");
        jobIdCol.setCellValueFactory(new PropertyValueFactory<>("jobID"));

        TableColumn<Job, String> pcIdCol = new TableColumn<>("PC ID");
        pcIdCol.setCellValueFactory(new PropertyValueFactory<>("pcID"));

        TableColumn<Job, String> jobStatusCol = new TableColumn<>("Job Status");
        jobStatusCol.setCellValueFactory(new PropertyValueFactory<>("jobStatus"));

        jobTableView.getColumns().addAll(jobIdCol, pcIdCol, jobStatusCol);
        jobTableView.setPlaceholder(new Label("No Jobs to Display"));
    }

    private void refreshJobs() {
        jobTableView.getItems().clear();
        try {
            ArrayList<Job> technicianJobs = jobController.getTechnicianJob(1);
            if (technicianJobs != null) {
                for (Job job : technicianJobs) {
                	jobTableView.getItems().add(job);
				}
                
            }
        } catch (SQLException e) {
            displayErrorMessage("Error retrieving technician jobs: " + e.getMessage());
        }
    }
    
    int selectedJobID = 0;
	String selectedJobPC;

	private void addEventListener() {
        jobTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Job>() {
            public void changed(ObservableValue<? extends Job> observable, Job oldValue, Job newValue) {
                if(newValue != null) {
                    selectedJobID = newValue.getJobID();
                    selectedJobPC = newValue.getPcID();
                }
            }
        });
    }
	
	private void initializeCompleteButton() {
        completeButton = new Button("Complete job");
        completeButton.setOnAction(event -> {
            if(selectedJobID == 0) {
                Alert alert = new Alert
                        (AlertType.ERROR, "Please select a Job !");
                
                alert.show();
                return;
            }
            String complete = "Complete";
            JobController controller3 = new JobController();
            try {
                if(!controller3.updateJobStatus(selectedJobID, complete, selectedJobPC)) return;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            
            Alert alert = new Alert
                    (AlertType.INFORMATION, "Job with id : " + selectedJobID + "is completed !");
            alert.show();
            TechnicianJobView.setScene(primaryStage);
        });
    }

    @Override
    public void displayErrorMessage(String error) {
        // Handle error display if needed
        System.out.println("Error: " + error);
    }
    
    private void _setScene(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setScene(scene);
		refreshJobs();
	}

	
}
