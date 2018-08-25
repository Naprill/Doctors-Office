package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.ScheduleSettings;
import com.chernivtsi.doctorsoffice.repository.SettingsRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class SettingsService extends DefaultCrudSupport<ScheduleSettings>{
	private SettingsRepository settingsRepository;

	public SettingsService(SettingsRepository settingsRepository) {
		super(settingsRepository);
		this.settingsRepository = settingsRepository;
	}

	public ScheduleSettings getSettings() {
		return settingsRepository.getSettings();
	}
}
