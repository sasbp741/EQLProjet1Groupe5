package fr.eql.ai109.projet1;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPanel extends BorderPane {

	public ObservableList<Student> observableStudents;

	public String winMode = "";
	private Button addButton = new Button("Ajouter un stagiaire");
	private Button editButton = new Button("Modifier le stagiaire");
	private Button delButton = new Button("Supprimer le stagiaire");


	final Menu File = new Menu("Fichier");
	final Menu Edition = new Menu("Edition");
	final Menu Mode = new Menu("Mode");
	final Menu Help = new Menu("Aide");

	MenuItem modify ;
	MenuItem delete ;
	RadioMenuItem user ;
	RadioMenuItem admin ;
	ToggleGroup userMode;
	TableView<Student> studentTable ;


	private Label lblTotalEntries ;

	@SuppressWarnings("unchecked")

	public MainPanel(StudentDao dao) {

		lblTotalEntries = new Label ("Affichage de "+ (dao.entriesNumber) +" stagiaires     ");
		setPrefSize(600, 600);

		// Menu
		MenuBar menuBar = new MenuBar();
		VBox menuBox = new VBox(menuBar);
		menuBar.getMenus().addAll(File, Edition, Mode, Help);

		MenuItem exportPDF = new MenuItem("Exporter en PDF");
		MenuItem add = new MenuItem("Ajouter un stagiaire");
		SeparatorMenuItem editSep = new SeparatorMenuItem();
		modify = new MenuItem("Modifier le stagiaire");
		modify.setDisable(true);
		delete = new MenuItem("Supprimer le stagiaire");
		delete.setDisable(true);

		user = new RadioMenuItem("Utilisateur");
		admin = new RadioMenuItem("Administrateur");
		userMode = new ToggleGroup();
		userMode.getToggles().addAll(user, admin);
		userMode.selectToggle(user);


		MenuItem doc = new MenuItem("Documentation");

		File.getItems().add(exportPDF);
		Edition.getItems().addAll(add, editSep, modify, delete);
		Mode.getItems().addAll(user, admin);
		Help.getItems().add(doc);

		// MainPan
		VBox mainPan = new VBox(10);

		// SearchBox
		HBox searchBox = new HBox(10);

		TextField searchField = new TextField();
		searchField.setPrefWidth(410);
		searchField.prefWidthProperty().bind(searchBox.widthProperty());
		searchField.setPromptText("Rechercher un stagiaire...");

		Button searchButton = new Button("OK");
		searchButton.setMinWidth(35);

		//		File icone = new File("C:/Users/Val/eclipse-workspace/AnnuaireEQL/src/fr/eql/ai109/projet1/icons/search.png");
		//		System.out.println(icone.exists());
		//		Image image = new Image("C:/Users/Val/eclipse-workspace/AnnuaireEQL/src/fr/eql/ai109/projet1/icons/icons/search.png");
		//		ImageView view = new ImageView(image);
		//		searchButton.setGraphic(view);

		Button advSearchButton = new Button("Recherche avancée...");
		advSearchButton.setMinWidth(130);

		searchBox.setPadding(new Insets(10, 5, 0, 5));
		//searchBox.getChildren().addAll(searchField, searchButton, advSearchButton);
		searchBox.getChildren().addAll(searchField);



		// Advanced Search Panel/Boxes
		HBox advSearchBox = new HBox(10);
		EditPanel editpanel = new EditPanel(winMode, null, null);		
		advSearchBox.getChildren().addAll(
				editpanel.getLblZipCode(),editpanel.getCbZipCode(),
				editpanel.getLblPromo(),editpanel.getCbPromo(),
				editpanel.getLblYear(),editpanel.getCbYear());
		TitledPane advSearchPane = new TitledPane() ;
		advSearchPane.setText("Affiner la recherche : ");
		advSearchPane.setExpanded(false);
		advSearchPane.setContent(advSearchBox);

		//Conditions for advanced search
		String zipSearch = editpanel.getCbYear().getSelectionModel().getSelectedItem();
		String promoSearch = editpanel.getCbYear().getSelectionModel().getSelectedItem();
		String yearSearch = editpanel.getCbYear().getSelectionModel().getSelectedItem();







		// ListPan


		observableStudents = FXCollections.observableArrayList();
		dao.loadStudentTree(observableStudents) ;

		studentTable = new TableView<Student>(observableStudents);


		TableColumn<Student, String> lastNameCol = new TableColumn<Student, String>("Nom");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));

		TableColumn<Student, String> firstNameCol = new TableColumn<Student, String>("Prénom");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));

		TableColumn<Student, String> zipCodeCol = new TableColumn<Student, String>("Département");
		zipCodeCol.setCellValueFactory(new PropertyValueFactory<Student, String>("zipCode"));

		TableColumn<Student, String> classCol = new TableColumn<Student, String>("Promotion");
		classCol.setCellValueFactory(new PropertyValueFactory<Student, String>("promo"));

		TableColumn<Student, String> yearCol = new TableColumn<Student, String>("Année");
		yearCol.setCellValueFactory(new PropertyValueFactory<Student, String>("year"));

