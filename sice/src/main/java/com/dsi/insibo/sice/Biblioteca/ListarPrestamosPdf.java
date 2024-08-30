package com.dsi.insibo.sice.Biblioteca;

import java.util.Map;
import java.awt.Color;
import java.net.URL;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.dsi.insibo.sice.entity.PrestamoLibro;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Biblioteca/verPrestamosPdf")
public class ListarPrestamosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(@NonNull Map<String, Object> model,@NonNull Document document,@NonNull PdfWriter writer,
    @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<PrestamoLibro> listarPrestamos = (List<PrestamoLibro>) model.get("prestamos");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(40, 40, 36, 72); // Margen de 3 cm a los lados y 2.5 cm abajo
        document.open();

        // Fuentes
        Font titleFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
        Font titleFont3 = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.UNDERLINE,Color.BLACK);
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);

        // Crear una tabla para alinear la imagen y los títulos
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(50);
        headerTable.setWidths(new float[] { 0.55f, 1.75f }); // Anchos relativos de las columnas (1: imagen, 3: títulos)

        // Añadir logo
        URL logoUrl = getClass().getResource("/static/Imagenes/LogoINSIBO.jpg");
        if (logoUrl != null) {
            try {
                Image img = Image.getInstance(logoUrl);
                float aspectRatio = img.getWidth() / (float) img.getHeight();
                float newWidth = 60;
                float newHeight = newWidth / aspectRatio;
                img.scaleToFit(newWidth, newHeight);
                img.setAlignment(Image.ALIGN_CENTER);

                PdfPCell logoCell = new PdfPCell(img);
                logoCell.setBorder(PdfPCell.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Ajustar la posición de la imagen para moverla hacia abajo
                logoCell.setPaddingTop(5); // Mover la imagen 10 puntos hacia abajo

                headerTable.addCell(logoCell);
            } catch (Exception e) {
                System.err.println("Error loading or resizing image: " + e.getMessage());
                PdfPCell emptyCell = new PdfPCell();
                emptyCell.setBorder(PdfPCell.NO_BORDER);
                headerTable.addCell(emptyCell);
            }
        } else {
            System.err.println("Logo image not found at path: /static/Imagenes/LogoINSIBO.jpg");
            PdfPCell emptyCell = new PdfPCell();
            emptyCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(emptyCell);
        }

        // Añadir títulos
        Paragraph title = new Paragraph("INSTITUTO NACIONAL SIMON BOLIVAR\nCODIGO 11694 SANTO TOMAS", titleFont);
        Paragraph title2 = new Paragraph("LISTADO DE PRESTAMOS REALIZADOS\n\n", titleFont2);
        title.setAlignment(Element.ALIGN_LEFT);
        title2.setAlignment(Element.ALIGN_LEFT);

        PdfPCell titleCell = new PdfPCell();
        titleCell.setBorder(PdfPCell.NO_BORDER);
        titleCell.addElement(title);
        titleCell.addElement(title2);
        // titleCell.addElement(title3);
        headerTable.addCell(titleCell);

        document.add(headerTable);

        PdfPTable tablaPrestamos = new PdfPTable(6); // 6 columnas para los datos del préstamo
        tablaPrestamos.setWidthPercentage(100);
        tablaPrestamos.setWidths(new float[] { 0.5f, 2f, 2f, 2f, 1.5f, 1.5f }); 

        // Encabezados de la tabla
        addTableHeader(tablaPrestamos, "ID", headerFont);
        addTableHeader(tablaPrestamos, "Alumno", headerFont);
        addTableHeader(tablaPrestamos, "Libro", headerFont);
        addTableHeader(tablaPrestamos, "Fecha Préstamo", headerFont);
        addTableHeader(tablaPrestamos, "Fecha Devolución", headerFont);
        addTableHeader(tablaPrestamos, "Estado", headerFont);

        // Datos de los préstamos
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (PrestamoLibro prestamo : listarPrestamos) {
            addTableCell(tablaPrestamos, String.valueOf(prestamo.getIdPrestamoLibro()), bodyFont);
            addTableCell(tablaPrestamos, prestamo.getAlumno().getNombreAlumno(), bodyFont);
            addTableCell(tablaPrestamos, prestamo.getInventarioLibro().getTituloLibro(), bodyFont);
            addTableCell(tablaPrestamos, String.valueOf(prestamo.getFechaPrestamo()), bodyFont);
        //    addTableCell(tablaPrestamos, dateFormat.format(prestamo.getFechaPrestamo()), bodyFont);
            addTableCell(tablaPrestamos, prestamo.getFechaDevolucion() != null ? dateFormat.format(prestamo.getFechaDevolucion()) : "No devuelto", bodyFont);
            addTableCell(tablaPrestamos, prestamo.getEstadoPrestamo(), bodyFont);
        }

        document.add(tablaPrestamos);

        String fechaImpresion = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Paragraph footer = new Paragraph("\nINSIBO - " + fechaImpresion, footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }

    private void addTableHeader(PdfPTable table, String header, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setBackgroundColor(new Color(0, 51, 102));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }
}