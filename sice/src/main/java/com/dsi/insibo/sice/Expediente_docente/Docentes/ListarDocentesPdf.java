package com.dsi.insibo.sice.Expediente_docente.Docentes;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Expediente_docente/Docentes/listarDocentes")
public class ListarDocentesPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub

        List<DocenteDTO> listadoDocentes = (List<DocenteDTO>) model.get("Docentes");

        document.setPageSize(PageSize.LETTER.rotate());
        document.setMargins(-20, -20, 20, 20);
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);

        celda = new PdfPCell(new Phrase("Planta docente", fuenteTitulo));
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);
        celda.setBorder(0);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);


        PdfPTable tablaDocentes = new PdfPTable(6);



        listadoDocentes.forEach(docente -> {

            tablaDocentes.addCell(docente.getDocente().getNombreDocente());
            tablaDocentes.addCell(docente.getDocente().getApellidoDocente());
            tablaDocentes.addCell(docente.getDocente().getProfesionDocente());
            tablaDocentes.addCell(docente.getDocente().getTelefonoDocente());
            tablaDocentes.addCell(String.valueOf(docente.getEdad()));
            tablaDocentes.addCell(docente.getDocente().getDuiDocente());
        });

        document.add(tablaTitulo);
        document.add(tablaDocentes);

        //throw new UnsupportedOperationException("Unimplemented method 'buildPdfDocument'");
    }

} 
 


