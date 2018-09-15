package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
	                                             HttpServletRequest request,
	                                             @AuthenticationPrincipal SecurityUser currentUser) {
		// Load file as Resource
		Resource resource = fileService.loadFileAsResource(fileName,currentUser.getId());

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
