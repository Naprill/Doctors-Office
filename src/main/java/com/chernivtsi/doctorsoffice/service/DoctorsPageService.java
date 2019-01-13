package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Chapter;
import com.chernivtsi.doctorsoffice.repository.DoctorPageRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class DoctorsPageService extends DefaultCrudSupport<Chapter> {

	private DoctorPageRepository repository;

	public DoctorsPageService(DoctorPageRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
