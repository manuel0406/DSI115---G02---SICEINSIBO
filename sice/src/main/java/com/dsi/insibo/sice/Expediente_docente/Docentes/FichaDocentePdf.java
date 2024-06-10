package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.awt.Color;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.dsi.insibo.sice.entity.Docente;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Expediente_docente/Docentes/fichaDocente")
public class FichaDocentePdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Docente docente = (Docente) model.get("profesor");
        
        document.setPageSize(PageSize.A4);
        document.open();
        
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        
        // Título del documento
        Phrase title = new Phrase("Expediente Docente", titleFont);
        document.add(title);
        
        // Espacio en blanco
        document.add(new Phrase("\n\n"));
        
        // Crear tabla para los datos del docente
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        
        addTableHeader(table, headerFont);
        addRows(table, docente, bodyFont);
        
        document.add(table);
    }
    
    private void addTableHeader(PdfPTable table, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);
        
        cell.setPhrase(new Phrase("Campo", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Valor", font));
        table.addCell(cell);
    }
    
    private void addRows(PdfPTable table, Docente docente, Font font) {
        table.addCell(new PdfPCell(new Phrase("DUI", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getDuiDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("NIT", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getNit(), font)));
        
        table.addCell(new PdfPCell(new Phrase("NUP", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getNup(), font)));
        
        table.addCell(new PdfPCell(new Phrase("NIP", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getNip(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Teléfono Fijo", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getTelefonoFijoDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Nombre", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getNombreDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Apellido", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getApellidoDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Fecha de Nacimiento", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getFechaNacimientoD().toString(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Dirección", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getDireccionDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Municipio", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getMunicipioD(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Departamento", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getDepartamentoD(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Distrito", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getDistritoDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Correo Electrónico", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getCorreoDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Teléfono", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getTelefonoDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Profesión", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getProfesionDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Fecha MINEDUCYT", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getFechaMineducyt().toString(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Zona", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getZonaDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Título", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getTituloDocente(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Especialidad en Estudio", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getEspecialidadEnEstudio(), font)));
        
        table.addCell(new PdfPCell(new Phrase("Curriculum", font)));
        table.addCell(new PdfPCell(new Phrase(docente.isCurriculumDocente() ? "Sí" : "No", font)));
        
        table.addCell(new PdfPCell(new Phrase("Atestados", font)));
        table.addCell(new PdfPCell(new Phrase(docente.isAtestadosDocente() ? "Sí" : "No", font)));
        
        table.addCell(new PdfPCell(new Phrase("Fecha de Entrega", font)));
        table.addCell(new PdfPCell(new Phrase(docente.getFechaEntrega().toString(), font)));
    }
}
