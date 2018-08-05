package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Reception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends CrudRepository<Reception,Long>{

	List<Reception> getReceptionsByDate(LocalDate date);

	Page<Reception> getReceptionsByDate(Pageable pageable, LocalDate date);

}