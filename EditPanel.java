package fr.eql.ai109.projet1;

import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditPanel extends BorderPane {


	private Label lblNom = new Label("Nom du stagiaire :");
	private Label lblPrenom = new Label("PrÃ©nom du stagiaire :");
	private Label lblDepartement = new Label("DÃ©partement :");
	private Label lblPromotion = new Label("Promotion");
	private Label lblAnnee = new Label("AnnÃ©e");
//	private Label lblRegexName = new Label("Votre nom est incorrecte");
//	private Label lblRegexSurname = new Label("Votre prenom est incorrecte");

	private Label lblLastName = new Label("Nom du student :");
	private Label lblFirstName = new Label("Prénom du student :");
	private Label lblZipCode = new Label("Département :");
	private Label lblPromo = new Label("Promotion");
	private Label lblYear = new Label("Année");


	private ObservableList<String> observableZipCodes = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
			"25");

	private ObservableList<String> observablePromos = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10");

	private ObservableList<String> observableYears = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10");

	
	public String nom, prenom, departement, promotion, annee = "";
	private TextField fldLastName = new TextField(nom);
	private TextField fldFirstName = new TextField(prenom);
	private ComboBox<String> cbZipCode = new ComboBox<String>(observableZipCodes);
	private ComboBox<String> cbPromo = new ComboBox<String>(observablePromos);
	private ComboBox<String> cbYear = new ComboBox<String>(observableYears);
	

	private Button saveButton = new Button("Ajouter le student");
	private Button cancelButton = new Button("Annuler");


	public EditPanel(String winMode, int studentIndex, Student student) {
		setPrefSize(350, 200);


		// setMaxWidth(300);
		// setMaxHeight(200);
		
//		lblRegexName.setVisible(false);
//		lblRegexSurname.setVisible(false);

		HBox header = new HBox(30);
		header.setAlignment(Pos.CENTER);

		Label headerTitle = new Label("Ajouter un student");
		headerTitle.setMinHeight(50);
		headerTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		header.getChildren().add(headerTitle);

		HBox middle = new HBox(10);
		VBox form = new VBox(20);
		middle.getChildren().add(form);
		int hSpace = 20;


		HBox hbNom = new HBox(hSpace);
		hbNom.getChildren().addAll(lblNom, fldLastName);//lblRegexName

		HBox hbPrenom = new HBox(hSpace);
		hbPrenom.getChildren().addAll(lblPrenom, fldFirstName);//lblRegexSurname

		HBox hbLastName = new HBox(hSpace);
		hbLastName.getChildren().addAll(lblLastName, fldLastName);

		HBox hbFirstName = new HBox(hSpace);
		hbFirstName.getChildren().addAll(lblFirstName, fldFirstName);


		HBox hbZipCode = new HBox(hSpace);
		hbZipCode.getChildren().addAll(lblZipCode, cbZipCode);

		HBox hbPromo = new HBox(hSpace);
		hbPromo.getChildren().addAll(lblPromo, cbPromo);

		HBox hbYear = new HBox(hSpace);
		hbYear.getChildren().addAll(lblYear, cbYear);

		HBox hbBoutons = new HBox(50);
		hbBoutons.setMinHeight(100);
		hbBoutons.getChildren().addAll(saveButton, cancelButton);
		hbBoutons.setAlignment(Pos.CENTER);

		Pos hbAlign = Pos.BASELINE_RIGHT;
		hbLastName.setAlignment(hbAlign);
		hbFirstName.setAlignment(hbAlign);
		hbZipCode.setAlignment(hbAlign);
		hbPromo.setAlignment(hbAlign);
		hbYear.setAlignment(hbAlign);
		middle.setAlignment(Pos.CENTER);
		form.getChildren().addAll(hbLastName, hbFirstName, hbZipCode, hbPromo, hbYear);

		setTop(header);
		setCenter(middle);
		setBottom(hbBoutons);

		if (winMode == "edit") {
			headerTitle.setText("Modifier un student");
			saveButton.setText("Confirmer la modification");
			fldLastName.setText(student.getLastName());
			fldFirstName.setText(student.getFirstName());
			cbZipCode.getSelectionModel().select(student.getZipCode());
			cbPromo.getSelectionModel().select(student.getPromo());
			cbYear.getSelectionModel().select(student.getYear());
		}

		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				if (winMode == "add") {
//					Stagiaire student = new Stagiaire(fldLastName.getText(), fldFirstName.getText(),
//							cbZipCode.getSelectionModel().getSelectedItem(), cbPromo.getSelectionModel().getSelectedItem(),
//							cbYear.getSelectionModel().getSelectedItem());
//					StagiaireDao.ajouterStagiaire(student);
//				} else if (winMode == "edit") {
//					Stagiaire student = new Stagiaire(fldLastName.getText(), fldFirstName.getText(),
//							cbZipCode.getSelectionModel().getSelectedItem(), cbPromo.getSelectionModel().getSelectedItem(),
//							cbYear.getSelectionModel().getSelectedItem());
//					StagiaireDao.modifierStagiaire(studentIndex, student);
//				}
//				if (REGEXLengthNom(fldPrenom.getText())) {
//					lblRegexSurname.setVisible(true);
//				}
//				if (REGEXLengthPrenom(fldNom.getText())) {
//					lblRegexSurname.setVisible(true);
//				}
			
				MainPanel root = new MainPanel();
				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);

			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainPanel root = new MainPanel();
				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);
			}
		});
		
		

	}
	
//	public static final String NAME_REGEX = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
//    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
//	public boolean REGEXLengthNom(String nom) {
//		if (nom.length() > 24) {
//			return true;
////		} else if (NAME_PATTERN.matcher(nom).matches()) {
////			return true;
//		} else if (nom.length() == 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public boolean REGEXLengthPrenom(String prenom) {
//		if (prenom.length() > 24) {
//			return true;
////		} else if (NAME_PATTERN.matcher(prenom).matches()) {
////			return true;
//		} else if (nom.length() == 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
