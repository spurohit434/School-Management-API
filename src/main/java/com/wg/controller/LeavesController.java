package com.wg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wg.dto.ApiResponseHandler;
import com.wg.model.Leaves;
import com.wg.model.StatusResponse;
import com.wg.services.LeavesService;

@RestController
public class LeavesController {
	private LeavesService leavesService;

	@Autowired
	public LeavesController(LeavesService leavesService) {
		this.leavesService = leavesService;
	}

	public LeavesController() {
	}

	@PutMapping("/user/{id}/leave/approve")
	public void approveLeave(@PathVariable String id) {
		leavesService.approveLeave(id);
	}

	@PutMapping("/user/{id}/leave/reject")
	public void rejectLeave(@PathVariable String id) {
		leavesService.rejectLeave(id);
	}

	@PostMapping("/user/{id}/leave")
	public ResponseEntity<Object> applyLeave(@RequestBody Leaves leave) {
		leavesService.applyLeave(leave);
		return ApiResponseHandler.apiResponseHandler("Leaves applied Successfully", StatusResponse.Success,
				HttpStatus.OK, leave);
	}

	@GetMapping("/leave")
	public ResponseEntity<Object> viewAllLeave() {
		List<Leaves> leaves = leavesService.viewAllLeave();
		return ApiResponseHandler.apiResponseHandler("Leaves Fetched Successfully", StatusResponse.Success,
				HttpStatus.OK, leaves);
	}

	@GetMapping("/user/{id}/leave")
	public ResponseEntity<Object> checkLeaveStatus(@PathVariable String id) {
		List<Leaves> leaves = leavesService.checkLeaveStatus(id);
		return ApiResponseHandler.apiResponseHandler("Leaves Fetched Successfully", StatusResponse.Success,
				HttpStatus.OK, leaves);
	}
}