package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {
	Optional<Admin> findByEmail(String email);

	@Query("select a from Admin a where a.onDuty = true") //TODO is not working
	Optional<Admin> findAdminOnDuty();

	@Query(value = "SELECT * FROM Admin ORDER BY id ASC LIMIT 1", nativeQuery = true)
	Admin findAny();
}
