package fr.eql.ai109.projet1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainBar extends Application {
	
	 final Menu File = new Menu("Fichier");
	 final Menu Edition = new Menu("Edition");
	 final Menu Mode = new Menu("Mode");
	 final Menu Help = new Menu("Aide");
	 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);
        menuBar.getMenus().addAll(File, Edition, Mode, Help);
        
        MenuItem exportPDF = new MenuItem("Export PDF");
        MenuItem addModify = new MenuItem("Ajouter/Modifier");
        MenuItem admin = new MenuItem("Administrateur");
        MenuItem user = new MenuItem("Utilisateur");
        MenuItem doc = new MenuItem("Documentation");
        
        
        File.getItems().add(exportPDF);
        Edition.getItems().add(addModify);
        Mode.getItems().addAll(admin, user);
        Help.getItems().add(doc);


        Scene scene = new Scene(vBox, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

