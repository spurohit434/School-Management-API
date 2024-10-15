package com.wg.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wg.model.CourseMarks;
import com.wg.services.CourseMarksService;

@RestController
public class CourseMarksController {
	private CourseMarksService courseMarksService;
	Scanner scanner = new Scanner(System.in);

	public CourseMarksController() {

	}

	@Autowired
	public CourseMarksController(CourseMarksService courseMarksService) {
		this.courseMarksService = courseMarksService;
	}

	@PostMapping("user/{userId}/marks")
	public void addMarks(@RequestBody CourseMarks courseMarks) {
		double marks = courseMarks.getMarks();
		if (marks > 100 || marks < 0) {
			System.out.println("Enter valid marks");
			return;
		}
		courseMarksService.addMarks(courseMarks);
	}

	@GetMapping("/user/{userId}/marks")
	public List<CourseMarks> checkMarks(@PathVariable String userId) {
		return courseMarksService.checkMarks(userId);
	}
}