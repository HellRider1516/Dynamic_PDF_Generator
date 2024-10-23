package in.mahesh.RestController;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.mahesh.Model.InvoiceRequest;
import in.mahesh.Service.PDFService;

@RestController
@RequestMapping("/api/pdf")
public class PDFRestController {

    @Autowired
    private PDFService pdfService;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody InvoiceRequest invoiceRequest) throws IOException {
        byte[] pdfBytes = pdfService.generatePdf(invoiceRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
    }
}

