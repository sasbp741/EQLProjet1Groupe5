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
        		ImageView imageView = new ImageView(getClass().getResource("/icons/logoeql.png").toString());
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
                ProgressIndicator progressIndicator = new ProgressIndicator();
                
                GridPane steps = new GridPane();
                steps.setPadding(new Insets(5,20,5,20));
                steps.setHgap(5);
                steps.setVgap(5);
                
                Label step1 = new Label("Importation du fichier source...");
                Label step2 = new Label("Cr�ation de l'annuaire...");
                Label step3 = new Label("Tri des donn�es...");
                Label step4 = new Label("Optimisation de la recherche...");
                
                steps.add(progressIndicator,0,0);
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
        		
        		// ------------- link bar de t�l�chargement
        		



		
        		//steps.add(progressIndicator,0,1);
				//steps.add(progressIndicator,0,2);
        		//steps.add(progressIndicator,0,3);
        		//System.out.println(SEQUENCE_LENGTH);
        		//System.out.println(entriesNumber);
				//System.out.println("valeur du tableau " + isWritten[0][0]);
        		//
        		//		for (int i = 0; i < isWritten.length; i++) {
        		//			System.out.println("valeur " + i + " " + isWritten[i][2]);
        		//		}
        		
        		
        		new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							
						if (!dao.binFile.exists()) {
							dao.initiateSettingsfile();
							
			        		dao.definemaxLength();
			        		
			        		progressBar.setProgress(0.25F);
			        		
			        		dao.showmaxLength();
			        		
			        		dao.writeDestinationFile();
			        		
			        		progressBar.setProgress(0.50F);
			        		
			        		dao.sortTargetFile();
			        		
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
