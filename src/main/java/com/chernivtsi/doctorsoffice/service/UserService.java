package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.controller.FileController;
import com.chernivtsi.doctorsoffice.model.Analysis;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.dto.AddressDTO;
import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserImmutableProfileDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserListDTO;
import com.chernivtsi.doctorsoffice.model.dto.UserUpdatableProfileDTO;
import com.chernivtsi.doctorsoffice.repository.UserRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends DefaultCrudSupport<User> {

	private UserRepository userRepository;
	private AnalysesService analysesService;

	public UserService(UserRepository repository, AnalysesService analysesService) {
		super(repository);
		this.userRepository = repository;
		this.analysesService = analysesService;
	}

	public Optional<User> findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUserEnabled(final boolean enabled, final Long userId) {
		userRepository.updateUserEnabled(enabled, userId);
	}

	public UserUpdatableProfileDTO getUserUpdatableProfileDTOById(Long userId) {
		User user = this.findById(userId).orElseThrow(EntityNotFoundException::new);
		log.trace("getUserUpdatableProfileDTOById: {}", user);
		return new UserUpdatableProfileDTO(user);
	}

	public UserImmutableProfileDTO getUserImmutableProfileDTOById(Long userId) {
		User user = this.findById(userId).orElseThrow(EntityNotFoundException::new);
		log.trace("getUserImmutableProfileDTOById: {}", user);
		return new UserImmutableProfileDTO(user);
	}

	public void updateUserProfile(UserUpdatableProfileDTO dto) {
		User user = this.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
		dto.getAddress().setPatient(user.getId());
		user.setAddress(AddressDTO.dtoToEntity(dto.getAddress(), user));
		user.setTelephone(dto.getTelephone());
		user.setEmail(dto.getEmail());
		log.trace("updateUserProfile: {}", user);
		update(user);
	}

	public void createAnalysesDirectory(Long id) {
		File directory = new File(FileController.ANALYSES_DIRECTORY + id);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public AnalysisDTO addAnalysisFile(Long id, String path, String originalFilename, String fileDownloadUrl) {
		User user = this.findById(id).orElseThrow(EntityNotFoundException::new);
		if (user.getAnalyses().stream().noneMatch(a -> a.getFileName().equals(originalFilename))) {
			Analysis analysis = new Analysis(path, originalFilename, LocalDateTime.now(), user, fileDownloadUrl);
			analysesService.create(analysis);
			return new AnalysisDTO(analysis);
		}
		return null;
	}

	public List<AnalysisDTO> getUserFiles(Long id) {
		User user = this.findById(id).orElseThrow(EntityNotFoundException::new);
		return user.getAnalyses().stream().map(AnalysisDTO::new).collect(Collectors.toList());
	}

	public Page<UserListDTO> findAll(Pageable pageable) {
		return userRepository.findAll(pageable).map(UserListDTO::new);
	}

	public void updatePassword(String updatedPassword, Long id) {
		userRepository.updatePassword(updatedPassword, id);
	}
}
