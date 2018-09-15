package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PreAuthorize("isAuthenticated()")
@Controller
public class FileController {

	public static final String ANALYSES_DIRECTORY = System.getProperty("user.dir") +
			System.getProperty("file.separator") + "analyses" + System.getProperty("file.separator");

	private UserService userService;

	public FileController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/uploadFiles")
	public ResponseEntity<List<AnalysisDTO>> uploadFiles(@RequestParam("uploadingFiles") List<MultipartFile> uploadingFiles,
	                                                    @AuthenticationPrincipal SecurityUser currentUser)
			throws IOException {
		log.info("uploadFiles: files count: {}, User id: {}", uploadingFiles.size(), currentUser.getId());
		Long entireSize = 0L;
		List<AnalysisDTO> analysisDTOList = new ArrayList<>();
		for (MultipartFile uploadedFile : uploadingFiles) {
			String path = ANALYSES_DIRECTORY + currentUser.getId() +
					System.getProperty("file.separator") + uploadedFile.getOriginalFilename();
			File file = new File(path);
			uploadedFile.transferTo(file);
			entireSize += uploadedFile.getSize();
			AnalysisDTO analysis = userService.addAnalysisFile(currentUser.getId(), path, uploadedFile.getOriginalFilename());
			//create url for file downloading
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadFile/")
					.path(StringUtils.cleanPath(uploadedFile.getOriginalFilename()))
					.toUriString();

			analysis.setFileDownloadUri(fileDownloadUri);
			analysisDTOList.add(analysis);
		}
		log.info("uploaded files with entire size: {}", entireSize);
		return new ResponseEntity<>(analysisDTOList, HttpStatus.OK);
	}

}
