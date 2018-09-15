package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Analysis;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AnalysisDTO {

	private String fileDownloadUri;

	private String fileName;

	private LocalDate date;

	public AnalysisDTO(Analysis analysis) {
		this.fileName = analysis.getFileName();
		this.date = analysis.getDate();
	}
}
