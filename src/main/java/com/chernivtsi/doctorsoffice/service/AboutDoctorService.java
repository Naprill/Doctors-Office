package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Chapter;
import com.chernivtsi.doctorsoffice.repository.AboutDoctorRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class AboutDoctorService extends DefaultCrudSupport<Chapter> {

	private AboutDoctorRepository repository;

	public AboutDoctorService(AboutDoctorRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
