package fr.eql.ai109.projet1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		//			StudentDao dao = new StudentDao();
		//			if (!dao.binFile.exists()) {
		//				dao.initiateSettingsfile();
		//	    		dao.definemaxLength();
		//	    		dao.showmaxLength();
		//	    		dao.writeDestinationFile();
		//	    		dao.sortTargetFile();
		//	    		dao.writeChildren();
		//			} else {
		//				dao.loadSettings();
		//			}

		//			MainPanel root = new MainPanel(dao);
		//			Scene scene = new Scene(root);
		//			primaryStage.setResizable(true);
		//			primaryStage.setScene(scene);
		//			primaryStage.sizeToScene();
		//			primaryStage.setTitle("Annuaire EQL");

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
