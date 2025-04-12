package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services;


import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class PdfGeneratorServices {


    public ByteArrayOutputStream generateServiceReceiptPdf(String receiptDetails) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        String imagePath = "src/main/resources/static/img.png";  // Update path if needed
        File file = new File(imagePath);

        if (!file.exists()) {
            System.out.println("Logo file not found at: " + file.getAbsolutePath());
        }

        else {
            ImageData imageData = ImageDataFactory.create(file.getAbsolutePath());
            Image logo = new Image(imageData);
            logo.setWidth(135);
            logo.setHeight(100);
            document.add(logo);
        }


        document.add(new Paragraph("Service Receipt")
                .setBold()
                .setFontSize(20));


        document.add(new Paragraph(receiptDetails).setFontSize(12));

        document.close();

        return outputStream;
    }



}
