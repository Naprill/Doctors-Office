package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.Reception;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

	private static final Float WORK_BEGIN = 9f;
	private static final Float WORK_END = 15f;
	private static final Float RECEPTION_TIME_RANGE = 15f;

	private ScheduleRepository repository;

	public ScheduleService(ScheduleRepository repository) {
		this.repository = repository;
	}

	public void checkAndGenerateReceptions() {
		List<ReceptionDTO> list = getReceptionsByDate(LocalDate.now());
		if (list.isEmpty()) {
			int count = (int) ((WORK_END - WORK_BEGIN) * 60 / RECEPTION_TIME_RANGE);

			LocalTime intervalStart = LocalTime.of(
					WORK_BEGIN.intValue(),
					0);

			LocalTime intervalEnd = intervalStart.plusMinutes(RECEPTION_TIME_RANGE.longValue());

			while (count > 0) {
				saveReception(LocalDate.now(), intervalStart, intervalEnd, Interval.FREE);
				intervalStart = intervalEnd;
				intervalEnd = intervalEnd.plusMinutes(RECEPTION_TIME_RANGE.longValue());
				count--;
			}
		}

	}

	private void saveReception(LocalDate date, LocalTime intervalStart, LocalTime intervalEnd, Interval interval) {
		repository.save(new Reception(date, intervalStart, intervalEnd, interval));
	}

	public List<ReceptionDTO> getReceptionsByDate(LocalDate date) {
		return convertToDTO(repository.getReceptionsByDateOrderByIntervalStartAsc(date));
	}

	public Page<ReceptionDTO> getReceptionsByDateIntervalAndUser(Pageable pageable, LocalDate date, Interval interval, Long userId) {
		Page<Reception> page = repository.getReceptionsByDateIntervalAndUser(pageable, date, interval, userId);
		return new PageImpl<>(convertToDTO(page.getContent()), pageable, page.getTotalElements());
	}

	private List<ReceptionDTO> convertToDTO(List<Reception> receptions) {
		return receptions.stream().map(ReceptionDTO::convertToDto).collect(Collectors.toList());
	}

	public ReceptionDTO getReceptionDtoById(Long id) {
		return ReceptionDTO.convertToDto(repository.getReceptionById(id));
	}

	@Transactional
	public void registerReception(RegisterReceptionDTO dto) {
		repository.registerReception(dto.getId(), dto.getUserId());
	}
}