//		lastNameCol.setMinWidth(100);
//		lastNameCol.setPrefWidth(150);
//
//		firstNameCol.setMinWidth(100);
//		firstNameCol.setPrefWidth(120);

				lastNameCol.setMinWidth(100);
				lastNameCol.setPrefWidth(160);
				firstNameCol.setMinWidth(100);
				firstNameCol.setPrefWidth(135);
				classCol.setMinWidth(100);
				classCol.setPrefWidth(110);
				yearCol.setMinWidth(80);
				yearCol.setPrefWidth(80);

		studentTable.getColumns().addAll(lastNameCol, firstNameCol, zipCodeCol, classCol, yearCol);
		studentTable.prefWidthProperty().bind(mainPan.widthProperty());
		studentTable.prefHeightProperty().bind(mainPan.heightProperty());
		

		//SearchingStudent filter
		//		FilteredList<Student> searchingStudent = new FilteredList<Student>(observableStudents);
		//		searchField.textProperty().addListener((observable, oldValue, newValue) -> {searchingStudent.setPredicate(student -> {
		//			return searchingStudentCondition(newValue, student); 
		//		});
		//		});

		//TableView Automatically Ascending Sorted by LastName then FirstName then YearCol(Descending)
		//		SortedList<Student> sortedData = new SortedList<>(searchingStudent);
		//		sortedData.comparatorProperty().bind(studentTable.comparatorProperty());
		//		studentTable.setItems(sortedData);
		//		yearCol.setSortType(SortType.DESCENDING);
		//		studentTable.getSortOrder().addAll(lastNameCol,firstNameCol,yearCol) ;




		AnchorPane listPan = new AnchorPane();
		AnchorPane.setTopAnchor(studentTable, 5.);
		AnchorPane.setBottomAnchor(studentTable, 5.);
		AnchorPane.setRightAnchor(studentTable, 5.);
		AnchorPane.setLeftAnchor(studentTable, 5.);
		//		ImageView addIcon = new ImageView("C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\icons\\add.png");
		//		addIcon.setFitWidth(20); 
		//		addIcon.setPreserveRatio(true);
		//        addButton.setGraphic(addIcon);
		listPan.getChildren().addAll(studentTable, addButton, editButton, delButton);
		// listPan.setPrefSize(600, 400);

		// ButtonBox
		HBox buttonBox = new HBox(20);

		buttonBox.getChildren().addAll(addButton, editButton, delButton);
		editButton.setDisable(true);
		delButton.setDisable(true);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPrefHeight(50);

		if (!MainApp.mode.equals("admin") ) {
			switchUserMode("user", modify, delete, editButton, delButton, user, admin, userMode);
		} else {
			userMode.selectToggle(admin);
		}

		mainPan.getChildren().addAll(searchBox, advSearchPane, listPan, lblTotalEntries);
		mainPan.setAlignment(Pos.TOP_RIGHT);
		this.setTop(menuBox);
		this.setCenter(mainPan);
		this.setBottom(buttonBox);



		// EVENTS--------------------------------------------------------------------------------------

		user.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switchUserMode("user", modify, delete, editButton, delButton, user, admin, userMode);
			}
		});

		admin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switchUserMode("admin", modify, delete, editButton, delButton, user, admin, userMode);
			}
		});



		// Events menu

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addStudentPanel(dao);
			}

		});

		//Pour la recherche dans le textfield
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			dao.simpleSearch(searchField.getText(),observableStudents);
			if(studentTable.getSelectionModel().getSelectedItem() == null) {
				disableButtons();
			} 
		});

		//		setOnAction(new EventHandler<ActionEvent>() {
		//			
		//			@Override
		//			public void handle(ActionEvent event) {
		//				dao.simpleSearch(searchField.getText());
		//				System.out.println("recherche : " + searchField.getText());
		//			}
		//		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addStudentPanel(dao);


			}

		});

		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				winMode = "edit";
				Student studentModif = studentTable.getSelectionModel().getSelectedItem();
				EditPanel root = new EditPanel(winMode, studentModif, dao);
				Scene scene2 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene2);
			}
		});
		
		modify.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				winMode = "edit";
				Student studentModif = studentTable.getSelectionModel().getSelectedItem();
				EditPanel root = new EditPanel(winMode, studentModif, dao);
				Scene scene2 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene2);
			}
		});

		delButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (dao.deleteStudentConfirmation(studentTable.getSelectionModel().getSelectedItem())) {					
					dao.deleteStudent(studentTable.getSelectionModel().getSelectedItem().getIndex());
					dao.loadStudentTree(observableStudents);
					//Decrease entriesnumber label :
					//	dao.entriesNumber-- ;   
					//editpanel.updateLblTotalEntriesNumber(dao.entriesNumber) ;  //attention : modifier dans settings aussi!
				}
			}});

		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (dao.deleteStudentConfirmation(studentTable.getSelectionModel().getSelectedItem())) {					
					dao.deleteStudent(studentTable.getSelectionModel().getSelectedItem().getIndex());
					dao.loadStudentTree(observableStudents);
					//Decrease entriesnumber label :
					//	dao.entriesNumber-- ;   
					//editpanel.updateLblTotalEntriesNumber(dao.entriesNumber) ;  //attention : modifier dans settings aussi!
				}
			}});
		
		studentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

			@Override
			public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
				if(MainApp.mode.equals("admin")) {
					enableButtons();
				}
			}


		});




		doc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File docFile = new File("C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\Documentation.html");
				try {
					Desktop.getDesktop().open(docFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});	


		exportPDF.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					showExportedPdfDialog(dao.exportToPdf(observableStudents)) ; 												
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateLblTotalEntriesNumber (int entriesNumber) {
		lblTotalEntries.setText("Affichage de "+ (entriesNumber) +" stagiaires     "); 
	}

	private void showExportedPdfDialog(String filename) {

		File file = new File(filename);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Export PDF");
		alert.setHeaderText("Votre document PDF a bien été exporté.");

		ButtonType seeFile = new ButtonType("Ouvrir le PDF");
		ButtonType seeDir = new ButtonType("Voir le dossier");
		ButtonType ok = new ButtonType("OK");

		// Remove default ButtonTypes
		alert.getButtonTypes().clear();

		alert.getButtonTypes().addAll(seeFile, seeDir, ok);

		// option != null.
		Optional<ButtonType> option = alert.showAndWait();

		if (option.get() == seeFile) {
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (option.get() == seeDir) {
			try {
				Desktop.getDesktop().open(file.getParentFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	private boolean searchingStudentCondition(String newValue, Student student) {
//		//null : display all // true = match found // false = doesnt match	
//		if (newValue == null || newValue.isEmpty()) { 
//			return true;
//		}
//		String lowerCaseFilter = newValue.toLowerCase();
//		if (student.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
//			return true; 
//		} 
//		else  
//			return false;
//	}

	private void addStudentPanel(StudentDao dao) {
		winMode = "add";
		EditPanel root = new EditPanel(winMode, null, dao);
		Scene scene2 = new Scene(root);
		Stage stage = (Stage) getScene().getWindow();

		stage.setScene(scene2);
	}

	private void disableButtons() {
		editButton.setDisable(true);
		delButton.setDisable(true);
		modify.setDisable(true);
		delete.setDisable(true);
	}
	private void enableButtons() {
		editButton.setDisable(false);
		delButton.setDisable(false);
		modify.setDisable(false);
		delete.setDisable(false);
	}

	public void switchUserMode(String userMode, MenuItem modify, MenuItem delete, Button editButton, Button delButton, RadioMenuItem user, RadioMenuItem admin,ToggleGroup menuUserMode) {
		if (userMode.equals("user")) {
			disableButtons();
			menuUserMode.selectToggle(user);
			MainApp.mode = "user" ;
		} else if (userMode.equals("admin")) {
			TextInputDialog passwordDialog = new TextInputDialog();
			passwordDialog.setTitle("Mode Administrateur");
			passwordDialog.setHeaderText("Identifiez-vous");
			passwordDialog.setContentText("Veuillez entrer le mot de passe");
			// Traditional way to get the response value.
			Optional<String> result = passwordDialog.showAndWait();
			result.ifPresent(password -> { 
				if(password.equals("eql")) {
					menuUserMode.selectToggle(admin);
					MainApp.mode = "admin" ;
					if(studentTable.getSelectionModel().getSelectedItem() != null) {
						enableButtons();
					}
				} else {
					menuUserMode.selectToggle(user);
					MainApp.mode = "user" ;
				}
			});
		}
	}



	//-----------------------------------------GET&SET 
	public ObservableList<Student> getObservableStudents() {
		return observableStudents;
	}



}