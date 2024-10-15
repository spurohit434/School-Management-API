package com.wg.dto;

import java.util.List;

import com.wg.model.Course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkSheetDTO {
	List<Course> courses;
	Double percentage;
	String result;
}
