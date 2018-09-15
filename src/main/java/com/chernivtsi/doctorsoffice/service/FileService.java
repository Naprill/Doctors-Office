package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.controller.FileController;
import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileService {

	private UserService userService;

	public FileService(UserService userService) {
		this.userService = userService;
	}

	public List<AnalysisDTO> uploadFiles(List<MultipartFile> uploadingFiles, Long userId) throws IOException{

		log.info("uploadFiles: files count: {}, User id: {}", uploadingFiles.size(), userId);
		Long entireSize = 0L;
		List<AnalysisDTO> analysisDTOList = new ArrayList<>();
		for (MultipartFile uploadedFile : uploadingFiles) {
			String path = FileController.ANALYSES_DIRECTORY + userId +
					System.getProperty("file.separator") + uploadedFile.getOriginalFilename();
			File file = new File(path);
			uploadedFile.transferTo(file);
			entireSize += uploadedFile.getSize();
			AnalysisDTO analysis = userService.addAnalysisFile(userId, path, uploadedFile.getOriginalFilename());
			//create url for file downloading
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/downloadFile/")
					.path(StringUtils.cleanPath(uploadedFile.getOriginalFilename()))
					.toUriString();

			analysis.setFileDownloadUri(fileDownloadUri);
			analysisDTOList.add(analysis);
		}
		log.info("uploaded files with entire size: {}", entireSize);
		return analysisDTOList;
	}
}
