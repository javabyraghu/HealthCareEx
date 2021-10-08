package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.repo.PatientRepository;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.util.UserUtil;

/**
 * @author:RAGHU SIR 
 *  Generated F/w:SHWR-Framework 
 */
@Service
public class PatientServiceImpl implements IPatientService {
	@Autowired
	private PatientRepository repo;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserUtil util;

	@Override
	@Transactional
	public Long savePatient(Patient patient) {
		Long id = repo.save(patient).getId();
		if(id!=null) {
			User user = new User();
			user.setDisplayName(patient.getFirstName()+" "+patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.PATIENT.name());
			userService.saveUser(user);
			// TODO : Email part is pending
		}
		return id;
	}

	@Override
	@Transactional
	public void updatePatient(Patient patient) {
		repo.save(patient);
	}

	@Override
	@Transactional
	public void deletePatient(Long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(
			readOnly = true
			)
	public Patient getOnePatient(Long id) {
		return repo.findById(id).get();
	}

	@Override
	@Transactional(
			readOnly = true
			)
	public List<Patient> getAllPatients() {
		return repo.findAll();
	}
}
