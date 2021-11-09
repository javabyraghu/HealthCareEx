package in.nareshit.raghu.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nareshit.raghu.entity.SlotRequest;

public class InvoiceSlipPdfView extends AbstractPdfView {
	@Override
	protected void buildPdfMetadata(
			Map<String, Object> model, 
			Document document, HttpServletRequest request)
	{
		HeaderFooter header = new HeaderFooter(new Phrase("INVOICE PDF VIEW"), false);
		header.setAlignment(Element.ALIGN_CENTER);
		//header.setBorder(HeaderFooter.UNDEFINED);
		document.setHeader(header);

		HeaderFooter footer = new HeaderFooter(new Phrase(new Date()+" (C) Nareshit, Page # "), true);
		footer.setAlignment(Element.ALIGN_RIGHT);
		//footer.setBorder(HeaderFooter.UNDEFINED);
		document.setFooter(footer);
	}

	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {
		//Download PDF
		//download PDF with a given filename
		response.addHeader("Content-Disposition", "attachment;filename=INVOICE.pdf");

		//Get Data from Controller
		SlotRequest sr = (SlotRequest) model.get("slotRequest");

		
		Font invoiceFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD, Color.BLACK);
		Paragraph invoiceId = new Paragraph(sr.getPatient().getFirstName()+" "+sr.getPatient().getLastName()+"-"+System.currentTimeMillis(),invoiceFont);
		invoiceId.setAlignment(Element.ALIGN_CENTER);
		invoiceId.setSpacingBefore(10.0f);
		invoiceId.setSpacingAfter(10.0f);
		//add to document
		document.add(invoiceId);

		//create element
		//Font (Family, Size, Style, Color)
		Font titleFont = new Font(Font.TIMES_ROMAN, 40, Font.BOLD, Color.RED);
		Paragraph title = new Paragraph("SAMPLE NIT HOSPITAL",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		title.setSpacingBefore(20.0f);
		title.setSpacingAfter(10.0f);
		//add to document
		document.add(title);

		Font addrFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD, Color.BLACK);
		Paragraph address = new Paragraph("Ameerpt, HYD, 500032",addrFont);
		address.setAlignment(Element.ALIGN_CENTER);
		address.setSpacingAfter(25.0f);
		//add to document
		document.add(address);


		//adding Image
		Image img = Image.getInstance("https://www.nareshit.com/wp-content/uploads/2019/06/Nareshit-Logo-Png.png");
		//set width and height
		//img.scaleAbsolute(width, height);
		//set alignment
		img.setAlignment(Element.ALIGN_CENTER);
		document.add(img);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		String date = sdf.format(sr.getAppointment().getDate());

		double fee = sr.getAppointment().getFee();
		double gst = fee * 6/100.0;
		double finalAmt = fee + (2*gst);

		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(35.0f);
		table.setSpacingAfter(15.0f);
		table.addCell(getDesignCell("Appointment Date"));
		table.addCell(getTextCell(date));
		table.addCell(getDesignCell("Patient Name"));
		table.addCell(getTextCell(
				sr.getPatient().getFirstName()+" "+sr.getPatient().getLastName())
				);

		table.addCell(getDesignCell("Doctor Name"));
		table.addCell(getTextCell(
				sr.getAppointment().getDoctor().getFirstName()+" "+sr.getAppointment().getDoctor().getLastName()
				));

		table.addCell(getDesignCell("Final Amount"));
		table.addCell(getTextCell(String.valueOf(finalAmt)));

		document.add(table);

		PdfPTable billdata = new PdfPTable(2);
		billdata.getDefaultCell().setBorderWidth(0f);
		billdata.setSpacingBefore(35.0f);
		billdata.setHorizontalAlignment(Element.ALIGN_RIGHT);
		billdata.addCell(getTextCell("Booking Amount "));
		billdata.addCell(sr.getAppointment().getFee().toString());
		billdata.addCell(getTextCell("CGST "));
		billdata.addCell(String.valueOf(gst));
		billdata.addCell(getTextCell("SGST "));
		billdata.addCell(String.valueOf(gst));
		billdata.addCell(getTextCell("Total Amount "));
		billdata.addCell(getTextCell(String.valueOf(finalAmt)));

		document.add(billdata);

		Font noteFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.RED);
		Paragraph note = new Paragraph("NOTE: THIS IS AUTO-GENERATED PAYMENT SLIP, FOR MORE DETALS CONTACT FRONTDESK @ 1234567890",noteFont);
		note.setAlignment(Element.ALIGN_CENTER);
		note.setSpacingBefore(15.0f);
		document.add(note);

		Font signFont = new Font(Font.TIMES_ROMAN, 20, Font.BOLD, Color.BLACK);
		Paragraph sign = new Paragraph("SIGNATURE",signFont);
		sign.setAlignment(Element.ALIGN_RIGHT);
		sign.setSpacingBefore(35.0f);
		sign.setSpacingAfter(10.0f);
		//add to document
		document.add(sign);
		Paragraph datePar = new Paragraph("Date :" + new Date());
		datePar.setAlignment(Element.ALIGN_RIGHT);
		document.add(datePar);
		

	}

	private Phrase getDesignCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLUE);
		return new Phrase(data, font);
	}
	private Phrase getTextCell(String data) {
		Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);
		return new Phrase(data, font);
	}

}
