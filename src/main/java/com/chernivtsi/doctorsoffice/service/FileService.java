package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.controller.FileController;
import com.chernivtsi.doctorsoffice.exception.MyFileNotFoundException;
import com.chernivtsi.doctorsoffice.model.dto.AnalysisDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileService {

	private UserService userService;

	public FileService(UserService userService) {
		this.userService = userService;
	}

	public List<AnalysisDTO> uploadFiles(List<MultipartFile> uploadingFiles, Long userId) throws IOException {

		log.info("uploadFiles: files count: {}, User id: {}", uploadingFiles.size(), userId);
		Long entireSize = 0L;
		List<AnalysisDTO> analysisDTOList = new ArrayList<>();
		for (MultipartFile uploadedFile : uploadingFiles) {
			String path = FileController.ANALYSES_DIRECTORY + userId +
					System.getProperty("file.separator") + uploadedFile.getOriginalFilename();
			File file = new File(path);
			uploadedFile.transferTo(file);
			entireSize += uploadedFile.getSize();
			//create url for file downloading
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/" + userId)
					.path("/downloadFile/")
					.path(StringUtils.cleanPath(uploadedFile.getOriginalFilename()))
					.toUriString();
			AnalysisDTO analysis = userService.addAnalysisFile(
					userId,
					path,
					uploadedFile.getOriginalFilename(),
					fileDownloadUri);
			if (analysis != null) {
				analysisDTOList.add(analysis);
			}
		}
		log.info("uploaded files with entire size: {}", entireSize);
		return analysisDTOList;
	}

	public Resource loadFileAsResource(String fileName, Long userId) {
		try {
			Path fileStorageLocation = Paths.get(FileController.ANALYSES_DIRECTORY + userId)
					.toAbsolutePath().normalize();
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}
}
