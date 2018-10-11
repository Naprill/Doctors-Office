package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapyRepository extends CrudRepository<Therapy, Long> {

	List<Therapy> getAllByPatientOrderByCreatedAtDesc(User patient);
}
