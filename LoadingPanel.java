package fr.eql.ai109.projet1;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

public class LoadingPanel extends Application {

	@Override
    public void start(Stage stage) {
        stage.setTitle("Annuaire EQL");
    	stage.setResizable(false);
    	stage.initStyle(StageStyle.UNDECORATED);
    	stage.centerOnScreen();
    	
    	Image logo;
    	FileInputStream fis = null;
    	
		try {
			fis = new FileInputStream("C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\icons\\logoeql.png");
			
	    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		logo = new Image(fis);
		ImageView imageView = new ImageView(logo);
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
        
        ProgressBar progressBar = new ProgressBar(.2);
        progressBar.setPrefWidth(400);
        
        ProgressIndicator progressIndicator = new ProgressIndicator();
        
        GridPane steps = new GridPane();
        steps.setPadding(new Insets(5,20,5,20));
        steps.setHgap(5);
        steps.setVgap(5);
        
        Label step1 = new Label("Importation du fichier source...");
        Label step2 = new Label("Cr�ation de l'annuaire");
        Label step3 = new Label("Tri des donn�es");
        Label step4 = new Label("Optimisation de la recherche");
        
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
 
        
        Scene scene = new Scene(loader, 400, 300);
        stage.setScene(scene);
        stage.show();
        
        try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static void main(String[] args) {
		launch(args);
	}

}