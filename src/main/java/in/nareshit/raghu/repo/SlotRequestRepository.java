package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.SlotRequest;

public interface SlotRequestRepository extends JpaRepository<SlotRequest, Long> {

	@Modifying
	@Query("UPDATE SlotRequest SET status=:status WHERE  id=:id")
	void updateSlotRequestStatus(Long id, String status);
	
	@Query("SELECT sr FROM SlotRequest sr INNER JOIN  sr.patient as patient WHERE patient.email=:patientMail")
	List<SlotRequest> getAllPatientSlots(String patientMail);
}
