package com.techverse.service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.properties.TextAlignment;
import com.techverse.model.Order;
import com.techverse.model.OrderItem;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

	
	
	public static String saveInvoice(Order order) {
	    byte[] pdfBytes = generateInvoice(order);
	    String filePath = "G:/" + order.getOrderId() + ".pdf"; // Specify your desired path here

	    try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
	        fos.write(pdfBytes);
	        fos.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error saving file: " + e.getMessage();
	    }
	    
	    return "Invoice saved successfully at " + filePath;
	}

	private static byte[] generateInvoice(Order order) {
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    
	    try {
	        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
	        PdfDocument pdfDocument = new PdfDocument(writer);
	        Document document = new Document(pdfDocument);

	        // Invoice Header
	        document.add(new Paragraph("Invoice").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(24));
	        document.add(new Paragraph("Invoice Id: " + order.getOrderId()));
	        document.add(new Paragraph("Invoice Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))));
	        document.add(new Paragraph("\nCustomer Details:"));
	        document.add(new Paragraph(order.getUser().getName())); // Assuming User has a getName() method
	    document.add(new Paragraph(order.getShippingAddress().getFullAddress())); // Assuming ShippingAddress has a method for full address

	        document.add(new Paragraph("\nProducts:"));
	        
	        // Product Table
	        Table table = new Table(new float[]{1, 1, 1});
	        table.addHeaderCell(new Cell().add(new Paragraph("Product Name")));
	        table.addHeaderCell(new Cell().add(new Paragraph("Quantity")));
	        table.addHeaderCell(new Cell().add(new Paragraph("Price")));

	        for (OrderItem item : order.getOrderItems()) {
	            table.addCell(new Cell().add(new Paragraph(item.getProduct().getTitle()))); // Adjust based on your OrderItem
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
	            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getPrice())))); // Assuming OrderItem has getPrice()
	        }
	        document.add(table);

	        // Total Price
	        document.add(new Paragraph("\nTotal Price: $" + order.getToatalPrice()));
	        document.add(new Paragraph("(Including Tax* and Shipping Tax*)"));

	        
	        
	        // Terms and Conditions
            document.add(new Paragraph("\nTERMS AND CONDITIONS:"));
            List list = new List();
            list.add(new ListItem("The seller shall not be liable to the buyer, directly or indirectly, for any loss or damage suffered by the buyer."));
            list.add(new ListItem("The seller warrants the product as per the details on the product."));
            list.add(new ListItem("Any purchase order received by the seller will be interpreted as accepting this offer and the sale offer in writing."));
            list.add(new ListItem("The buyer may purchase the product in this offer only under the terms and conditions of the seller included in this offer."));
            document.add(list);

	        // Additional details...
	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return byteArrayOutputStream.toByteArray();
	}

}
