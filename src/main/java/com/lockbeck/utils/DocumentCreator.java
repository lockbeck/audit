package com.lockbeck.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class DocumentCreator {
    public  XWPFDocument createDocument() {
        return new XWPFDocument();
    }

    public static void paragraph(XWPFDocument document, String text,int fontsize, boolean bold, int space,
                                 String color, boolean italic,ParagraphAlignment alignment) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(bold);
        run.setFontSize(fontsize);
        run.setItalic(italic);
        run.setFontFamily("Times New Roman");
        run.setColor(color == null ? "000000" : color);
        paragraph.setSpacingAfter(space);
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
