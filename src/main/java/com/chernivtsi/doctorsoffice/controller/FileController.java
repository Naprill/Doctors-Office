package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
	public String uploadFiles(@RequestParam("uploadingFiles") List<MultipartFile> uploadingFiles,
	                          @AuthenticationPrincipal SecurityUser currentUser)
			throws IOException {
		log.info("uploadFiles: files count: {}, User id: {}", uploadingFiles.size(), currentUser.getId());
		Long entireSize = 0L;
		for (MultipartFile uploadedFile : uploadingFiles) {
			String path = ANALYSES_DIRECTORY + currentUser.getId() +
					System.getProperty("file.separator") + uploadedFile.getOriginalFilename();
			File file = new File(path);
			uploadedFile.transferTo(file);
			entireSize += uploadedFile.getSize();
			userService.addAnalysisFile(currentUser.getId(), path, uploadedFile.getOriginalFilename());
		}
		log.info("uploaded files with entire size: {}", entireSize);
		return "redirect:/profile";
	}

}
