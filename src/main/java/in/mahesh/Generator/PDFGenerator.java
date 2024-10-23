package in.mahesh.Generator;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import in.mahesh.Model.InvoiceRequest;

@Service
public class PDFGenerator {

    @Autowired
    private TemplateEngine templateEngine;

    public byte[] createPdf(InvoiceRequest invoiceRequest) throws IOException {
        Context context = new Context();
        context.setVariable("invoice", invoiceRequest);

        String htmlContent = templateEngine.process("invoice-template", context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.createPDF(baos);
        baos.close();

        return baos.toByteArray();
    }
}

