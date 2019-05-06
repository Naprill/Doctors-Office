package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Reception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ScheduleRepositoryCustom {
	Page<Reception> getReceptionsByDateAndUser(Pageable pageable, LocalDate date, Long userId);
}
