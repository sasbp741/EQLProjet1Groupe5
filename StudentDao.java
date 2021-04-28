package fr.eql.ai109.projet1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;

public class StudentDao {


	private static final int TREE_ROOT = 657;

	//Valentin----------------------------------------------------------------
	//	private static String originalPath = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiaires.txt";
	//	private static String destinationPath = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiairesRaf.bin";
	//	private static String settingsPathFile = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\settings.bin";

	//Sabrina --------------------------------------------------------------
	private static String originalPath = "c:/projet1/stagiaires.txt";
	private static String destinationPath = "c:/projet1/stagiairesRaf.bin";
	private static String settingsPathFile = "c:/projet1/settings.bin";

	//		private static String originalPath = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiaires.txt";
	//		private static String destinationPath = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\stagiairesRaf.bin";
	//		private static String settingsPathFile = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\settings.bin";
	//		private static String settingsPathFile = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\settings.bin";

	private static ArrayList<Student> studentList = new ArrayList<Student>();

	private static int[] maxLength = new int[7];
	private static String spaceChar = " ";
	private static int CHILDREN_MAX_LENGTH = 12;
	public File binFile = new File(destinationPath);

	private static int visibleEntriesNumber =0;
	private static int SEQUENCE_LENGTH = 0;
	static int entriesNumber = 0;
	private static boolean[][] isWritten = new boolean[1314][3];

	public static File settingsFile = new File(settingsPathFile);
	public static int settingsLength = 5;

	public List<Student> loadStudentFile() {
		System.out.println("loaded");
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
						studentInfo[4], i); // i=index
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

