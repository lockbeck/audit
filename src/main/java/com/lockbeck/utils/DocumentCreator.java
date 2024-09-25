package com.lockbeck.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class DocumentCreator {
    public  XWPFDocument createDocument() {
        return new XWPFDocument();
    }

    public static void paragraph(XWPFDocument document, String text,int fontsize, boolean bold, int space,
                                 String color, boolean italic,ParagraphAlignment alignment,Boolean pageBreak) {

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(fontsize);
        run.setItalic(italic);
        run.setFontFamily("Times New Roman");
        run.setColor(color == null ? "000000" : color);
        if(pageBreak){
            run.addBreak(BreakType.PAGE);
        }
        paragraph.setSpacingAfter(space);
    }
    public static void addPageHeader(XWPFDocument document, String headerText) {
        // Create header/footer policy for the document
        XWPFHeaderFooterPolicy headerFooterPolicy = document.createHeaderFooterPolicy();

        // Create header for odd-numbered pages
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        // Create a paragraph in the header
        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER); // Align header in the center

        // Create a run to add text to the paragraph
        XWPFRun run = paragraph.createRun();
        run.setText(headerText); // Set header text
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("Times New Roman");
    }
    public static void picture(XWPFDocument document,Integer width, Integer height) throws InvalidFormatException, IOException {
        XWPFParagraph paragraphForPicture = document.createParagraph();
        paragraphForPicture.setAlignment(ParagraphAlignment.CENTER);
        paragraphForPicture.setSpacingAfter(1600);
        XWPFRun runForPicture = paragraphForPicture.createRun();
        String imgFile = "pictures/img.png"; // Replace with your image path

        // Open the image file input stream
        FileInputStream fis = new FileInputStream(imgFile);

        // Add the image to the document
        runForPicture.addPicture(fis,
                XWPFDocument.PICTURE_TYPE_PNG,  // Image type
                imgFile,                        // Image file name
                Units.toEMU(width),               // Image width in EMUs (optional, adjust as needed)
                Units.toEMU(height));              // Image height in EMUs (optional, adjust as needed)
    }

    public static void styleHeaderRow(XWPFTable table) {
        XWPFTableRow row = table.getRow(0);
        setCellText(row.getCell(0), "№", "FFFFFF", true);
        setCellText(row.getCell(1), "Hostning tarmoqdagi nomi", "FFFFFF", true);
        setCellText(row.getCell(2), "Hostning MAC manzili", "FFFFFF", true);
    }

    public static void styleDataRow(XWPFTableRow row, String cell1Text, String cell2Text, String cell3Text, boolean isBold) {
        String bgColor = isBold ? "DCE6F1" : "FFFFFF";  // Alternating colors
        setCellText(row.getCell(0), cell1Text, bgColor, false);
        setCellText(row.getCell(1), cell2Text, bgColor, false);
        setCellText(row.getCell(2), cell3Text, bgColor, false);
    }

    public static void setCellText(XWPFTableCell cell, String text, String bgColor, boolean bold) {
        cell.setColor(bgColor);

        // Clear any existing paragraphs and create a new one
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();

        // Set paragraph alignment (optional, but can be important for table layout)
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        // Create a run to hold the text and apply formatting
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);  // Set font size to 14
        run.setBold(bold);    // Set bold if necessary
        run.setText(text);    // Set the text in the run
    }
}
