package views;

import java.sql.SQLException;

import controllers.JobController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CompleteJobView extends Application {
    private JobController jobController;
    private TextField jobIdField;
    private Button completeButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        jobController = new JobController();

        primaryStage.setTitle("Complete Job");

        initializeFields();
        initializeCompleteButton();

        VBox container = new VBox();
        container.getChildren().addAll(new Label("Job ID:"), jobIdField, completeButton);

        Scene scene = new Scene(container, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeFields() {
        jobIdField = new TextField();
        jobIdField.setPromptText("Enter Job ID");
    }

    private void initializeCompleteButton() {
        completeButton = new Button("Complete Job");
        completeButton.setOnAction(event -> completeJob());
    }

    private void completeJob() {
        String jobID = jobIdField.getText();
        try {
			jobController.completeJobStatus(jobID, "Complete");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Additional logic if needed
    }
}
