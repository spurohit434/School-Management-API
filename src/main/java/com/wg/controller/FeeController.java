package com.wg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wg.dto.FeeResponse;
import com.wg.model.Fee;
import com.wg.services.FeeService;

@RestController
public class FeeController {
	private FeeService feeService;

	public FeeController() {
	}

	@Autowired
	public FeeController(FeeService feeService) {
		this.feeService = feeService;
	}

	@PatchMapping("/user/{id}/fee")
	public void payFees(@PathVariable String id) {
		feeService.payFees(id);
	}

	@GetMapping("/user/{id}/fee")
	public double checkFees(@PathVariable String id) {
		return feeService.checkFees(id);
	}

	@PutMapping("/user/{id}/fee")
	public ResponseEntity<FeeResponse> updateFees(@RequestBody Fee fee, @PathVariable String id) {
		return ResponseEntity.ok(feeService.updateFees(fee, id));
	}

	@PostMapping("/user/{id}/fee")
	public ResponseEntity<FeeResponse> addFees(@RequestBody Fee fee, @PathVariable String id) {
		return ResponseEntity.created(null).body(feeService.addFees(fee, id));
	}

	@GetMapping("user/{id}/fine")
	public double calculateFine(String id) {
		return feeService.calculateFine(id);
	}
}