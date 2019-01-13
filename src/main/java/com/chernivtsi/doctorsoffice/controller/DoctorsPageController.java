package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Chapter;
import com.chernivtsi.doctorsoffice.service.DoctorsPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/about-doctor")
public class DoctorsPageController {

	private DoctorsPageService service;

	public DoctorsPageController(DoctorsPageService service) {
		this.service = service;
	}

	@GetMapping
	public ModelAndView getChapters() {
		List<Chapter> chapters = service.findAll();
		log.trace("Chapters: {}", chapters);
		ModelAndView modelAndView = new ModelAndView("about-doctor");
		modelAndView.addObject("chapters", chapters);
		return modelAndView;
	}

}
