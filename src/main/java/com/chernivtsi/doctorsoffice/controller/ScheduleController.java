package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.dto.ReceptionDTO;
import com.chernivtsi.doctorsoffice.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/schedule")
public class ScheduleController {

	private ScheduleService scheduleService;

	@Autowired
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@GetMapping
	public ModelAndView getReceptions(@PageableDefault(size = 4) Pageable pageRequest) {
		ModelAndView modelAndView = new ModelAndView("schedule");

		scheduleService.checkAndGenerateReceptions();
		Page<ReceptionDTO> receptionDTOs = scheduleService.getReceptionsByDate(pageRequest, LocalDate.now());
		modelAndView.addObject("receptions", receptionDTOs);
		modelAndView.addObject("pageRequest", pageRequest);

		return modelAndView;
	}
}
