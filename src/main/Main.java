package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.DisplayAllPCScene;
import views.RegisterScene;
import views.ViewAllJobScene;
import views.ViewAllStaffScene;
import views.ViewAllTransactionScene;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ViewAllStaffScene.setScene(primaryStage);
		primaryStage.show();
	}
}
