package fr.eql.ai109.projet1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

// ------------ AJOUT
import java.util.LinkedList;
import java.util.Queue;

public class MainApp extends Application {

	private static String originalPath = "C:\\Users\\formation\\Documents\\COURS_EQL\\Projet1\\stagiaires.txt";
	private static String destinationPath = "C:\\Users\\formation\\Documents\\COURS_EQL\\Projet1\\stagiairesRaf.bin";
	public static int[] maxLength = new int[7];
	private static String spaceChar = " ";
	private static int CHILDREN_MAX_LENGTH = 12;
	static File binFile = new File(destinationPath);
	private static int SEQUENCE_LENGTH = 0;
	static int entriesNumber = 0;
	private static boolean[][] isWritten = new boolean[1314][3];

		@Override
		public void start(Stage primaryStage) throws Exception {
	
			MainPanel root = new MainPanel();
			Scene scene = new Scene(root);
	
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setTitle("Annuaire EQL");
			primaryStage.show();
		}

	public static void main(String[] args) {

		//definemaxLength(maxLength);
		//showmaxLength(maxLength);

		//		if (!binFile.exists()) {
		//writeDestinationFile();
		//		}
		//System.out.println(SEQUENCE_LENGTH);
		//System.out.println(entriesNumber);

		//sortTargetFile();
		//writeChildren();
		//		System.out.println("valeur du tableau " + isWritten[0][0]);
		//
		//		for (int i = 0; i < isWritten.length; i++) {
		//			System.out.println("valeur " + i + " " + isWritten[i][2]);
		//		}

		launch(args);
		//addNewStudentToTree();
		//displayNames();


	}

	//----------------- AJOUT
	
	public static void addNewStudentToTree() {
		File dest = new File(destinationPath);
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(dest, "rw");
//			raf.seek(SEQUENCE_LENGTH * entriesNumber);
//			raf.write("PHOMMA               Yasamine            92AI 94     2015                        ".getBytes());
//			entriesNumber++;
			int indexNewStudent = entriesNumber - 1;
			String newStudentName = getStudentString(indexNewStudent, raf).trim();
			String medianName = getStudentString(657,raf).trim();
			if (newStudentName.compareTo(medianName) >= 0) {
				String SAD = getStudentSAD(657, raf);
				if (SAD.length() > 0) {
					int indexSAD = Integer.parseInt(SAD);
					addNewStudentToTreeRecursive(indexSAD, newStudentName, raf, indexNewStudent);
				} else {
					System.out.println("pas de lancement");
				}
			} else {
				String SAG = getStudentSAG(657, raf);
				if (SAG.length() > 0) {
					int indexSAG = Integer.parseInt(SAG);
					addNewStudentToTreeRecursive(indexSAG, newStudentName, raf, indexNewStudent);
				} else {
					System.out.println("pas de lancement");
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

	static void addNewStudentToTreeRecursive(int index, String name, RandomAccessFile raf, int indexNewFile) throws IOException {
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

	private static void writeChildren() {
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

	static void dichotomousWriteChildren(int debut, int fin, RandomAccessFile raf) throws IOException {
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

	static boolean writeSAG(int parent, RandomAccessFile raf, int SAG) {
		if (isWritten[parent][0] == false && isWritten[SAG][2] == false) {
			try {
				raf.seek((parent + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH * 2);
				raf.write(Integer.toString(SAG).getBytes());
				isWritten[parent][0] = true;
				isWritten[SAG][2] = true;
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	static boolean writeSAD(int parent, RandomAccessFile raf, int SAD) {
		if (isWritten[parent][1] == false && isWritten[SAD][2] == false) {
			try {
				raf.seek((parent + 1) * SEQUENCE_LENGTH - CHILDREN_MAX_LENGTH);
				raf.write(Integer.toString(SAD).getBytes());
				isWritten[parent][1] = true;
				isWritten[SAD][2] = true;
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	// ----------------------------------------------------------- TRIER LE TABLEAU

	private static void sortTargetFile() {
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

	private static void swapLines(int i, int j, RandomAccessFile raf) throws IOException {
		String buffer = getStudent(i, raf);
		writeStudent(getStudent(j, raf), i, raf);
		writeStudent(buffer, j, raf);
	}

	private static void writeStudent(String student, int index, RandomAccessFile raf) throws IOException {
		raf.seek(index * SEQUENCE_LENGTH);
		raf.write(student.getBytes());
	}

	// ----------------------------------------------------------------- AFFICHER LE
	// TABLEAU

	private static void displayNames() {

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

	private static String getStudentString(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(0, maxLength[0] + maxLength[1]);
	}

	private static String getStudent(int index, RandomAccessFile raf) throws IOException {
		raf.seek(index * SEQUENCE_LENGTH);
		byte[] b = new byte[SEQUENCE_LENGTH];
		raf.read(b);
		String student = new String(b);
		return student;
	}

	private static String getStudentSAD(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(SEQUENCE_LENGTH - maxLength[5], SEQUENCE_LENGTH).trim();
	}
	private static String getStudentSAG(int index, RandomAccessFile raf) throws IOException {
		return getStudent(index, raf).substring(SEQUENCE_LENGTH - maxLength[5] - maxLength[6], SEQUENCE_LENGTH - maxLength[5]).trim();
	}

	// ------------------------------------------------------ ECRITURE DU RAF
	// (fichier de destination)

	private static void writeDestinationFile() {
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
			}
			System.out.println("Fichier correctement écrit");
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

	public static void showmaxLength(final int[] maxLength) {
		for (int g = 0; g < maxLength.length; g++) {
			System.out.println(maxLength[g]);
		}
	}

	private static void definemaxLength(final int[] maxLength) {
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
}
