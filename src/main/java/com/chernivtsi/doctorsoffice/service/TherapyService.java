package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.TherapyDTO;
import com.chernivtsi.doctorsoffice.repository.TherapyRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TherapyService extends DefaultCrudSupport<Therapy> {
	private TherapyRepository therapyRepository;
	private UserService userService;

	public TherapyService(TherapyRepository therapyRepository,
	                      UserService userService) {
		super(therapyRepository);
		this.therapyRepository = therapyRepository;
		this.userService = userService;
	}

	public void saveTherapy(TherapyDTO therapyDTO) {
		User user = userService.findById(therapyDTO.getPatient()).orElseThrow(EntityNotFoundException::new);
		Therapy therapy = therapyDTO.toEntity(user);
		therapyRepository.save(therapy);
		user.getTherapies().add(therapy);
	}

	public TherapyDTO getTherapyById(Long id) {
		Therapy therapy = therapyRepository.findOne(id);
		return new TherapyDTO(therapy);
	}

	public void updateTherapy(TherapyDTO therapy) {
		TherapyDTO therapyOriginal = getTherapyById(therapy.getId());
		therapyOriginal.setText(therapy.getText());
		log.trace("updateTherapy(): {}", therapy);
		User user = userService.findById(therapyOriginal.getPatient()).orElseThrow(EntityNotFoundException::new);
		this.update(therapyOriginal.toEntity(user));
	}

	public List<TherapyDTO> getTherapiesByPatient(Long userId) {
		User patient = userService.findById(userId).orElseThrow(EntityNotFoundException::new);
		return therapyRepository.getAllByPatientOrderByCreatedAtDesc(patient)
				.stream()
				.map(TherapyDTO::new)
				.collect(Collectors.toList());
	}
}
