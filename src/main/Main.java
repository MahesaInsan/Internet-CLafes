package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.LoginScene;
import views.RegisterScene;

//Group 7
//2501981024	MAHESA INSAN RAUSHANFIKIR
//2502012312	NAUFAL DAFFA RYQUELME
//2501984096	KENNETH MATTHEW
//2501986422	KENRICK PANCA DEWANTO

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	//Starting application with register scene
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		RegisterScene.setScene(primaryStage);

		primaryStage.show();
	}
}