	public String studentToString(Student student) {

		String[] str = new String[] {student.getLastName(), student.getFirstName(), student.getZipCode(),
				student.getPromo(), student.getYear() };
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < 6; j++) {
			if (j < 5) {
				sb.append(str[j]);
				for (int i = 0; i < maxLength[j] - str[j].length(); i++) {
					sb.append(spaceChar);
				}
			} else {
				for (int i = 0; i < 2 * CHILDREN_MAX_LENGTH; i++) {
					sb.append(spaceChar);
				}
			}
		}
		return sb.toString();
	}

	public void addStudent(Student student) {
		String chaine = studentToString(student)+"1";
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(destinationPath, "rw");
			raf.seek(SEQUENCE_LENGTH * (entriesNumber));
			byte[] b = chaine.getBytes();
			raf.write(b);
			addNewStudentToTree(entriesNumber); 
			entriesNumber++;
			writeSettings(6, entriesNumber);
			
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

	public void editStudent(int studentId, Student student) {
		deleteStudent(studentId);
		addStudent(student);
	}

	public Student getStudent(int studentId) {
		return studentList.get(studentId);
	}

	public boolean deleteStudentConfirmation(Student student) { 
		Alert deleteStudentAlert = new Alert(AlertType.CONFIRMATION);
		deleteStudentAlert.setTitle("Suppression d'un stagiaire");
		deleteStudentAlert.setHeaderText("Vous �tes sur le point de supprimer un stagiaire.");
		deleteStudentAlert.setContentText("Voulez-vous continuer ?");

		Optional<ButtonType> option = deleteStudentAlert.showAndWait();
		if (!option.isPresent() || option.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	// ----------------- AJOUT

	public void addNewStudentToTree(int indexNewStudent) {
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
			// raf.seek(SEQUENCE_LENGTH * entriesNumber);
			// raf.write("PHOMMA Yasamine 92AI 94 2015 ".getBytes());
			// entriesNumber++;
			// int indexNewStudent = entriesNumber - 1;
			String newStudentName = getStudentString(indexNewStudent, raf).trim();
			String medianName = getStudentString(657, raf).trim();
			if (newStudentName.compareTo(medianName) >= 0) {
				String SAD = getStudentSAD(657, raf);
				if (SAD.length() > 0) {
					int indexSAD = Integer.parseInt(SAD);
					addNewStudentToTreeRecursive(indexSAD, newStudentName, raf, indexNewStudent);
				} else {
				}
			} else {
				String SAG = getStudentSAG(657, raf);
				if (SAG.length() > 0) {
					int indexSAG = Integer.parseInt(SAG);
					addNewStudentToTreeRecursive(indexSAG, newStudentName, raf, indexNewStudent);
				} else {
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

	public void addNewStudentToTreeRecursive(int index, String name, RandomAccessFile raf, int indexNewFile)
			throws IOException {
		String addName = getStudentString(index, raf);
		String SAD = getStudentSAD(index, raf);
		String SAG = getStudentSAG(index, raf);
		if (addName.compareTo(name) < 0) {
			if (SAD.length() < 1) {
				raf.seek((index + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH -1);
				raf.write(Integer.toString(indexNewFile).getBytes());
				return;
			} else {
				addNewStudentToTreeRecursive(Integer.parseInt(SAD), name, raf, indexNewFile);
			}
		} else {
			if (SAG.length() < 1) {
				raf.seek((index + 1) * SEQUENCE_LENGTH - (CHILDREN_MAX_LENGTH * 2)-1 );
				raf.write(Integer.toString(indexNewFile).getBytes());
				return;
			} else {
				addNewStudentToTreeRecursive(Integer.parseInt(SAG), name, raf, indexNewFile);
			}
		}
	}

	// ------------------ ECRITURE DES SAG ET SAD

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
		// if (fin - debut <= 2 ) {
		// // writeFeuille(median, raf);
		// return;
		// } else {
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
				raf.seek((parent + 1) * SEQUENCE_LENGTH - (CHILDREN_MAX_LENGTH * 2)- 1);
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
				raf.seek((parent + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH -1);
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

	public void sortTargetFile(ProgressBar progressBar) {
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
					float progress = (float)(entriesNumber-i)/entriesNumber;
	                progressBar.setProgress(.5+(progress*.25F));
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
		return getStudent(index, raf).substring(SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH-1, SEQUENCE_LENGTH-1).trim();
	}

	public String getStudentSAG(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf)
				.substring(SEQUENCE_LENGTH - (CHILDREN_MAX_LENGTH*2)-1, SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH-1).trim();
	}

	public boolean isVisible(int index,RandomAccessFile raf) {

		try {
			raf.seek((index+1) * SEQUENCE_LENGTH-1);
			byte[] b = new byte[1];
			raf.read(b);
			String studentVisible = new String(b);
			if(studentVisible.equals("1")) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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
				raf.write("1".getBytes());
			}
			for (int i = 0; i < maxLength.length; i++) {
				SEQUENCE_LENGTH += maxLength[i];
				if (i < 5) {
					writeSettings(i + 1, maxLength[i]);
				} 
			} SEQUENCE_LENGTH += 1 ;
			
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
			SEQUENCE_LENGTH = (int) Integer.parseInt(value.trim());
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

	public void simpleSearch(String term, ObservableList<Student> observableStudents) {
		RandomAccessFile raf = null;
		observableStudents.clear();
		try {
			raf = new RandomAccessFile(binFile, "r");
			if (!term.trim().equals("")) {
				simpleSearchRecursive(term.toUpperCase(), TREE_ROOT, raf, observableStudents);
			} else {
				System.out.println("test");
				loadStudentTree(observableStudents);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void simpleSearchRecursive(String term, int index, RandomAccessFile raf,ObservableList<Student> observableStudents) {
		try {
			raf.seek(SEQUENCE_LENGTH * index);
			byte[] a = new byte[maxLength[0]];
			raf.read(a);
			byte[] b = new byte[maxLength[1]];
			raf.read(b);
			String lastName = new String(a).trim();
			String firstName = new String(b).trim();
			String name = lastName + " " + firstName;
			if (name.toUpperCase().contains(term) && isVisible(index, raf)) {
				String[] studentInfo = new String[7];
				raf.seek(SEQUENCE_LENGTH*index + maxLength[0]+maxLength[1]);
				for (int i = 2; i < 5; i++) {
					byte[] c = new byte[maxLength[i]];
					raf.read(c);
					studentInfo[i] = new String(c).trim();
				}
				Student student = new Student(lastName, firstName, studentInfo[2], studentInfo[3], studentInfo[4], index);
				observableStudents.add(student);
			}

			for (int i = 2; i > 0; i--) {
				raf.seek(SEQUENCE_LENGTH * (index + 1) - (CHILDREN_MAX_LENGTH * i)-1);
				b = new byte[CHILDREN_MAX_LENGTH];
				raf.read(b);
				String childValue = new String(b).trim();
				if (childValue.length() > 0) {
					simpleSearchRecursive(term, Integer.valueOf(childValue), raf, observableStudents);
				} 
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void deleteStudent(int indexDeleteStudent) { 
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
			raf.seek(SEQUENCE_LENGTH * (indexDeleteStudent+1)-1);
			raf.write("0".getBytes());
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

	//---------------------------------------------------------------------------------------
	//	public void affichageAlphabetique(ObservableList<Student> observableStudents) {
	//        File dest = new File(destinationPath);
	//        RandomAccessFile raf = null;
	//        try {
	//            raf = new RandomAccessFile(dest, "rw");
	//            String medianSAD = getStudentSAD(TREE_ROOT,raf).trim();
	//            String medianSAG = getStudentSAG(TREE_ROOT,raf).trim();
	//            AffichageAlphabetiqueRecursive(Integer.parseInt(medianSAG), raf, observableStudents);
	//            // ajout du milieu dans observablesStudents
	//            AffichageAlphabetiqueRecursive(Integer.parseInt(medianSAD), raf, observableStudents);
	//        } catch (IOException e) {
	//            e.printStackTrace();
	//        } finally {
	//            try {
	//                if (raf != null) {
	//                    raf.close();
	//                }
	//            } catch (IOException e) {
	//                e.printStackTrace();
	//            }
	//        }
	//    }
	//	
	//	public void AffichageAlphabetiqueRecursive(int index, RandomAccessFile raf, ObservableList<Student> observableStudents) {
	//        try {
	//            String SAD = getStudentSAD(index, raf);
	//            String SAG = getStudentSAG(index, raf);
	//            if (SAG.length() >= 1) {
	//                AffichageAlphabetiqueRecursive(Integer.parseInt(SAG), raf, observableStudents);
	//                addInTableview(index, raf, observableStudents);
	//                if (SAD.length() >= 1) {
	//                    AffichageAlphabetiqueRecursive(Integer.parseInt(SAD), raf, observableStudents);
	//                }
	//            } else if (SAG.length() < 1 && SAD.length() < 1){
	//                addInTableview(index, raf, observableStudents);
	//            }else if (SAG.length() < 1 && SAD.length() >= 1){
	//                addInTableview(index, raf, observableStudents);
	//                AffichageAlphabetiqueRecursive(Integer.parseInt(SAD), raf, observableStudents);
	//            } else {
	//                System.out.println("dont work");;
	//            }
	//        } catch (IOException e) {
	//            e.printStackTrace();
	//        }
	//    }


	public void addInTableview(int index, RandomAccessFile raf, ObservableList<Student> observableStudents) {
		try {
			raf.seek(index * SEQUENCE_LENGTH - 1);
			String[] studentInfo = new String[7];
			for (int j = 0; j < 5; j++) {
				byte[] c = new byte[maxLength[j]];
				raf.read(c);
				studentInfo[j] = new String(c).trim();
			}
			Student student = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3],studentInfo[4],index);
			observableStudents.add(student);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//	List<Student> studentList2 = new ArrayList<>(); 
	//    StudentDao dao = new StudentDao();
	//   // studentList2 = FXCollections.observableArrayList();
	//    dao.affichageAlphabetique(studentList2);
	//    for (int i = 0; i < 50 ; i++) {
	//        System.out.println(studentList2.get(i).getFirstName() + "  " + studentList2.get(i).getLastName() );
	//    }









	public void loadStudentTree(ObservableList<Student> observableStudents) {
		RandomAccessFile raf = null;
		observableStudents.clear();
		try {
			raf = new RandomAccessFile(binFile, "r");
			loadStudentTreeRecursive(TREE_ROOT, raf, observableStudents);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void loadStudentTreeRecursive(int index, RandomAccessFile raf,	ObservableList<Student> observableStudents) {
		
		try {

			String SAD = getStudentSAD(index, raf);
			String SAG = getStudentSAG(index, raf);

			if (SAG.length()>0) {
				loadStudentTreeRecursive(Integer.parseInt(SAG),raf,observableStudents);
			}
			raf.seek(SEQUENCE_LENGTH * index);
			String[] studentInfo = new String[7];
			for (int j = 0; j < 5; j++) {
				byte[] c = new byte[maxLength[j]];
				raf.read(c);
				studentInfo[j] = new String(c).trim();
			}
			Student student = new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3],studentInfo[4],index);
			
			if (isVisible(index, raf)) {
				observableStudents.add(student);
				
				//visibleEntriesNumber++ ;
			}

			if (SAD.length()>0) {

				loadStudentTreeRecursive(Integer.parseInt(SAD),raf,observableStudents);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}






	public static String exportToPdf(ObservableList<Student> observableStudents) throws IOException {
		String title = "Annuaire EQL";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM:yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String[] today = String.valueOf(dtf.format(now)).split(" ");
		DateTimeFormatter dtfFile = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String todayFile = dtfFile.format(now);
        String filename = "C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\annuaireEQL_"+todayFile+".pdf";
		
		//On initialise un nouveau document dans PDFBox
		PDDocument annuairePdf = new PDDocument();

		//On définit les polices que l'on va utiliser dans le document, ainsi que leur taille
		PDFont titleFont = PDType1Font.HELVETICA_BOLD;
		PDFont subtitleFont = PDType1Font.HELVETICA;
		PDFont marginFont = PDType1Font.HELVETICA;
		PDFont h1Font = PDType1Font.HELVETICA_BOLD;
		PDFont bodyFont = PDType1Font.HELVETICA;

		int titleFontSize = 40;
		int subtitleFontSize = 12;
		int marginFontSize = 12;
		int h1FontSize = 18;
		int bodyFontSize = 14;

		try {

			//PREMIERE PAGE
			//La première page n'est pas dynamique, on y ajoute juste un titre,
			//le logo de l'école, la date et l'heure de l'export et peut-être
			//le nombre total d'entrées.

			//On crée une page
			PDPage firstPage = new PDPage();
			//On ajoute la page à notre doc
			annuairePdf.addPage(firstPage);

			//On ouvre un stream pour injecter les données dans la première page
			PDPageContentStream contents = new PDPageContentStream(annuairePdf, firstPage);

			//Même si le logo apparait en dessous du titre, on le dessine en premier
			//afin que le titre soit au premier plan (un peu comme les calques sur Photoshop)
			PDImageXObject schoolLogo = PDImageXObject.createFromFile(
					"C:\\Users\\formation\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\icons\\logoeql.png",
					annuairePdf);
			//			schoolLogo.set
			//On dessine l'iimage avec ses coordonnées puis ses dimensions
			contents.drawImage(schoolLogo, 180, 350, 250, 101);

			//On commence à écrire du texte sur la page
			contents.beginText();
			contents.setFont(titleFont, titleFontSize);
			contents.setLeading(14.5f);

			//newLineAtOffset permet de définir une nouvelle ligne en définissant
			//où sur la page on souhaite qu'elle commence (traite la page comme un
			//plan géométrique, donc les coordonnées (0,0) correspondent au coin
			//bas-gauche de la page.
			//Une fois que l'on a déclaré cette méthode, elle devient relative :
			//les positions suivantes visent des coordonnées par rapport au premier point.

			//Ici on vise un point à peu près au centre-haut de la page pour y placer le titre
			contents.newLineAtOffset(170, 470);
			//On y affiche le titre
			contents.showText(title);
			contents.setFont(subtitleFont, subtitleFontSize);

			//Vu que newLineAtOffset est relative, 40 déplace le curseur de 40 à droite
			//par rapport à la dernière fois où on l'a utilisée, et -170 descend dans la page.
			contents.newLineAtOffset(40, -145);
			contents.showText("Export� le " + today[0] + " � " + today[1]);
			contents.newLineAtOffset(55, -15);
			contents.showText(entriesNumber+" entr�es");
			contents.endText();
			contents.close();

			//On initialise le n° de la deuxième page, pour pouvoir aller écrire ce numéro
			//en bas de page

			int nbPages = 2;

			//Ici on crée une boucle qui va permettre d'écrire le nombre total de stagiaires
			// (j'ai mis ce nombre à 250 pour tester)
			// En fonction de la mise en page choisie, on peut ici afficher 7 stagiaires par page
			// (la deuxième boucle s'occupe d'écrire les étudiants sur la page, à chaque fois que
			//les 7 sont écrits ont refait un tour de la première boucle en créant une nouvelle page.

			for (int i = 0; i <  observableStudents.size(); i++) {
				PDPage contentPage = new PDPage();
				annuairePdf.addPage(contentPage);
				contents = new PDPageContentStream(annuairePdf, contentPage);

				contents.beginText();
				contents.setLeading(17f);
				contents.setFont(marginFont, marginFontSize);
				//On va écrire le header (le footer est après la boucle)
				contents.newLineAtOffset(50, 740);
				contents.showText("Annuaire EQL");
				contents.newLineAtOffset(400, 0);
				contents.showText(today[0] + " " + today[1]);
				contents.newLineAtOffset(-400, -50);

				//On écrit nos 7 stagiaires
				for (int j = 1; j < 8; j++) {
					if (i >= observableStudents.size())
						break;
					//h1 est le nom que j'ai choisi pour le style du nom/prénom
					contents.setFont(h1Font, h1FontSize);
					contents.showText(observableStudents.get(i).getLastName() + " " +  observableStudents.get(i).getFirstName());
					contents.newLine();
					//body est le nom pour le corps de texte sous le nom du stagiaire
					contents.setFont(bodyFont, bodyFontSize);
					contents.showText("D�partement : " + observableStudents.get(i).getZipCode());
					contents.newLine();
					contents.showText("Promotion : " + observableStudents.get(i).getPromo());
					contents.newLine();
					contents.showText("Ann�e : " + observableStudents.get(i).getYear());
					//On descend de 35 pour aller écrire le suivant
					contents.newLineAtOffset(0, -35);
					i++;
				}
				//On écrit ensuite le footer
				contents.newLineAtOffset(250, -40);
				contents.setFont(marginFont, marginFontSize);
				contents.showText(String.valueOf(nbPages));
				nbPages++;
				contents.endText();
				contents.close();
				annuairePdf.save(filename);
			}
		} finally {
			//On ferme le fichier pour le libérer de la mémoire
			annuairePdf.close();

			// Tout ça c'est le code de fenêtre Alert disant "l'annuaire
			// a correctement été exporté", avec 3 boutons d'options :
			// - Ouvrir le fichier
			// - Ouvrir le dossier
			// - Ok (rien faire du coup)
			// A voir où on le met au moment de l'intégration :)




		}
		return filename;
	}

}