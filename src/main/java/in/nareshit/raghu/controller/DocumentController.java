package in.nareshit.raghu.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.nareshit.raghu.entity.Document;
import in.nareshit.raghu.service.IDocumentService;

@Controller
@RequestMapping("/doc")
public class DocumentController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	private IDocumentService service;

	//1. show Documents page
	@GetMapping("/all")
	public String showDocs(Model model) {
		model.addAttribute("idVal", System.currentTimeMillis());
		List<Object[]>  list = service.getDocumentIdAndName();
		model.addAttribute("list", list);
		return "Documents";
	}
	
	//2. upload document
	@PostMapping("/upload")
	public String uploadDoc(
			@RequestParam Long docId,
			@RequestParam MultipartFile docOb 
			) 
	{
		try {
			Document doc = new Document();
			doc.setDocId(docId);
			doc.setDocName(docOb.getOriginalFilename());
			doc.setDocData(docOb.getBytes());
			service.saveDocument(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// response.sendRedirect('all');
		return "redirect:all";
	}
	
	//3. download by id
	@GetMapping("/download")
	public void downloadDoc(
			@RequestParam Long id,
			HttpServletResponse response
			) 
	{
		
		try {
			//1. fetch DB object
			Document doc = service.getDocumentById(id);
			
			//2. provide file name using header
			response.setHeader(
					"Content-Disposition", 
					"attachment;filename="+doc.getDocName());
			
			//3. copy data from Doc to Response object
			//from -- to
			FileCopyUtils.copy(doc.getDocData(), response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//4. delete by id
	@GetMapping("/delete")
	public String deleteDoc(
			@RequestParam Long id
			) 
	{
		try {
			service.deleteDocumentById(id);
		} catch (RuntimeException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return "redirect:all";
	}
}
