package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.Reception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends CrudRepository<Reception, Long> {

	List<Reception> getReceptionsByDate(LocalDate date);

	@Query("select r from Reception r where r.date = :date " +
			"and (:interval is null or r.interval = :interval) " +
			"and (:userId is null or r.user.id = :userId) ")
	Page<Reception> getReceptionsByDateIntervalAndUser(
			Pageable pageable,
			@Param("date") LocalDate date,
			@Param("interval") Interval interval,
			@Param("userId") Long userId);

}