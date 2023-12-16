package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.DisplayAllPCScene;
import views.EditPCScene;
import views.RegisterScene;
import views.ViewAllJobScene;
import views.ViewAllStaffScene;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ViewAllJobScene.setScene(primaryStage);
		primaryStage.show();
	}
}
