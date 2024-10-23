package in.mahesh.Service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import in.mahesh.Generator.PDFGenerator;
import in.mahesh.Model.InvoiceRequest;

@Service
public class PDFService {

    private static final String PDF_STORAGE_PATH = "pdf-storage/";

    @Autowired
    private PDFGenerator pdfGenerator;

    public byte[] generatePdf(InvoiceRequest invoiceRequest) throws IOException {
        String hash = hashInvoiceRequest(invoiceRequest);
        String pdfFilePath = PDF_STORAGE_PATH + hash + ".pdf";

        File pdfFile = new File(pdfFilePath);
        if (pdfFile.exists()) {
            return Files.readAllBytes(pdfFile.toPath());
        }

        byte[] pdfBytes = pdfGenerator.createPdf(invoiceRequest);

        Files.write(Paths.get(pdfFilePath), pdfBytes);

        return pdfBytes;
    }

    private String hashInvoiceRequest(InvoiceRequest invoiceRequest) {
        return DigestUtils.md5DigestAsHex(invoiceRequest.toString().getBytes());
    }
}

