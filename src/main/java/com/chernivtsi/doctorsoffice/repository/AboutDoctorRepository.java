package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Chapter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutDoctorRepository extends CrudRepository<Chapter, Long> {
}