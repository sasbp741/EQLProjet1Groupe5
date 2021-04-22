package fr.eql.ai109.projet1;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;

public class StudentDao {

	public Callback loadStudentFile() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void addStudent(Student student) {
		// TODO Auto-generated method stub
		
	}

	public static void editStudent(int studentId, Student student) {
		// TODO Auto-generated method stub
		
	}

	public Student getStudent(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void deleteStudent(int selectedIndex) {
		Alert deleteStudentAlert = new Alert(AlertType.CONFIRMATION);
		deleteStudentAlert.setTitle("Suppression d'un stagiaire");
		deleteStudentAlert.setHeaderText("Vous allez supprimer le stagiaire suivant :\nNOM Prénom");
		deleteStudentAlert.setContentText("Voulez-vous continuer ?");

		deleteStudentAlert.showAndWait();
	}

}
