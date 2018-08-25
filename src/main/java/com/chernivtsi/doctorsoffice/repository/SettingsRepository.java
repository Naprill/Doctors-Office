package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.ScheduleSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends CrudRepository<ScheduleSettings, Long> {

	@Query("select s from ScheduleSettings s where s.id = 1")
	ScheduleSettings getSettings();
}
