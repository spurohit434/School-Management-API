package com.wg.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wg.model.Attendance;
import com.wg.services.AttendanceServices;

@RestController
public class AttendanceController {
	private AttendanceServices attendanceService;
	Scanner scanner = new Scanner(System.in);

	@Autowired
	public AttendanceController(AttendanceServices attendanceService) {
		this.attendanceService = attendanceService;
	}

	public AttendanceController() {
	}

	@PostMapping("/user/{id}/attendance")
	public boolean addAttendance(@RequestBody Attendance attendance) {
		return attendanceService.addAttendance(attendance);
	}

	@GetMapping("/attendance/{standard}")
	public List<Attendance> viewAttendanceByStandard(@PathVariable int standard) {
		List<Attendance> list = null;
		list = attendanceService.viewAttendanceByStandard(standard);
		return list;
	}

	@GetMapping("/user/{studentId}/attendance")
	public List<Attendance> viewAttendanceById(@PathVariable String studentId) {
		List<Attendance> list = null;
		list = attendanceService.viewAttendanceById(studentId);
		return list;
	}
}
