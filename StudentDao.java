package fr.eql.ai109.projet1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class StudentDao {

	private static String destinationPath = "c:/projet1/stagiairesRaf.bin";
	private static ArrayList<Student> studentList = new ArrayList<Student>();

	static int entriesNumber = 1314;
	static int SEQUENCE_LENGTH = 81;
	static int[] maxLength = new int[] {21,20,2,10,4};
	private static String spaceChar = " ";
	private static int CHILDREN_MAX_LENGTH = 12;


	public List<Student> loadStudentFile() {
		studentList.clear();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(destinationPath, "r");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < entriesNumber; i++) {
			String[] studentInfo = new String[5];
			try {
				raf.seek(i * SEQUENCE_LENGTH);
				for (int j = 0; j < 5; j++) {
					byte[] b = new byte[maxLength[j]];
					raf.read(b);
					studentInfo[j] = new String(b).trim();
				}
				// System.out.println(studentInfo[0] + " " + studentInfo[1] + " " +
				// studentInfo[2] + " " + studentInfo[3] + " " + studentInfo[4]);
				Student student = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3],
						studentInfo[4]);
				studentList.add(student);
				// System.out.println(student.getLastname() + " : "+student.getFirstname() + " :
				// "+student.getZipcode() + " : "+student.getPromo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	private static String studentToString (Student student) {

		String[] str = new String[] {student.getLastName(),student.getFirstName(),student.getZipCode(),student.getPromo(),student.getYear()};
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < 6; j++) {
			if (j < 5) {
				sb.append(str[j]);
				for (int i = 0; i < maxLength[j] - str[j].length(); i++) {
					sb.append(spaceChar);
				}
			}else {
				for (int i = 0; i < 2 * CHILDREN_MAX_LENGTH; i++) {
					sb.append(spaceChar);
				}
			}
		}
		return sb.toString();
	}

	public static void addStudent(Student student) { 
		String chaine = studentToString(student) ;
		MainApp addMethode = new MainApp() ;
		RandomAccessFile raf = null;			
			try {
				raf = new RandomAccessFile(destinationPath, "rw");
				raf.seek(SEQUENCE_LENGTH * entriesNumber);
				byte[] b = chaine.getBytes();
				raf.write(b);
				addMethode.addNewStudentToTree(entriesNumber+1) ; //to be confirmed, index of new student = entriesNumber+1 ? even when deleted? to be confirmed
				entriesNumber++;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void editStudent(int studentId, Student student) {
		// TODO Auto-generated method stub

	}

	public Student getStudent(int studentId) {
		return studentList.get(studentId);
	}

	public static boolean deleteStudentConfirmation(int selectedIndex) { //TODO : suppression dans le fichier binaire
		Alert deleteStudentAlert = new Alert(AlertType.CONFIRMATION);
		deleteStudentAlert.setTitle("Suppression d'un stagiaire");
		deleteStudentAlert.setHeaderText("Vous allez supprimer le stagiaire suivant :\nNOM Prénom");
		deleteStudentAlert.setContentText("Voulez-vous continuer ?");

		Optional<ButtonType> option = deleteStudentAlert.showAndWait();
		if (!option.isPresent() || option.get() == ButtonType.OK){
			return true ;
		} else {
			return false;
		}
	}
}
