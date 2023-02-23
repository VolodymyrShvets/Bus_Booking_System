package com.booking.system.utility;

import com.booking.system.model.TicketStatus;
import com.booking.system.model.dto.TicketDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfUtil {
    private static String filePath = "src\\main\\resources\\pdf\\";
    private static final String extension = ".pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        TicketDTO ticket = new TicketDTO();

        ticket.setBusName("355H");
        ticket.setBusDepartureCity("Kyiv");
        ticket.setBusArrivalCity("Lviv");
        ticket.setBusDepartureTime(LocalDateTime.now());
        ticket.setBusArrivalTime(LocalDateTime.now().plusHours(5).plusMinutes(15));
        ticket.setPrice(355L);
        ticket.setSeat(6);
        ticket.setStatus(TicketStatus.ACTIVE);
        ticket.setUserFirstName("Volodymyr");
        ticket.setUserLastName("Shvets");
        ticket.setUserEmail("vshvets295@gmail.com");

        createPdf(ticket);
    }

    private static String generateTicketName(String firstName, String lastName) {
        return filePath + firstName + "_" + lastName + extension;
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

    public static void createPdf(TicketDTO ticket) throws IOException, DocumentException {
        Document document = new Document();
        File file = new File(
                generateTicketName(
                        ticket.getUserFirstName(),
                        ticket.getUserLastName()));

        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.setPageSize(PageSize.A5);

        createHeader(document);

        createTable(document, ticket);

        document.close();
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
        PdfPCell pdfCell7 = new PdfPCell(new Phrase(ticket.getUserFirstName() + " " + ticket.getUserLastName())); // TODO set alignment to left
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
