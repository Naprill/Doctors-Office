package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Chapter;
import com.chernivtsi.doctorsoffice.service.AboutDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/about-doctor")
public class AboutDoctorController {

	private static final String REDIRECT_ABOUT_DOCTOR = "redirect:/about-doctor";
	private static final String STATUS = "status";
	private AboutDoctorService service;

	public AboutDoctorController(AboutDoctorService service) {
		this.service = service;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public String createChapter(@ModelAttribute @Valid Chapter chapter,
	                            BindingResult result,
	                            RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(STATUS, "failure");
			return REDIRECT_ABOUT_DOCTOR;
		} else {
			service.create(chapter);
			log.trace("createChapter(): {}", chapter);
			redirectAttributes.addFlashAttribute(STATUS, "success");
			return REDIRECT_ABOUT_DOCTOR;
		}
	}

	@GetMapping
	public ModelAndView getChapters() {
		List<Chapter> chapters = service.findAll();
		log.trace("Chapters: {}", chapters);
		ModelAndView modelAndView = new ModelAndView("about-doctor");
		modelAndView.addObject("chapters", chapters);
		return modelAndView;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Chapter> getChapter(@PathVariable Long id) {
		Chapter chapter = service.findById(id).orElseThrow(EntityNotFoundException::new);
		log.trace("Chapter: {}", chapter);
		return new ResponseEntity<>(chapter, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/update")
	public String updateChapter(@ModelAttribute @Valid Chapter chapter,
	                            BindingResult result,
	                            RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addAttribute(STATUS, "failure");
			return REDIRECT_ABOUT_DOCTOR;
		} else {
			service.update(chapter);
			log.trace("updateChapter(): {}", chapter);
			redirectAttributes.addAttribute(STATUS, "success");
			return REDIRECT_ABOUT_DOCTOR;
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public @ResponseBody
	String deleteChapter(@PathVariable Long id) {
		service.delete(id);
		return REDIRECT_ABOUT_DOCTOR;
	}
}
