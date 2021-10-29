package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Appointment;

/**
 * @author:RAGHU SIR 
 *  Generated F/w:SHWR-Framework 
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.fee FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.id=:docId")
	public List<Object[]> getAppoinmentsByDoctor(Long docId);
	
	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.fee, aptm.details FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.email=:userName")
	public List<Object[]> getAppoinmentsByDoctorEmail(String userName);
}
