package in.nareshit.raghu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	@Query("SELECT id, firstName, lastName FROM Doctor")
	public List<Object[]> getDoctorIdAndNames();
}
