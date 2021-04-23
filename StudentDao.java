package fr.eql.ai109.projet1;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class StudentDao {
	private static String destinationPath = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiairesRaf.bin";
	private static ArrayList<Student> studentList = new ArrayList<Student>();

	public List<Student> loadStudentFile() {
		studentList.clear();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(destinationPath, "r");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		int entriesNumber = 1314;
		int SEQUENCE_LENGTH = 81;
		int[] maxLength = new int[5];
		maxLength[0] = 21;
		maxLength[1] = 20;
		maxLength[2] = 2;
		maxLength[3] = 10;
		maxLength[4] = 4;

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
		return studentList;
	}

	public static void addStudent(Student student) {
		// TODO Auto-generated method stub

	}

	public static void editStudent(int studentId, Student student) {
		// TODO Auto-generated method stub

	}

	public Student getStudent(int studentId) {
		return studentList.get(studentId);
	}

	public static void deleteStudent(int selectedIndex) {
		Alert deleteStudentAlert = new Alert(AlertType.CONFIRMATION);
		deleteStudentAlert.setTitle("Suppression d'un stagiaire");
		deleteStudentAlert.setHeaderText("Vous allez supprimer le stagiaire suivant :\nNOM Prénom");
		deleteStudentAlert.setContentText("Voulez-vous continuer ?");

		deleteStudentAlert.showAndWait();
	}

    public static void exportToPdf() throws IOException {
        String filename = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\sample.pdf";
        String title = "Annuaire EQL";
         
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);
             
            PDFont titleFont = PDType1Font.HELVETICA_BOLD;
 
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(titleFont, 30);
            contents.newLineAtOffset(50, 700);
            contents.showText(title);
            contents.endText();
            contents.close();
             
            doc.save(filename);
        }
        finally {
            doc.close();
        }
    }
}
