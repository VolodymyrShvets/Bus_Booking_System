package com.booking.system.utility;

import com.booking.system.model.dto.TicketDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class PdfUtil {
    private static String filePath = "src\\main\\resources\\pdf\\";
    public static final String extension = ".pdf";

    public static String getFileSimpleName(String firstName, String lastName) {
        return firstName + "_" + lastName;
    }

    public static String getTicketName(String firstName, String lastName) {
        return filePath + getFileSimpleName(firstName, lastName) + extension;
    }

    private static String getDateFormatted(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return time.format(formatter);
    }

    private static Font getFont(int style, int size) {
        Font f = new Font();
        f.setStyle(style);
        f.setSize(size);
        return f;
    }

    public static void deleteFile(String firstName, String lastName) {
        try {
            String fileName = filePath + getFileSimpleName(firstName, lastName);
            Files.deleteIfExists(Path.of(fileName));
        } catch (IOException ex) {
            log.error(ex.toString());
        }
    }

    public static void createPdf(TicketDTO ticket) {
        Document document = new Document();

        try {
            File file = new File(
                    getTicketName(
                            ticket.getUserFirstName(),
                            ticket.getUserLastName()));

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.setPageSize(PageSize.A5);

            createHeader(document);

            createTable(document, ticket);

            document.close();
        } catch (DocumentException | IOException ex) {
            log.error(ex.toString());
        }
    }

    private static void createHeader(Document document) throws DocumentException {
        Paragraph p = new Paragraph("Bus Booking System", getFont(Font.BOLD, 15));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        p = new Paragraph("Ticket", getFont(Font.BOLD, 18));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
    }

    private static void createTable(Document document, TicketDTO ticket) throws DocumentException {
        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.setSpacingBefore(40);

        // First Row - Bus Name
        int TABLE_FONT_SIZE = 11;
        PdfPCell pdfCell1 = new PdfPCell(new Phrase("BUS: " + ticket.getBusName(), getFont(Font.BOLD, TABLE_FONT_SIZE)));
        pdfCell1.setColspan(3);
        int PADDING = 5;
        pdfCell1.setPadding(PADDING);

        pdfPTable.addCell(pdfCell1);

        // Second Row: Departure -> Departure Date/Time -> Passenger
        PdfPCell pdfCell2 = new PdfPCell(new Phrase("DEPARTURE DATE/TIME:", getFont(Font.BOLD, TABLE_FONT_SIZE)));
        PdfPCell pdfCell3 = new PdfPCell(new Phrase(getDateFormatted(ticket.getBusDepartureTime())));
        PdfPCell pdfCell4 = new PdfPCell(new Phrase("NAME/SURNAME", getFont(Font.BOLD, TABLE_FONT_SIZE)));

        pdfCell2.setPadding(PADDING);
        pdfCell3.setPadding(PADDING);
        pdfCell4.setPadding(PADDING);

        pdfPTable.addCell(pdfCell2);
        pdfPTable.addCell(pdfCell3);
        pdfPTable.addCell(pdfCell4);

        // Third Row: From -> City -> Passenger's First/Last Names
        PdfPCell pdfCell5 = new PdfPCell(new Phrase("FROM:", getFont(Font.BOLD, TABLE_FONT_SIZE)));
        PdfPCell pdfCell6 = new PdfPCell(new Phrase(ticket.getBusDepartureCity()));
        PdfPCell pdfCell7 = new PdfPCell(new Phrase(ticket.getUserFirstName() + " " + ticket.getUserLastName()));
        pdfCell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

        pdfCell5.setPadding(PADDING);
        pdfCell6.setPadding(PADDING);
        pdfCell7.setPadding(PADDING);

        pdfPTable.addCell(pdfCell5);
        pdfPTable.addCell(pdfCell6);
        pdfPTable.addCell(pdfCell7);

        // Fourth Row: Arrival -> Arrival Date/Time -> Seat
        PdfPCell pdfCell8 = new PdfPCell(new Phrase("ARRIVAL DATE/TIME:", getFont(Font.BOLD, TABLE_FONT_SIZE)));
        PdfPCell pdfCell9 = new PdfPCell(new Phrase(getDateFormatted(ticket.getBusArrivalTime())));
        PdfPCell pdfCell10 = new PdfPCell(new Phrase("SEAT: " + ticket.getSeat(), getFont(Font.BOLD, TABLE_FONT_SIZE)));

        pdfCell8.setPadding(PADDING);
        pdfCell9.setPadding(PADDING);
        pdfCell10.setPadding(PADDING);

        pdfPTable.addCell(pdfCell8);
        pdfPTable.addCell(pdfCell9);
        pdfPTable.addCell(pdfCell10);

        // Fifth Row: To -> City -> Price
        PdfPCell pdfCell11 = new PdfPCell(new Phrase("TO:", getFont(Font.BOLD, TABLE_FONT_SIZE)));
        PdfPCell pdfCell12 = new PdfPCell(new Phrase(ticket.getBusArrivalCity()));
        PdfPCell pdfCell13 = new PdfPCell(new Phrase("PRICE: " + ticket.getPrice() + " UAN", getFont(Font.BOLD, TABLE_FONT_SIZE)));

        pdfCell11.setPadding(PADDING);
        pdfCell12.setPadding(PADDING);
        pdfCell13.setPadding(PADDING);

        pdfPTable.addCell(pdfCell11);
        pdfPTable.addCell(pdfCell12);
        pdfPTable.addCell(pdfCell13);

        pdfPTable.setWidthPercentage(90);

        document.add(pdfPTable);
    }
}
