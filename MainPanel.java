package fr.eql.ai109.projet1;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPanel extends BorderPane {

	private StudentDao dao = new StudentDao();
	private ObservableList<Student> observableStudents ;

	public String winMode = "";
	private Button addButton = new Button("Ajouter un stagiaire");
	private Button editButton = new Button("Modifier le stagiaire");
	private Button delButton = new Button("Supprimer le stagiaire");
	public static String iconDir = "C:/Users/Val/eclipse-workspace/AnnuaireEQL/src/fr/eql/ai109/projet1/icons/";


	final Menu File = new Menu("Fichier");
	final Menu Edition = new Menu("Edition");
	final Menu Mode = new Menu("Mode");
	final Menu Help = new Menu("Aide");

	@SuppressWarnings("unchecked")

	public MainPanel() {
		setPrefSize(600, 450);

		// Menu
		MenuBar menuBar = new MenuBar();
		VBox menuBox = new VBox(menuBar);
		menuBar.getMenus().addAll(File, Edition, Mode, Help);

		MenuItem exportPDF = new MenuItem("Exporter en PDF");
		MenuItem add = new MenuItem("Ajouter un stagiaire");
		SeparatorMenuItem editSep = new SeparatorMenuItem();
		MenuItem modify = new MenuItem("Modifier le stagiaire");
		modify.setDisable(true);
		MenuItem delete = new MenuItem("Supprimer le stagiaire");
		delete.setDisable(true);

		RadioMenuItem user = new RadioMenuItem("Utilisateur");
		RadioMenuItem admin = new RadioMenuItem("Administrateur");
		ToggleGroup userMode = new ToggleGroup();
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
		searchBox.getChildren().addAll(searchField, searchButton, advSearchButton);
		searchButton.getParent().requestFocus();

		// ListPan


		observableStudents = FXCollections.observableArrayList(dao.loadStudentFile());

		TableView<Student> studentTable = new TableView<Student>(observableStudents);

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

		lastNameCol.setMinWidth(100);
		lastNameCol.setPrefWidth(150);

		firstNameCol.setMinWidth(100);
		firstNameCol.setPrefWidth(120);

		studentTable.getColumns().addAll(lastNameCol, firstNameCol, zipCodeCol, classCol, yearCol);
		studentTable.prefWidthProperty().bind(mainPan.widthProperty());
		studentTable.prefHeightProperty().bind(mainPan.heightProperty());


		//SearchingStudent filter
		FilteredList<Student> searchingStudent = new FilteredList<Student>(observableStudents);
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {searchingStudent.setPredicate(student -> {
			return searchingStudentCondition(newValue, student); 
		});
		});

		//TableView Automatically Ascending Sorted by LastName then FirstName then YearCol(Descending)
		SortedList<Student> sortedData = new SortedList<>(searchingStudent);
		sortedData.comparatorProperty().bind(studentTable.comparatorProperty());
		studentTable.setItems(sortedData);
		yearCol.setSortType(SortType.DESCENDING);
		studentTable.getSortOrder().addAll(lastNameCol,firstNameCol,yearCol) ;




		AnchorPane listPan = new AnchorPane();
		AnchorPane.setTopAnchor(studentTable, 5.);
		AnchorPane.setBottomAnchor(studentTable, 5.);
		AnchorPane.setRightAnchor(studentTable, 5.);
		AnchorPane.setLeftAnchor(studentTable, 5.);
		listPan.getChildren().addAll(studentTable, addButton, editButton, delButton);
		// listPan.setPrefSize(600, 400);

		// ButtonBox
		HBox buttonBox = new HBox(20);

		buttonBox.getChildren().addAll(addButton, editButton, delButton);
		editButton.setDisable(true);
		delButton.setDisable(true);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPrefHeight(50);

		mainPan.getChildren().addAll(searchBox, listPan);
		this.setTop(menuBox);
		this.setCenter(mainPan);
		this.setBottom(buttonBox);


		// EVENTS--------------------------------------------------------------------------------------

		// Events menu

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addStudentPanel();
			}

		});

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addStudentPanel();


			}

		});

		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				winMode = "edit";
				int nbStudentModif = studentTable.getSelectionModel().getSelectedIndex();
				EditPanel root = new EditPanel(winMode, nbStudentModif, dao.getStudent(nbStudentModif));
				Scene scene2 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene2);
			}
		});

		delButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (StudentDao.deleteStudentConfirmation(studentTable.getSelectionModel().getSelectedIndex())) {
//					studentTable.getItems().remove(studentTable.getSelectionModel().getSelectedIndex());
					observableStudents.remove(studentTable.getSelectionModel().getSelectedIndex());
			}
		}});

		studentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

			@Override
			public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
				editButton.setDisable(false);
				delButton.setDisable(false);
			}
		});


	}

	private boolean searchingStudentCondition(String newValue, Student student) {
		//null : display all // true = match found // false = doesnt match	
		if (newValue == null || newValue.isEmpty()) { 
			return true;
		}
		String lowerCaseFilter = newValue.toLowerCase();
		if (student.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
			return true; 
		} 
		else  
			return false;
	}

	private void addStudentPanel() {
		winMode = "add";
		EditPanel root = new EditPanel(winMode, -1, null);
		Scene scene2 = new Scene(root);
		Stage stage = (Stage) getScene().getWindow();

		stage.setScene(scene2);
	}

	public void switchUserMode(String userMode) {
		if (userMode.equals("admin")) {
			userMode = "user";
		} else if (userMode.equals("user")) {
			TextInputDialog passwordDialog = new TextInputDialog();
			passwordDialog.setTitle("Mode Administrateur");
			passwordDialog.setHeaderText("Identifiez-vous");
			passwordDialog.setContentText("Veuillez entrer le mot de passe");
			// Traditional way to get the response value.
			Optional<String> result = passwordDialog.showAndWait();
			if (result.isPresent()) {
				System.out.println("Your name: " + result.get());
			}

			// The Java 8 way to get the response value (with lambda expression).
			result.ifPresent(name -> System.out.println("Your name: " + name));
		}
	}

	//-----------------------------------------GET&SET 
	public ObservableList<Student> getObservableStudents() {
		return observableStudents;
	}

	public StudentDao getDao() {
		return dao;
	}

	public void setDao(StudentDao dao) {
		this.dao = dao;
	}


}