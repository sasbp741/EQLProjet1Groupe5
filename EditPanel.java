package fr.eql.ai109.projet1;

import java.time.Year;

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

	//Attention aux accents/encodage
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
	private Label lblPromo = new Label("Promotion :");
	private Label lblYear = new Label("Année :");
	


	private ObservableList<String> observableZipCodes = FXCollections.observableArrayList(
		//French Departements
		"01" , "02" , "03" , "04" , "05" , "06" , "07" , "08" , "09" , 	"10" , "11" , "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "21" , "22" , "23" , "24" , "25" , "26" , "27" , "28" , "29" , 	"30" , "31" , "32" , "33" , "34" , "35" , "36" , "37" , "38" , "39" , 
		"40" , "41" , "42" , "43" , "44" , "45" , "46" , "47" , "48" , "49" , "50" , "51" , "52" , "53" , "54" , "55" , "56" , "57" , "58" , "59" , 	"60" , "61" , "62" , "63" , "64" , "65" , "66" , "67" , "68" , "69" , 			"70" , "71" , "72" , "73" , "74" , "75" , "76" , "77" , "78" , "79" , "80" , "81" , "82" , "83" , "84" , "85" , "86" , "87" , "88" , "89" , "90" , "91" , "92" , "93" , "94" , "95" , "2A" , "2B" , "971" , "972" , "973" , "974" , "976",
		//Country ISO code
		"AE" , "AF" , "AG" , "AI" , "AL" , "AM" , "AN" , "AO" , "AQ" , "AR" , "AS" , "AT" , "AU" , "AW" , "AX" , "AZ" ,  "BA" , "BB" , "BD" , "BE" , "BF" , "BG" , "BH" , "BI" , "BJ" , "BM" , "BN" , "BO" , "BR" , "BS" , "BT" , "BV" , "BW" , "BY" , "BZ" , "CA" , "CC" , "CD" , "CF" , "CG" , "CH" , "CI" , "CK" , "CL" , "CM" , "CN" , "CO" , "CR" , "CS" , "CU" , "CV" , "CX" , "CY" , "CZ" , 	"DE" , "DJ" , "DK" , "DM" , "DO" , "DZ" ,  "EC" , "EE" , "EG" , "EH" , "ER" , "ES" , "ET" , "FI" , "FJ" , "FK" , "FM" , "FO" , "FR" , 	"GA" , "GB" , "GD" , "GE" , "GF" , "GH" , "GI" , "GL" , "GM" , "GN" , "GP" , "GQ" , "GR" , "GS" , "GT" , "GU" , "GW" , "GY" , "HK" , "HM" , "HN" , "HR" , "HT" , "HU" , "ID" , "IE" , "IL" , "IM" , "IN" , "IO" , "IQ" , "IR" , "IS" , "IT" , 	
		"JM" , "JO" , "JP" , "KE" , "KG" , "KH" , "KI" , "KM" , "KN" , "KP" , "KR" , "KW" , "KY" , "KZ" , 	"LA" , "LB" , "LC" , "LI" , "LK" , "LR" , "LS" , "LT" , "LU" , "LV" , "LY" , "MA" , "MC" , "MD" , "MG" , "MH" , "MK" , "ML" , "MM" , "MN" , "MO" , "MP" , "MQ" , "MR" , "MS" , "MT" , "MU" , "MV" , "MW" , "MX" , "MY" , "MZ" , "NA" , "NC" , "NE" , "NF" , "NG" , "NI" , "NL" , "NO" , "NP" , "NR" , "NU" , "NZ" , 	"OM" , 	"PA" , "PE" , "PF" , "PG" , "PH" , "PK" , "PL" , "PM" , "PN" , "PR" , "PS" , "PT" , "PW" , "PY" , "QA" , 	"RE" , "RO" , "RU" , "RW" , "SA" , "SB" , "SC" , "SD" , "SE" , "SG" , "SH" , "SI" , "SJ" , "SK" , "SL" , "SM" , "SN" , "SO" , "SR" , "ST" , "SV" , "SY" , "SZ" , "TC" , "TD" , "TF" , "TG" , "TH" , "TJ" , "TK" , "TL" , "TM" , "TN" , "TO" , "TR" , "TT" , "TV" , "TW" , "TZ" , "UA" , "UG" , "UM" , "US" , "UY" , "UZ" , "VA" , "VC" , "VE" , "VG" , "VI" , "VN" , "VU" , "WF" , "WS" , "YE" , "YT" , "ZA" , "ZM" , "ZW" , "ZM" );

	//Arraylist à remplir avec les données du RAF (promo)
	private ObservableList<String> observablePromos = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5","6", "7", "8", "9", "10");

	private ObservableList<String> observableYears = FXCollections.observableArrayList(comboBoxYearRange());

	public String nom, prenom, departement, promotion, annee = "";
	private TextField fldLastName = new TextField(nom);
	private TextField fldFirstName = new TextField(prenom);
	private TextField fldPromo = new TextField(promotion); // to addStudent
	private ComboBox<String> cbZipCode = new ComboBox<String>(observableZipCodes);
	private ComboBox<String> cbPromo = new ComboBox<String>(observablePromos); //for advSearch
	private ComboBox<String> cbYear = new ComboBox<String>(observableYears);

	private Button saveButton = new Button("Ajouter le student");
	private Button cancelButton = new Button("Annuler");


	public EditPanel(String winMode, int studentIndex, Student student, StudentDao dao) {
		
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

		HBox hbPromo = new HBox(hSpace);
		hbPromo.getChildren().addAll(lblPromo, fldPromo);
		//	fldPromo.setMaxWidth(100.);

		HBox hbZipCode = new HBox(hSpace);
		hbZipCode.getChildren().addAll(lblZipCode, cbZipCode);

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
		form.getChildren().addAll(hbLastName, hbFirstName, hbPromo, hbZipCode, hbYear);

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

				String lastName =  fldLastName.getText().toUpperCase();
				String firstName = capitalizeWords(fldFirstName.getText()) ;
				String zipCode = cbZipCode.getSelectionModel().getSelectedItem() ;
				String promo = fldPromo.getText().toUpperCase() ;
				String year = cbYear.getSelectionModel().getSelectedItem();
				Student studentGetTextField = new Student (lastName, firstName, zipCode, promo, year) ;

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

				MainPanel root = new MainPanel(dao);

				root.getObservableStudents().add(studentGetTextField) ;
				
				dao.addStudent(studentGetTextField);
				

				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);

			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainPanel root = new MainPanel(dao);
				Scene scene1 = new Scene(root);
				Stage stage = (Stage) getScene().getWindow();

				stage.setScene(scene1);
			}
		});



	}

	public static String capitalizeWords(String string) { 
		char[] chars = string.toLowerCase().toCharArray(); 
		boolean found = false; 
		for (int i = 0; i < chars.length; i++) { 
			if (!found && Character.isLetter(chars[i])) { 
				chars[i] = Character.toUpperCase(chars[i]); 
				found = true; 
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'' || chars[i]=='-'  || chars[i]==' ') { 
				found = false; 
			}
		}
		return String.valueOf(chars); 
	}

	public String[] comboBoxYearRange() {    
		Year startDate = Year.now().minusYears(20);  //magic number memo pour refacto //si valeur de départ fixe : Year.of(2002)
		Year endDate   = Year.now().plusYears(10);   //magic number memo pour refacto
		Year dateCalculate = startDate ;
		String yearList[] = new String[30]; //magic number memo pour refacto 20+10 =30
		while(dateCalculate.isBefore(endDate)){
			for(int i = 0 ; i < yearList.length ; i++) {
				dateCalculate = dateCalculate.plusYears(1) ;
				yearList[i]=dateCalculate.toString() ;
			}
		}
		return yearList;
	}


	public TextField getFldLastName() {
		return fldLastName;
	}

	public void setFldLastName(TextField fldLastName) {
		this.fldLastName = fldLastName;
	}

	public TextField getFldFirstName() {
		return fldFirstName;
	}

	public void setFldFirstName(TextField fldFirstName) {
		this.fldFirstName = fldFirstName;
	}

	public TextField getFldPromo() {
		return fldPromo;
	}

	public void setFldPromo(TextField fldPromo) {
		this.fldPromo = fldPromo;
	}


	public ComboBox<String> getCbZipCode() {
		return cbZipCode;
	}

	public void setCbZipCode(ComboBox<String> cbZipCode) {
		this.cbZipCode = cbZipCode;
	}

	public ComboBox<String> getCbPromo() {
		return cbPromo;
	}

	public void setCbPromo(ComboBox<String> cbPromo) {
		this.cbPromo = cbPromo;
	}

	public ComboBox<String> getCbYear() {
		return cbYear;
	}

	public void setCbYear(ComboBox<String> cbYear) {
		this.cbYear = cbYear;
	}

	public Label getLblZipCode() {
		return lblZipCode;
	}

	public void setLblZipCode(Label lblZipCode) {
		this.lblZipCode = lblZipCode;
	}

	public Label getLblPromo() {
		return lblPromo;
	}

	public void setLblPromo(Label lblPromo) {
		this.lblPromo = lblPromo;
	}

	public Label getLblYear() {
		return lblYear;
	}

	public void setLblYear(Label lblYear) {
		this.lblYear = lblYear;
	}

}
