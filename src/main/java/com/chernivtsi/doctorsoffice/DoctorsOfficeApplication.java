package com.chernivtsi.doctorsoffice;

import com.chernivtsi.doctorsoffice.controller.FileController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DoctorsOfficeApplication {

	public static void main(String[] args) {
		File directory = new File(FileController.ANALYSES_DIRECTORY);
		if (! directory.exists()){
			directory.mkdirs();
		}
		SpringApplication.run(DoctorsOfficeApplication.class, args);
	}
}
