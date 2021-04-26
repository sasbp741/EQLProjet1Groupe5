package fr.eql.ai109.projet1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sound.midi.Sequence;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class StudentDao {

	private static ArrayList<Student> studentList = new ArrayList<Student>();
	private static String originalPath = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiaires.txt";
	private static String destinationPath = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiairesRaf.bin";
	private static int[] maxLength = new int[7];
	private static String spaceChar = " ";
	private static int CHILDREN_MAX_LENGTH = 12;
	public File binFile = new File(destinationPath);
	private static int SEQUENCE_LENGTH = 0;
	static int entriesNumber = 0;
	private static boolean[][] isWritten = new boolean[1314][3];
	private static String settingsPathFile = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\settings.bin";
	public static File settingsFile = new File(settingsPathFile);
	public static int settingsLength = 5;


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

	public String studentToString (Student student) {

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

	public void addStudent(Student student) { 
		String chaine = studentToString(student) ;
		RandomAccessFile raf = null;			
		try {
			raf = new RandomAccessFile(destinationPath, "rw");
			raf.seek(SEQUENCE_LENGTH * entriesNumber);
			byte[] b = chaine.getBytes();
			raf.write(b);
			addNewStudentToTree(entriesNumber) ; //to be confirmed, index of new student = entriesNumber+1 ? even when deleted? to be confirmed
			entriesNumber++;
			writeSettings(6, entriesNumber);
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

	public void editStudent(int studentId, Student student) {
		// TODO Auto-generated method stub

	}

	public Student getStudent(int studentId) {
		return studentList.get(studentId);
	}

	public boolean deleteStudentConfirmation(int selectedIndex) { //TODO : suppression dans le fichier binaire
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
	//----------------- AJOUT

	public void addNewStudentToTree(int indexNewStudent) {
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
			//			raf.seek(SEQUENCE_LENGTH * entriesNumber);
			//			raf.write("PHOMMA               Yasamine            92AI 94     2015                        ".getBytes());
			//			entriesNumber++;
			//			int indexNewStudent = entriesNumber - 1;
			String newStudentName = getStudentString(indexNewStudent, raf).trim();
			String medianName = getStudentString(657,raf).trim();
			if (newStudentName.compareTo(medianName) >= 0) {
				String SAD = getStudentSAD(657, raf);
				if (SAD.length() > 0) {
					int indexSAD = Integer.parseInt(SAD);
					addNewStudentToTreeRecursive(indexSAD, newStudentName, raf, indexNewStudent);
				} else {
					//					System.out.println("pas de lancement");
				}
			} else {
				String SAG = getStudentSAG(657, raf);
				if (SAG.length() > 0) {
					int indexSAG = Integer.parseInt(SAG);
					addNewStudentToTreeRecursive(indexSAG, newStudentName, raf, indexNewStudent);
				} else {
					//					System.out.println("pas de lancement");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void addNewStudentToTreeRecursive(int index, String name, RandomAccessFile raf, int indexNewFile) throws IOException {
		String addName = getStudentString(index, raf);
		String SAD = getStudentSAD(index, raf);
		String SAG = getStudentSAG(index, raf);
		if (addName.compareTo(name) < 0) {
			if (SAD.length() < 1) {
				raf.seek((index + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH);
				raf.write(Integer.toString(indexNewFile).getBytes());
				return;
			} else {
				addNewStudentToTreeRecursive(Integer.parseInt(SAD), name, raf, indexNewFile);
			}
		} else {
			if (SAG.length() < 1) {
				raf.seek((index + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH * 2);
				raf.write(Integer.toString(indexNewFile).getBytes());
				return;
			} else {
				addNewStudentToTreeRecursive(Integer.parseInt(SAG), name, raf, indexNewFile);
			}
		}
	}

	// ------------------  ECRITURE DES SAG ET SAD

	public void writeChildren() {
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
			dichotomousWriteChildren(0, entriesNumber, raf);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void dichotomousWriteChildren(int debut, int fin, RandomAccessFile raf) throws IOException {
		int median = (debut + fin) / 2;
		//		if (fin - debut <= 2 ) {
		//			// writeFeuille(median, raf);
		//			return;
		//		} else {
		int SAG = (debut + median) / 2;
		int SAD = (median + fin) / 2;
		if (SAG == entriesNumber / 2 || SAD == entriesNumber / 2) {
			return;
		}
		if (writeSAG(median, raf, SAG) == true) {
			dichotomousWriteChildren(debut, median, raf);
		}
		if (writeSAD(median, raf, SAD) == true) {
			dichotomousWriteChildren(median, fin, raf);
		}
		// }
	}

	public boolean writeSAG(int parent, RandomAccessFile raf, int SAG) {
		if (isWritten[parent][0] == false && isWritten[SAG][2] == false) {
			try {
				raf.seek((parent + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH * 2);
				raf.write(Integer.toString(SAG).getBytes());
				isWritten[parent][0] = true;
				isWritten[SAG][2] = true;
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean writeSAD(int parent, RandomAccessFile raf, int SAD) {
		if (isWritten[parent][1] == false && isWritten[SAD][2] == false) {
			try {
				raf.seek((parent + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH);
				raf.write(Integer.toString(SAD).getBytes());
				isWritten[parent][1] = true;
				isWritten[SAD][2] = true;
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// ----------------------------------------------------------- TRIER LE TABLEAU

	public void sortTargetFile() {
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");

			for (int i = entriesNumber; i > 1; i--) {
				for (int j = 0; j < i - 1; j++) {
					String nom1 = getStudentString(j, raf).toUpperCase();
					String nom2 = getStudentString(j + 1, raf).toUpperCase();
					if (nom1.compareTo(nom2) > 0) {
						swapLines(j, j + 1, raf);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void swapLines(int i, int j, RandomAccessFile raf) throws IOException {
		String buffer = getStudent(i, raf);
		writeStudent(getStudent(j, raf), i, raf);
		writeStudent(buffer, j, raf);
	}

	public void writeStudent(String student, int index, RandomAccessFile raf) throws IOException {
		raf.seek(index * SEQUENCE_LENGTH);
		raf.write(student.getBytes());
	}

	// ----------------------------------------------------------------- AFFICHER LE
	// TABLEAU

	public void displayNames() {

		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
			for (int i = 0; i < entriesNumber; i++) {
				System.out.println(getStudent(i, raf));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getStudentString(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(0, maxLength[0] + maxLength[1]);
	}

	public String getStudent(int index, RandomAccessFile raf) throws IOException {
		raf.seek(index * SEQUENCE_LENGTH);
		byte[] b = new byte[SEQUENCE_LENGTH];
		raf.read(b);
		String student = new String(b);
		return student;
	}

	public String getStudentSAD(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(SEQUENCE_LENGTH - maxLength[5], SEQUENCE_LENGTH).trim();
	}
	public String getStudentSAG(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(SEQUENCE_LENGTH - maxLength[5] - maxLength[6], SEQUENCE_LENGTH - maxLength[5]).trim();
	}

	// ------------------------------------------------------ ECRITURE DU RAF
	// (fichier de destination)

	public void writeDestinationFile() {
		File src = new File(originalPath);
		File dest = new File(destinationPath);
		dest.delete();
		BufferedReader reader = null;
		RandomAccessFile raf = null;
		try {
			// reader = new BufferedReader(new InputStreamReader(new FileInputStream(src),
			// "Cp1252"));
			reader = new BufferedReader(new FileReader(src));
			raf = new RandomAccessFile(dest, "rw");
			String line = "";
			String lineToWrite = "";

			while (reader.ready()) {
				lineToWrite = "";
				for (int j = 0; j < 6; j++) {
					line = reader.readLine().trim();
					if (j < 5) {
						lineToWrite += line;
						for (int i = 0; i < maxLength[j] - line.length(); i++) {
							lineToWrite += spaceChar;
						}
					} else {
						for (int i = 0; i < 2 * CHILDREN_MAX_LENGTH; i++) {
							lineToWrite += spaceChar;
						}
					}
				}
				byte[] b = lineToWrite.getBytes();
				raf.write(b);
			}
			for (int i = 0; i < maxLength.length; i++) {
				SEQUENCE_LENGTH += maxLength[i];
				if (i<5) {
					writeSettings(i+1, maxLength[i]);
				}
			}
			System.out.println("Fichier correctement écrit");
			writeSettings(0, SEQUENCE_LENGTH);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (raf != null) {
					raf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ---------------------------------------------------------- RECUPERER LES
	// TAILLES MAX ET LES AFFICHER

	public void showmaxLength() {
		for (int g = 0; g < maxLength.length; g++) {
			System.out.println(maxLength[g]);
		}
	}

	public void definemaxLength() {
		File src = new File(originalPath);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(src));
			String line = "";

			while (reader.ready()) {
				for (int j = 0; j < 6; j++) {
					line = reader.readLine().trim();
					if (j < 5) {
						if (line.length() > maxLength[j]) {
							maxLength[j] = line.length();
						}
					}

				}
				entriesNumber++;
			}
			writeSettings(6, entriesNumber);
			maxLength[5] = CHILDREN_MAX_LENGTH;
			maxLength[6] = CHILDREN_MAX_LENGTH;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initiateSettingsfile() {
		RandomAccessFile raf = null;
		try {
			settingsFile.delete();
			raf = new RandomAccessFile(settingsFile, "rw");
			for (int i = 0; i < 50; i++) {
				try {
					raf.write(spaceChar.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}
			}
			try {
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeSettings(int param, int value) {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(settingsFile, "rw");
			System.out.println("param : " + param);
			System.out.println("value : " + value);
			System.out.println(" longeur : " + settingsLength);
			raf.seek(param * settingsLength);
			raf.write(String.valueOf(value).getBytes());
			
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void loadSettings() {
		RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(settingsFile, "rw");
				byte[] b = new byte[settingsLength];
				raf.read(b);
				String value = new String(b);
				SEQUENCE_LENGTH = (int)Integer.parseInt(value.trim()); 
				for (int i = 0; i < 5; i++) {
							raf.read(b);
							value = new String(b);
							maxLength[i] = Integer.valueOf(value.trim());
				}
				raf.read(b);
				value = new String(b);
				entriesNumber = Integer.valueOf(value.trim());
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

}