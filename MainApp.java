package fr.eql.ai109.projet1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	public static String mode= "";
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		LoadingPanel lp = new LoadingPanel();
		Scene scene = new Scene(lp);

		primaryStage.setTitle("Annuaire EQL");
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.centerOnScreen();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
