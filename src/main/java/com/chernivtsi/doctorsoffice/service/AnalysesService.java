package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Analysis;
import com.chernivtsi.doctorsoffice.repository.AnalysesRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class AnalysesService extends DefaultCrudSupport<Analysis> {

	public AnalysesService(AnalysesRepository repository) {
		super(repository);
	}
}
