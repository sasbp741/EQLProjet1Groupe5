//package fr.eql.ai109.projet1;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDFont;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//
//public class PdfExport {
//	public static void main(String[] args) throws IOException {
//		exportToPdf();
//	}
//
//	public static void exportToPdf() throws IOException {
//		String filename = "C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\sample.pdf";
//		String title = "Annuaire EQL";
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM:yyyy HH:mm:ss");  
//		   LocalDateTime now = LocalDateTime.now();
//		   String[] today = String.valueOf(dtf.format(now)).split(" ");
//
//		PDDocument annuairePdf = new PDDocument();
//		try {
//			PDPage firstPage = new PDPage();
//			annuairePdf.addPage(firstPage);
//
//			PDPageContentStream contents = new PDPageContentStream(annuairePdf, firstPage);
//			PDImageXObject schoolLogo = PDImageXObject.createFromFile("C:\\Users\\Val\\eclipse-workspace\\AnnuaireEQL\\src\\fr\\eql\\ai109\\projet1\\icons\\logoeql.png",annuairePdf);
//			contents.drawImage(schoolLogo,200,300,200,200);
//			
//			PDFont titleFont = PDType1Font.HELVETICA_BOLD;
//			int titleFontSize = 40;
//			PDFont subtitleFont = PDType1Font.HELVETICA;
//			int subtitleFontSize = 12;
//
//			contents.beginText();
//			contents.setFont(titleFont, titleFontSize);
//			contents.setLeading(14.5f);
//			contents.newLineAtOffset(170,470);
//			contents.showText(title);
//			contents.setFont(subtitleFont, subtitleFontSize);
//			contents.newLineAtOffset(40,-170);
//			contents.showText("Exporté le "+today[0]+ " à " + today[1]);
//			contents.newLineAtOffset(55,-15);
//			contents.showText("1314 entrées");
//			contents.endText();
//			contents.close();
//
//			PDPage page0 = new PDPage();
//			annuairePdf.addPage(page0);
//			contents = new PDPageContentStream(annuairePdf, page0);
//			annuairePdf.save(filename);
//		} finally {
//			annuairePdf.close();
//		}
//	}
//}