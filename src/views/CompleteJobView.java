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
	container.getChildren().add(navbar(UserController.currentUser));
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

	//Inisiasi variable Navbar
	MenuBar menuBar;
	Menu adminMenu;
	Menu custMenu;
	Menu techMenu;
	Menu operatorMenu;
	MenuItem viewAdminPCMI;
	MenuItem viewCustPCMI;
	MenuItem viewReportMI;
	MenuItem viewStaffJobMI;
	MenuItem viewTransMI;
	MenuItem viewStaffMI;
	MenuItem viewTechJobMI;
	MenuItem viewPCBookedMI;
	MenuItem viewCustTransMI;
	
	//Membuat navbar dan memberikan label setiap navbar
	public MenuBar initiate(User user) {
		menuBar = new MenuBar();
		
		adminMenu = new Menu("Main Menu");
		custMenu = new Menu("Main Menu");
		techMenu = new Menu("Main Menu");
		operatorMenu = new Menu("Main Menu");
		
		viewAdminPCMI = new MenuItem("View All PC");
		viewCustPCMI = new MenuItem("View All PC");
		viewReportMI = new MenuItem("View All Report");
		viewStaffJobMI = new MenuItem("View All Staff Job");
		viewTransMI = new MenuItem("View All Transaction");
		viewStaffMI = new MenuItem("View All Staff");
		viewTechJobMI = new MenuItem("View All Technician Job");
		viewPCBookedMI = new MenuItem("View All Booked PC");
		viewCustTransMI = new MenuItem("View All TransactionHistory");
		buttonConfig();
		
		adminMenu.getItems().addAll(viewAdminPCMI, viewStaffJobMI, viewStaffMI, viewReportMI, viewTransMI);
		custMenu.getItems().addAll(viewCustPCMI, viewCustTransMI);
		techMenu.getItems().addAll(viewTechJobMI);
		operatorMenu.getItems().addAll(viewPCBookedMI);
		
		if(user.getRole().equals("Admin")) {
			menuBar.getMenus().add(adminMenu);
			return menuBar;
		}
		if(user.getRole().equals("Customer")) {
			menuBar.getMenus().add(custMenu);
			return menuBar;
		}
		if(user.getRole().equals("Computer Technician")) {
			menuBar.getMenus().add(techMenu);
			return menuBar;
		}
		if(user.getRole().equals("Operator")) {
			menuBar.getMenus().add(operatorMenu);
			return menuBar;
		}
		return menuBar;
	}
	
	//Menginisiasi functional dari masing-masing submenu untuk melakukan navigasi
	private void buttonConfig() {
		viewAdminPCMI.setOnAction((e)->{
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewCustPCMI.setOnAction((e)->{
			DisplayAllPCScene.setScene(primaryStage);
		});
		viewReportMI.setOnAction((e)->{
			ReportView.setScene(primaryStage);
		});
		viewStaffJobMI.setOnAction((e)->{
			ViewAllJobScene.setScene(primaryStage);
		});
		viewTransMI.setOnAction((e)->{
			ViewAllTransactionScene.setScene(primaryStage);
		});
		viewStaffMI.setOnAction((e)->{
			ViewAllStaffScene.setScene(primaryStage);
		});
		viewTechJobMI.setOnAction((e)->{
			TechnicianJobView.setScene(primaryStage);
		});
		viewPCBookedMI.setOnAction((e)->{
			
		});
		viewCustTransMI.setOnAction((e)->{
			
		});
	}
}
