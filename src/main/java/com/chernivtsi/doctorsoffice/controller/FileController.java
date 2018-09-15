package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@PreAuthorize("isAuthenticated()")
@Controller
public class FileController {

	public static final String ANALYSES_DIRECTORY = System.getProperty("user.dir") +
			System.getProperty("file.separator") + "analyses" + System.getProperty("file.separator");

	private FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/uploadFiles")
	public ResponseEntity<List<AnalysisDTO>> uploadFiles(
			@RequestParam("uploadingFiles") List<MultipartFile> uploadingFiles,
			@AuthenticationPrincipal SecurityUser currentUser) throws IOException{

		List<AnalysisDTO> analysisDTOList = fileService.uploadFiles(uploadingFiles, currentUser.getId());
		return new ResponseEntity<>(analysisDTOList, HttpStatus.OK);
	}

}
