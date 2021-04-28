package fr.eql.ai109.projet1;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadingPanel extends BorderPane {

	public LoadingPanel() {
		
		StudentDao dao = new StudentDao();
		ImageView imageView = new ImageView("/icons/logoeql.png");
    	imageView.setX(50); 
        imageView.setY(25); 
        imageView.setFitWidth(200); 
        imageView.setPreserveRatio(true);
        
        VBox titleBox = new VBox(2);
        Label title = new Label("Annuaire");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");
        Label subtitle = new Label("Premier lancement");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title,subtitle);
        
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        ProgressIndicator progressIndicator1 = new ProgressIndicator();
        ProgressIndicator progressIndicator2 = new ProgressIndicator();
        ProgressIndicator progressIndicator3 = new ProgressIndicator();
        ProgressIndicator progressIndicator4 = new ProgressIndicator();
        
        int progressIndicatorSize = 20;
        progressIndicator1.setPrefSize(progressIndicatorSize,progressIndicatorSize);
        progressIndicator2.setPrefSize(progressIndicatorSize,progressIndicatorSize);
        progressIndicator3.setPrefSize(progressIndicatorSize, progressIndicatorSize);
        progressIndicator4.setPrefSize(progressIndicatorSize, progressIndicatorSize);
        
        progressIndicator2.setVisible(false);
        progressIndicator3.setVisible(false);
        progressIndicator4.setVisible(false);
        
        GridPane steps = new GridPane();
        steps.setPadding(new Insets(5,20,5,20));
        steps.setHgap(5);
        steps.setVgap(5);
        
        Label step1 = new Label("Importation du fichier source...");
        Label step2 = new Label("Création de l'annuaire...");
        Label step3 = new Label("Tri des données...");
        Label step4 = new Label("Optimisation de la recherche...");
        
        steps.add(progressIndicator1,0,0);
        steps.add(progressIndicator2,0,1);
        steps.add(progressIndicator3,0,2);
        steps.add(progressIndicator4,0,3);
        
        steps.add(step1,1,0);
        steps.add(step2,1,1);
        steps.add(step3,1,2);
        steps.add(step4,1,3);
        
        VBox loader = new VBox(15);
        loader.setStyle("-fx-background-color: white;");
        loader.setPadding(new Insets(10));
        //loader.setHgap(10);
        loader.setAlignment(Pos.CENTER);
        loader.getChildren().addAll(imageView,titleBox,steps,progressBar);
		this.setCenter(loader);
		

		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					
				if (!dao.binFile.exists()) {
					dao.initiateSettingsfile();
					
	        		dao.definemaxLength();
	        		progressIndicator1.setVisible(false);
	        		progressIndicator2.setVisible(true);
	        		progressBar.setProgress(0.25F);
	        		
	        		dao.writeDestinationFile();
	        		
	        		progressIndicator2.setVisible(false);
	        		progressIndicator3.setVisible(true);
	        		progressBar.setProgress(0.50F);
	        		
	        		dao.sortTargetFile(progressBar);
	        		
	        		progressIndicator3.setVisible(false);
	        		progressIndicator4.setVisible(true);
	        		progressBar.setProgress(0.75F);
	        		
	        		dao.writeChildren();
	        		
				progressBar.setProgress(1.0F);         
} else {
	dao.loadSettings();
		
}
							Thread.sleep(0);
							
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									StudentDao dao = new StudentDao() ;
									MainPanel root = new MainPanel(dao);
									Scene scene2 = new Scene(root);
									Stage primaryStage2 = new Stage() ;
									
									primaryStage2.setScene(scene2);
									primaryStage2.show();
									getScene().getWindow().hide();
								}
							});	
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
						}
					}
        		}).start();
    }
}
