package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Admin;
import com.chernivtsi.doctorsoffice.repository.AdminRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;

import java.util.Optional;

public class AdminService extends DefaultCrudSupport<Admin>{
	private AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository){
		super(adminRepository);
		this.adminRepository = adminRepository;
	}

	public Optional<Admin> findAdminByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
}
