package fr.eql.ai109.projet1;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingPanel extends BorderPane {

//    	Image logo;
//    	FileInputStream fis = null;
    	

    	public LoadingPanel() {
    		
    			StudentDao dao = new StudentDao();
    			
        		ImageView imageView = new ImageView(getClass().getResource("icons/logoeql.png").toString());
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
                Label step2 = new Label("Création de l'annuaire...");
                Label step3 = new Label("Tri des données...");
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
        		
        		
        		// ------------- link bar de téléchargement
        		
        		
        		
        		
        		progressBar.setProgress(0.25F);
        		//steps.add(progressIndicator,0,1);


        		if (!dao.binFile.exists()) {
        			dao.initiateSettingsfile();
	        		dao.definemaxLength();
	        		dao.showmaxLength();
	        		dao.writeDestinationFile();
	        		dao.sortTargetFile();
	        		dao.writeChildren();
        		} else {
        			dao.loadSettings();
        		}
        		//progressBar.setProgress(0.50F);
        		//steps.add(progressIndicator,0,2);
        		//System.out.println(SEQUENCE_LENGTH);
        		//System.out.println(entriesNumber);


        		
        		//progressBar.setProgress(0.75F);
        		//steps.add(progressIndicator,0,3);
        		
        		//progressBar.setProgress(1.0F);
        		//System.out.println("valeur du tableau " + isWritten[0][0]);
        		//
        		//		for (int i = 0; i < isWritten.length; i++) {
        		//			System.out.println("valeur " + i + " " + isWritten[i][2]);
        		//		}
    		
//        		MainPanel root = new MainPanel(dao);
//				Scene scene2 = new Scene(root);
//				Stage primaryStage = (Stage)getScene().getWindow();
//				primaryStage.setScene(scene2);
    }
}
