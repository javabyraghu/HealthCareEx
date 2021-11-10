package in.nareshit.raghu.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.nareshit.raghu.constants.SlotStatus;
import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.SlotRequest;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.ISlotRequestService;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.util.AdminDashboardUtil;
import in.nareshit.raghu.view.InvoiceSlipPdfView;

@Controller
@RequestMapping("/slots")
public class SlotRequestController {

	@Autowired
	private ISlotRequestService service;

	@Autowired
	private IAppointmentService appointmentService;

	@Autowired
	private IPatientService patientService;
	
	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private ISpecializationService specializationService;
	
	@Autowired
	private AdminDashboardUtil util;
	
	@Autowired
	private ServletContext context;
	

	// patient id, appointment id
	@GetMapping("/book")
	public String bookSlot(
			@RequestParam Long appid,
			HttpSession session,
			Model model
			) 
	{
		Appointment app = appointmentService.getOneAppointment(appid);

		//for patient object
		User user = (User) session.getAttribute("userOb");
		String email = user.getUsername();
		Patient patient = patientService.getOneByEmail(email);

		// create slot object
		SlotRequest sr = new SlotRequest();
		sr.setAppointment(app);
		sr.setPatient(patient);
		sr.setStatus(SlotStatus.PENDING.name());
		try {

			service.saveSlotRequest(sr);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			String appDte = sdf.format( app.getDate());

			String message = " Patient " + (patient.getFirstName()+" "+patient.getLastName())
					+", Request for Dr. " + app.getDoctor().getFirstName() +" "+app.getDoctor().getLastName()
					+", On Date : " + appDte +", submitted with status: "+sr.getStatus();

			model.addAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "BOOKING REQUEST ALREADY MADE FOR THIS APPOINTMENT/DATE");
		}

		return "SlotRequestMesage";
	}

	@GetMapping("/all")
	public String viewAllReq(Model model) {
		List<SlotRequest> list = service.getAllSlotRequests();
		model.addAttribute("list", list);
		return "SlotRequestData";
	}
	
	@GetMapping("/patient")
	public String viewMyReqPatient(
			Principal principal,
			Model model) 
	{
		String email = principal.getName();
		List<SlotRequest> list = service.viewSlotsByPatientMail(email);
		model.addAttribute("list", list);
		return "SlotRequestDataPatient";
	}
	
	@GetMapping("/doctor")
	public String viewMyReqDoc(
			Principal principal,
			Model model) 
	{
		String email = principal.getName();
		List<SlotRequest> list = service.viewSlotsByDoctorMail(email);
		model.addAttribute("list", list);
		return "SlotRequestDataDoctor";
	}
	
	@GetMapping("/accept")
	public String updateSlotAccept(
			@RequestParam Long id
			) 
	{
		service.updateSlotRequestStatus(id, SlotStatus.ACCEPTED.name());
		SlotRequest sr = service.getOneSlotRequest(id);
		if(sr.getStatus().equals(SlotStatus.ACCEPTED.name())) {
			appointmentService.updateSlotCountForAppoinment(
					sr.getAppointment().getId(), -1);
		}
		return "redirect:all";
	}
	
	@GetMapping("/reject")
	public String updateSlotReject(
			@RequestParam Long id
			) 
	{
		service.updateSlotRequestStatus(id, SlotStatus.REJECTED.name());
		return "redirect:all";
	}
	
	@GetMapping("/cancel")
	public String cancelSlotReject(
			@RequestParam Long id
			) 
	{
		SlotRequest sr = service.getOneSlotRequest(id);
		if(sr.getStatus().equals(SlotStatus.ACCEPTED.name())) {
			service.updateSlotRequestStatus(id, SlotStatus.CANCELLED.name());
			appointmentService.updateSlotCountForAppoinment(
					sr.getAppointment().getId(), 1);
		}
		return "redirect:patient";
	}

	@GetMapping("/dashboard")
	public String adminDashboard(Model model) 
	{
		model.addAttribute("doctors",doctorService.getDoctorCount());
		model.addAttribute("patients",patientService.getPatientCount());
		model.addAttribute("appointments",appointmentService.getAppointmentCount());
		model.addAttribute("specialization",specializationService.getSpecializationCount());

		String path = context.getRealPath("/"); //root folder
		
		List<Object[]> list = service.getSlotsStatusAndCount();
		util.generateBar(path, list);
		util.generatePie(path, list);
		return "AdminDashboard";
	}

	@GetMapping("/invoice")
	public ModelAndView generateInvoice(
			@RequestParam Long id
			) 
	{
		ModelAndView m = new ModelAndView();
		m.setView(new InvoiceSlipPdfView());
		SlotRequest slotRequest=service.getOneSlotRequest(id);
		m.addObject("slotRequest", slotRequest);
		return m;
	}

}
