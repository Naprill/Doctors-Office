package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.TherapyDTO;
import com.chernivtsi.doctorsoffice.repository.TherapyRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TherapyService {
	private TherapyRepository therapyRepository;
	private UserService userService;

	public TherapyService(TherapyRepository therapyRepository,
	                      UserService userService) {
		this.therapyRepository = therapyRepository;
		this.userService = userService;
	}

	public void saveTherapy(TherapyDTO therapyDTO) {
		User user = userService.findById(therapyDTO.getPatient()).orElseThrow(EntityNotFoundException::new);
		Therapy therapy = therapyDTO.toEntity(user);
		therapyRepository.save(therapy);
		user.getTherapies().add(therapy);
	}
}
