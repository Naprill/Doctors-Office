package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Admin;
import com.chernivtsi.doctorsoffice.repository.AdminRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AdminService extends DefaultCrudSupport<Admin> {

	private AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		super(adminRepository);
		this.adminRepository = adminRepository;
	}

	public Optional<Admin> findAdminByEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	public Admin getAdminOnDuty() {
		return adminRepository.findAdminOnDuty().orElse(adminRepository.findAny());
	}
}
