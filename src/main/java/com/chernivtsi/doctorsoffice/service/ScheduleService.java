package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.Reception;
import com.chernivtsi.doctorsoffice.model.ScheduleSettings;
import com.chernivtsi.doctorsoffice.model.dto.CancelReceptionDTO;
import com.chernivtsi.doctorsoffice.model.dto.ReceptionDTO;
import com.chernivtsi.doctorsoffice.model.dto.RegisterReceptionDTO;
import com.chernivtsi.doctorsoffice.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

	private ScheduleRepository scheduleRepository;
	private SettingsService settingsService;
	private ScheduleSettings scheduleSettings;

	public ScheduleService(ScheduleRepository scheduleRepository, SettingsService settingsService) {
		this.scheduleRepository = scheduleRepository;
		this.settingsService = settingsService;
	}

	public void checkAndGenerateReceptions(LocalDate date) {
		List<Reception> list = getReceptionsByDate(date);
		if (list.isEmpty() || isDayWithoutReceptions(list)) {
			scheduleSettings = settingsService.getSettings();
			scheduleRepository.delete(list);
			List<Reception> receptions = generateReceptions(date);
			receptions.forEach(this::saveReception);
		}
	}

	private boolean isDayWithoutReceptions(List<Reception> list) {
		return list.stream().noneMatch(elem -> elem.getInterval() == Interval.BUSY);
	}

	private List<Reception> generateReceptions(LocalDate date) {
		int count = (int) ((scheduleSettings.getWorkEnd() - scheduleSettings.getWorkStart()) * 60 / scheduleSettings.getReceptionTimeRange());

		LocalTime intervalStart = LocalTime.of(scheduleSettings.getWorkStart().intValue(), 0);
		LocalTime intervalEnd = intervalStart.plusMinutes(scheduleSettings.getReceptionTimeRange().longValue());

		List<Reception> receptionList = new ArrayList<>();
		while (count > 0) {
			receptionList.add(new Reception(date, intervalStart, intervalEnd, Interval.FREE));
			intervalStart = intervalEnd;
			intervalEnd = intervalEnd.plusMinutes(scheduleSettings.getReceptionTimeRange().longValue());
			count--;
		}
		return receptionList;
	}

	private void saveReception(Reception reception) {
		scheduleRepository.save(reception);
	}

	private List<Reception> getReceptionsByDate(LocalDate date) {
		return scheduleRepository.getReceptionsByDateOrderByIntervalStartAsc(date);
	}

	public Page<ReceptionDTO> getReceptionsByDateIntervalAndUser(Pageable pageable, LocalDate date, Interval interval, Long userId) {
		Page<Reception> page = scheduleRepository.getReceptionsByDateIntervalAndUser(pageable, date, interval, userId);
		return new PageImpl<>(convertToDTO(page.getContent()), pageable, page.getTotalElements());
	}

	private List<ReceptionDTO> convertToDTO(List<Reception> receptions) {
		return receptions.stream().map(ReceptionDTO::convertToDto).collect(Collectors.toList());
	}

	public ReceptionDTO getReceptionDtoById(Long id) {
		return ReceptionDTO.convertToDto(scheduleRepository.getReceptionById(id));
	}

	@Transactional
	public void registerReception(RegisterReceptionDTO dto) {
		scheduleRepository.registerReception(dto.getId(), dto.getUserId());
	}

	@Transactional
	public void cancelReception(CancelReceptionDTO dto) {
		scheduleRepository.cancelReception(dto.getId());
	}
}
