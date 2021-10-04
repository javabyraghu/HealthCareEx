package in.nareshit.raghu.controller;

import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.service.IPatientService;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author:RAGHU SIR 
 *  Generated F/w:SHWR-Framework 
 */
@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private IPatientService service;

	@GetMapping("/register")
	public String registerPatient(Model model) {
		model.addAttribute("patient",new Patient());
		return "PatientRegister";
	}

	@PostMapping("/save")
	public String savePatient(@ModelAttribute Patient patient, Model model) {
		java.lang.Long id=service.savePatient(patient);
		model.addAttribute("message","Patient created with Id:"+id);
		model.addAttribute("patient",new Patient()) ;
		return "PatientRegister";
	}

	@GetMapping("/all")
	public String getAllPatients(Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Patient> list=service.getAllPatients();
		model.addAttribute("list",list);
		model.addAttribute("message",message);
		return "PatientData";
	}

	@GetMapping("/delete")
	public String deletePatient(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.deletePatient(id);
			attributes.addAttribute("message","Patient deleted with Id:"+id);
		} catch(PatientNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}

	@GetMapping("/edit")
	public String editPatient(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Patient ob=service.getOnePatient(id);
			model.addAttribute("patient",ob);
			page="PatientEdit";
		} catch(PatientNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
			page="redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updatePatient(@ModelAttribute Patient patient, RedirectAttributes attributes) {
		service.updatePatient(patient);
		attributes.addAttribute("message","Patient updated");
		return "redirect:all";
	}
}
