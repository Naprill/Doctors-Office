package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.ScheduleSettings;
import com.chernivtsi.doctorsoffice.repository.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
	private SettingsRepository settingsRepository;

	public SettingsService(SettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	public ScheduleSettings getSettings() {
		return settingsRepository.getSettings();
	}
}
