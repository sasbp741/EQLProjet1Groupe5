package fr.eql.ai109.projet1;

import java.time.Year;
import java.util.ArrayList;
import java.util.Set;

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
	private Label lblPrenom = new Label("Prénom du stagiaire :");
	private Label lblDepartement = new Label("Département :");
	private Label lblPromotion = new Label("Promotion");
	private Label lblAnnee = new Label("Année");
	//	private Label lblRegexName = new Label("Votre nom est incorrecte");
	//	private Label lblRegexSurname = new Label("Votre prenom est incorrecte");

	private Label lblLastName = new Label("Nom du stagiaire : ");
	private Label lblFirstName = new Label("Pr�nom du stagiaire : ");
	private Label lblZipCode = new Label("D�partement :");
	private Label lblPromo = new Label("Promotion :");
	private Label lblYear = new Label("Ann�e :");
	
	
	


	public ObservableList<String> observableZipCodes = FXCollections.observableArrayList(
		//French Departements
		"--", "01" , "02" , "03" , "04" , "05" , "06" , "07" , "08" , "09" , 	"10" , "11" , "12" , "13" , "14" , "15" , "16" , "17" , "18" , "19" , "21" , "22" , "23" , "24" , "25" , "26" , "27" , "28" , "29" , 	"30" , "31" , "32" , "33" , "34" , "35" , "36" , "37" , "38" , "39" , 
		"40" , "41" , "42" , "43" , "44" , "45" , "46" , "47" , "48" , "49" , "50" , "51" , "52" , "53" , "54" , "55" , "56" , "57" , "58" , "59" , 	"60" , "61" , "62" , "63" , "64" , "65" , "66" , "67" , "68" , "69" , 			"70" , "71" , "72" , "73" , "74" , "75" , "76" , "77" , "78" , "79" , "80" , "81" , "82" , "83" , "84" , "85" , "86" , "87" , "88" , "89" , "90" , "91" , "92" , "93" , "94" , "95" , "2A" , "2B" , "971" , "972" , "973" , "974" , "976",
		//Country ISO code
		"AE" , "AF" , "AG" , "AI" , "AL" , "AM" , "AN" , "AO" , "AQ" , "AR" , "AS" , "AT" , "AU" , "AW" , "AX" , "AZ" ,  "BA" , "BB" , "BD" , "BE" , "BF" , "BG" , "BH" , "BI" , "BJ" , "BM" , "BN" , "BO" , "BR" , "BS" , "BT" , "BV" , "BW" , "BY" , "BZ" , "CA" , "CC" , "CD" , "CF" , "CG" , "CH" , "CI" , "CK" , "CL" , "CM" , "CN" , "CO" , "CR" , "CS" , "CU" , "CV" , "CX" , "CY" , "CZ" , 	"DE" , "DJ" , "DK" , "DM" , "DO" , "DZ" ,  "EC" , "EE" , "EG" , "EH" , "ER" , "ES" , "ET" , "FI" , "FJ" , "FK" , "FM" , "FO" , "FR" , 	"GA" , "GB" , "GD" , "GE" , "GF" , "GH" , "GI" , "GL" , "GM" , "GN" , "GP" , "GQ" , "GR" , "GS" , "GT" , "GU" , "GW" , "GY" , "HK" , "HM" , "HN" , "HR" , "HT" , "HU" , "ID" , "IE" , "IL" , "IM" , "IN" , "IO" , "IQ" , "IR" , "IS" , "IT" , 	
		"JM" , "JO" , "JP" , "KE" , "KG" , "KH" , "KI" , "KM" , "KN" , "KP" , "KR" , "KW" , "KY" , "KZ" , 	"LA" , "LB" , "LC" , "LI" , "LK" , "LR" , "LS" , "LT" , "LU" , "LV" , "LY" , "MA" , "MC" , "MD" , "MG" , "MH" , "MK" , "ML" , "MM" , "MN" , "MO" , "MP" , "MQ" , "MR" , "MS" , "MT" , "MU" , "MV" , "MW" , "MX" , "MY" , "MZ" , "NA" , "NC" , "NE" , "NF" , "NG" , "NI" , "NL" , "NO" , "NP" , "NR" , "NU" , "NZ" , 	"OM" , 	"PA" , "PE" , "PF" , "PG" , "PH" , "PK" , "PL" , "PM" , "PN" , "PR" , "PS" , "PT" , "PW" , "PY" , "QA" , 	"RE" , "RO" , "RU" , "RW" , "SA" , "SB" , "SC" , "SD" , "SE" , "SG" , "SH" , "SI" , "SJ" , "SK" , "SL" , "SM" , "SN" , "SO" , "SR" , "ST" , "SV" , "SY" , "SZ" , "TC" , "TD" , "TF" , "TG" , "TH" , "TJ" , "TK" , "TL" , "TM" , "TN" , "TO" , "TR" , "TT" , "TV" , "TW" , "TZ" , "UA" , "UG" , "UM" , "US" , "UY" , "UZ" , "VA" , "VC" , "VE" , "VG" , "VI" , "VN" , "VU" , "WF" , "WS" , "YE" , "YT" , "ZA" , "ZM" , "ZW" , "ZM" );

	
	public ObservableList<String> observablePromos = FXCollections.observableArrayList(	"--", "AI 56", "AI 57", "AI 58", "AI 59", "AI 60", "AI 61", "AI 62", "AI 63", "AI 64", "AI 65", "AI 66", "AI 67", "AI 68", "AI 69", "AI 70", "AI 71", "AI 72", "AI 73", "AI 74", "AI 75", "AI 76", "AI 77", "AI 78", "AI 79", "AI 80", "AI 81", "AI 82", "AI 83", "AI 84", "AI 8412", "AI 85", "AI 86", "AI 87", "AI 88", "AI 89", "AI 90", "AI 91", "AI 92", "AI 93", "AI 94", "AI 95", "AI 96", "AI 97", "AROBAS 1", "AROBAS 2", "AROBAS 3", "ATOD 1", "ATOD 10", "ATOD 11", "ATOD 12", "ATOD 13", "ATOD 13 CP", "ATOD 14", "ATOD 15", "ATOD 15 CP", "ATOD 16", "ATOD 16 CP", "ATOD 17", "ATOD 17 CP", "ATOD 18", "ATOD 18 CP", "ATOD 19", "ATOD 19 CP", "ATOD 2", "ATOD 20", "ATOD 20 CP", "ATOD 21", "ATOD 21 CP", "ATOD 22", "ATOD 22 CP", "ATOD 23", "ATOD 23 CP", "ATOD 24", "ATOD 24 CP", "ATOD 25", "ATOD 25 CP", "ATOD 26", "ATOD 26 CP", "ATOD 3", "ATOD 4", "ATOD 5", "ATOD 6", "ATOD 7", "ATOD 8", "ATOD 9", "BOBI 1", "BOBI 2", "BOBI 3", "BOBI 4", "BOBI 5");
	
	public ObservableList<String> observableYears = FXCollections.observableArrayList(comboBoxYearRange());

	public String nom, prenom, departement, promotion, annee = "";
	private TextField fldLastName = new TextField(nom);
	private TextField fldFirstName = new TextField(prenom);
	private TextField fldPromo = new TextField(promotion); // to addStudent
	public ComboBox<String> cbZipCode = new ComboBox<String>(observableZipCodes);
	public ComboBox<String> cbPromo = new ComboBox<String>(observablePromos); //for advSearch
	public ComboBox<String> cbYear = new ComboBox<String>(observableYears);

	private Button saveButton = new Button("Ajouter le stagiaire");
	private Button cancelButton = new Button("Annuler");

	

	public EditPanel(String winMode, Student student, StudentDao dao) {
		
		
		
		
		setPrefSize(350, 200);

		// setMaxWidth(300);
		// setMaxHeight(200);

		//		lblRegexName.setVisible(false);
		//		lblRegexSurname.setVisible(false);

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


//		HBox hbNom = new HBox(hSpace);
//		hbNom.getChildren().addAll(lblNom, fldLastName);//lblRegexName
//
//		HBox hbPrenom = new HBox(hSpace);
//		hbPrenom.getChildren().addAll(lblPrenom, fldFirstName);//lblRegexSurname

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
			headerTitle.setText("Modifier un stagiaire");
			saveButton.setText("Confirmer la modification");
			fldLastName.setText(student.getLastName());
			fldFirstName.setText(student.getFirstName());
			cbZipCode.getSelectionModel().select(student.getZipCode());
			fldPromo.setText(student.getPromo());
			cbYear.getSelectionModel().select(student.getYear());
		}

	
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (winMode == "edit") {
					dao.deleteStudent(student.getIndex());
				} 
				
				String lastName =  fldLastName.getText().toUpperCase();
				String firstName = capitalizeWords(fldFirstName.getText()) ;
				String zipCode = cbZipCode.getSelectionModel().getSelectedItem() ;
				String promo = fldPromo.getText().toUpperCase() ;
				String year = cbYear.getSelectionModel().getSelectedItem();
				Student studentGetTextField = new Student (lastName, firstName, zipCode, promo, year,dao.entriesNumber) ;
				
				MainPanel root = new MainPanel(dao);
				dao.addStudent(studentGetTextField);
				dao.loadStudentTree(root.observableStudents);
				//Increase entriesnumber label :
				//updateLblTotalEntriesNumber(dao.entriesNumberVisible++) ;

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

			//	root.getObservableStudents().add(studentGetTextField) ;
								
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
		}) ;
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
		Year startDate = Year.now().minusYears(20);  //magic number memo pour refacto //si valeur de d�part fixe : Year.of(2002)
		Year endDate   = Year.now().plusYears(0);   //magic number memo pour refacto
		Year dateCalculate = startDate ;
		String yearList[] = new String[30] ;
		yearList[0] = "--"; //magic number memo pour refacto 20+10 =30
		while(dateCalculate.isBefore(endDate)){
			for(int i = 1 ; i < yearList.length ; i++) {
				dateCalculate = dateCalculate.plusYears(1) ;
				yearList[i]=dateCalculate.toString() ;
			}
		}
		return yearList;
	}

	public ArrayList<String> comboBoxPromoList(StudentDao dao) {    
		ArrayList <String>promoArrayList = new ArrayList<String>();
		Set<String>keys = dao.promoList.keySet() ;
		promoArrayList.add("--") ;
		for(String key : keys) {				
				promoArrayList.add(key) ;
			} 
		for (String key : promoArrayList) {
			System.out.println(key);
		}
		return promoArrayList;			
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

//	public Label getLblTotalEntries() {
//		return lblTotalEntries;
//	}
//
//	public void setLblTotalEntries(Label lblTotalEntries) {
//		this.lblTotalEntries = lblTotalEntries;
//	}

}
