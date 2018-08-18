package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Admin;
import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.Message;
import com.chernivtsi.doctorsoffice.model.Role;
import com.chernivtsi.doctorsoffice.model.dto.CancelReceptionDTO;
import com.chernivtsi.doctorsoffice.model.dto.ReceptionDTO;
import com.chernivtsi.doctorsoffice.model.dto.RegisterReceptionDTO;
import com.chernivtsi.doctorsoffice.repository.MessageRepository;
import com.chernivtsi.doctorsoffice.security.SecurityUser;
import com.chernivtsi.doctorsoffice.service.AdminService;
import com.chernivtsi.doctorsoffice.service.ScheduleService;
import com.chernivtsi.doctorsoffice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private MessageRepository messageRepository;
	private AdminService adminService;

	@Autowired
	public ScheduleController(ScheduleService scheduleService,
	                          UserService userService,
	                          MessageRepository messageRepository,
	                          AdminService adminService) {
		this.scheduleService = scheduleService;
		this.userService = userService;
		this.messageRepository = messageRepository;
		this.adminService = adminService;
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

	@GetMapping("/{id}")
	public ResponseEntity<ReceptionDTO> getReceptionItem(@PathVariable Long id) {
		log.trace("Get reception item to view with id {}", id);
		ReceptionDTO receptionDTO = scheduleService.getReceptionDtoById(id);
		return new ResponseEntity<>(receptionDTO, HttpStatus.OK);
	}

	@ResponseBody
	@PutMapping
	public ResponseEntity registerReception(@RequestBody RegisterReceptionDTO dto,
	                                        @AuthenticationPrincipal SecurityUser currentUser) {
		Long userId = currentUser.getId();
		if (dto.getUserId() == null) dto.setUserId(userId);
		scheduleService.registerReception(dto);
		log.trace("RegisterReceptionDTO: {}", dto.toString());
		return new ResponseEntity(HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping
	public ResponseEntity cancelReception(@RequestBody CancelReceptionDTO dto,
	                                      @AuthenticationPrincipal SecurityUser currentUser) {
		Admin admin = adminService.getAdminOnDuty();
		Message message;
		if (currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			message = new Message(admin.getId(), dto.getUserId(), dto.getMessage(), Role.ADMIN, Role.USER);
		} else {
			message = new Message(dto.getUserId(), admin.getId(), dto.getMessage(), Role.USER, Role.ADMIN);
		}
		messageRepository.save(message);
		scheduleService.cancelReception(dto);
		log.trace("CancelReceptionDTO: {}", dto.toString());
		return new ResponseEntity(HttpStatus.OK);
	}
}
