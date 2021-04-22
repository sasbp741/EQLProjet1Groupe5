package fr.eql.ai109.projet1;

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
	private Label lblPrenom = new Label("Prénom du stagiaire :");
	private Label lblDepartement = new Label("Département :");
	private Label lblPromotion = new Label("Promotion");
	private Label lblAnnee = new Label("Année");

	private ObservableList<String> observableDepartements = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
			"25");

	private ObservableList<String> observablePromos = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10");

	private ObservableList<String> observableAnnees = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "10");

	
	public String nom, prenom, departement, promotion, annee = "";
	private TextField fldNom = new TextField(nom);
	private TextField fldPrenom = new TextField(prenom);
	private ComboBox<String> cbDepartement = new ComboBox<String>(observableDepartements);
	private ComboBox<String> cbPromotion = new ComboBox<String>(observablePromos);
	private ComboBox<String> cbAnnee = new ComboBox<String>(observableAnnees);
	

	private Button btSauv = new Button("Ajouter le stagiaire");
	private Button btAnnul = new Button("Annuler");

	public EditPanel(String winMode, int stagiaireIndex, Student stagiaire) {
		setPrefSize(350, 200);

		// setMaxWidth(300);
		// setMaxHeight(200);

		HBox header = new HBox(30);
		header.setAlignment(Pos.CENTER);

		Label headerTitle = new Label("Ajouter un stagiaire");
		headerTitle.setMinHeight(50);
		headerTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		header.getChildren().add(headerTitle);

		HBox middle = new HBox(10);
		VBox form = new VBox(20);
		middle.getChildren().add(form);
		int hSpace = 20;

		HBox hbNom = new HBox(hSpace);
		hbNom.getChildren().addAll(lblNom, fldNom);

		HBox hbPrenom = new HBox(hSpace);
		hbPrenom.getChildren().addAll(lblPrenom, fldPrenom);

		HBox hbDepartement = new HBox(hSpace);
		hbDepartement.getChildren().addAll(lblDepartement, cbDepartement);

		HBox hbPromotion = new HBox(hSpace);
		hbPromotion.getChildren().addAll(lblPromotion, cbPromotion);

		HBox hbAnnee = new HBox(hSpace);
		hbAnnee.getChildren().addAll(lblAnnee, cbAnnee);

		HBox hbBoutons = new HBox(50);
		hbBoutons.setMinHeight(100);
		hbBoutons.getChildren().addAll(btSauv, btAnnul);
		hbBoutons.setAlignment(Pos.CENTER);

		Pos hbAlign = Pos.BASELINE_RIGHT;
		hbNom.setAlignment(hbAlign);
		hbPrenom.setAlignment(hbAlign);
		hbDepartement.setAlignment(hbAlign);
		hbPromotion.setAlignment(hbAlign);
		hbAnnee.setAlignment(hbAlign);
		middle.setAlignment(Pos.CENTER);
		form.getChildren().addAll(hbNom, hbPrenom, hbDepartement, hbPromotion, hbAnnee);

		setTop(header);
		setCenter(middle);
		setBottom(hbBoutons);

//		if (winMode == "edit") {
//			headerTitle.setText("Modifier une série");
//			btSauv.setText("Confirmer la modification");
//			fldNom.setText(stagiaire.getNom());
//			fldPrenom.setText(stagiaire.getPrenomSortie());
//			cbDepartement.getSelectionModel().select(stagiaire.getNbSaisons());
//			fldPromotion.setText(stagiaire.getMaisonProd());
//			cbAnnee.getSelectionModel().select(stagiaire.getNote());
//		}

		btSauv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				if (winMode == "add") {
//					Stagiaire stagiaire = new Stagiaire(fldNom.getText(), fldPrenom.getText(),
//							cbDepartement.getSelectionModel().getSelectedItem(), cbPromotion.getSelectionModel().getSelectedItem(),
//							cbAnnee.getSelectionModel().getSelectedItem());
//					StagiaireDao.ajouterStagiaire(stagiaire);
//				} else if (winMode == "edit") {
//					Stagiaire stagiaire = new Stagiaire(fldNom.getText(), fldPrenom.getText(),
//							cbDepartement.getSelectionModel().getSelectedItem(), cbPromotion.getSelectionModel().getSelectedItem(),
//							cbAnnee.getSelectionModel().getSelectedItem());
//					StagiaireDao.modifierStagiaire(stagiaireIndex, stagiaire);
//				}
				MainPanel root = new MainPanel();
				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);

			}
		});

		btAnnul.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainPanel root = new MainPanel();
				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);
			}
		});

	}

}
