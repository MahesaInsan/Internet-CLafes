package views;

import controllers.JobController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Job;

import java.sql.SQLException;
import java.util.ArrayList;

public class TechnicianJobView extends Application implements IErrorMessage {
    private JobController jobController;
    private Stage primaryStage;
    private TableView<Job> jobTableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        jobController = new JobController();

        primaryStage.setTitle("Technician Job Viewer");

        initializeTable();
        addEventListener(); // Add the event listener

        VBox container = new VBox();
        container.getChildren().add(jobTableView);

        Scene scene = new Scene(container, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        refreshJobs();
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
            ArrayList<Job> technicianJobs = jobController.getTechnicianJob(100);
            if (technicianJobs != null) {
                jobTableView.getItems().addAll(technicianJobs);
            }
        } catch (SQLException e) {
            displayErrorMessage("Error retrieving technician jobs: " + e.getMessage());
        }
    }
    
    int selectedJobID;
	int selectedJobPC;

    private void addEventListener() {
    	
        jobTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedJobID = newValue.getJobID();
                selectedJobPC = newValue.getPcID();
            }
        });
    }

    @Override
    public void displayErrorMessage(String error) {
        // Handle error display if needed
        System.out.println("Error: " + error);
    }
}
