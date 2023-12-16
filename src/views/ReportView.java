package views;

import controllers.ReportController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Report;

import java.sql.SQLException;
import java.util.Optional;

public class ReportView extends Application {

    private ReportController reportController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        reportController = new ReportController();

        primaryStage.setTitle("Report Viewer");

        VBox root = new VBox();
        root.setSpacing(10);

        Label titleLabel = new Label("Report Viewer");
        

        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);

        Button addReportButton = new Button("Make a Report");
        addReportButton.setOnAction(event -> {
			try {
				showAddReportDialog(primaryStage);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

        root.getChildren().addAll(titleLabel, reportTextArea, addReportButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddReportDialog(Stage primaryStage) throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Make a Report");
        dialog.setHeaderText("Enter PC ID and Report Note");
        dialog.setContentText("PC ID:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String pcID = result.get();

            // Show another dialog for report note
            TextInputDialog noteDialog = new TextInputDialog();
            noteDialog.setTitle("Make a Report");
            noteDialog.setHeaderText("Enter PC ID and Report Note");
            noteDialog.setContentText("Report Note:");

            Optional<String> noteResult = noteDialog.showAndWait();
            if (noteResult.isPresent()) {
                String reportNote = noteResult.get();
                reportController.addNewReport("Customer", pcID, reportNote);
            }
        }
    }
}
