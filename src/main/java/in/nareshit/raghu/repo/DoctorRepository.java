package in.nareshit.raghu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
