package in.nareshit.raghu.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nareshit.raghu.entity.Specialization;

public class SpecializationExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
//		1. define your own excel file name
		response.addHeader("Content-Disposition", "attachment;filename=SPECS.xls");
		
//		2. read data given by controller
		@SuppressWarnings("unchecked")
		List<Specialization> specs = (List<Specialization>) model.get("list");
		
//		3. create one sheet
		Sheet sheet = workbook.createSheet("SPECIALIZATION");
		
//		4. create row-0 as header
		setHead(sheet);
		
		setBody(sheet, specs);

	}

	
	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("NAME");
		row.createCell(3).setCellValue("NOTE");
		
	}
	
	private void setBody(Sheet sheet, List<Specialization> specs) {
		int rowNum = 1;
		for(Specialization spec : specs) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(spec.getId());
			row.createCell(1).setCellValue(spec.getSpecCode());
			row.createCell(2).setCellValue(spec.getSpecName());
			row.createCell(3).setCellValue(spec.getSpecNote());
		}
		
	}


}
