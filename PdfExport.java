package fr.eql.ai109.projet1;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfExport {
	public static void main(String[] args) throws IOException {
		exportToPdf();

	}

	public static void exportToPdf() throws IOException {
		String filename = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\annuaireEQL.pdf";
		String title = "Annuaire EQL";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM:yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String[] today = String.valueOf(dtf.format(now)).split(" ");

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
					"C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\icons\\logoeql.png",
					annuairePdf);
			//On dessine l'iimage avec ses coordonnées puis ses dimensions
			contents.drawImage(schoolLogo, 200, 300, 200, 200);

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
			contents.newLineAtOffset(40, -170);
			contents.showText("Exporté le " + today[0] + " à " + today[1]);
			contents.newLineAtOffset(55, -15);
			contents.showText("1314 entrées");
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
			
			for (int i = 0; i < 250; i++) {
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
					if (i >= 250)
						break;
					//h1 est le nom que j'ai choisi pour le style du nom/prénom
					contents.setFont(h1Font, h1FontSize);
					contents.showText("NOM Prénom " + i);
					contents.newLine();
					//body est le nom pour le corps de texte sous le nom du stagiaire
					contents.setFont(bodyFont, bodyFontSize);
					contents.showText("Département");
					contents.newLine();
					contents.showText("Promotion");
					contents.newLine();
					contents.showText("Année");
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

			/*
			 * Alert pdfExportedAlert = new Alert(AlertType.CONFIRMATION);
			 * pdfExportedAlert.setTitle("Export PDF");
			 * pdfExportedAlert.setHeaderText("Export de l'annuaire en PDF");
			 * pdfExportedAlert.setContentText("L'annuaire a correctement été exporté.");
			 * 
			 * ButtonType exportPDFViewFile = new ButtonType("Voir le fichier"); ButtonType
			 * exportPDFViewDirectory = new ButtonType("Voir le dossier"); ButtonType
			 * exportPDFConfirm = new ButtonType("OK"); //ButtonType buttonTypeCancel = new
			 * ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			 * 
			 * pdfExportedAlert.getButtonTypes().setAll(exportPDFViewFile,
			 * exportPDFViewDirectory, exportPDFConfirm);
			 * 
			 * 
			 * Optional<ButtonType> result = pdfExportedAlert.showAndWait(); if
			 * (result.get() == exportPDFViewFile){ Desktop.getDesktop().open(new
			 * File(filename)); } else if (result.get() == exportPDFViewDirectory) { // ...
			 * user chose "Two" } pdfExportedAlert.showAndWait();
			 * System.out.println("Fichier exporté");
			 */
		}
	}
}