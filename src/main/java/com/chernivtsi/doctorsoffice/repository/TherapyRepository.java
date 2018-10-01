package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Therapy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TherapyRepository extends CrudRepository<Therapy, Long> {
}
