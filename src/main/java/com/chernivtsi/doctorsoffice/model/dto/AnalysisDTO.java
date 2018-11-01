package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Analysis;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AnalysisDTO {

	private String fileDownloadUri;

	private String pathToFile;

	private String fileName;

	private LocalDateTime date;

	public AnalysisDTO(Analysis analysis) {
		this.pathToFile = analysis.getPathToFile();
		this.fileName = analysis.getFileName();
		this.date = analysis.getDate();
		this.fileDownloadUri = analysis.getFileDownloadUrl();
	}
}
