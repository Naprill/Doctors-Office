package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.dto.ReceptionDTO;
import com.chernivtsi.doctorsoffice.service.ScheduleService;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.EnumSet;

@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/schedule")
public class ScheduleController {

	private ScheduleService scheduleService;
	private UserService userService;

	@Autowired
	public ScheduleController(ScheduleService scheduleService, UserService userService) {
		this.scheduleService = scheduleService;
		this.userService = userService;
	}

	@GetMapping
	public ModelAndView getReceptions(@PageableDefault(size = 4) Pageable pageRequest) {
		ModelAndView modelAndView = new ModelAndView("schedule");

		scheduleService.checkAndGenerateReceptions();
		Page<ReceptionDTO> receptionDTOs =
				scheduleService.getReceptionsByDateIntervalAndUser(pageRequest, LocalDate.now(), null, null);
		modelAndView.addObject("receptions", receptionDTOs);
		modelAndView.addObject("pageRequest", pageRequest);
		modelAndView.addObject("intervals", EnumSet.allOf(Interval.class));
		modelAndView.addObject("users", userService.findAll());

		return modelAndView;
	}

	@GetMapping(value = "filter")
	public ModelAndView getReceptions(
			@PageableDefault(size = 4) Pageable pageRequest,
			@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(name = "interval", required = false) Interval interval,
			@RequestParam(name = "user", required = false) Long userId
	) {
		ModelAndView modelAndView = new ModelAndView("schedule");

		LocalDate newDate = date;
		if (date == null) {
			newDate = LocalDate.now();
		}
		Page<ReceptionDTO> receptionDTOs =
				scheduleService.getReceptionsByDateIntervalAndUser(pageRequest, newDate, interval, userId);
		modelAndView.addObject("receptions", receptionDTOs);
		modelAndView.addObject("pageRequest", pageRequest);
		modelAndView.addObject("intervals", EnumSet.allOf(Interval.class));
		modelAndView.addObject("users", userService.findAll());

		return modelAndView;
	}
}
